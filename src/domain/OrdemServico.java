package domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdemServico {
    private long numero;
    private double total;
    private LocalDate agenda;
    private double desconto;
    private List<ItemOS> itensOS = new ArrayList<>();

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getAgenda() {
        return agenda;
    }

    public void setAgenda(LocalDate agenda) {
        this.agenda = agenda;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double calcularServio(){
        total =0;
        for(ItemOS itemOS: itensOS){
            total += itemOS.getValorServico();
        }
        total -=desconto;

        return total;
    }

    public void add(ItemOS itemOS){
        itensOS.add(itemOS);
    }

    public void remove(ItemOS itemOS){
        itensOS.remove(itemOS);
    }

}
