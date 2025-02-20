/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

public class Modelo {

	private int id;
	
	private String descricao;
	
	private Marca marca;

	private Motor motor = new Motor();

	private ECategoria eCategoria;


	public Modelo(int id, String descricao, Marca marca) {
		this.id = id;
		this.descricao = descricao;
		this.marca = marca;
	}
        public Modelo(String descricao, Marca marca){
            this.marca = marca;
            this.descricao = descricao;
        }
        
        public Modelo(){
                
            
        }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	@Override
	public String toString() {
		return "\nIdentificador do modelo - " + id + "\nDescricao do modelo - " + descricao + marca;

	}


	
}
