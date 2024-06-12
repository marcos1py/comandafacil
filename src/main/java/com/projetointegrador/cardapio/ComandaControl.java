package com.projetointegrador.cardapio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("comandaControl") 
@SessionScope
public class ComandaControl implements Serializable {

    private static final long serialVersionUID = 1L;

    private ItemCardapio itemCardapio = new ItemCardapio();
    private List<ItemCardapio> itensCardapio;

    @Autowired
    private ItemCardapioDao itemCardapioDao;

    @PostConstruct
    public void iniciar() {
        itensCardapio = itemCardapioDao.findAll();
        filteredItensCardapio = new ArrayList<>(itensCardapio); // Inicialmente, a lista filtrada é a mesma dos itens do cardápio
    }

    public void salvar() {
        itemCardapioDao.save(itemCardapio);
        itemCardapio = new ItemCardapio();  
        atualizarItensCardapio(); 
    }

    public void deletar(ItemCardapio item) {
        itemCardapioDao.delete(item);
        itensCardapio.remove(item);
        filteredItensCardapio.remove(item); // Remover da lista filtrada também
    }

    public void search() {
        filteredItensCardapio = itensCardapio.stream()
                .filter(item -> item.getNome().toLowerCase().contains(searchKeyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Método auxiliar para atualizar a lista de itens do cardápio e a lista filtrada
    private void atualizarItensCardapio() {
        itensCardapio = itemCardapioDao.findAll();
        filteredItensCardapio = new ArrayList<>(itensCardapio); // Atualizar a lista filtrada também
    }

    // Getters e setters
    public ItemCardapio getItemCardapio() {
        return itemCardapio;
    }

    public void setItemCardapio(ItemCardapio itemCardapio) {
        this.itemCardapio = itemCardapio;
    }

    public List<ItemCardapio> getItensCardapio() {
        return itensCardapio;
    }

    public void setItensCardapio(List<ItemCardapio> itensCardapio) {
        this.itensCardapio = itensCardapio;
    }

    public List<ItemCardapio> getFilteredItensCardapio() {
        return filteredItensCardapio;
    }

    public void setFilteredItensCardapio(List<ItemCardapio> filteredItensCardapio) {
        this.filteredItensCardapio = filteredItensCardapio;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }
}
