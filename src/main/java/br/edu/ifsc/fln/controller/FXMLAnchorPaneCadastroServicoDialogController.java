package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.domain.Servico;
import br.edu.ifsc.fln.model.enums.ECategoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLAnchorPaneCadastroServicoDialogController implements Initializable {
    @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private TextField tfNome;
    @FXML
    private  TextField tfPreco;

    @FXML
    private ChoiceBox<ECategoria> cbCategoria;

    private Stage dialogStage;
    private boolean btConfirmarClicked = false;
    private Servico servico;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbCategoria.getItems().addAll(ECategoria.values());
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

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
        this.tfNome.setText(servico.getDescricao());
        this.tfPreco.setText(String.valueOf(servico.getValor()));
        this.cbCategoria.setValue(servico.getCategoria());
    }


    @FXML
    public void handleBtConfirmar() {
        if (validarEntradaDeDados()) {
            servico.setDescricao(tfNome.getText());
            servico.setValor(Double.parseDouble(tfPreco.getText()));
            servico.setCategoria(cbCategoria.getValue());

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
        if (this.tfNome.getText() == null || this.tfNome.getText().isEmpty()) {
            errorMessage += "Nome inválido.\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            //exibindo uma mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Servicorija os campos inválidos!");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

}
