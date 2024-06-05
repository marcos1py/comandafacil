package com.projetointegrador.cardapio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemCardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public String nome;
    public double precoUnitario;
    public String descricao;

    @ManyToOne
    private Cardapio cardapio;

    public Integer getId() {
        return id;
    }


    
    public void setId(Integer id) {
        this.id = id;
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

    public Cardapio getCardapio() {
        return cardapio;
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
    }



    public String getDescricao() {
        return descricao;
    }



    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
