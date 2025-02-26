package domain;

public class ImpressaoOS {
     public static String imprimirOS(OrdemServico os){
         StringBuilder sb = new StringBuilder();

         sb.append("ORDEM DE SERVIÇO N° ").append(os.getNumero())
                 .append("\n").append("Agenda: ").append(os.getAgenda())
                 .append("\n").append("Desconto: ").append(os.getDesconto())
                 .append("\n").append("Valor: ").append(os.getTotal());

         return sb.toString();
     }

}
