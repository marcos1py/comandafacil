package com.projetointegrador.comanda;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.projetointegrador.cardapio.ItemCardapio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "comandaBean")
@ViewScoped
public class ComandaBean implements Serializable {

    private ItemCardapio itemSelecionado;
    private List<ItemCardapio> itensCardapio;
    private List<ItemCardapio> itensComanda;
    private ItemCardapio selectedItemCardapio;
    private double total;

    public ComandaBean() {
        itemSelecionado = new ItemCardapio();
        itensCardapio = new ArrayList<>();
        itensComanda = new ArrayList<>();
        // Inicialize os itens do cardápio
    }

    public void adicionarItemComanda() {
        if (selectedItemCardapio != null) {
            itensComanda.add(selectedItemCardapio);
            total += selectedItemCardapio.getPrecoUnitario();
            selectedItemCardapio = null; // Limpa a seleção
        }
    }

    public void excluirItem(ItemCardapio item) {
        itensComanda.remove(item);
        total -= item.getPrecoUnitario();
    }

    // Getters e Setters

    public ItemCardapio getItemSelecionado() {
        return itemSelecionado;
    }

    public void setItemSelecionado(ItemCardapio itemSelecionado) {
        this.itemSelecionado = itemSelecionado;
    }

    public List<ItemCardapio> getItensCardapio() {
        return itensCardapio;
    }

    public void setItensCardapio(List<ItemCardapio> itensCardapio) {
        this.itensCardapio = itensCardapio;
    }

    public List<ItemCardapio> getItensComanda() {
        return itensComanda;
    }

    public void setItensComanda(List<ItemCardapio> itensComanda) {
        this.itensComanda = itensComanda;
    }

    public ItemCardapio getSelectedItemCardapio() {
        return selectedItemCardapio;
    }

    public void setSelectedItemCardapio(ItemCardapio selectedItemCardapio) {
        this.selectedItemCardapio = selectedItemCardapio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
