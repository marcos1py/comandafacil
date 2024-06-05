package com.projetointegrador.comanda;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ComandaBean implements Serializable {

    private List<ItemComanda> itensComanda;
    private BigDecimal total;

    @PostConstruct
    public void init() {
        itensComanda = new ArrayList<>();
        total = BigDecimal.ZERO;
    }

    public List<ItemComanda> getItensComanda() {
        return itensComanda;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void adicionarItem(String descricao, BigDecimal precoUnitario) {
        ItemComanda item = new ItemComanda(descricao, precoUnitario);
        itensComanda.add(item);
        calcularTotal();
    }

    public void excluirItem(ItemComanda item) {
        itensComanda.remove(item);
        calcularTotal();
    }

    private void calcularTotal() {
        total = BigDecimal.ZERO;
        for (ItemComanda item : itensComanda) {
            total = total.add(item.getSubtotal());
        }
    }
}
