/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsc.fln.model.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author mpisc
 */
public abstract class Cliente {
    protected  int id;
    protected String nome;
    protected String celular;
    protected String email;
    protected LocalDate dataCadastro = LocalDate.now();
    protected Pontuacao pontuacao = new Pontuacao();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        if (this instanceof PessoaFisica) {
            return ((PessoaFisica) this).getCpf();
        } else if (this instanceof PessoaJuridica) {
            return ((PessoaJuridica) this).getCnpj();
        } else {
            return "";
        }
    }


    public abstract void inserirComplemento(Connection connection, int idCliente) throws SQLException;

    @Override
    public String toString() {
        return nome;
    }
}
