package ar.edu.ungs.prog2.smartgrids;

public class Main {

	public static void main(String[] args) {
		String INICIO = "2026-10-08T00:00:00";
	    String HORA_MON_1 = "2026-10-08T10:00:00";
	    String HORA_MON_2 = "2026-10-08T11:00:00";
	    String HORA_BAJA = "2026-10-08T12:00:00";
	    
		ISmartGrids sistema = new SmartGrids();
		sistema.registrarPlantaSolar("Solar Norte", "Ruta 1", 1200, INICIO, "Sol", 80.0, 1500, 12, 6.0);
        sistema.registrarPlantaEolica("Eolica Sur", "Ruta 2", 800, HORA_MON_1, "Viento", 0.75, 4, 80, 5);
        sistema.registrarPlantaHidraulica("Hidraulica Oeste", "Ruta 3", 10, HORA_MON_2, "Aire", 10, 10, 10);
		sistema.registrarPlantaTermica("Termica Este", "Ruta 4", 233, HORA_BAJA, "Nose", 10, 10);
		
		System.out.println(sistema.capacidadMinimaCuota("Solar Norte"));
		
		/*
		System.out.println("PLANTAS REGISTRADAS: \n");
		for (String msj : sistema.consultarPlantas()) {
			System.out.println(msj);
		}*/
	}

}
