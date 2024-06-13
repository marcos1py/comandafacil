package com.projetointegrador.teste;

public class Comanda extends ItemDoCardapio {

    private int quantidade;

    public Comanda() {
    }

   
    public String getName() {
        return super.getName();
    }

    public double getPrice() {
        return super.getPrice();
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
