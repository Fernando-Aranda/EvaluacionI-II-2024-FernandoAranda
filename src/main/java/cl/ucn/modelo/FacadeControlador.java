package cl.ucn.modelo;

public class FacadeControlador implements FacadeInterfaz  {
	private FacadeInterfaz Facade;

	public FacadeControlador(FacadeInterfaz facade) {
		super();
		Facade = facade;
	}

	@Override
	public void ejecutar() {
		Facade.ejecutar();	
	} 
	
}
