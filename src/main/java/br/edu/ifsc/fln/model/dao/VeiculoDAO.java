package br.edu.ifsc.fln.model.dao;

import br.edu.ifsc.fln.model.domain.*;
import br.edu.ifsc.fln.model.enums.ECategoria;
import br.edu.ifsc.fln.model.enums.ETipoCombustivel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeiculoDAO {
    private Connection connection;

    private ModeloDAO modeloDAO = new ModeloDAO();
    private CorDAO corDAO = new CorDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Veiculo veiculo) {
        String sqlVeiculo = "INSERT INTO veiculo(placa, observacao, modelo_id, cliente_id, cor_id) VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sqlVeiculo, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getObservacao());
            stmt.setInt(3, veiculo.getModelo().getId());
            stmt.setInt(4, veiculo.getCliente().getId());
            stmt.setInt(5, veiculo.getCor().getId());
            stmt.execute();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Veiculo veiculo) {
        String sql = "UPDATE veiculo SET placa=?, observacao=?, modelo_id=?, cliente_id=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getObservacao());
            stmt.setInt(3, veiculo.getModelo().getId());
            stmt.setInt(4, veiculo.getCliente().getId());
            stmt.setInt(5, veiculo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    public boolean remover(Veiculo veiculo) {
        String sql = "DELETE FROM veiculo WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, veiculo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Veiculo> listar() {
        String sql = "SELECT * FROM veiculo";
        List<Veiculo> retorno = new ArrayList<>();
        modeloDAO.setConnection(connection);
        corDAO.setConnection(connection);
        clienteDAO.setConnection(connection);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setId(resultado.getInt("id"));
                veiculo.setPlaca(resultado.getString("placa"));
                veiculo.setModelo(modeloDAO.buscar(resultado.getInt("modelo_id")));
                veiculo.setCliente(clienteDAO.buscar(resultado.getInt("cliente_id")));
                veiculo.setCor(corDAO.buscar(resultado.getInt("cor_id")));
                veiculo.setObservacao(resultado.getString("observacao"));

                retorno.add(veiculo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Veiculo buscar(Veiculo veiculo) {
        Veiculo retorno = buscar(veiculo.getId());
        return retorno;
    }

    public Veiculo buscar(int id) {
        String sql = "SELECT * FROM veiculo WHERE id=?";
        Veiculo retorno = new Veiculo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno.setId(resultado.getInt("id"));
                retorno.setObservacao(resultado.getString("observacao"));
                retorno.setPlaca(resultado.getString("placa"));
                retorno.setModelo(modeloDAO.buscar(resultado.getInt("modelo_id")));
                retorno.setCor(corDAO.buscar(resultado.getInt("cor_id")));
//                retorno.setCliente(buscarCliente(resultado.getInt("cliente_id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

//    public Cliente buscarCliente(int veiculo_id) {
//        String sql = "SELECT * FROM cliente WHERE id_veiculo=?";
////        Cliente retorno = new Cliente();
//        try {
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            stmt.setInt(1, veiculo_id);
//            ResultSet resultado = stmt.executeQuery();
//            if (resultado.next()) {
////                retorno.set(resultado.getInt("potencia"));
////                retorno.setETipoCombustivel(ETipoCombustivel.valueOf(resultado.getString("tipo_combustivel")));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return retorno;
//    }


}
