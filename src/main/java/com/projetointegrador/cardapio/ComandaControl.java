package com.projetointegrador.cardapio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

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
    }

    public void salvar() {
        itemCardapioDao.save(itemCardapio);
        itemCardapio = new ItemCardapio();  
        itensCardapio = itemCardapioDao.findAll(); 
    }

    public void deletar(ItemCardapio item) {
        itemCardapioDao.delete(item);
        itensCardapio.remove(item);
    }

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
}
