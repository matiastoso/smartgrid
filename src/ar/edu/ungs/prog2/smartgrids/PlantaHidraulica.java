package ar.edu.ungs.prog2.smartgrids;

public class PlantaHidraulica extends Planta {
	private String fuentePrimaria;
	private double eficienciaPromedio;
    private int cantidadTurbinas;
    private double capacidadEmbalse;

	public PlantaHidraulica(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio, int cantidadTurbinas, double capacidadEmbalse) {
		super(nombre, direccion, capacidadMaxima, fechaHoraPuestaEnFuncionamiento);

		this.fuentePrimaria = fuentePrimaria;
		this.eficienciaPromedio = eficienciaPromedio;
		this.cantidadTurbinas = cantidadTurbinas;
		this.capacidadEmbalse = capacidadEmbalse;
	}

	@Override
	public String toString() {
		return super.getNombre();
	}

	@Override
	public double calcularCapacidadMinimaCuota() {
		return (super.getCapacidadMaxima() * eficienciaPromedio);
	}

}
