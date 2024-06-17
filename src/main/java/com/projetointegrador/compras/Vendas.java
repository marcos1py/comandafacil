package com.projetointegrador.compras;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;

import com.projetointegrador.home.MesaDao;
import com.projetointegrador.teste.*;

@Entity
public class Vendas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dataFinal;
    private Date dataInicio;

    private double valorTotal;
    private int numeroDaMesa;
    private String formaPagamento;
    private String alimento;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<Comanda> comandas;

    public Vendas() {
        
    }


    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<Comanda> getComandas() {
        return comandas;
    }

    public void setComandas(List<Comanda> comandas) {
        this.comandas = comandas;
    }

    public int getNumeroDaMesa() {
        return numeroDaMesa;
    }

    public void setNumeroDaMesa(int numeroDaMesa) {
        this.numeroDaMesa = numeroDaMesa;
    }
    
    // Método para formatar a data até os minutos
    public String getDataFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return formatter.format(this.getDataFinal());
    }

    // Método para formatar a data até os minutos
    public String getDataFormatadaInicio() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return formatter.format(this.getDataInicio().toInstant());
    }

    public String getAlimento() {
        return alimento;
    }



    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }



    public Date getDataInicio() {
        return dataInicio;
    }



    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
}