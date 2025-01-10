package domain.cliente;

import domain.IDados;
import domain.Pontuacao;
import domain.Veiculo;

import java.time.LocalDate;
import java.util.List;

public abstract class Cliente implements IDados {
    protected int id;
    protected String nome;
    protected String celular;
    protected String email;
    protected LocalDate dataCadastro;
    protected Pontuacao pontuacao = new Pontuacao();
    protected List<Veiculo> veiculos;

    public Cliente(int id, String nome, String celular, String email, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String getDados() {
        return "Nome: " + this.nome + "\nCelular: " + this.celular + "\nEmail: " + this.email + "\nData de Cadastro: " + this.dataCadastro + "\n" + "Pontuacao: " + this.pontuacao.getQuantidade();
    }

    //overloading
    @Override
    public String getDados(String observacao) {
        return "Nome: " + this.nome + "\nCelular: " + this.celular + "\nEmail: " + this.email + "\nData de Cadastro: " + this.dataCadastro + "\n" + "Pontuacao: " + this.pontuacao.getQuantidade() + "\nObservação: " + observacao;
    }

    public void add(Veiculo veiculo) {}
    public void remove(Veiculo veiculo) {}
}
