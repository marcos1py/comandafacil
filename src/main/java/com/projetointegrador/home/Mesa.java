package com.projetointegrador.home;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.projetointegrador.teste.ItemDoCardapio;

@Entity
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int numero;
    private boolean ocupada;
    private int ocupantes = 0;
    private String cupom = "";
    private Date tempoChegada;
    private String status = "Livre";
    private double totalPagar = 0.0;
    
@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
@JoinColumn(name = "mesa_id")
private List<ItemDoCardapio> listaDaMesa;



    // Getters and Setters
    public Integer getId() {
        return id;
    }
    public void reinicializarAtributos() {
        this.ocupada = false;
        this.ocupantes = 0; 
        this.cupom = ""; 
        this.tempoChegada = null; 
        this.totalPagar = 0.0; 
        this.status = "Livre"; 
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

    public Date getTempoChegada() {
        return tempoChegada;
    }

    public void setTempoChegada(Date tempoChegada) {
        this.tempoChegada = tempoChegada;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<ItemDoCardapio> getListaDaMesa() {
        return listaDaMesa;
    }
    public void setListaDaMesa(List<ItemDoCardapio> listaDaMesa) {
        this.listaDaMesa = listaDaMesa;
    }
    
    
    
}