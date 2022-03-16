all:
	javac src/*.java
	javac src/dominio/tests/drivers/*.java
	javac src/dominio/clases/*.java
	javac src/dominio/clases/kakuro/*.java
	javac src/dominio/controladores/*.java
	javac -cp lib/junit-4.13.1.jar:. src/dominio/tests/junits/*.java
	javac src/persistencia/controladores/*.java
	javac src/presentacion/controladores/*.java
	javac src/presentacion/vistas/*.java

Main:
	javac src/*.java

Drivers:
	javac src/dominio/tests/drivers/*.java
Junits:
	javac src/dominio/clases/*.java
	javac src/dominio/controladores/*.java
	javac -cp lib/junit-4.13.1.jar:. src/dominio/tests/junits/*.java
runMG:
	java src.Main 0
runMT:
	java src.Main 1
runDP:
	java src.dominio.tests.drivers.DriverPartida
runDD:
	java src.dominio.tests.drivers.DriverCtrlDominio
runDR:
	java src.dominio.tests.drivers.DriverCtrlRankingDomini
runDS:
	java src.dominio.tests.drivers.DriverSortrbyTime
runJ:
	java -cp lib/junit-4.13.1.jar:lib/hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore src.dominio.tests.junits.TestSumCell
	java -cp lib/junit-4.13.1.jar:lib/hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore src.dominio.tests.junits.TestWhiteCell
	java -cp lib/junit-4.13.1.jar:lib/hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore src.dominio.tests.junits.TestKakuro
	java -cp lib/junit-4.13.1.jar:lib/hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore src.dominio.tests.junits.TestCombos
	java -cp lib/junit-4.13.1.jar:lib/hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore src.dominio.tests.junits.TestSolver
	java -cp lib/junit-4.13.1.jar:lib/hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore src.dominio.tests.junits.TestGenerator
	java -cp lib/junit-4.13.1.jar:lib/hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore src.dominio.tests.junits.TestSlotRanking
	

clean:
	rm -f src/*.class
	rm -f src/dominio/clases/*.class
	rm -f src/dominio/clases/kakuro/*.class
	rm -f src/dominio/controladores/*.class
	rm -f src/dominio/tests/drivers/*.class
	rm -f src/dominio/tests/junits/*.class
	rm -f src/persistencia/controladores/*.class
	rm -f src/presentacion/controladores/*.class
	rm -f src/presentacion/vistas/*.class
	
