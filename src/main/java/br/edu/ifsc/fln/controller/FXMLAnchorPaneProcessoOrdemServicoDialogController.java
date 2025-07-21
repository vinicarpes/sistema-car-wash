package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.ClienteDAO;
import br.edu.ifsc.fln.model.dao.ServicoDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Cliente;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.edu.ifsc.fln.model.domain.ItemOs;
import br.edu.ifsc.fln.model.domain.OrdemServico;
import br.edu.ifsc.fln.model.domain.Servico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

public class FXMLAnchorPaneProcessoOrdemServicoDialogController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

//    @FXML
//    private ComboBox<Cliente> comboBoxClientes;
//    @FXML
//    private DatePicker datePickerData;
//    @FXML
//    private CheckBox checkBoxPago;
//    @FXML
//    private TableView<ItemOs> tableViewItensDeVenda;
//    @FXML
//    private TableColumn<ItemOs, Servico> tableColumnServico;
//    @FXML
//    private TableColumn<ItemOs, Integer> tableColumnQuantidade;
//    @FXML
//    private TableColumn<ItemOs, Double> tableColumnValor;
//    @FXML
//    private TextField textFieldValor;
//    @FXML
//    private ComboBox<Servico> comboBoxServico;
//    @FXML
//    private TextField textFieldQuantidadeServico;
//    @FXML
//    private Button buttonAdicionar;
//    @FXML
//    private Button buttonConfirmar;
//    @FXML
//    private Button buttonCancelar;
//    @FXML
//    private ContextMenu contextMenuTableView;
//    @FXML
//    private MenuItem contextMenuItemAtualizarQtd;
//    @FXML
//    private MenuItem contextMenuItemRemoverItem;
//    @FXML
//    private ChoiceBox choiceBoxSituacao;
//    @FXML
//    private TextField textFieldDesconto;
//
//
//    private List<Cliente> listaClientes;
//    private List<Servico> listaServicos;
//    private ObservableList<Cliente> observableListClientes;
//    private ObservableList<Servico> observableListServicos;
//    private ObservableList<ItemOs> observableListItensDeVenda;
//
//    //atributos para manipulação de banco de dados
//    private final Database database = DatabaseFactory.getDatabase("mysql");
//    private final Connection connection = database.conectar();
//    private final ClienteDAO clienteDAO = new ClienteDAO();
//    private final ServicoDAO servicoDAO = new ServicoDAO();
//
//    private Stage dialogStage;
//    private boolean buttonConfirmarClicked = false;
//    private OrdemServico ordemServico;
//
//    /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        clienteDAO.setConnection(connection);
//        servicoDAO.setConnection(connection);
//        carregarComboBoxClientes();
//        carregarComboBoxServicos();
//        carregarChoiceBoxSituacao();
//        setFocusLostHandle();
//        tableColumnServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
//        tableColumnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
//        tableColumnValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
//    }
//
//    private void carregarComboBoxClientes() {
//        listaClientes = clienteDAO.listar();
//        observableListClientes = FXCollections.observableArrayList(listaClientes);
//        comboBoxClientes.setItems(observableListClientes);
//    }
//
//    private void carregarComboBoxServicos() {
//        /* carrega apenas os servicos  com estoque cuja SITUACAO está em ATIVO para operações */
//        listaServicos = servicoDAO.listar(ESituacao.ATIVO);
//        observableListServicos = FXCollections.observableArrayList(listaServicos);
//        comboBoxServico.setItems(observableListServicos);
//    }
//
//
//    public void carregarChoiceBoxSituacao() {
//        choiceBoxSituacao.setItems( FXCollections.observableArrayList(EStatusVenda.values()));
//        choiceBoxSituacao.getSelectionModel().select(0);
//    }
//
//    private void setFocusLostHandle() {
//        textFieldDesconto.focusedProperty().addListener((ov, oldV, newV) -> {
//            if (!newV) { // focus lost
//                if (textFieldDesconto.getText() != null && !textFieldDesconto.getText().isEmpty()) {
//                    //System.out.println("teste focus lost");
//                    ordemServico.setTaxaDesconto(Double.parseDouble(textFieldDesconto.getText()));
//                    textFieldValor.setText(ordemServico.getTotal().toString());
//
//                }
//            }
//        });
//    }
//
//    /**
//     * @return the dialogStage
//     */
//    public Stage getDialogStage() {
//        return dialogStage;
//    }
//
//    /**
//     * @param dialogStage the dialogStage to set
//     */
//    public void setDialogStage(Stage dialogStage) {
//        this.dialogStage = dialogStage;
//    }
//
//    /**
//     * @return the buttonConfirmarClicked
//     */
//    public boolean isButtonConfirmarClicked() {
//        return buttonConfirmarClicked;
//    }
//
//    /**
//     * @param buttonConfirmarClicked the buttonConfirmarClicked to set
//     */
//    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
//        this.buttonConfirmarClicked = buttonConfirmarClicked;
//    }
//
//    /**
//     * @return the ordemServico
//     */
//    public OrdemServico getVenda() {
//        return ordemServico;
//    }
//
//    /**
//     * @param ordemServico the ordemServico to set
//     */
//    public void setVenda(OrdemServico ordemServico) {
//        this.ordemServico = ordemServico;
//        if (ordemServico.getId() != 0) {
//            comboBoxClientes.getSelectionModel().select(this.ordemServico.getCliente());
//            datePickerData.setValue(this.ordemServico.getData());
//            checkBoxPago.setSelected(this.ordemServico.isPago());
//            observableListItensDeOrdemServico = FXCollections.observableArrayList(
//                    this.ordemServico.getItensDeVenda());
//            tableViewItensDeVenda.setItems(observableListItensDeVenda);
//            textFieldValor.setText(String.format("%.2f", this.ordemServico.getTotal()));
//            textFieldDesconto.setText(String.format("%.2f", this.ordemServico.getTaxaDesconto()));
//            choiceBoxSituacao.getSelectionModel().select(this.ordemServico.getStatusVenda());
//
//        }
//    }
//
//    @FXML
//    public void handleButtonAdicionar() {
//        Servico servico;
//        ItemOs itemOs = new ItemOs();
//        if (comboBoxServico.getSelectionModel().getSelectedItem() != null) {
//            //o comboBox possui dados sintetizados de Servico para evitar carga desnecessária de informação
//            servico = comboBoxServico.getSelectionModel().getSelectedItem();
//            //a instrução a seguir busca detalhes do servico selecionado
//            servico = servicoDAO.buscar(servico);
//            if (servico.getEstoque().getQuantidade() >= Integer.parseInt(textFieldQuantidadeServico.getText())) {
//                itemOs.setServico(servico);
//                itemOs.setQuantidade(Integer.parseInt(textFieldQuantidadeServico.getText()));
//                itemOs.setValor(servico.getPreco().multiply(BigDecimal.valueOf(itemOs.getQuantidade())));
//                itemOs.setVenda(ordemServico);
//                ordemServico.getItensDeVenda().add(itemOs);
//                observableListItensDeOrdemServico = FXCollections.observableArrayList(ordemServico.getItensDeVenda());
//                tableViewItensDeVenda.setItems(observableListItensDeVenda);
//                textFieldValor.setText(String.format("%.2f", ordemServico.getTotal()));
//            } else {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setHeaderText("Problemas na escolha do servico");
//                alert.setContentText("Não existe quantidade suficiente de servicos para ordemServico.");
//                alert.show();
//            }
//        }
//    }
//
//    @FXML
//    private void handleButtonConfirmar() {
//        if (validarEntradaDeDados()) {
//            ordemServico.setCliente(comboBoxClientes.getSelectionModel().getSelectedItem());
//            ordemServico.setPago(checkBoxPago.isSelected());
//            ordemServico.setData(datePickerData.getValue());
//            ordemServico.setStatusVenda((EStatusVenda)choiceBoxSituacao.getSelectionModel().getSelectedItem());
//            ordemServico.setTaxaDesconto(Double.parseDouble(textFieldDesconto.getText()));
//            buttonConfirmarClicked = true;
//            dialogStage.close();
//        }
//    }
//
//    @FXML
//    private void handleButtonCancelar() {
//        dialogStage.close();
//    }
//
//    @FXML
//    void handleTableViewMouseClicked(MouseEvent event) {
//        ItemOs itemOs
//                = tableViewItensDeVenda.getSelectionModel().getSelectedItem();
//        if (itemOs == null) {
//            contextMenuItemAtualizarQtd.setDisable(true);
//            contextMenuItemRemoverItem.setDisable(true);
//        } else {
//            contextMenuItemAtualizarQtd.setDisable(false);
//            contextMenuItemRemoverItem.setDisable(false);
//        }
//
//    }
//
//    @FXML
//    private void handleContextMenuItemAtualizarQtd() {
//        ItemOs itemOs
//                = tableViewItensDeVenda.getSelectionModel().getSelectedItem();
//        int index = tableViewItensDeVenda.getSelectionModel().getSelectedIndex();
//
//        int qtdAtualizada = Integer.parseInt(inputDialog(itemOs.getQuantidade()));
//        if (itemOs.getServico().getEstoque().getQuantidade() >= qtdAtualizada) {
//            itemOs.setQuantidade(qtdAtualizada);
//            //ordemServico.getItensDeVenda().set(ordemServico.getItensDeVenda().indexOf(itemOs),itemOs);
//            ordemServico.getItensDeVenda().set(index, itemOs);
//            itemOs.setValor(itemOs.getServico().getPreco().multiply(BigDecimal.valueOf(itemOs.getQuantidade())));
//            tableViewItensDeVenda.refresh();
//            textFieldValor.setText(String.format("%.2f", ordemServico.getTotal()));
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText("Erro no estoque");
//            alert.setContentText("Não há quantidade suficiente de servicos para ordemServico.");
//            alert.show();
//        }
//    }
//
//    private String inputDialog(int value) {
//        TextInputDialog dialog = new TextInputDialog(Integer.toString(value));
//        dialog.setTitle("Entrada de dados.");
//        dialog.setHeaderText("Atualização da quantidade de servicos.");
//        dialog.setContentText("Quantidade: ");
//
//        // Traditional way to get the response value.
//        Optional<String> result = dialog.showAndWait();
//        return result.get();
//    }
//
//    @FXML
//    private void handleContextMenuItemRemoverItem() {
//        ItemOs itemOs
//                = tableViewItensDeVenda.getSelectionModel().getSelectedItem();
//        int index = tableViewItensDeVenda.getSelectionModel().getSelectedIndex();
//        ordemServico.getItensDeVenda().remove(index);
//        observableListItensDeOrdemServico = FXCollections.observableArrayList(ordemServico.getItensDeVenda());
//        tableViewItensDeVenda.setItems(observableListItensDeVenda);
//
//        textFieldValor.setText(String.format("%.2f", ordemServico.getTotal()));
//    }
//
//    //validar entrada de dados do cadastro
//    private boolean validarEntradaDeDados() {
//        String errorMessage = "";
//
//        if (comboBoxClientes.getSelectionModel().getSelectedItem() == null) {
//            errorMessage += "Cliente inválido!\n";
//        }
//
//        if (datePickerData.getValue() == null) {
//            errorMessage += "Data inválida!\n";
//        }
//
//        if (observableListItensDeOrdemServico == null) {
//            errorMessage += "Itens de ordemServico inválidos!\n";
//        }
//
//        DecimalFormat df = new DecimalFormat("0.00");
//        try {
//            textFieldDesconto.setText(df.parse(textFieldDesconto.getText()).toString());
//        } catch (ParseException ex) {
//            errorMessage += "A taxa de desconto está incorreta! Use \",\" como ponto decimal.\n";
//        }
//
//        if (errorMessage.length() == 0) {
//            return true;
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erro no cadastro");
//            alert.setHeaderText("Campos inválidos, por favor corrija...");
//            alert.setContentText(errorMessage);
//            alert.show();
//            return false;
//        }
//    }
}
