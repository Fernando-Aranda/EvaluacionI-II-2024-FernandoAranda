package cl.ucn.modelo;

public class EventoFactory {
	public Evento crearEvento(int Id, String Fecha, String Nombre, String Lugar) {
		Evento evento = new Evento();
		evento.setId(Id);
        evento.setFecha(Fecha);
        evento.setNombre(Nombre);
        evento.setLugar(Lugar);
        return evento;
        
	}
}
