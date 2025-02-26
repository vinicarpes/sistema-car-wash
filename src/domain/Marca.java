/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

public class Marca {
	private int id;
	private String nome;
	
	public Marca(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
       public Marca(String nome) {
		this.id = 0;
		this.nome = nome;
	}
       public Marca() {
		this.id = 0;
		this.nome = null;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@Override
	public String toString() {

		return "\nIdentificador da marca - " + id + "\nMarca do veículo - " + nome;

	}
}
