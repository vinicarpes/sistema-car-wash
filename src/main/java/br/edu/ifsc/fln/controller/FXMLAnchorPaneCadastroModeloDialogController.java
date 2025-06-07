/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.MarcaDAO;
import br.edu.ifsc.fln.model.dao.ModeloDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Marca;
import br.edu.ifsc.fln.model.domain.Modelo;
import br.edu.ifsc.fln.model.domain.Motor;
import br.edu.ifsc.fln.model.enums.ECategoria;
import br.edu.ifsc.fln.model.enums.ETipoCombustivel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

/**
 * FXML Controller class
 *
 * @author mpisc
 */
public class FXMLAnchorPaneCadastroModeloDialogController implements Initializable {

    @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private TextField tfDescricao;

    @FXML
    private ChoiceBox<ECategoria> choiceCategoria;

    @FXML
    private ComboBox<Marca> comboMarca;

    @FXML
    private TextField tfPotencia;

    @FXML
    private ChoiceBox<ETipoCombustivel> choiceCombustivel;

    private Stage dialogStage;
    private boolean btConfirmarClicked = false;
    private Modelo modelo;
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final MarcaDAO marcaDAO = new MarcaDAO();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        marcaDAO.setConnection(connection);
        List<Marca> listaMarcas = marcaDAO.listar();
        comboMarca.setItems(FXCollections.observableList(listaMarcas));
        choiceCategoria.getItems().addAll(ECategoria.values());
        choiceCombustivel.getItems().addAll(ETipoCombustivel.values());
    }       

    public boolean isBtConfirmarClicked() {
        return btConfirmarClicked;
    }

    public void setBtConfirmarClicked(boolean btConfirmarClicked) {
        this.btConfirmarClicked = btConfirmarClicked;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
        tfDescricao.setText(modelo.getDescricao());
        choiceCategoria.setValue(modelo.geteCategoria());
        tfPotencia.setText(String.valueOf(modelo.getMotor().getPotencia()));
        choiceCombustivel.setValue(modelo.getMotor().getETipoCombustivel());
        comboMarca.setValue(modelo.getMarca());
    }
    

    @FXML
    public void handleBtConfirmar() {
        if (validarEntradaDeDados()) {
            modelo.setDescricao(tfDescricao.getText());
            modelo.setMarca(comboMarca.getValue());
            modelo.seteCategoria(choiceCategoria.getValue());
            Motor motor = modelo.getMotor();
            motor.setPotencia(Integer.parseInt(tfPotencia.getText()));
            motor.setETipoCombustivel(choiceCombustivel.getValue());

            btConfirmarClicked = true;
            dialogStage.close();

            btConfirmarClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleBtCancelar() {
        dialogStage.close();
    }
    
    //método para validar a entrada de dados
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        if (this.tfDescricao.getText() == null || this.tfDescricao.getText().length() == 0) {
            errorMessage += "Modelo inválido.\n";
        }
        if (this.choiceCategoria.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Categoria inválida.\n";
        }
        if (this.comboMarca.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Marca inválida.\n";
        }
        if (this.tfPotencia.getText() == null || this.tfPotencia.getText().length() == 0) {
            errorMessage += "Potência inválida.\n";
        }
        if (this.choiceCombustivel.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Combustível inválido.\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            //exibindo uma mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Corrija os campos inválidos!");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    
}
