package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Cliente implements IDados {
    protected int id;
    protected String nome;
    protected String celular;
    protected String email;
    protected LocalDate dataCadastro;
    protected Pontuacao pontuacao = new Pontuacao();
    protected List<Veiculo> veiculos = new ArrayList<>();

    public Cliente(int id, String nome, String celular, String email, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    public Pontuacao getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Pontuacao pontuacao) {
        this.pontuacao = pontuacao;
    }

    @Override
    public String getDados() {
        return "Nome: " + this.nome +
                "\nCelular: " + this.celular +
                "\nEmail: " + this.email +
                "\nData de Cadastro: " + this.dataCadastro +
                "\n" + "Pontuacao: " + this.pontuacao.getQuantidade();
    }

    //overloading
    @Override
    public String getDados(String observacao) {
        return "Nome: " + this.nome + "\nCelular: " + this.celular + "\nEmail: " + this.email + "\nData de Cadastro: " + this.dataCadastro + "\n" + "Pontuacao: " + this.pontuacao.getQuantidade() + "\nObservação: " + observacao;
    }

    public void add(Veiculo veiculo) {
        this.veiculos.add(veiculo); //lista de veiculos do cliente
        veiculo.setCliente(this);//amarração para garantir que o veiculo esteja na lista de veiculos do cliente dessa instância (this)
    }
    public void remove(Veiculo veiculo) {
        this.veiculos.remove(veiculo);
        veiculo.setCliente(null);
    }

    public String listarVeiculos(){
        StringBuilder sb = new StringBuilder();
        for (Veiculo veiculo : this.veiculos) {
            sb.append("\n").append(veiculo.toString());
        }

        return sb.toString();
    }
}
