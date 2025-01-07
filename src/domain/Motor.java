/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author User
 */
public class Motor {
    private int potencia;
    private ETipoCombustivel combustivel;

    public Motor() {
    }
    
    public int getPotencia() {
        return potencia;
    }

    public ETipoCombustivel getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(ETipoCombustivel combustivel) {
        this.combustivel = combustivel;
    }

}
