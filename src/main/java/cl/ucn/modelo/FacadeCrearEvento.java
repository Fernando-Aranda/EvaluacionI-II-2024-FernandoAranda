package cl.ucn.modelo;

import com.jcabi.log.Logger;

import cl.ucn.main.Main;
import jakarta.persistence.EntityManager;

public class FacadeCrearEvento implements FacadeInterfaz {
	private EntityManager em;
	private int id;
	private String fecha;
	private String nombre;
	private String lugar;

	// Se crea un factory, para crear eventos
	private EventoFactory eventoFactory;

	public FacadeCrearEvento(EntityManager em, int id, String fecha, String nombre, String lugar) {
		super();
		this.em = em;
		this.id = id;
		this.fecha = fecha;
		this.nombre = nombre;
		this.lugar = lugar;
		this.eventoFactory = new EventoFactory();
	}

	private void crearEvento() {
		Evento evento = eventoFactory.crearEvento(id, fecha, nombre, lugar);
		em.getTransaction().begin();
		em.persist(evento);
		em.getTransaction().commit();
		Logger.info(Main.class, "Se ha ingresado el evento N° " + evento.getId() + " Nombre: " + evento.getNombre());

	}

	@Override
	public void ejecutar() {
		crearEvento();

	}

}
