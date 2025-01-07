/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

public class Veiculo {

	private int id;
	private String placa;
	private Modelo modelo;
        private String observacoes;
        private Cor cor;
	
	public Veiculo(int id, String placa, Modelo modelo, String observacoes, Cor cor) {
		this.id = id;
		this.placa = placa;
		this.modelo = modelo;
                this. observacoes = observacoes;
                this.cor = cor;
	}
        public Veiculo(int id, String placa, Modelo modelo) {
		this.id = id;
		this.placa = placa;
		this.modelo = modelo;
        }
        
        public Veiculo(String placa, Modelo modelo){
            this.modelo = modelo;
            this.placa = placa;
        }
        
        public Veiculo(String placa){
            this.placa = placa;
        }
        
        public Veiculo(){}
	
        public String getObservacoes() {
            return observacoes;
                }

        public void setObservacoes(String observacoes) {
            this.observacoes = observacoes;
        }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public Modelo getModelo() {
		return modelo;
	}
	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	@Override
	public String toString() {

		return "\nIdentificador do veículo - " + id + "\nPlaca do veículo - " + placa + "\n" + modelo + cor;

	}
	
	
}