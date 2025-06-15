/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.ClienteDAO;
import br.edu.ifsc.fln.model.dao.CorDAO;
import br.edu.ifsc.fln.model.dao.MarcaDAO;
import br.edu.ifsc.fln.model.dao.ModeloDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.*;
import br.edu.ifsc.fln.model.enums.ECategoria;
import br.edu.ifsc.fln.model.enums.ETipoCombustivel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author mpisc
 */
public class FXMLAnchorPaneCadastroVeiculoDialogController implements Initializable {

    @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private TextField tfPlaca;

    @FXML
    private TextField tfObs;

    @FXML
    private ComboBox<Modelo> cbModelo;

    @FXML
    private ComboBox<Cor> cbCor;

    @FXML
    private ComboBox<Cliente> cbCliente;



    private Stage dialogStage;
    private boolean btConfirmarClicked = false;
    private Veiculo veiculo;
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ModeloDAO modeloDAO = new ModeloDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final CorDAO corDAO = new CorDAO();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modeloDAO.setConnection(connection);
        clienteDAO.setConnection(connection);
        corDAO.setConnection(connection);
        List<Cor> cores = corDAO.listar();
        List<Cliente> clientes = clienteDAO.listar();
        List<Modelo> listaModelos = modeloDAO.listar();
        cbModelo.setItems(FXCollections.observableList(listaModelos));
        cbCliente.setItems(FXCollections.observableList(clientes));
        cbCor.setItems(FXCollections.observableList(cores));
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

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
        tfPlaca.setText(veiculo.getPlaca());
        tfObs.setText(veiculo.getObservacao());
        cbModelo.setValue(veiculo.getModelo());
        cbCor.setValue(veiculo.getCor());
        cbCliente.setValue(veiculo.getCliente());
    }
    

    @FXML
    public void handleBtConfirmar() {
        if (validarEntradaDeDados()) {
            veiculo.setPlaca(tfPlaca.getText());
            veiculo.setModelo(cbModelo.getValue());
            veiculo.setCor(cbCor.getValue());
            veiculo.setCliente(cbCliente.getValue());
            veiculo.setObservacao(tfObs.getText());

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
        if (this.tfPlaca.getText() == null || this.tfPlaca.getText().length() == 0) {
            errorMessage += "Placa inválido.\n";
        }
        if (this.cbCor.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cor inválida.\n";
        }
        if (this.cbCliente.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cliente inválido.\n";
        }
        if (this.cbModelo.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Modelo inválido.\n";
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
