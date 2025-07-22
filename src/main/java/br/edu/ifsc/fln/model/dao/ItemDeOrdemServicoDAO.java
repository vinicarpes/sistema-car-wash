package br.edu.ifsc.fln.model.dao;

import br.edu.ifsc.fln.model.domain.ItemOs;
import br.edu.ifsc.fln.model.domain.OrdemServico;
import br.edu.ifsc.fln.model.domain.Servico;
import br.edu.ifsc.fln.model.enums.ECategoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemDeOrdemServicoDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public boolean inserir(List<ItemOs> itens, long os_numero) {
        String sql = "INSERT INTO itens_os(valor_servico, servico_id, observacoes, os_numero ) VALUES(?, ?, ?, ?)";
        for(ItemOs item : itens) {
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setDouble(1, item.getValorServico());
                stmt.setDouble(2, item.getServico().getId());
                stmt.setString(3, item.getObservacoes());
                stmt.setLong(4, os_numero);
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;
    }

    public boolean alterar(Servico servico) {
        String sql = "UPDATE ordem_servico SET total=?, desconto=?, agenda=? WHERE numero=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, servico.getDescricao());
            stmt.setDouble(2, servico.getValor());
            stmt.setString(3, servico.getCategoria().toString());
            stmt.setInt(4, servico.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(OrdemServico os) {
        String sql = "DELETE FROM ordem_servico WHERE numero=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, os.getNumero());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Servico> listar() {
        String sql = "SELECT * FROM ordem_servico";
        List<Servico> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Servico servico = new Servico();
                servico.setId(resultado.getInt("id"));
                servico.setDescricao(resultado.getString("descricao"));
                servico.setCategoria(ECategoria.valueOf(resultado.getString("categoria")));
                servico.setValor(resultado.getDouble("valor"));
                retorno.add(servico);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    public List<ItemOs> buscar(long osNumero) {
        String sql = "SELECT io.*, s.id as servico_id, s.descricao, s.valor, s.categoria " +
                "FROM itens_os io " +
                "JOIN servico s ON io.servico_id = s.id " +
                "WHERE io.os_numero = ?";

        List<ItemOs> itens = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, osNumero);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Monta o serviço
                Servico servico = new Servico();
                servico.setId(rs.getInt("servico_id"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setValor(rs.getDouble("valor"));
                servico.setCategoria(ECategoria.valueOf(rs.getString("categoria")));

                // Monta o item OS
                ItemOs item = new ItemOs();
                item.setId(rs.getInt("id"));
                item.setServico(servico);
                item.setValorServico(rs.getDouble("valor_servico"));
                item.setObservacoes(rs.getString("observacoes"));

                itens.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return itens;
    }

    public boolean removerPorOrdemServico(long numeroOs) {
        String sql = "DELETE FROM itens_os WHERE os_numero = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, numeroOs);
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItemDeOrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
