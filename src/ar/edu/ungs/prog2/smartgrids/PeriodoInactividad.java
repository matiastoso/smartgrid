package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class PeriodoInactividad {
    private LocalDateTime inicio; // o LocalDate según prefieras
    private LocalDateTime fin;

    public PeriodoInactividad(LocalDateTime inicio, LocalDateTime fin) {
        this.inicio = inicio;
        this.fin = fin;
    }
    
    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }
    
    public boolean incluye(LocalDateTime fechaHora) {
        return !fechaHora.isBefore(inicio) && !fechaHora.isAfter(fin);
    }

	@Override
	public String toString() {
		return "inicio=" + inicio + ", fin=" + fin + "]";
	}
    
}