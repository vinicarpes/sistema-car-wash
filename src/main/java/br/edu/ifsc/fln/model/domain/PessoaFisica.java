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
    // Em PessoaFisica:
    @Override
    public boolean atualizarComplemento(Connection connection) throws SQLException {
        String sql = "UPDATE pessoa_fisica SET cpf=?, data_nascimento=? WHERE id_cliente=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, getCpf());
            stmt.setDate(2, java.sql.Date.valueOf(getDataNascimento()));
            stmt.setInt(3, getId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        }
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
