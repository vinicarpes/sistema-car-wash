/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author User
 */
public class Cor {
    private long id;
    private String nome;
    
    public Cor(long id, String nome){
        this.nome = nome;
        this.id = id;
    }
    public Cor(String nome){
        this.nome = nome;
    }
    public Cor(){
   }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "\n\nCor \nID - " + id + "\nCor - " + nome;
    }
    
    
}
