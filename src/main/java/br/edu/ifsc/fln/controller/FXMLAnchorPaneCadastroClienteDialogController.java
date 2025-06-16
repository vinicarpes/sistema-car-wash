package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.domain.Cliente;
import br.edu.ifsc.fln.model.domain.PessoaFisica;
import br.edu.ifsc.fln.model.domain.PessoaJuridica;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FXMLAnchorPaneCadastroClienteDialogController implements Initializable {

    @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private DatePicker dpDataNascimento;

    @FXML
    private TextField tfDoc;

    @FXML
    private Label lbDoc;

    @FXML
    private Label lbInscricaoEstadual;

    @FXML
    private TextField tfInscricaoEstadual;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfCelular;

    @FXML
    private TextField tfEmail;

    @FXML
    private Label lbDataNascimento;

    @FXML
    private ChoiceBox<String> cbNatureza;

    private Stage dialogStage;
    private boolean btConfirmarClicked = false;
    private Cliente cliente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbNatureza.getItems().addAll("Pessoa Fisica", "Pessoa Juridica");
        cbNatureza.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configurarCampos(newValue)
        );
        lbDataNascimento.setVisible(false);
        dpDataNascimento.setVisible(false);

        lbInscricaoEstadual.setDisable(true);
        tfInscricaoEstadual.setDisable(true);

    }

    private void configurarCampos(String natureza) {
        boolean isPessoaFisica = "Pessoa Fisica".equals(natureza);

        lbDoc.setText(isPessoaFisica ? "CPF:" : "CNPJ:");
        tfDoc.setVisible(true);
        lbDataNascimento.setVisible(isPessoaFisica);
        dpDataNascimento.setVisible(isPessoaFisica);

        lbInscricaoEstadual.setVisible(!isPessoaFisica);
        tfInscricaoEstadual.setVisible(!isPessoaFisica);
        lbInscricaoEstadual.setDisable(isPessoaFisica);
        tfInscricaoEstadual.setDisable(isPessoaFisica);

    }

    public boolean isBtConfirmarClicked() {
        return btConfirmarClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;

        if (cliente != null) {

        cbNatureza.setValue(cliente instanceof PessoaFisica ? "Pessoa Fisica" : "Pessoa Juridica");
        tfNome.setText(cliente.getNome());
        tfCelular.setText(cliente.getCelular());
        tfEmail.setText(cliente.getEmail());

        cbNatureza.setDisable(true);

        if (cliente instanceof PessoaFisica) {
            PessoaFisica pf = (PessoaFisica) cliente;
            tfDoc.setText(pf.getCpf());
            dpDataNascimento.setValue(pf.getDataNascimento());
        } else if (cliente instanceof PessoaJuridica) {
            PessoaJuridica pj = (PessoaJuridica) cliente;
            tfDoc.setText(pj.getCnpj());
            tfInscricaoEstadual.setText(pj.getInscricaoEstadual());
        }
        }
        configurarCampos(cbNatureza.getValue());
    }

    @FXML
    public void handleBtConfirmar() {
        if (validarEntradaDeDados()) {
            if (cbNatureza.getValue().equals("Pessoa Fisica")) {
                PessoaFisica pf = cliente instanceof PessoaFisica ? (PessoaFisica) cliente : new PessoaFisica();

                pf.setNome(tfNome.getText());
                pf.setCpf(tfDoc.getText());
                pf.setCelular(tfCelular.getText());
                pf.setEmail(tfEmail.getText());
                pf.setDataNascimento(dpDataNascimento.getValue());

                cliente = pf;

            } else if (cbNatureza.getValue().equals("Pessoa Juridica")) {
                PessoaJuridica pj = cliente instanceof PessoaJuridica ? (PessoaJuridica) cliente : new PessoaJuridica();

                pj.setNome(tfNome.getText());
                pj.setCelular(tfCelular.getText());
                pj.setEmail(tfEmail.getText());
                pj.setCnpj(tfDoc.getText());
                pj.setInscricaoEstadual(tfInscricaoEstadual.getText());

                cliente = pj;
            }

            btConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleBtCancelar() {
        dialogStage.close();
    }

    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (tfNome.getText() == null || tfNome.getText().isEmpty()) {
            errorMessage += "Nome inválido.\n";
        }

        if (tfDoc.getText() == null || tfDoc.getText().isEmpty()) {
            errorMessage += (cbNatureza.getValue().equals("Pessoa Fisica") ? "CPF inválido.\n" : "CNPJ inválido.\n");
        }

        if (cbNatureza.getValue() == null) {
            errorMessage += "Selecione a natureza (Pessoa Física ou Jurídica).\n";
        } else if (cbNatureza.getValue().equals("Pessoa Fisica")) {
            if (dpDataNascimento.getValue() == null) {
                errorMessage += "Data de nascimento inválida.\n";
            }
            if (tfCelular.getText() == null || tfCelular.getText().isEmpty()) {
                errorMessage += "Celular inválido.\n";
            }
            if (tfEmail.getText() == null || tfEmail.getText().isEmpty()) {
                errorMessage += "Email inválido.\n";
            }
        } else if (cbNatureza.getValue().equals("Pessoa Juridica")) {
            if (tfInscricaoEstadual.getText() == null || tfInscricaoEstadual.getText().isEmpty()) {
                errorMessage += "Inscrição Estadual inválida.\n";
            }
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Corrija os campos inválidos!");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
}
