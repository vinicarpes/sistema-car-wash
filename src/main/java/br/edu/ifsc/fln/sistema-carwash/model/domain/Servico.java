package domain;

public class Servico {
    private int id;
    private String descricao;
    private double valor;
    private static int pontos;
    private ECategoria eCategoria;

    public Servico(int id, String descricao, double valor, ECategoria eCategoria) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.eCategoria = eCategoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }


    public ECategoria geteCategoria() {
        return eCategoria;
    }

    public void seteCategoria(ECategoria eCategoria) {
        this.eCategoria = eCategoria;
    }

    public static void setPontos(int pontos) {
        Servico.pontos = pontos;
    }

    public int getPontos() {
        return pontos;
    }
}
