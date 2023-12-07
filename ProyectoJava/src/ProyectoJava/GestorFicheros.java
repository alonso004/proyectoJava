package ProyectoJava;
import java.io.*;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class GestorFicheros {

	//Creamos los arrayList donde guardamos los datos creados de libros autor y prestamos
	 static  ArrayList<Libro> libros = new ArrayList<>();
	 static  ArrayList<Autor> autor = new ArrayList<>();
	 static  ArrayList<Prestamos> prestamos = new ArrayList<>();
	
	 //Creamos el metodo para escribir libros en binario
	public void obtenerLibros(Libro nuevoLibro)  {

		libros.add(nuevoLibro);
		
		try {
		FileOutputStream oos = new FileOutputStream("C:\\java\\libros.bin");
		ObjectOutputStream os = new ObjectOutputStream(oos);

		os.writeObject(libros);
		os.close();
		
		
		System.out.println("Se ha creado el libro");
		}catch(Exception e) {
			e.printStackTrace();
		}

		
	    
		return;
	}

	//Lo mostramos por pantalla
	public void listarLibros()  {
		try {

		// Cargar libros existentes si el archivo ya tiene datos
	    ObjectInputStream obIn = new ObjectInputStream(new FileInputStream("C:\\java\\libros.bin"));

	    
	    
	    libros =  (ArrayList<Libro>) obIn.readObject();
	    
	    obIn.close();
	    
		for(Libro libro : libros) {
			System.out.println("ID: " + libro.getId()+" Titulo: "+libro.getTitulo()+"\n Autor: "+libro.getAutor()
			+" Año de publicacion: "+libro.getAnioPublicacion()+" Genero: "+libro.getGenero());
	    }
	    
	    
	    
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//Creamos el metodo para escribir autores en binario
public void obtenerAutores(Autor nuevoAutor)  {
		
	autor.add(nuevoAutor);
		try {
		FileOutputStream oos = new FileOutputStream("C:\\java\\autor.bin");
		ObjectOutputStream os = new ObjectOutputStream(oos);
		
		os.writeObject(autor);
		os.close();
		
		
		
		System.out.println("Se ha creado el autor");
		}catch(Exception e) {
			e.printStackTrace();
		}

		
	    
		return;
	}
//Lo mostramos por pantalla
public void listarAutores()  {
	
	
	
	
	try {

	// Cargar libros existentes si el archivo ya tiene datos
		ObjectInputStream obIn = new ObjectInputStream(new FileInputStream("C:\\java\\autor.bin"));
    
    autor =  (ArrayList<Autor>) obIn.readObject();
    obIn.close();
    
	for(Autor autor : autor) {
		System.out.println("ID: " + autor.getId()+" Nombre: "+autor.getNombre()+"\n Nacionalidad: "+autor.getNacionalidad()
		+" Año de nacimiento: "+autor.getAnioNacimiento());
    }
    
    
    
    obIn.close();
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	
}


//Lo mostramos por pantalla
public static void guardarPrestamosTxt(Prestamos nuevoPrestamo) {

	try (BufferedWriter oos = new BufferedWriter(new FileWriter("C:\\java\\prestamo.txt"))){

		oos.write("El id del préstamo es " + nuevoPrestamo.getId() + ".");
		oos.newLine();
		oos.write("Contiene el libro " + nuevoPrestamo.getLibro() + ".");
		oos.newLine();
		oos.write("El nombre del usuario es " + nuevoPrestamo.getNombreUsuario() + ".");
		oos.newLine();
		oos.write("La fecha del prestamo es " + nuevoPrestamo.getFechaPrestamo() + ".");
		oos.newLine();
		oos.write("Su fecha de devolución es " + nuevoPrestamo.getFechaDevolucion() + ".");
		oos.newLine();
		prestamos.add(nuevoPrestamo);

		System.out.println("Se ha creado el prestamo");
	} catch (Exception e) {
		e.printStackTrace();
	}

	return;
}
//Metodo de exportar
public void exportarDatosXML() {
    try {
        // Crear un nuevo documento XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        // Crear el elemento raíz
        Element rootElement = document.createElement("biblioteca");
        document.appendChild(rootElement);

        // Exportar libros
        for (Libro libro : libros) {
            Element elementoLibro = document.createElement("libro");
            elementoLibro.setAttribute("id", String.valueOf(libro.getId()));
            
            Element elementoTitulo = document.createElement("titulo");
            elementoTitulo.appendChild(document.createTextNode(libro.getTitulo()));
            elementoLibro.appendChild(elementoTitulo);

            Element elementoAutor = document.createElement("autor");
            elementoAutor.appendChild(document.createTextNode(libro.getAutor()));
            elementoLibro.appendChild(elementoAutor);

            Element elementoAnioPublicacion = document.createElement("anio_publicacion");
            elementoAnioPublicacion.appendChild(document.createTextNode(String.valueOf(libro.getAnioPublicacion())));
            elementoLibro.appendChild(elementoAnioPublicacion);

            Element elementoGenero = document.createElement("genero");
            elementoGenero.appendChild(document.createTextNode(libro.getGenero()));
            elementoLibro.appendChild(elementoGenero);

            rootElement.appendChild(elementoLibro);
        }
        //Exportar autores
        for (Autor autor : autor) {
            Element elementoautor = document.createElement("autor");
            elementoautor.setAttribute("id", String.valueOf(autor.getId()));
            
            Element elementoNombre = document.createElement("nombre");
            elementoNombre.appendChild(document.createTextNode(autor.getNombre()));
            elementoautor.appendChild(elementoNombre);

            Element elementoAnioNacimiento = document.createElement("anio_nacimiento");
            elementoAnioNacimiento.appendChild(document.createTextNode(String.valueOf(autor.getAnioNacimiento())));
            elementoautor.appendChild(elementoAnioNacimiento);

            Element elementoNacionalidad = document.createElement("nacionalidad");
            elementoNacionalidad.appendChild(document.createTextNode(autor.getNacionalidad()));
            elementoautor.appendChild(elementoNacionalidad);

           

            rootElement.appendChild(elementoautor);
        }
        
        
        
        // Transformar el documento a XML y escribirlo en un archivo
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Indentar el XML

        DOMSource source = new DOMSource(document);

        // Reemplazar "C:/java/catalogo_libros.xml" con la ruta y nombre de tu archivo de salida
        StreamResult result = new StreamResult("C:/java/catalogo_libros.xml");
        transformer.transform(source, result);

        System.out.println("Datos escritos en el archivo XML correctamente.");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
//Metodo importar

public void importarDatosXML() {
    try{
        File file = new File("C:/java/catalogo_libros.xml");

        // Crear un objeto DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Crear un objeto DocumentBuilder
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Parsear el archivo XML y obtener un objeto Document
        Document document = builder.parse(file);

        // Obtener listas separadas de nodos para libros, autores y préstamos
        NodeList nodeListLibros = document.getElementsByTagName("libro");
        NodeList nodeListAutores = document.getElementsByTagName("autor");
       

        //  Código para libros 
        try (ObjectOutputStream ossLibros = new ObjectOutputStream(new FileOutputStream("C:\\java\\libros.bin"));){
        	
        Libro libro1 = new Libro(0, null, null, 0, null);
        	
        for (int i = 0; i < nodeListLibros.getLength(); i++) {
        	
           Node node =  nodeListLibros.item(i);
           if (node.getNodeType() == Node.ELEMENT_NODE) {
        	   Element element = (Element) node;
        	// Obtener los elementos del libro
        	   libro1.setId(Integer.parseInt(element.getAttribute("id")));
               libro1.setTitulo(element.getElementsByTagName("titulo").item(0).getTextContent());
               libro1.setAutor(element.getElementsByTagName("autor").item(0).getTextContent());
               libro1.setAnioPublicacion(Integer.parseInt(element.getElementsByTagName("anio_publicacion").item(0).getTextContent()));
               libro1.setGenero(element.getElementsByTagName("genero").item(0).getTextContent());

                       
                libros.add(libro1);
                ossLibros.writeObject(libros);
                
           }
    
            
        }
        
        }catch (IOException e) {
            e.printStackTrace();
        }
        //Codigo para la importacion de autores
        try (ObjectOutputStream ossLibros = new ObjectOutputStream(new FileOutputStream("C:\\java\\autor.bin"));){
        	
            Autor autor1 = new Autor(0, null, null, 0);
            	
            for (int i = 0; i < nodeListAutores.getLength(); i++) {
            	
               Node node =  nodeListAutores.item(i);
               if (node.getNodeType() == Node.ELEMENT_NODE) {
            	   Element element = (Element) node;
            	// Obtener los elementos del autor
            	   autor1.setId(Integer.parseInt(element.getAttribute("id")));
            	   autor1.setNombre(element.getElementsByTagName("nombre").item(0).getTextContent());
            	   autor1.setNacionalidad(element.getElementsByTagName("nacionalidad").item(0).getTextContent());
            	   autor1.setAnioNacimiento(Integer.parseInt(element.getElementsByTagName("anio_nacimiento").item(0).getTextContent()));
            	  

                           
                    autor.add(autor1);
                    ossLibros.writeObject(autor);
                    
               }
               
       
            }
            
            }catch (IOException e) {
                e.printStackTrace();
            }

    } catch (Exception e) {
        e.printStackTrace();
    }


}

	
	
}
