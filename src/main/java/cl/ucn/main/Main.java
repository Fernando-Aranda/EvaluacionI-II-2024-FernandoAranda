package cl.ucn.main;

import cl.ucn.modelo.*;
import jakarta.persistence.*;
import com.jcabi.log.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;

public class Main {

	private static final EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("ticketmaster");

	private static final org.apache.logging.log4j.Logger fileLogger = LogManager.getLogger(" ");

	public static void main(String[] args) {

		// Cargamos los datos a nuestra base de datos
		EntityManager em = entityManagerFactory.createEntityManager();

		// Creamos el controlador de la Facade
		FacadeInterfaz FacadaControlador;

		// ********* Eliminar un Evento para un Asistente ************
		// ***********************************************************

		// Obtenemos las informaciones del asistente 217638392
		em.getTransaction().begin();
		long rut = 217638392;
		Asistente asistente = em.find(Asistente.class, rut);
		// Creamos la Facade para eliminar eventos y la ejecutamos
		FacadaControlador = new FacadeEliminarEventoParaUnAsistente(asistente, em);
		FacadaControlador.ejecutar();

		// ***********************************************************
		// ***************** Ingresa un nuevo Evento *****************
		// ***********************************************************

		int ultimoEvento = 0;
		Query query = em.createNativeQuery("select max(id) from Evento");
		try {
			ultimoEvento = (int) query.getSingleResult();
		} catch (NoResultException e) {
			Logger.info(Main.class, "No existen resultados");
		}

		int id = ultimoEvento + 1;
		
		// Se crea una Facada para crear los eventos
		FacadaControlador = new FacadeCrearEvento(em, id, "2024-10-25", "Alice in Chains", "Parque Ohiggins");
		FacadaControlador.ejecutar();

		// ***********************************************************
		// ************* Asociar un Evento con Asistente *************
		// ***********************************************************
		em.getTransaction().begin();
		rut = 64389216;
		asistente = em.find(Asistente.class, rut);
		Evento evento2 = em.find(Evento.class, 4);
		
		// Crear Facada para Asociar Evento a un asistente
		FacadaControlador = new FacadeAsociarEventoConAsistente(em, asistente, evento2);
		
		// ***********************************************************
		// ******************* Actualizar un evento ******************
		// ***********************************************************
		// Agregar Observers
		Observer observador = new EventoObserver();
		evento2.agregarObserver(observador);
		em.getTransaction().begin();
		id = 1;
		evento2 = em.find(Evento.class, id);
		evento2.setLugar("Estadio Santa Laura");
		em.merge(evento2);
		em.getTransaction().commit();
		// ***********************************************************

		fileLogger.info("******************************* RESUMEN ******************************* ");
		List<Asistente> asistentes = em.createQuery("from Asistente a", Asistente.class).getResultList();
		for (Asistente asistente2 : asistentes) {
			fileLogger.info("{} , {} , {}", asistente2.getRut(), asistente2.getNombre(), asistente2.getEmail());
			for (Evento evento1 : asistente2.getEventos()) {
				fileLogger.info("  ->  {} , {} , {} , {}", evento1.getId(), evento1.getNombre(), evento1.getFecha(),
						evento1.getLugar());
			}
		}
		em.close();
	}
}
