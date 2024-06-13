package com.projetointegrador.teste;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.projetointegrador.home.Mesa;
import com.projetointegrador.home.MesaControl;
import com.projetointegrador.home.MesaDao;

@Component("cardapioControler")
@SessionScope
public class CardapioControler implements Serializable {
    public int quatidadeTemp;
    public Comanda comanda = new Comanda();
    private static final long serialVersionUID = 1L;
    private List<ItemDoCardapio> itensDoCardapio;
    private ItemDoCardapio selecionarItemDoCardapio;
    private List<ItemDoCardapio> selecionarItensDoCardapio;
    private List<ItemDoCardapio> listaTempDaMesa; // Lista temporária para os itens da mesa
    private ItemDoCardapio itemDoCardapio;
    private List<ItemDoCardapio> filteredItensCardapio; // Lista filtrada para exibir resultados da busca
    private String searchKeyword;

    @Autowired
    private MesaDao mesaDao;

    private MesaControl mesaControl;

    @Autowired
    private ItemCardapioDao1 itemCardapioDao1;

    @PostConstruct
    public void init() {
        itensDoCardapio = itemCardapioDao1.findAll();
        this.selecionarItensDoCardapio = new ArrayList<>();
        this.listaTempDaMesa = new ArrayList<>();
        filteredItensCardapio = new ArrayList<>(itensDoCardapio);
        itemDoCardapio = new ItemDoCardapio(); // Initialize itemDoCardapio

    }

    public void addItensMesaTemp() {
        boolean itemExists = false;
        for (ItemDoCardapio item : listaTempDaMesa) {
            if (item.getName().equals(itemDoCardapio.getName())) {
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            ItemDoCardapio newItem = new ItemDoCardapio();
            newItem.setName(itemDoCardapio.getName());
            newItem.setQuantity(itemDoCardapio.getQuantity());
            newItem.setPrice(itemDoCardapio.getPrice());


            listaTempDaMesa.add(newItem);
            for (ItemDoCardapio item : listaTempDaMesa) {
                System.out.println(item.getQuantity());
            }

        } else {
            System.out.println("Item with the same name already exists in the list.");
        }

        // Reset itemDoCardapio
        itemDoCardapio = new ItemDoCardapio();
        reseta();

        for (ItemDoCardapio item : listaTempDaMesa) {
            System.out.println(item.getQuantity());
        }
    }

    public void search() {
        filteredItensCardapio = itensDoCardapio.stream()
                .filter(item -> item.getName().toLowerCase().contains(searchKeyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void reseta() {
        itemDoCardapio.reinicia();
    }

    public List<ItemDoCardapio> getItensDoCardapio() {
        return itensDoCardapio;
    }

    public ItemDoCardapio getSelecionarItemDoCardapio() {
        return selecionarItemDoCardapio;
    }

    public void setSelecionarItemDoCardapio(ItemDoCardapio selecionarItemDoCardapio) {
        this.selecionarItemDoCardapio = selecionarItemDoCardapio;
    }

    public int getQuatidadeTemp() {
        return quatidadeTemp;
    }

    public void setQuatidadeTemp(int quatidadeTemp) {
        this.quatidadeTemp = quatidadeTemp;
    }

    public List<ItemDoCardapio> getSelecionarItensDoCardapio() {
        return selecionarItensDoCardapio;
    }

    public void setSelecionarItensDoCardapio(List<ItemDoCardapio> selecionarItensDoCardapio) {
        this.selecionarItensDoCardapio = selecionarItensDoCardapio;
    }

    public void openNew() {
        this.selecionarItemDoCardapio = new ItemDoCardapio();
    }

    public void saveProduct() {
        if (this.selecionarItemDoCardapio.getCode() == null) {
            this.selecionarItemDoCardapio.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            this.itensDoCardapio.add(this.selecionarItemDoCardapio);
            itemCardapioDao1.save(this.selecionarItemDoCardapio);
            atualizarItensCardapio();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ItemDoCardapio Added"));
        } else {
            itemCardapioDao1.save(this.selecionarItemDoCardapio);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ItemDoCardapio Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:productScroller");
    }

    public void deleteProduct() {
        itemCardapioDao1.delete(this.selecionarItemDoCardapio); // Remover do banco de dados
        this.itensDoCardapio.remove(this.selecionarItemDoCardapio);
        this.selecionarItensDoCardapio.remove(this.selecionarItemDoCardapio);
        filteredItensCardapio.remove(this.selecionarItemDoCardapio); // Remover da lista filtrada também

        this.selecionarItemDoCardapio = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ItemDoCardapio Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-itensDoCardapio");
    }

    private void atualizarItensCardapio() {
        itensDoCardapio = itemCardapioDao1.findAll();
        filteredItensCardapio = new ArrayList<>(itensDoCardapio); // Atualizar a lista filtrada também
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedProducts()) {
            int size = this.selecionarItensDoCardapio.size();
            return size > 1 ? size + " itensDoCardapio selected" : "1 product selected";
        }

        return "Delete";
    }

    public boolean hasSelectedProducts() {
        return this.selecionarItensDoCardapio != null && !this.selecionarItensDoCardapio.isEmpty();
    }

    public void deleteSelectedProducts() {
        for (ItemDoCardapio item : this.selecionarItensDoCardapio) {
            itemCardapioDao1.delete(item); // Remover do banco de dados
        }
        this.itensDoCardapio.removeAll(this.selecionarItensDoCardapio);
        this.selecionarItensDoCardapio = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Products Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-itensDoCardapio");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }

    public List<ItemDoCardapio> getFilteredItensCardapio() {
        return filteredItensCardapio;
    }

    public void setFilteredItensCardapio(List<ItemDoCardapio> filteredItensCardapio) {
        this.filteredItensCardapio = filteredItensCardapio;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public List<ItemDoCardapio> getListaTempDaMesa() {
        return listaTempDaMesa;
    }

    public void setListaTempDaMesa(List<ItemDoCardapio> listaTempDaMesa) {
        this.listaTempDaMesa = listaTempDaMesa;
    }

    public ItemDoCardapio getItemDoCardapio() {
        return itemDoCardapio;
    }

    public void setItemDoCardapio(ItemDoCardapio itemDoCardapio) {
        this.itemDoCardapio = itemDoCardapio;
    }

}
