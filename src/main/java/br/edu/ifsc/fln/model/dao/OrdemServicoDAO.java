package br.edu.ifsc.fln.model.dao;

import br.edu.ifsc.fln.model.domain.OrdemServico;
import br.edu.ifsc.fln.model.domain.Servico;
import br.edu.ifsc.fln.model.domain.Veiculo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdemServicoDAO {

    private Connection connection;
    private VeiculoDAO veiculoDAO;
    private ItemDeOrdemServicoDAO itensDAO = new ItemDeOrdemServicoDAO();

    // Getters e Setters
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setVeiculoDAO(VeiculoDAO veiculoDAO) {
        this.veiculoDAO = veiculoDAO;
    }

    public boolean inserir(OrdemServico os) {
        itensDAO.setConnection(connection);
        String sql = "INSERT INTO ordem_servico(total, desconto, agenda, veiculo_id) VALUES(?, ?, ?, ?)";

        try {
            // Solicita retorno de chaves geradas
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, os.getTotal());
            stmt.setDouble(2, os.getDesconto());
            stmt.setDate(3, java.sql.Date.valueOf(os.getAgenda()));
            stmt.setInt(4, os.getVeiculo().getId());
            stmt.executeUpdate();

            // Recupera o valor do campo auto_increment 'numero'
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                long numeroGerado = generatedKeys.getLong(1);
                os.setNumero(numeroGerado); // atualiza o objeto com o número gerado
                itensDAO.inserir(os.getItens(), numeroGerado);
            } else {
                throw new SQLException("Falha ao obter o número da ordem de serviço inserida.");
            }

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(OrdemServico os) {
        String sql = "UPDATE ordem_servico SET total = ?, desconto = ?, agenda = ?, veiculo_id = ? WHERE numero = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, os.getTotal());
            stmt.setDouble(2, os.getDesconto());
            stmt.setDate(3, java.sql.Date.valueOf(os.getAgenda()));
            stmt.setInt(4, os.getVeiculo().getId());
            stmt.setLong(5, os.getNumero());
            stmt.executeUpdate();

            // Atualizar itens: remover os antigos e inserir os novos
            itensDAO.setConnection(connection);
            itensDAO.removerPorOrdemServico(os.getNumero());
            itensDAO.inserir(os.getItens(), os.getNumero());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
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

    public List<OrdemServico> listar() {
        String sql = "SELECT * FROM ordem_servico";
        List<OrdemServico> retorno = new ArrayList<>();
        System.out.println("DAO.listar() → connection = " + connection);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                OrdemServico os = new OrdemServico();
                os.setAgenda(LocalDate.parse(resultado.getString("agenda")));
                os.setDesconto(resultado.getDouble("desconto"));
                os.setNumero(resultado.getLong("numero"));
                os.setTotal(resultado.getDouble("total"));

                // Buscar veículo com DAO já preparado
                Veiculo veiculo = veiculoDAO.buscar(resultado.getInt("veiculo_id"));
                os.setVeiculo(veiculo);

                retorno.add(os);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public OrdemServico buscar(OrdemServico os) {
        return buscar(os.getNumero());
    }

    public OrdemServico buscar(Long numero) {
        String sql = "SELECT * FROM ordem_servico WHERE numero=?";
        OrdemServico retorno = new OrdemServico();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, numero);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno.setNumero(resultado.getLong("numero"));
                retorno.setAgenda(LocalDate.parse(resultado.getString("agenda")));
                retorno.setTotal(resultado.getDouble("total"));
                retorno.setDesconto(resultado.getDouble("desconto"));

                Veiculo veiculo = veiculoDAO.buscar(resultado.getInt("veiculo_id"));
                retorno.setVeiculo(veiculo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Map<Integer, ArrayList<Object>> listarFaturamentoPorMes() {
        Map<Integer, ArrayList<Object>> resultado = new LinkedHashMap<>();
        String sql = """
        SELECT YEAR(agenda) AS ano, MONTH(agenda) AS mes, SUM(total) AS faturamento
        FROM ordem_servico
        GROUP BY ano, mes
        ORDER BY ano, mes
    """;

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int ano = rs.getInt("ano");
                int mes = rs.getInt("mes");
                double faturamento = rs.getDouble("faturamento");

                if (!resultado.containsKey(ano)) {
                    resultado.put(ano, new ArrayList<>());
                }

                resultado.get(ano).add(mes);         // índice par
                resultado.get(ano).add(faturamento); // índice ímpar
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

}
