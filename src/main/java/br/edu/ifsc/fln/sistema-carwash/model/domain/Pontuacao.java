package domain;

public class Pontuacao {
    private int quantidade;

    public Pontuacao() {
        this.quantidade = 0;
    }

    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int adicionar(int quantidade) {
        this.quantidade += quantidade;
        return this.quantidade; //retornando o valor total
    }

    public int subtrair(int quantidade) {
        this.quantidade -= quantidade;
        return this.quantidade; //retornando a quantidade que sobra
    }

    public int getSaldo(){
        return this.quantidade; //retorna o saldo
    }
}
