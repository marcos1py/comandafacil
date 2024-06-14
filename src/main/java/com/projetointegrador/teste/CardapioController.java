package com.projetointegrador.cardapio;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.List;

@Controller
@SessionScope
public class CardapioController implements Serializable {

    @Autowired
    private ItemCardapioDao itemCardapioDao;

    private ItemCardapio selectedItem;
    private List<ItemCardapio> itensDoCardapio;

    public List<ItemCardapio> getItensDoCardapio() {
        if (itensDoCardapio == null) {
            itensDoCardapio = itemCardapioDao.findAll();
        }
        return itensDoCardapio;
    }

    public ItemCardapio getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(ItemCardapio selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void uploadImage(FileUploadEvent event) {
        if (selectedItem == null) {
            selectedItem = new ItemCardapio();
            }
        }

    public void saveProduct() {
        if (selectedItem != null) {
            itemCardapioDao.save(selectedItem);
            itensDoCardapio = itemCardapioDao.findAll(); // Atualiza a lista de itens
            selectedItem = null; // Limpa o item selecionado após salvar
        }
    }

    public void deleteProduct() {
        if (selectedItem != null) {
            itemCardapioDao.delete(selectedItem);
            itensDoCardapio = itemCardapioDao.findAll(); // Atualiza a lista de itens
            selectedItem = null; // Limpa o item selecionado após deletar
        }
    }
}
