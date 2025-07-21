package br.edu.ifsc.fln.model.dao;

import br.edu.ifsc.fln.model.domain.OrdemServico;
import br.edu.ifsc.fln.model.domain.Servico;
import br.edu.ifsc.fln.model.domain.Veiculo;
import br.edu.ifsc.fln.model.enums.ECategoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdemServicoDAO {
    private Connection connection;
    private VeiculoDAO veiculoDAO = new VeiculoDAO();

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Servico servico) {
        String sql = "INSERT INTO servico(descricao, valor, categoria) VALUES(?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, servico.getDescricao());
            stmt.setDouble(2, servico.getValor());
            stmt.setString(3, servico.getCategoria().toString());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Servico servico) {
        String sql = "UPDATE Servico SET descricao=?, valor=?, categoria=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, servico.getDescricao());
            stmt.setDouble(2, servico.getValor());
            stmt.setString(3, servico.getCategoria().toString());
            stmt.setInt(4, servico.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Servico servico) {
        String sql = "DELETE FROM Servico WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, servico.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<OrdemServico> listar() {
        String sql = "SELECT * FROM ordem_servico";
        List<OrdemServico> retorno = new ArrayList<>();
        veiculoDAO.setConnection(connection);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                OrdemServico os = new OrdemServico();
//                os.setId(resultado.getInt("id"));
                os.setAgenda(LocalDate.parse(resultado.getString("agenda")));
                os.setDesconto(resultado.getDouble("desconto"));
                os.setNumero(resultado.getLong("numero"));
                os.setTotal(resultado.getDouble("total"));
                Veiculo veiculo = veiculoDAO.buscar(resultado.getInt("veiculo_id"));
                os.setVeiculo(veiculo);
                retorno.add(os);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public OrdemServico buscar(OrdemServico os) {
        OrdemServico retorno = buscar(os.getNumero());
        return retorno;
    }

    public OrdemServico buscar(Long id) {
        String sql = "SELECT * FROM ordem_servico WHERE id=?";
        veiculoDAO.setConnection(connection);
        OrdemServico retorno = new OrdemServico();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno.setNumero(resultado.getLong("numero"));
                retorno.setAgenda(LocalDate.parse(resultado.getString("agenda")));
                retorno.setVeiculo(veiculoDAO.buscar(resultado.getInt("veiculo_id")));
                retorno.setTotal(resultado.getDouble("total"));
                retorno.setDesconto(resultado.getDouble("desconto"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
