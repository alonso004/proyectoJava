package ProyectoJava;



import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Principal {
	//Para que te funcione facil crea una carpeta con el mismo nombre entre comillas en el disco local "java"
	// Aqui definimos los arrayList
	private static Scanner scanner = new Scanner(System.in);
	private static List<Autor> listaAutores = new ArrayList<>();
	private static List<Libro> listaLibros = new ArrayList<>();
	private static List<Prestamos> listaPrestamos = new ArrayList<>();
	private static ArrayList<Libro> librosImportados = new ArrayList<>();

	private static int idAutorActual = 1;
	private static int idLibroActual = 1;
	private static int idPrestamoActual = 1;
	private static GestorFicheros gestorFicheros = new GestorFicheros();
	
	private static void mostrarMenu() {
		System.out.println("Bienvenido al Sistema de Gestión de Biblioteca");
		System.out.println("1. Gestionar Libros");
		System.out.println("2. Gestionar Autores");
		System.out.println("3. Gestionar Préstamos");
		System.out.println("4. Exportar/Importar Datos (XML)");
		System.out.println("5. Salir");
		System.out.print("Seleccione una opción: ");
	}

	// Método principal que ejecuta el programa
	public static void main(String[] args) {
		boolean salir = false;
		while (!salir) {
			mostrarMenu();
			int opcion = scanner.nextInt();
			switch (opcion) {
			case 1:
				// Gestionar libros
				gestionarLibros();
				break;
			case 2:
				// Gestionar autores
				gestionarAutores();
				break;
			case 3:
				// Gestionar préstamos
				gestionarPrestamos();
				break;
			case 4:
				// Exportar/Importar datos con XML
				gestionarExportImportXML();
				break;
			case 5:
				salir = true;
				break;
			default:
				System.out.println("Opción no válida. Por favor, intente de nuevo.");
			}
		}
	}

	//Muestra el menu principal de libros
	private static void gestionarLibros() {
		boolean salirLibros = false;

		while (!salirLibros) {
			System.out.println("Seleccione una opción para Libros:");
			System.out.println("1. Crear libro");
			System.out.println("2. Mostrar libros");
			System.out.println("3. Actualizar libro");
			System.out.println("4. Eliminar libro");
			System.out.println("5. Volver al Menú Principal");

			int opcion = scanner.nextInt();
			scanner.nextLine(); // Limpiar el buffer después de leer el número

			switch (opcion) {
			case 1:
				// Crear Libros
				System.out.println("Ingresa el título del libro:");
				String titulo = scanner.nextLine();
				

				System.out.println("Ingresa el autor del libro:");
				String autor = scanner.nextLine();

				System.out.println("Ingresa el año de publicación del libro:");
				int anioPublicacion = scanner.nextInt();

				System.out.println("Ingresa el género del libro:");
				String genero = scanner.next();
				
				
				gestorFicheros.obtenerLibros(new Libro(idLibroActual, titulo, autor, anioPublicacion, genero));			
				
				idLibroActual++;

				System.out.println("El libro se ha agregado con éxito.");
				break;
			case 2:
				// Mostrar Libros
				gestorFicheros.listarLibros();
				break;
			case 3:
				// Actualizar Libro
				System.out.println("Ingrese el ID del libro a actualizar:");
				int idLibroActualizar = scanner.nextInt();
				scanner.nextLine(); // Limpiar el buffer después de leer el número

				Libro libroExistenteActualizar = encontrarLibroPorId(idLibroActualizar);

				if (libroExistenteActualizar != null) {
					System.out.println("Ingrese el nuevo título del libro:");
					libroExistenteActualizar.setTitulo(scanner.nextLine());

					System.out.println("Ingrese el nuevo autor del libro:");
					libroExistenteActualizar.setAutor(scanner.nextLine());

					System.out.println("Ingrese el nuevo año de publicación del libro:");
					libroExistenteActualizar.setAnioPublicacion(scanner.nextInt());

					System.out.println("Ingrese el nuevo género del libro:");
					libroExistenteActualizar.setGenero(scanner.next());

					System.out.println("Libro actualizado con éxito.");
				} else {
					System.out.println("No se encontró un libro con el ID proporcionado.");
				}
				break;
			case 4:
				// Eliminar Libro
				System.out.println("Ingrese el ID del libro a eliminar:");
				int idLibroEliminar = scanner.nextInt();
				scanner.nextLine(); // Limpiar el buffer después de leer el número

				Libro libroExistenteEliminar = encontrarLibroPorId(idLibroEliminar);

				if (libroExistenteEliminar != null) {
					listaLibros.remove(libroExistenteEliminar);
					System.out.println("Libro eliminado con éxito.");
				} else {
					System.out.println("No se encontró un libro con el ID proporcionado.");
				}
				break;
			case 5:
				salirLibros = true;
				break;
			default:
				System.out.println("Opción no válida. Por favor, intente de nuevo.");
			}
		}
	}

	//Muestra el menu principal de autores
	private static void gestionarAutores() {
		boolean salirAutores = false;

		while (!salirAutores) {
			System.out.println("Seleccione una opción para Autores:");
			System.out.println("1. Crear autor");
			System.out.println("2. Mostrar autor");
			System.out.println("3. Actualizar autor");
			System.out.println("4. Eliminar autor");
			System.out.println("5. Volver al Menú Principal");

			int opcion = scanner.nextInt();
			scanner.nextLine(); // Limpiar el buffer después de leer el número

			switch (opcion) {
			case 1:
				// Crear Autores
				System.out.println("Ingresa el nombre del autor:");
				String nombre = scanner.nextLine();

				System.out.println("Ingresa la nacionalidad del autor:");
				String nacionalidad = scanner.nextLine();

				System.out.println("\nIngresa el año de nacimiento del autor:");
				int anioNacimiento = scanner.nextInt();

				
				gestorFicheros.obtenerAutores(new Autor(idAutorActual, nombre, nacionalidad, anioNacimiento));

				System.out.println("El autor se ha agregado con éxito.");
				break;
			case 2:
				// Mostrar Autores aqui esta llamando a la clase gestor ficheros desde un arrayList
				gestorFicheros.listarAutores();
				
				break;
			case 3:
				// Actualizar Autores
				System.out.println("Ingrese el ID del autor a actualizar:");
				int idAutorActualizar = scanner.nextInt();
				scanner.nextLine(); // Limpiar el buffer después de leer el número

				Autor autorExistenteActualizar = encontrarAutorPorId(idAutorActualizar);

				if (autorExistenteActualizar != null) {
					System.out.println("Ingrese el nuevo nombre del autor:");
					autorExistenteActualizar.setNombre(scanner.nextLine());

					System.out.println("Ingrese la nueva nacionalidad del autor:");
					autorExistenteActualizar.setNacionalidad(scanner.nextLine());

					System.out.println("Ingrese el nuevo año de nacimiento del autor:");
					autorExistenteActualizar.setAnioNacimiento(scanner.nextInt());

					System.out.println("Autor actualizado con éxito.");
				} else {
					System.out.println("No se encontró un autor con el ID proporcionado.");
				}
				break;
			case 4:
				// Eliminar Autores
				System.out.println("Ingrese el ID del autor a eliminar:");
				int idAutorEliminar = scanner.nextInt();
				scanner.nextLine(); // Limpiar el buffer después de leer el número

				Autor autorExistenteEliminar = encontrarAutorPorId(idAutorEliminar);

				if (autorExistenteEliminar != null) {
					listaAutores.remove(autorExistenteEliminar);
					System.out.println("Autor eliminado con éxito.");
				} else {
					System.out.println("No se encontró un autor con el ID proporcionado.");
				}
				break;
			case 5:
				salirAutores = true;
				break;
			default:
				System.out.println("Opción no válida. Por favor, intente de nuevo.");
			}
		}

	}

	//Muestra el menu principal de Prestamos
	private static void gestionarPrestamos() {
		boolean salirPrestamos = false;

		while (!salirPrestamos) {
			System.out.println("Seleccione una opción para Préstamos:");
			System.out.println("1. Registrar préstamo");
			System.out.println("2. Mostrar préstamos");
			System.out.println("3. Actualizar préstamo");
			System.out.println("4. Eliminar préstamo");
			System.out.println("5. Volver al Menú Principal");

			int opcion = scanner.nextInt();
			scanner.nextLine();

			switch (opcion) {
			case 1:
				// Crear Prestamos y cuando se crea lo guarda en un arrayList que tenemos dentro de la clase gestor ficheros y que los guarda en modo binario
				System.out.println("Ingrese el nombre del libro:");
				String libro = scanner.nextLine();

				System.out.println("Ingrese el nombre del usuario:");
				String nombreUsuario = scanner.nextLine();

				System.out.println("Ingrese la fecha de préstamo (AAAA-MM-DD):");
				String fechaPrestamoStr = scanner.nextLine();
				System.out.println("Ingrese la fecha de devolución (AAAA-MM-DD):");
				String fechaDevolucionStr = scanner.nextLine();

				Prestamos nuevoPrestamo = new Prestamos(idPrestamoActual, libro, nombreUsuario, fechaPrestamoStr,
						fechaDevolucionStr);
				gestorFicheros.guardarPrestamosTxt(nuevoPrestamo);
				listaPrestamos.add(nuevoPrestamo);
				idPrestamoActual++;

				if (fechaPrestamoStr != null && fechaDevolucionStr != null) {
					System.out.println("Préstamo registrado con éxito.");
				} else {
					System.out.println("Error al ingresar las fechas. El préstamo no se registró.");
				}

				break;
			case 2:
				// Mostrar Préstamos
				System.out.println("Lista préstamos " + listaPrestamos.size());
				for (Prestamos prestamo : listaPrestamos) {
					System.out.println("El id del prestamo es " + prestamo.getId() + ", el libro es "
							+ prestamo.getLibro() + ", el nombre del usuario es " + prestamo.getNombreUsuario()
							+ ", la fecha del prestamo es " + prestamo.getFechaPrestamo()
							+ " y la fecha de devolución es " + prestamo.getFechaDevolucion());
				}
				break;
			case 3:
				// Actualizar Préstamos
				System.out.println("Ingrese el ID del préstamo a actualizar:");
				int idPrestamoActualizar = scanner.nextInt();
				scanner.nextLine();

				Prestamos prestamoExistenteActualizar = encontrarPrestamosPorId(idPrestamoActualizar);

				if (prestamoExistenteActualizar != null) {
					System.out.println("Ingrese el nuevo nombre del libro:");
					prestamoExistenteActualizar.setLibro(scanner.nextLine());

					System.out.println("Ingrese el nuevo nombre del usuario:");
					prestamoExistenteActualizar.setNombreUsuario(scanner.nextLine());

					System.out.println("Ingrese la nueva fecha de préstamo (AAAA-MM-DD):");
					String nuevaFechaPrestamoStr = scanner.nextLine();

					System.out.println("Ingrese la nueva fecha de devolución (AAAA-MM-DD):");
					String nuevaFechaDevolucionStr = scanner.nextLine();

					System.out.println("Préstamo actualizado con éxito.");

				} else {
					System.out.println("No se encontró un préstamo con el ID proporcionado.");
				}
				break;
			case 4:
				// Eliminar Préstamos
				System.out.println("Ingrese el ID del préstamo a eliminar:");
				int idPrestamoEliminar = scanner.nextInt();
				scanner.nextLine();

				Prestamos prestamoExistenteEliminar = encontrarPrestamosPorId(idPrestamoEliminar);

				if (prestamoExistenteEliminar != null) {
					listaPrestamos.remove(prestamoExistenteEliminar);
					System.out.println("Préstamo eliminado con éxito.");
				} else {
					System.out.println("No se encontró un préstamo con el ID proporcionado.");
				}
				break;
			case 5:
				salirPrestamos = true;
				break;
			default:
				System.out.println("Opción no válida. Por favor, intente de nuevo.");
			}
		}
	}

	//Muestra el menu principal de exportar e importar 
	private static void gestionarExportImportXML() {
		System.out.println("Seleccione una opción para Exportar/Importar datos con XML:");
		System.out.println("1. Exportar datos a XML");
		System.out.println("2. Importar datos desde XML");

		int opcion = scanner.nextInt();
		scanner.nextLine(); // Limpiar el buffer después de leer el número

		switch (opcion) {
		case 1:
			exportarDatosXML();
			break;
		case 2:
			importarDatosXML();
			break;
		default:
			System.out.println("Opción no válida. Por favor, intente de nuevo.");
		}
	}

	// MÉTODOS
	// Método dentro de gestionarLibros para encontrar el libro
	private static Libro encontrarLibroPorId(int idLibro) {
		for (Libro libro : listaLibros) {
			System.out.println("ID del libro actual: " + libro.getId());
			if (libro.getId() == idLibro) {
				return libro;
			}
		}
		System.out.println("No se encontró un libro con el ID: " + idLibro);
		return null;
	}

	// Método dentro de gestionarAutores para buscar el autor
	private static Autor encontrarAutorPorId(int idAutor) {
		for (Autor autor : listaAutores) {
			System.out.println("ID del autor actual: " + autor.getId());
			if (autor.getId() == idAutor) {
				return autor;
			}
		}
		System.out.println("No se encontró un autor con el ID: " + idAutor);
		return null;
	}

	// Métodos dentro de gestionarPrestamos para encontrar el prestamo
	private static Prestamos encontrarPrestamosPorId(int idPrestamos) {
		for (Prestamos prestamos : listaPrestamos) {
			System.out.println("ID del prestamo actual: " + prestamos.getId());
			if (prestamos.getId() == idPrestamos) {
				return prestamos;
			}
		}
		System.out.println("No se encontró un prestamo con el ID: " + idPrestamos);
		return null;
	}

	
	private static Date parsearFecha(String fechaStr) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("AAAA-MM-DD");
			return new Date(dateFormat.parse(fechaStr).getTime());
		} catch (ParseException e) {
			System.out.println("Error al parsear la fecha.");
			return null;
		}
	}

	// Métodos dentro de gestionarExportImportXML estos metodos los hemos hecho en la clase gestor ficheros

	private static void exportarDatosXML() {
	    gestorFicheros.exportarDatosXML();
	}



	
	private static void importarDatosXML() {
		gestorFicheros.importarDatosXML();
	}


}

