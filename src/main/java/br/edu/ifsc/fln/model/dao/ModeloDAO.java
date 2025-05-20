package br.edu.ifsc.fln.model.dao;

import br.edu.ifsc.fln.model.domain.Marca;
import br.edu.ifsc.fln.model.domain.Modelo;
import br.edu.ifsc.fln.model.domain.Motor;
import br.edu.ifsc.fln.model.enums.ECategoria;
import br.edu.ifsc.fln.model.enums.ETipoCombustivel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModeloDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Modelo modelo) {
        String sqlModelo = "INSERT INTO modelo(descricao, marca_id) VALUES(?, ?)";
        String sqlMotor = "INSERT INTO motor(id_modelo, potencia, tipo_combustivel) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sqlModelo);
            stmt.setString(1, modelo.getDescricao());
            stmt.setInt(2, modelo.getMarca().getId());
            stmt.execute();
            stmt = connection.prepareStatement(sqlMotor);
            stmt.setInt(1, modelo.getId());
            stmt.setInt(2, modelo.getMotor().getPotencia());
            stmt.setString(3, modelo.getMotor().getETipoCombustivel().toString());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Modelo modelo) {
        String sql = "UPDATE modelo SET descricao=?, marca_id=?, categoria=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, modelo.getDescricao());
            stmt.setInt(2, modelo.getMarca().getId());
            stmt.setString(3, modelo.geteCategoria().toString());
            stmt.setInt(4, modelo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Modelo modelo, Motor motor) {
        String sql = "UPDATE motor SET potencia=?, tipo_combustivel=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, motor.getPotencia());
            stmt.setString(2, motor.getETipoCombustivel().toString());
            stmt.setInt(3, modelo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Modelo modelo) {
        String sql = "DELETE FROM modelo WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modelo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Modelo> listar() {
        String sql = "SELECT * FROM modelo";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
//                Modelo modelo = new Modelo();
//                modelo.setId(resultado.getInt("id"));
//                modelo.setDescricao(resultado.getString("descricao"));
////                modelo.setMarca(resultado.getInt);
//                retorno.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Modelo buscar(Modelo modelo) {
        Modelo retorno = buscar(modelo.getId());
        return retorno;
    }

    public Modelo buscar(int id) {
        String sql = "SELECT * FROM modelo WHERE id=?";
        Modelo retorno = new Modelo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno.setId(resultado.getInt("id"));
                retorno.setDescricao(resultado.getString("descricao"));
                retorno.seteCategoria(ECategoria.valueOf(resultado.getString("categoria")));
                retorno.setMotor(buscarMotor(id));
                retorno.setMarca(buscarMarca(id));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Motor buscarMotor(int modelo_id) {
        String sql = "SELECT * FROM motor WHERE id=?";
        Motor retorno = new Motor();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modelo_id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno.setPotencia(resultado.getInt("potencia"));
                retorno.setETipoCombustivel(ETipoCombustivel.valueOf(resultado.getString("tipo_combustivel")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Marca buscarMarca(int modelo_id) {
        String sql = "SELECT marca_id FROM modelo WHERE id=?";
        Marca retorno = new Marca();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modelo_id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno.setNome(resultado.getString("nome"));
                retorno.setId(resultado.getInt("id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
