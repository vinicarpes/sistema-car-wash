package br.edu.ifsc.fln.model.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class PessoaFisica extends Cliente{
    private String cpf;
    private LocalDate dataNascimento;

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

    @Override
    public void inserirComplemento(Connection connection, int idCliente) throws SQLException {
        String sql = "INSERT INTO pessoa_fisica(id_cliente, cpf, data_nascimento) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.setString(2, this.getCpf());
            stmt.setDate(3, java.sql.Date.valueOf(this.getDataNascimento()));
            stmt.executeUpdate();
        }
    }


}
