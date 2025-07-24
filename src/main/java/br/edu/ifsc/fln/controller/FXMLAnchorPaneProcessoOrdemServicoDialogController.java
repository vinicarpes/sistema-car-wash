package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.ClienteDAO;
import br.edu.ifsc.fln.model.dao.ServicoDAO;
import br.edu.ifsc.fln.model.dao.VeiculoDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.*;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @FXML
    private ComboBox<Veiculo> comboBoxVeiculos;
    @FXML
    private DatePicker datePickerData;
    @FXML
    private TableView<ItemOs> tableViewItens;
    @FXML
    private TableColumn<ItemOs, Servico> tableColumnServico;
    @FXML
    private TableColumn<Servico, Double> tableColumnValor;
    @FXML
    private TextField textFieldValor;
    @FXML
    private ComboBox<Servico> comboBoxServico;
    @FXML
    private Button buttonAdicionar;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private ContextMenu contextMenuTableView;
    @FXML
    private MenuItem contextMenuItemAtualizarQtd;
    @FXML
    private MenuItem contextMenuItemRemoverItem;
    @FXML
    private TextField textFieldDesconto;
    @FXML
    private TextField textFieldValorServico;


    private List<Veiculo> listaVeiculos;
    private List<Servico> listaServicos;
    private ObservableList<Veiculo> observableListVeiculos;
    private ObservableList<Servico> observableListServicos;
    private ObservableList<ItemOs> observableListItens;

    //atributos para manipulação de banco de dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ServicoDAO servicoDAO = new ServicoDAO();
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private OrdemServico ordemServico;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        servicoDAO.setConnection(connection);
        veiculoDAO.setConnection(connection);
        carregarComboBoxClientes();
        carregarComboBoxServicos();
        setFocusLostHandle();
        tableColumnServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
        tableColumnValor.setCellValueFactory(new PropertyValueFactory<>("valorServico"));

    }

    private void carregarComboBoxClientes() {
        listaVeiculos = veiculoDAO.listar();
        observableListVeiculos = FXCollections.observableArrayList(listaVeiculos);
        comboBoxVeiculos.setItems(observableListVeiculos);
    }

    private void carregarComboBoxServicos() {
        listaServicos = servicoDAO.listar();
        observableListServicos = FXCollections.observableArrayList(listaServicos);
        comboBoxServico.setItems(observableListServicos);
    }


    private void setFocusLostHandle() {
        // Atualiza ao perder o foco
        textFieldDesconto.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) { // perdeu o foco
                aplicarDesconto();
            }
        });

        // Atualiza ao digitar/alterar o valor
        textFieldDesconto.textProperty().addListener((observable, oldValue, newValue) -> {
            aplicarDesconto();
        });

        comboBoxServico.getSelectionModel().selectedItemProperty().addListener((obs, oldServico, newServico) -> {
            if (newServico != null) {
                Servico servicoDetalhado = servicoDAO.buscar(newServico);
                if (servicoDetalhado != null) {
                    textFieldValorServico.setText(String.format("%.2f", servicoDetalhado.getValor()));
                } else {
                    textFieldValorServico.setText("");
                }
            } else {
                textFieldValorServico.setText("");
            }
        });
    }
    private void aplicarDesconto() {
        try {
            String texto = textFieldDesconto.getText();
            if (texto != null && !texto.isEmpty()) {
                NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
                Number number = nf.parse(texto);
                double desconto = number.doubleValue();
                ordemServico.setDesconto(desconto);
            } else {
                ordemServico.setDesconto(0.0);
            }
            ordemServico.calcularTotal();
            textFieldValor.setText(String.format("%.2f", ordemServico.getTotal()));
        } catch (ParseException e) {
            textFieldValor.setText("Erro");
        }
    }



    /**
     * @return the dialogStage
     */
    public Stage getDialogStage() {
        return dialogStage;
    }

    /**
     * @param dialogStage the dialogStage to set
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * @return the buttonConfirmarClicked
     */
    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    /**
     * @param buttonConfirmarClicked the buttonConfirmarClicked to set
     */
    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

    /**
     * @return the ordemServico
     */
    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    /**
     * @param ordemServico the ordemServico to set
     */
    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
        if (ordemServico.getNumero() != null && ordemServico.getNumero() != 0L) {
            comboBoxVeiculos.getSelectionModel().select(this.ordemServico.getVeiculo());
            datePickerData.setValue(this.ordemServico.getAgenda());
            observableListItens = FXCollections.observableArrayList(this.ordemServico.getItens());
            tableViewItens.setItems(observableListItens);
            textFieldValor.setText(String.format("%.2f", this.ordemServico.getTotal()));
            textFieldDesconto.setText(String.format("%.2f", this.ordemServico.getDesconto()));
        }
    }


    @FXML
    public void handleButtonAdicionar() {
        try {
            Servico servico;
            ItemOs itemOs = new ItemOs();
            if (comboBoxServico.getSelectionModel().getSelectedItem() != null) {
                servico = comboBoxServico.getSelectionModel().getSelectedItem();
                servico = servicoDAO.buscar(servico);
                if (servico != null) {
                    NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
                    Number number = nf.parse(textFieldValorServico.getText());
                    double valor = number.doubleValue();

                    itemOs.setValorServico(valor);
                    itemOs.setServico(servico);
                    itemOs.setObservacoes(servico.getDescricao());

                    ordemServico.getItens().add(itemOs);
                    observableListItens = FXCollections.observableArrayList(ordemServico.getItens());
                    tableViewItens.setItems(observableListItens);
                    textFieldValor.setText(String.format("%.2f", ordemServico.getTotal()));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Problemas na escolha do serviço");
                    alert.setContentText("Não existe quantidade suficiente de serviços para ordem de serviço.");
                    alert.show();
                }
            }
        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro ao ler o valor do serviço");
            alert.setContentText("Por favor, insira um valor válido para o serviço (exemplo: 45,00).");
            alert.show();
        }
    }



    @FXML
    private void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            ordemServico.setAgenda(datePickerData.getValue());
            ordemServico.setDesconto(Double.parseDouble(textFieldDesconto.getText()));
            ordemServico.setVeiculo(comboBoxVeiculos.getValue());
            ordemServico.setItens(observableListItens);
            ordemServico.calcularTotal();
            textFieldValor.setText(String.format("%.2f", ordemServico.getTotal()));
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleButtonCancelar() {
        dialogStage.close();
    }

    @FXML
    void handleTableViewMouseClicked(MouseEvent event) {
        ItemOs itemOs
                = tableViewItens.getSelectionModel().getSelectedItem();
        if (itemOs == null) {
            contextMenuItemAtualizarQtd.setDisable(true);
            contextMenuItemRemoverItem.setDisable(true);
        } else {
            contextMenuItemAtualizarQtd.setDisable(false);
            contextMenuItemRemoverItem.setDisable(false);
        }

    }

    @FXML
    private void handleContextMenuItemAtualizarQtd() {
        ItemOs itemOs
                = tableViewItens.getSelectionModel().getSelectedItem();
        int index = tableViewItens.getSelectionModel().getSelectedIndex();

//        int qtdAtualizada = Integer.parseInt(inputDialog(itemOs.getQuantidade()));
//        if (itemOs.getServico().getEstoque().getQuantidade() >= qtdAtualizada) {
//            itemOs.setQuantidade(qtdAtualizada);
//            //ordemServico.getItens().set(ordemServico.getItens().indexOf(itemOs),itemOs);
//            ordemServico.getItens().set(index, itemOs);
//            itemOs.setValor(itemOs.getServico().getPreco().multiply(BigDecimal.valueOf(itemOs.getQuantidade())));
//            tableViewItens.refresh();
//            textFieldValor.setText(String.format("%.2f", ordemServico.getTotal()));
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText("Erro no estoque");
//            alert.setContentText("Não há quantidade suficiente de servicos para ordemServico.");
//            alert.show();
//        }
    }

    private String inputDialog(int value) {
        TextInputDialog dialog = new TextInputDialog(Integer.toString(value));
        dialog.setTitle("Entrada de dados.");
        dialog.setHeaderText("Atualização da quantidade de servicos.");
        dialog.setContentText("Quantidade: ");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        return result.get();
    }

    @FXML
    private void handleContextMenuItemRemoverItem() {
        ItemOs itemOs
                = tableViewItens.getSelectionModel().getSelectedItem();
        int index = tableViewItens.getSelectionModel().getSelectedIndex();
//        ordemServico.getItens().remove(index);
//        observableListItens = FXCollections.observableArrayList(ordemServico.getItens());
        tableViewItens.setItems(observableListItens);

        textFieldValor.setText(String.format("%.2f", ordemServico.getTotal()));
    }

    //validar entrada de dados do cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (comboBoxVeiculos.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cliente inválido!\n";
        }

        if (datePickerData.getValue() == null) {
            errorMessage += "Data inválida!\n";
        }

//        if (observableListItens == null) {
//            errorMessage += "Itens de ordemServico inválidos!\n";
//        }

        DecimalFormat df = new DecimalFormat("0.00");
        try {
            textFieldDesconto.setText(df.parse(textFieldDesconto.getText()).toString());
        } catch (ParseException ex) {
            errorMessage += "A taxa de desconto está incorreta! Use \",\" como ponto decimal.\n";
        }

        if (observableListItens == null || observableListItens.isEmpty()) {
            errorMessage += "A ordem de serviço deve conter ao menos um item.\n";
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }

    }
}
