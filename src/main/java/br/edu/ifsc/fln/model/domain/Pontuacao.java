package br.edu.ifsc.fln.model.domain;

public class Pontuacao {
    private int quantidade = 0;

    public void adicionar(int qtd){
        this.quantidade += qtd;
    }

    public int remover(int qtd){
        if(this.quantidade > qtd && this.quantidade > 0){
            return this.quantidade -= qtd;
        }
        throw  new RuntimeException("Erro ao remover pontuação.");
    }

    public int saldo(){
        return this.quantidade;
    }

}
