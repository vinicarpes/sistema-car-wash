package br.edu.ifsc.fln.model.domain;

import br.edu.ifsc.fln.model.enums.ETipoCombustivel;

public class Motor {
    private int potencia = 0;

    private ETipoCombustivel eTipoCombustivel;

    public ETipoCombustivel getETipoCombustivel() {
        return eTipoCombustivel;
    }

    public void setETipoCombustivel(ETipoCombustivel eTipoCombustivel) {
        this.eTipoCombustivel = eTipoCombustivel;
    }

    public Motor(){
        this.potencia = 0;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

}
