package com.projetointegrador.teste;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.projetointegrador.cardapio.ItemCardapio;

@Named
@ViewScoped
public class CardapioControler implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ItemDoCardapio> itensDoCardapio;
    private ItemDoCardapio selecionarItemDoCardapio;
    private List<ItemDoCardapio> selecionarItensDoCardapio;
    private List<ItemDoCardapio> filteredItensCardapio; // Lista filtrada para exibir resultados da busca
    private String searchKeyword; // Palavra-chave de busca



    @Autowired
    private ItemCardapioDao1 itemCardapioDao1;

    @PostConstruct
    public void init() {
        itensDoCardapio = itemCardapioDao1.findAll();
        this.selecionarItensDoCardapio = new ArrayList<>();
        filteredItensCardapio = new ArrayList<>(itensDoCardapio); // Inicialmente, a lista filtrada é a mesma dos itens do cardápio

    }

        public void search() {
        filteredItensCardapio = itensDoCardapio.stream()
                .filter(item -> item.getName().toLowerCase().contains(searchKeyword.toLowerCase()))
                .collect(Collectors.toList());
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
            itemCardapioDao1.save(this.selecionarItemDoCardapio); // Salvar no banco de dados
            atualizarItensCardapio();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ItemDoCardapio Added"));
        } else {
            itemCardapioDao1.save(this.selecionarItemDoCardapio); // Atualizar no banco de dados
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ItemDoCardapio Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-itensDoCardapio");
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

    // Método auxiliar para atualizar a lista de itens do cardápio e a lista filtrada
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
}
