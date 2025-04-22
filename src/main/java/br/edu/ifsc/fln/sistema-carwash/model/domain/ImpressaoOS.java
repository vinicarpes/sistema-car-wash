package domain;

public class ImpressaoOS {
     public static String imprimirOS(OrdemServico os){
         StringBuilder sb = new StringBuilder();

         sb.append("****************ORDEM DE SERVIÇO N°").append(os.getNumero()).append(" ****************")
                 .append("\n").append("Agenda: ").append(os.getAgenda());

         sb.append("\n\n***DADOS CLIENTE***\n");
         sb.append(os.getVeiculo().getCliente().getDados());

         sb.append("\n\n***DADOS VEÍCULO***\n");
         sb.append(os.getVeiculo());

         sb.append("\n\n***DADOS SERVIÇOS***\n");
         sb.append(os.getItensOS());

         sb.append("\n\n").append("Desconto: ").append(os.getDesconto())
                 .append("\n").append("Valor: ").append(os.calcularServico());

         sb.append("\n*********************************************************");

         return sb.toString();
     }

}
