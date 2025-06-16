package br.edu.ifsc.fln.model.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PessoaJuridica extends Cliente{
    protected String cnpj;
    protected String inscricaoEstadual;

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

    @Override
    public void inserirComplemento(Connection connection, int idCliente) throws SQLException {
        String sql = "INSERT INTO pessoa_juridica(id_cliente, cnpj, inscricao_estadual) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.setString(2, this.getCnpj());
            stmt.setString(3, this.getInscricaoEstadual());
            stmt.executeUpdate();
        }
    }

}
