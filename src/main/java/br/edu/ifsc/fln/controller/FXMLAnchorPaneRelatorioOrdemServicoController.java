package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.exception.DAOException;
import br.edu.ifsc.fln.model.dao.OrdemServicoDAO;
import br.edu.ifsc.fln.model.dao.VeiculoDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Veiculo;
import br.edu.ifsc.fln.model.domain.Cliente;
import br.edu.ifsc.fln.model.domain.OrdemServico;
import br.edu.ifsc.fln.utils.AlertDialog;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class FXMLAnchorPaneRelatorioOrdemServicoController implements Initializable {

    @FXML
    private TableView<OrdemServico> tableView;
    @FXML
    private TableColumn<OrdemServico, Integer> tableColumnOrdemServicoNum;
    @FXML
    private TableColumn<OrdemServico, String> tableColumnOrdemServicoAgenda;
    @FXML
    private TableColumn<OrdemServico, BigDecimal> tableColumnOrdemServicoDesconto;
    @FXML
    private TableColumn<OrdemServico, Cliente> tableColumnOrdemServicoTotal;
    @FXML
    private TableColumn<OrdemServico, Veiculo> tableColumnOrdemServicoVeiculo;
    @FXML
    private Button buttonImprimir;

    private List<OrdemServico> listaOrdemServicos;
    private ObservableList<OrdemServico> observableListOrdemServicos;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ordemServicoDAO.setConnection(connection);
        veiculoDAO.setConnection(connection);
        ordemServicoDAO.setVeiculoDAO(veiculoDAO);
        carregarTableView();
    }

    private void carregarTableView() {
        listaOrdemServicos = ordemServicoDAO.listar();

        tableColumnOrdemServicoNum.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tableColumnOrdemServicoAgenda.setCellValueFactory(new PropertyValueFactory<>("agenda"));
        tableColumnOrdemServicoDesconto.setCellValueFactory(new PropertyValueFactory<>("desconto"));
        tableColumnOrdemServicoTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tableColumnOrdemServicoVeiculo.setCellValueFactory(new PropertyValueFactory<>("veiculo"));
        tableColumnOrdemServicoVeiculo.setCellValueFactory(new PropertyValueFactory<>("veiculo"));

        tableColumnOrdemServicoVeiculo.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Veiculo veiculo, boolean empty) {
                super.updateItem(veiculo, empty);
                if (veiculo == null || empty) {
                    setText(null);
                } else {
                    setText(veiculo.getPlaca() + " / " + veiculo.getCliente().getNome());
                }
            }
        });

        observableListOrdemServicos = FXCollections.observableArrayList(listaOrdemServicos);
        tableView.setItems(observableListOrdemServicos);
    }

    //@FXML
    public void handleImprimir() throws JRException {
        URL url = getClass().getResource("/reports/RelatorioOS.jasper");
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(url);

        //null: caso não existam filtros
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);

        //false: não deixa fechar a aplicação principal
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
        jasperViewer.setVisible(true);
    }

}