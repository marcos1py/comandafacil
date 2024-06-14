package com.projetointegrador.teste;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import com.projetointegrador.compras.Vendas;

@Entity
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private int quantidade;
    private String nome;
    private double preco;
    private double total;
    private Integer idDaMesa;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Vendas venda;

    public Comanda() {

    }


    public void reset(){
        this.quantidade = 0;
        this.nome = "";
        this.preco = 0.0;
        this.total = 0.0;
        this.idDaMesa = 0;
    } 

    public String getNome() {
        return nome;
    }



    public void setNome(String nome) {
        this.nome = nome;
    }



    public double getPreco() {
        return preco;
    }



    public void setPreco(double preco) {
        this.preco = preco;
    }



    public double getTotal() {
        return total;
    }



    public void setTotal(double total) {
        this.total = total;
    }



    public Integer getIdDaMesa() {
        return idDaMesa;
    }



    public void setIdDaMesa(Integer idDaMesa) {
        this.idDaMesa = idDaMesa;
    }



    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
