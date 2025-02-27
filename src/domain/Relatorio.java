package domain;

public class Relatorio {
    public static String imprimir(Cliente cliente) {
        StringBuilder sb = new StringBuilder();

        sb.append("**********DADOS DO CLIENTE**********");
        sb.append("\n").append(cliente.getDados());
        sb.append("********LISTA DE VEICULOS***********");
        sb.append(cliente.listarVeiculos());
        sb.append("\n************************************");

        return sb.toString();
    }
}
