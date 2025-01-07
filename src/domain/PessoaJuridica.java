package domain;

import java.time.LocalDate;

public class PessoaJuridica extends Cliente{
    private String cnpj;
    private String inscricaoEstadual;

    public PessoaJuridica(int id, String nome, String celular, String email, LocalDate dataCadastro, String cnpj, String inscricaoEstadual) {
        super(id, nome, celular, email, dataCadastro);
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
    }

    @Override
    public String getDados() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getDados());
        sb.append("\nCNPJ: ").append(cnpj);
        sb.append("\nInscrição estadual: ").append(inscricaoEstadual);
        return sb.toString();
    }

    @Override
    public String getDados(String observacao) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getDados());
        sb.append("\nCNPJ: ").append(cnpj);
        sb.append("\nInscrição estadual: ").append(inscricaoEstadual);
        sb.append("\nObservação: ").append(observacao);
        return sb.toString();
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }
}
