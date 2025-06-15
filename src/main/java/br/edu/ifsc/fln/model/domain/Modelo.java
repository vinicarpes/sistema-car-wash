package br.edu.ifsc.fln.model.domain;

import br.edu.ifsc.fln.model.enums.ECategoria;

public class Modelo {
    private int id;
    private String descricao;
    private ECategoria eCategoria;
    private Marca marca;
    private Motor motor = new Motor();

    public ECategoria geteCategoria() {
        return eCategoria;
    }

    public void seteCategoria(ECategoria eCategoria) {
        this.eCategoria = eCategoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }


    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Motor getMotor() {
        return motor;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return descricao;
    }
}

