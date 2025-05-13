package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.ServicoDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Servico;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLAnchorPaneCadastroServicoController implements Initializable {

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnInserir;

    @FXML
    private Label lbServicoNome;

    @FXML
    private Label lbServicoId;

    @FXML
    private Label lbServicoCategoria;

    @FXML
    private Label lbServicoValor;

    @FXML
    private TableColumn<Servico, String> tableColumnServicos;

    @FXML
    private TableView<Servico> tableViewServico;

    private List<Servico> listaServico;
    private ObservableList<Servico> observableListServico;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ServicoDAO servicoDAO = new ServicoDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servicoDAO.setConnection(connection);
        carregarTableViewServico();

        tableViewServico.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewServico(newValue));
    }

    public void carregarTableViewServico() {
        tableColumnServicos.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        listaServico = servicoDAO.listar();

        observableListServico = FXCollections.observableArrayList(listaServico);
        tableViewServico.setItems(observableListServico);
    }

    public void selecionarItemTableViewServico(Servico servico) {
        if (servico != null) {
            lbServicoId.setText(String.valueOf(servico.getId()));
            lbServicoNome.setText(servico.getDescricao());
            lbServicoCategoria.setText(servico.getCategoria().name());
            lbServicoValor.setText(String.valueOf(servico.getValor()));
        } else {
            lbServicoId.setText("");
            lbServicoNome.setText("");
            lbServicoCategoria.setText("");
            lbServicoValor.setText("");
        }

    }

    @FXML
    public void handleBtInserir() throws IOException {
        Servico servico = new Servico();
        boolean btConfirmarClicked = showFXMLAnchorPaneCadastroServicoDialog(servico);
        if (btConfirmarClicked) {
            servicoDAO.inserir(servico);
            carregarTableViewServico();
        }
    }

    @FXML
    public void handleBtAlterar() throws IOException {
        Servico servico = tableViewServico.getSelectionModel().getSelectedItem();
        if (servico != null) {
            boolean btConfirmarClicked = showFXMLAnchorPaneCadastroServicoDialog(servico);
            if (btConfirmarClicked) {
                servicoDAO.alterar(servico);
                carregarTableViewServico();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde uma Servico na tabela ao lado");
            alert.show();
        }
    }

    @FXML
    public void handleBtExcluir() throws IOException {
        Servico servico = tableViewServico.getSelectionModel().getSelectedItem();
        if (servico != null) {
            servicoDAO.remover(servico);
            carregarTableViewServico();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde uma Servico na tabela ao lado");
            alert.show();
        }
    }

    private boolean showFXMLAnchorPaneCadastroServicoDialog(Servico servico) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastroServicoController.class.getResource("/view/FXMLAnchorPaneCadastroServicoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        //criação de um estágio de diálogo (StageDialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Servico");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        //enviando o obejto Servico para o controller
        FXMLAnchorPaneCadastroServicoDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setServico(servico);

        //apresenta o diálogo e aguarda a confirmação do usuário
        dialogStage.showAndWait();

        return controller.isBtConfirmarClicked();
    }
}
