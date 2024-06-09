package com.projetointegrador.comanda;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ComandaBean {
    private List<ItemComanda> itensComanda = new ArrayList<>();
    private double total;

    public List<ItemComanda> getItensComanda() {
        return itensComanda;
    }

    public double getTotal() {
        total = itensComanda.stream().mapToDouble(ItemComanda::getSubtotal).sum();
        return total;
    }

    public void adicionarItemComanda(String descricao, int quantidade, double precoUnitario) {
        ItemComanda item = new ItemComanda();
        item.setDescricao(descricao);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(precoUnitario);
        item.setSubtotal(quantidade * precoUnitario);
        itensComanda.add(item);
    }

    public void excluirItem(ItemComanda item) {
        itensComanda.remove(item);
    }
}
