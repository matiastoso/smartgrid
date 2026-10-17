package ar.edu.ungs.prog2.smartgrids;

public class Main {

	public static void main(String[] args) {
		ISmartGrids sistema = new SmartGrids();
		sistema.registrarPlantaSolar("Solar Norte", "Ruta 1", 1000, "INICIO","Sol", 0.8, 1500, 12, 10);
		sistema.registrarPlantaSolar("Solar Sur", "Ruta 1", 1000, "INICIO","Sol", 0.8, 1500, 12, 10);
		sistema.registrarPlantaSolar("Solar Este", "Ruta 1", 1000, "INICIO","Sol", 0.8, 1500, 12, 10);

		sistema.registrarPlantaSolar("Solar Oeste", "Ruta 1", 1000, "INICIO","Sol", 0.8, 1500, 12, 10);

		
		System.out.println(sistema.toString());
	}

}
