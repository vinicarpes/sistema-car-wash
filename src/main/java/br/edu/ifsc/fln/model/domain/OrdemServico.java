package br.edu.ifsc.fln.model.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdemServico {
    private Long numero;
    private Double total;
    private LocalDate agenda;
    private Double desconto;
    private List<ItemOs> itens = new ArrayList();
    private Veiculo veiculo;

    public void calcularTotal() {
        double total = 0.0;
        if (itens != null) {
            for (ItemOs itemOs : itens) {
                Double valor = itemOs.getValorServico();
                if (valor != null) {
                    total += valor;
                }
                total = total - (total*(desconto/100));
            }
        }
        this.setTotal(total);
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDate getAgenda() {
        return agenda;
    }

    public void setAgenda(LocalDate agenda) {
        this.agenda = agenda;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public List<ItemOs> getItens() {
        return itens;
    }

    public void setItens(List<ItemOs> itens) {
        this.itens = itens;
    }
}
