package ProyectoJava;

import java.io.Serializable;

//En esta clase creamos los atributos, el constructor y los getter y setter
public class Autor implements Serializable {
	private int id;
	private String nombre;
	private String nacionalidad;
	private int anioNacimiento;

	// Constructor
	public Autor(int id, String nombre, String nacionalidad, int anioNacimiento) {
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.anioNacimiento = anioNacimiento;
	}

	// Getters y setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public int getAnioNacimiento() {
		return anioNacimiento;
	}

	public void setAnioNacimiento(int anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}
}
