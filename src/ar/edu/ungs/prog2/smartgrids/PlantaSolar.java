package ar.edu.ungs.prog2.smartgrids;

public class PlantaSolar extends Planta{
	private String fuentePrimaria;
	private double eficienciaPromedio;
	private double superficieCubierta;
	private int cantidadPaneles;
	private double horasRadiacionDiaria;

	public PlantaSolar(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio, double superficieCubierta, int cantidadPaneles, double horasRadiacionDiaria) {
		super(nombre, direccion, capacidadMaxima, fechaHoraPuestaEnFuncionamiento);
		this.fuentePrimaria = fuentePrimaria;
		this.eficienciaPromedio = eficienciaPromedio;
		this.superficieCubierta = superficieCubierta;
		this.cantidadPaneles = cantidadPaneles;
		this.horasRadiacionDiaria = horasRadiacionDiaria;
	}
	
	@Override
	public String toString() {
		return super.getNombre();
	}

	@Override
	public double calcularCapacidadMinimaCuota() {
	    return (super.getCapacidadMaxima() * horasRadiacionDiaria * eficienciaPromedio) / 24.0;
	}

    @Override
    public String fuenteRenovable() {
       return fuentePrimaria;
}

}
