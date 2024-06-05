package com.projetointegrador.comanda;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class ItemComanda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private BigDecimal precoUnitario;
    private int quantidade;
    private BigDecimal subtotal;

    public ItemComanda() {
    }

    public ItemComanda(String descricao, BigDecimal precoUnitario) {
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.quantidade = 1;
        this.subtotal = precoUnitario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        calcularSubtotal();
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    private void calcularSubtotal() {
        this.subtotal = this.precoUnitario.multiply(new BigDecimal(this.quantidade));
    }
}

