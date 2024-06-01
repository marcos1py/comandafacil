package com.projetointegrador.cardapio;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.List;

@ManagedBean
@ViewScoped
public class ComandaControl {

    private ItemCardapio itemCardapio = new ItemCardapio();
    private List<ItemCardapio> itensCardapio;

    @Inject
    private ItemCardapioDao itemCardapioDao;

    @PostConstruct
    public void iniciar(){
        itensCardapio = itemCardapioDao.findAll();
    }

    public void salvar() {
            itemCardapioDao.save(itemCardapio);
            itensCardapio.add(itemCardapio); 
            itemCardapio = new ItemCardapio();
            System.out.println("Item salvo com sucesso!");
       
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
