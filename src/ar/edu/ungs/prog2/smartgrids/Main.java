package ar.edu.ungs.prog2.smartgrids;

public class Main {

	public static void main(String[] args) {
		String INICIO = "2026-10-08T00:00:00";
	    String HORA_MON_1 = "2026-10-08T10:00:00";
	    String HORA_MON_2 = "2026-10-08T11:00:00";
	    String HORA_BAJA = "2026-10-08T12:00:00";
	    
		ISmartGrids sistema = new SmartGrids();
		sistema.registrarPlantaSolar("Solar Norte", "Ruta 1", 1000, INICIO, "Sol", 5.0, 1500, 12, 12);
        sistema.registrarPlantaEolica("Eolica Sur", "Ruta 2", 400, HORA_MON_1, "Viento", 0.5, 4, 80, 5);
        sistema.registrarPlantaHidraulica("Hidraulica Oeste", "Ruta 3", 800, HORA_MON_2, "Aire", 0.25, 10, 10);
		sistema.registrarPlantaTermica("Termica Este", "Ruta 4", 600, HORA_BAJA, "Nose", 10, 10);

		System.out.println(sistema.capacidadMinimaCuota("Termica Este"));
		
		System.out.println("Total generado: "
		        + sistema.totalGenerado("Solar Norte"));

		System.out.println("Plantas renovables por fuente: "
		        + sistema.plantasRenovablesPorFuente());
		
	}
	

}
