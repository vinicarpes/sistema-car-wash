package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.OrdemServicoDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class FXMLAnchorPaneGraficoFaturamentoMesController implements Initializable {

    @FXML
    private BarChart<String, Double> barChart;
    @FXML
    private NumberAxis numberAxis;
    @FXML
    private CategoryAxis categoryAxis;

    private ObservableList<String> observableListMeses = FXCollections.observableArrayList();
    //atributos para manipulação do banco de dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] meses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        observableListMeses.addAll(Arrays.asList(meses));
        categoryAxis.setCategories(observableListMeses);

        ordemServicoDAO.setConnection(connection);

        Map<Integer, ArrayList<Object>> dados = ordemServicoDAO.listarFaturamentoPorMes();
        for (Map.Entry<Integer, ArrayList<Object>> dadosItem : dados.entrySet()) {
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName(String.valueOf(dadosItem.getKey())); // ano

            ArrayList<Object> valores = dadosItem.getValue();
            for (int i = 0; i < valores.size(); i += 2) {
                int mes = (int) valores.get(i);
                double faturamento = (double) valores.get(i + 1);
                series.getData().add(new XYChart.Data<>(retornaNomeMes(mes), faturamento));
            }

            barChart.getData().add(series);
        }
    }


    private String retornaNomeMes(int mes) {
        return switch (mes) {
            case 1 -> "Jan";
            case 2 -> "Fev";
            case 3 -> "Mar";
            case 4 -> "Abr";
            case 5 -> "Mai";
            case 6 -> "Jun";
            case 7 -> "Jul";
            case 8 -> "Ago";
            case 9 -> "Set";
            case 10 -> "Out";
            case 11 -> "Nov";
            case 12 -> "Dez";
            default -> "";
        };
    }


}