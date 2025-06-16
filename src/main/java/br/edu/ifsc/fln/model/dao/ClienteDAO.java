/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsc.fln.model.dao;

import br.edu.ifsc.fln.model.domain.Cliente;
import br.edu.ifsc.fln.model.domain.PessoaFisica;
import br.edu.ifsc.fln.model.domain.PessoaJuridica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mpisc
 */
public class ClienteDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Cliente cliente) {
        String sqlCliente = "INSERT INTO cliente(nome, celular, email, data_cadastro) VALUES(?, ?, ?, ?)";

        try (
                PreparedStatement stmt = connection.prepareStatement(sqlCliente, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCelular());
            stmt.setString(3, cliente.getEmail());
            stmt.setDate(4, java.sql.Date.valueOf(cliente.getDataCadastro()));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idCliente = rs.getInt(1);
                    cliente.inserirComplemento(connection, idCliente);
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }



    public boolean alterar(Cliente cliente) {
        String sqlCliente = "UPDATE cliente SET nome=?, celular=?, email=?, data_cadastro=? WHERE id=?";

        try {
            // Atualiza tabela cliente
            PreparedStatement stmt = connection.prepareStatement(sqlCliente);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCelular());
            stmt.setString(3, cliente.getEmail());
            stmt.setDate(4, java.sql.Date.valueOf(cliente.getDataCadastro()));
            stmt.setInt(5, cliente.getId());
            stmt.executeUpdate();
            stmt.close();

            // Atualiza dados complementares conforme o tipo de cliente
            boolean complementoAtualizado = cliente.atualizarComplemento(connection);

            return complementoAtualizado;

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    public boolean remover(Cliente cliente) {
        String sql = "DELETE FROM cliente WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Cliente> listar() {
    String sql = """
        SELECT c.id, c.nome, c.celular, c.email, c.data_cadastro,
               pf.cpf, pf.data_nascimento,
               pj.cnpj, pj.inscricao_estadual
        FROM cliente c
        LEFT JOIN pessoa_fisica pf ON c.id = pf.id_cliente
        LEFT JOIN pessoa_juridica pj ON c.id = pj.id_cliente
        ORDER BY c.id
    """;

    List<Cliente> retorno = new ArrayList<>();

    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Cliente cliente;

            if (rs.getString("cpf") != null) {
                PessoaFisica pf = new PessoaFisica();
                pf.setId(rs.getInt("id"));
                pf.setNome(rs.getString("nome"));
                pf.setCelular(rs.getString("celular"));
                pf.setEmail(rs.getString("email"));
                pf.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                pf.setCpf(rs.getString("cpf"));
                pf.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                cliente = pf;
            } else {
                PessoaJuridica pj = new PessoaJuridica();
                pj.setId(rs.getInt("id"));
                pj.setNome(rs.getString("nome"));
                pj.setCelular(rs.getString("celular"));
                pj.setEmail(rs.getString("email"));
                pj.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                pj.setCnpj(rs.getString("cnpj"));
                pj.setInscricaoEstadual(rs.getString("inscricao_estadual"));
                cliente = pj;
            }

            retorno.add(cliente);
        }

        stmt.close();
        rs.close();
    } catch (SQLException ex) {
        Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return retorno;
}

//    public Cliente buscar(Cliente cliente) {
//        String sql = "SELECT * FROM cliente WHERE id=?";
//        Cliente retorno;
//        try {
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            stmt.setInt(1, cliente.getId());
//            ResultSet resultado = stmt.executeQuery();
//            if (resultado.next()) {
//                retorno = populateVO(resultado);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return retorno;
//    }
    
//    private Cliente populateVO(ResultSet rs) throws SQLException {
//        Cliente cliente = new Cliente();
//        cliente.setId(rs.getInt("id"));
//        cliente.setNome(rs.getString("nome"));
//        cliente.setCpf(rs.getString("cpf"));
//        cliente.setTelefone(rs.getString("telefone"));
//        cliente.setEndereco(rs.getString("endereco"));
//        cliente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
//        return cliente;
//    }
}
