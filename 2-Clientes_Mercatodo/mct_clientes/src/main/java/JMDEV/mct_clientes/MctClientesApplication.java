package JMDEV.mct_clientes;
import JMDEV.mct_clientes.Model.Cliente;
import JMDEV.mct_clientes.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
import java.util.Scanner;


//@SpringBootApplication
public class MctClientesApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger = LoggerFactory.getLogger(MctClientesApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando aplicacion");
		SpringApplication.run(MctClientesApplication.class, args);
		logger.info("Finalizando aplicacion");
	}

	@Override
	public void run(String... args) throws Exception {

		clientesMercatodoApp();
	}

	private void clientesMercatodoApp(){
		boolean salir = false;
		Scanner consola = new Scanner(System.in);
		while (!salir){
			int opcion = mostrarMenu(consola);
			salir = ejecutarOpciones(consola, opcion);
			logger.info(nl);
		}
	}

	private int mostrarMenu(Scanner consola){

		logger.info("""
					\n**** Aplicacion clienter mercartodo ****
					
					1. Listar clientes
					2. Buscar cliente
					3. Agregar cliente
					4. Modificar cliente
					5. Eliminar cliente
					6. Salir
					Elige una opcion\s
					""");

		return Integer.parseInt(consola.nextLine());
	}

	private boolean ejecutarOpciones(Scanner consola, int opcion){
		boolean salir = false;

		switch (opcion){
			case 1 -> {
				logger.info(nl + "---Listado de clientes---"+nl);
				List<Cliente> clientes = clienteServicio.listarClientes();
				clientes.forEach(cliente -> logger.info(cliente.toString()+nl));
			}

			case 2 ->{
				logger.info(nl+"---Buscar cliente por ID---"+nl);
				logger.info("Ingrese el ID del cliente: "+nl);
				int idCliente = Integer.parseInt(consola.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null){
					logger.info("Cliente encontrado: " + cliente + nl);
				}else{
					logger.info("Cliente NO encontrado"+nl);
				}
			}

			case 3 -> {
				logger.info(nl+"---Agregar cliente---"+nl);
				logger.info("Nombre: ");
				String nombre = consola.nextLine();

				logger.info("Apellido: ");
				String apellido = consola.nextLine();

				logger.info("Ciudad: ");
				String ciudad = consola.nextLine();

				logger.info("Direccion: ");
				String direccion = consola.nextLine();

				logger.info("Telefono: ");
				String telefono = consola.nextLine();

				logger.info("Puntos Compra: ");
				int puntosPC = Integer.parseInt(consola.nextLine());

				Cliente clienteAgregar = new Cliente();

				clienteAgregar.setNombre(nombre);
				clienteAgregar.setApellido(apellido);
				clienteAgregar.setCiudad(ciudad);
				clienteAgregar.setDireccion(direccion);
				clienteAgregar.setTelefono(telefono);
				clienteAgregar.setPuntosPC(puntosPC);

				clienteServicio.guardarCliente(clienteAgregar);

				logger.info("Cliente agregado correctamente"+clienteAgregar+nl);
			}

			case 4 -> {
				logger.info("---Modificar cliente---"+nl);
				logger.info("ID Cliente");
				int idClienteMod = Integer.parseInt(consola.nextLine());
				Cliente clienteAgregar = clienteServicio.buscarClientePorId(idClienteMod);

				if (clienteAgregar != null){

					logger.info("Nombre: ");
					String nombre = consola.nextLine();

					logger.info("Apellido: ");
					String apellido = consola.nextLine();

					logger.info("Ciudad: ");
					String ciudad = consola.nextLine();

					logger.info("Direccion: ");
					String direccion = consola.nextLine();

					logger.info("Telefono: ");
					String telefono = consola.nextLine();

					logger.info("Puntos Compra: ");
					int puntosPC = Integer.parseInt(consola.nextLine());

					clienteAgregar.setNombre(nombre);
					clienteAgregar.setApellido(apellido);
					clienteAgregar.setCiudad(ciudad);
					clienteAgregar.setDireccion(direccion);
					clienteAgregar.setTelefono(telefono);
					clienteAgregar.setPuntosPC(puntosPC);

					clienteServicio.guardarCliente(clienteAgregar);

					logger.info("Cliente modificado correctamente: "+clienteAgregar+nl);

				}else {
					logger.info("Cliente no encontrado"+nl);
				}
			}

			case 5 -> {
				logger.info(nl+"---Eliminar cliente---"+nl);
				logger.info("Ingrese el id del cliente a eliminar: ");
				int idCliente = Integer.parseInt(consola.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null){
					clienteServicio.eliminarCliente(cliente);
					logger.info("Cliente eliminado correctamente"+nl);
				}else {
					logger.info("No se encontro el cliente"+nl);
				}

			}

			case 6 -> {
				logger.info("Gracias por usar nuestra aplicacion"+nl +nl);
				salir = true;
			}

			default -> logger.info("Opcion no reconocida"+opcion+nl);

		}
		return salir;
	}

}
