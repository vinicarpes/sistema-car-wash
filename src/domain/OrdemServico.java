package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdemServico {
    private long numero;
    private double total;
    private LocalDate agenda;
    private double desconto = 0.0;
    private List<ItemOS> itensOS = new ArrayList<>();
    private Veiculo veiculo;

    public OrdemServico(long numero, double total, LocalDate agenda){
        this.numero = numero;
        this.total = total;
        this.agenda = agenda;
    }

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

    public double calcularServico(){
        total =0;
        for(ItemOS itemOS: itensOS){
            total += itemOS.getValorServico();
        }
        total -=desconto;

        return total;
    }

    public void add(ItemOS itemOS){
        itensOS.add(itemOS);
        itemOS.setOrdemServico(this);
    }

    public void atualizarPontuacao(){
        for (ItemOS itemOS: itensOS){
        veiculo.getCliente().getPontuacao().adicionar(itemOS.getServico().getPontos());
        }
    }

    public void remove(ItemOS itemOS){
        itensOS.remove(itemOS);
        itemOS.setOrdemServico(null);
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public List<ItemOS> getItensOS() {
        return itensOS;
    }

}
