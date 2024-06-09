package com.projetointegrador.home;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int numero;
    private boolean ocupada;
    private int ocupantes = 0;
    private String cupom = "";
    private String tempoChegada = "";
    private double totalPagar = 0.0;

    // Getters and Setters
    public Integer getId() {
        return id;
    }
    public void reinicializarAtributos() {
        this.ocupada = false;
        this.ocupantes = 0; // Defina aqui o valor padrão para 'ocupantes'
        this.cupom = ""; // Defina aqui o valor padrão para 'cupom'
        this.tempoChegada = ""; // Defina aqui o valor padrão para 'tempoChegada'
        this.totalPagar = 0.0; // Defina aqui o valor padrão para 'totalPagar'
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public int getOcupantes() {
        return ocupantes;
    }

    public void setOcupantes(int ocupantes) {
        this.ocupantes = ocupantes;
    }

    public String getCupom() {
        return cupom;
    }

    public void setCupom(String cupom) {
        this.cupom = cupom;
    }

    public String getTempoChegada() {
        return tempoChegada;
    }

    public void setTempoChegada(String tempoChegada) {
        this.tempoChegada = tempoChegada;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }
}
