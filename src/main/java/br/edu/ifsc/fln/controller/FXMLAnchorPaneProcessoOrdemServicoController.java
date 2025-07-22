package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.ItemDeOrdemServicoDAO;
import br.edu.ifsc.fln.model.dao.OrdemServicoDAO;
import br.edu.ifsc.fln.model.dao.ServicoDAO;
import br.edu.ifsc.fln.model.dao.VeiculoDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.ItemOs;
import br.edu.ifsc.fln.model.domain.OrdemServico;
import br.edu.ifsc.fln.utils.AlertDialog;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class FXMLAnchorPaneProcessoOrdemServicoController implements Initializable{

    @FXML
    private Button buttonAlterar;

    @FXML
    private Button buttonInserir;

    @FXML
    private Button buttonRemover;

    @FXML
    private Label labelServicos;

    @FXML
    private Label labelCliente;

    @FXML
    private Label labelData;

    @FXML
    private Label labelDesconto;

    @FXML
    private Label labelId;

    @FXML
    private Label labelVeiculo;

    @FXML
    private Label labelTotal;

    @FXML
    private TableView<OrdemServico> tableView;

    @FXML
    private TableColumn<OrdemServico, Integer> tableColumnOrdemServicoId;

    @FXML
    private TableColumn<OrdemServico, LocalDate> tableColumnOrdemServicoData;

    @FXML
    private TableColumn<OrdemServico, OrdemServico> tableColumnOrdemServicoVeiculo;

    private List<OrdemServico> listaOrdemServicos;
    private ObservableList<OrdemServico> observableListOrdemServicos;

    //acesso ao banco de dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private Connection connection = database.conectar();
    private final OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
    private final ItemDeOrdemServicoDAO itemDeOrdemServicoDAO = new ItemDeOrdemServicoDAO();
    private final ServicoDAO servicoDAO = new ServicoDAO();
    private VeiculoDAO veiculoDAO = new VeiculoDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        veiculoDAO.setConnection(connection);
        ordemServicoDAO.setConnection(connection);          
        ordemServicoDAO.setVeiculoDAO(veiculoDAO);

        carregarTableView();

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));
    }

    public void carregarTableView() {
        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        tableColumnOrdemServicoId.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tableColumnOrdemServicoData.setCellValueFactory(new PropertyValueFactory<>("agenda"));
        tableColumnOrdemServicoVeiculo.setCellValueFactory(new PropertyValueFactory<>("veiculo"));
        tableColumnOrdemServicoData.setCellFactory(column -> {
            return new TableCell<OrdemServico, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(myDateFormatter.format(item));
                    }
                }
            };
        });


        listaOrdemServicos = ordemServicoDAO.listar();

        observableListOrdemServicos = FXCollections.observableArrayList(listaOrdemServicos);
        tableView.setItems(observableListOrdemServicos);
    }

    public void selecionarItemTableView(OrdemServico venda) {
        if (venda != null) {
            labelId.setText(Long.toString(venda.getNumero()));
            labelData.setText(venda.getAgenda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            labelTotal.setText(String.format("%.2f", venda.getTotal()));
            labelDesconto.setText((String.format("%.2f", venda.getDesconto())) + "%");
            labelVeiculo.setText(venda.getVeiculo().getPlaca());
            labelCliente.setText(venda.getVeiculo().getCliente().getNome());
            labelServicos.setText(String.valueOf(venda.getItens().size()));
        } else {
            labelId.setText("");
            labelData.setText("");
            labelTotal.setText("");
            labelDesconto.setText("");
            labelVeiculo.setText("");
            labelCliente.setText("");
            labelServicos.setText("");
        }
    }

    @FXML
    private void handleButtonInserir(ActionEvent event) throws IOException, SQLException {
        OrdemServico venda = new OrdemServico();
//        List<ItemOs> itensDeOrdemServico = new ArrayList<>();
//        venda.setItensOs(itensDeOrdemServico);
        boolean buttonConfirmarClicked = showFXMLAnchorPaneProcessoOrdemServicoDialog(venda);
        if (buttonConfirmarClicked) {
            ordemServicoDAO.setConnection(connection);
            ordemServicoDAO.inserir(venda);
            carregarTableView();
        }
    }

    @FXML
    private void handleButtonAlterar(ActionEvent event) throws IOException {
        OrdemServico venda = tableView.getSelectionModel().getSelectedItem();

        if (venda != null) {
            itemDeOrdemServicoDAO.setConnection(connection);
            venda.setItens(itemDeOrdemServicoDAO.buscar(venda.getNumero()));

            boolean buttonConfirmarClicked = showFXMLAnchorPaneProcessoOrdemServicoDialog(venda);

            if (buttonConfirmarClicked) {
                ordemServicoDAO.alterar(venda);
                carregarTableView();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhuma seleção");
            alert.setContentText("Por favor, selecione uma ordem de serviço na tabela.");
            alert.show();
        }
    }

    @FXML
    private void handleButtonRemover(ActionEvent event) throws SQLException {
        OrdemServico venda = tableView.getSelectionModel().getSelectedItem();
        if (venda != null) {
            if (AlertDialog.confirmarExclusao("Tem certeza que deseja excluir a OS? " + venda.getNumero())) {
                ordemServicoDAO.setConnection(connection);
                ordemServicoDAO.remover(venda);
                carregarTableView();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Por favor, escolha uma venda na tabela!");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneProcessoOrdemServicoDialog(OrdemServico venda) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneProcessoOrdemServicoDialogController.class.getResource(
                "/view/FXMLAnchorPaneProcessoOrdemServicoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        //criando um estágio de diálogo  (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de vendas");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        //Setando o venda ao controller
        FXMLAnchorPaneProcessoOrdemServicoDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setOrdemServico(venda);

        //Mostra o diálogo e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }
}
