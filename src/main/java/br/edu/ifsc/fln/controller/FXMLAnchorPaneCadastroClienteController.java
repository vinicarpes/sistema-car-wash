/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.ClienteDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Cliente;
import br.edu.ifsc.fln.model.domain.PessoaFisica;
import br.edu.ifsc.fln.model.domain.PessoaJuridica;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author mpisc
 */
public class FXMLAnchorPaneCadastroClienteController implements Initializable {


    @FXML
    private Button btAlterar;

    @FXML
    private Button btExcluir;

    @FXML
    private Button btInserir;

    @FXML
    private Label lbClienteId;

    @FXML
    private Label lbClienteDoc;

    @FXML
    private Label lbClienteNascimento;

    @FXML
    private Label lbClienteIE;

    @FXML
    private Label lbClienteNome;

    @FXML
    private Label lbClienteTelefone;

    @FXML
    private Label lbClienteEmail;

    @FXML
    private Label lbClienteDtCadastro;

    @FXML
    private Label lbClienteDtNascimento;

    @FXML
    private Label lbClienteInscricaoEstadual;

    @FXML
    private TableColumn<Cliente, String> tableColumnCliente;

    @FXML
    private TableColumn<Cliente, String> tableColumnClienteNome;

    @FXML
    private TableView<Cliente> tableViewClientes;


    private List<Cliente> listaClientes;
    private ObservableList<Cliente> observableListClientes;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        carregarTableViewCliente();

        tableViewClientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewClientes(newValue));
    }

    public void carregarTableViewCliente() {
        tableColumnClienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCliente.setCellValueFactory(new PropertyValueFactory<>("documento"));

        listaClientes = clienteDAO.listar();

        observableListClientes = FXCollections.observableArrayList(listaClientes);
        tableViewClientes.setItems(observableListClientes);
    }

    public void selecionarItemTableViewClientes(Cliente cliente) {
        if (cliente != null) {
            lbClienteId.setText(String.valueOf(cliente.getId()));
            lbClienteNome.setText(cliente.getNome());
            lbClienteEmail.setText(cliente.getEmail());
            lbClienteTelefone.setText(cliente.getCelular());
            lbClienteDtCadastro.setText(cliente.getDataCadastro().toString());
            if (cliente instanceof PessoaFisica) {
                lbClienteNascimento.setVisible(true);
                lbClienteDtNascimento.setVisible(true);
                lbClienteIE.setVisible(false);
                lbClienteInscricaoEstadual.setVisible(false);
                lbClienteDtNascimento.setText(((PessoaFisica) cliente).getDataNascimento().toString());
                lbClienteDoc.setText(((PessoaFisica) cliente).getCpf());
            }else if (cliente instanceof PessoaJuridica) {
                lbClienteNascimento.setVisible(false);
                lbClienteDtNascimento.setVisible(false);
                lbClienteIE.setVisible(true);
                lbClienteInscricaoEstadual.setVisible(true);
                lbClienteDoc.setText((((PessoaJuridica) cliente).getCnpj()));
                lbClienteInscricaoEstadual.setText((((PessoaJuridica) cliente).getInscricaoEstadual()));
            }
        }

    }

    @FXML
    public void handleBtInserir() throws IOException {
        Cliente novoCliente = showFXMLAnchorPaneCadastroClienteDialog(null);

        if (novoCliente != null) {
            clienteDAO.inserir(novoCliente);
            carregarTableViewCliente();
        }
    }


    @FXML
    public void handleBtAlterar() throws IOException {
        Cliente clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            Cliente clienteAtualizado = showFXMLAnchorPaneCadastroClienteDialog(clienteSelecionado);
            if (clienteAtualizado != null) {
                clienteDAO.alterar(clienteAtualizado);
                carregarTableViewCliente();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde um Cliente na tabela ao lado");
            alert.show();
        }
    }

    @FXML
    public void handleBtExcluir() throws IOException {
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            clienteDAO.remover(cliente);
            carregarTableViewCliente();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde uma Cliente na tabela ao lado");
            alert.show();
        }
    }

    private Cliente showFXMLAnchorPaneCadastroClienteDialog(Cliente cliente) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastroClienteController.class.getResource("/view/FXMLAnchorPaneCadastroClienteDialog.fxml"));

        AnchorPane page = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Cliente");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        FXMLAnchorPaneCadastroClienteDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCliente(cliente);

        dialogStage.showAndWait();

        if (controller.isBtConfirmarClicked()) {
            return controller.getCliente(); // aqui pega o cliente preenchido corretamente
        } else {
            return null;
        }
    }

}
