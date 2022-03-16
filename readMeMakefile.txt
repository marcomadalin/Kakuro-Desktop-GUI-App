Per tal de compilar el projecte podem utilitzar les comandes:
    $/ make         -- Per compilar tot el projecte
    $/ make Main    -- Per compilar les clases del projecte
    $/ make Drivers -- Per compilar els dominio.tests.drivers
    $/ make Junits  -- Per compilar els Junits

Una vegada compilats podem executar utilitzant les comandes seguents:
    $/ make runM    -- Per executar el programa
    $/ make runP   -- Per executar el driver de Partida
    $/ make runD   -- Per executar el driver de CtrlDominio	
    $/ make runR   -- Per executar el driver de CtrlRepoGlobal
    $/ make runPr   -- Per executar el driver de CtrlPresentacion
    $/ make runJS   -- Per executar els JUnits

Finalment podem eliminar tots els fitxers creats per la compilació i l'execució amb la comanda '$/ make clean'.
