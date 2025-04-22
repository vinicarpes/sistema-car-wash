package domain;

import java.time.LocalDate;

public class PessoaFisica extends Cliente{
    private String cpf;
    private LocalDate dataNascimento;

    public PessoaFisica(int id, String nome, String celular, String email, LocalDate dataCadastro, String cpf, LocalDate dataNascimento) {
        super(id, nome, celular, email, dataCadastro);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    // RETORNAR STRING CONCATENADA COM OS DADOS
    @Override
    public String getDados() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getDados());
        sb.append("\nCPF: ").append(cpf);
        sb.append("\nData de nascimento: ").append(dataNascimento);
        return sb.toString();
    }

    @Override
    public String getDados(String observacao) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getDados());
        sb.append("\nCPF: ").append(cpf);
        sb.append("\nData de nascimento: ").append(dataNascimento);
        sb.append("\nObservação: ").append(observacao);
        return sb.toString();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
