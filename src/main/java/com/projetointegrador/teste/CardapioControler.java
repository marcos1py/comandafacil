package com.projetointegrador.teste;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

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

import com.projetointegrador.compras.Vendas;
import com.projetointegrador.compras.VendasDao;
import com.projetointegrador.home.Mesa;
import com.projetointegrador.home.MesaControl;
import com.projetointegrador.home.MesaDao;

@Component("cardapioControler")
@SessionScope
public class CardapioControler implements Serializable {
    public int quatidadeTemp;
    private static final long serialVersionUID = 1L;
    private List<ItemDoCardapio> itensDoCardapio;
    private ItemDoCardapio selecionarItemDoCardapio;
    private List<ItemDoCardapio> selecionarItensDoCardapio;
    public static List<ItemDoCardapio> listaTempDaMesa; // Lista temporária para os itens da mesa
    private ItemDoCardapio itemDoCardapio;
    private List<ItemDoCardapio> filteredItensCardapio; // Lista filtrada para exibir resultados da busca
    private String searchKeyword;
    public static int numeroDaMesa;

    @Autowired
    private MesaDao mesaDao;

    @Autowired
    private VendasDao vendasDao;
    

    @Autowired
    private MesaControl mesaControl;

    @Autowired
    private ComandaDao comandaDao;

    private Comanda comanda;

    @Autowired
    private ItemCardapioDao1 itemCardapioDao1;

    @PostConstruct
    public void init() {
        itensDoCardapio = itemCardapioDao1.findAll();
        this.selecionarItensDoCardapio = new ArrayList<>();
        this.listaTempDaMesa = new ArrayList<>();
        filteredItensCardapio = new ArrayList<>(itensDoCardapio);
        itemDoCardapio = new ItemDoCardapio(); // Initialize itemDoCardapio
        this.comanda = new Comanda();
        criarLista(mesaControl.numeroDaMesa);
    }

    public void addItensMesaTemp(Mesa mesa) {
        Integer idDaMesa = mesa.getId();
        boolean itemUpdated = false;

        for (Comanda comanda : comandaDao.findAll()) {
            if (comanda.getIdDaMesa().equals(idDaMesa) && comanda.getNome().equals(itemDoCardapio.getName())) {
                comanda.setQuantidade(comanda.getQuantidade() + itemDoCardapio.getQuantity());
                comandaDao.save(comanda);
                itemUpdated = true;
                break;
            }
        }

        if (!itemUpdated) {
            Comanda newComanda = new Comanda();
            newComanda.setIdDaMesa(idDaMesa);
            newComanda.setNome(itemDoCardapio.getName());
            newComanda.setPreco(itemDoCardapio.getPrice());
            newComanda.setQuantidade(itemDoCardapio.getQuantity());
            comandaDao.save(newComanda);
        }

        listaTempDaMesa.clear();
        for (Comanda comanda : comandaDao.findAll()) {
            if (comanda.getIdDaMesa().equals(idDaMesa)) {
                ItemDoCardapio temp = new ItemDoCardapio();
                temp.setName(comanda.getNome());
                temp.setPrice(comanda.getPreco());
                temp.setQuantity(comanda.getQuantidade());
                listaTempDaMesa.add(temp);
            }
        }

        itemDoCardapio = new ItemDoCardapio();
        reseta();

        for (ItemDoCardapio item : listaTempDaMesa) {
            System.out.println(item.getQuantity());
        }
    }

    public void finalizarVenda(Mesa mesaAtual) {

        for (Comanda comanda : comandaDao.findAll()) {
            if (comanda.getIdDaMesa() == mesaAtual.getId()) {
                Vendas venda = new Vendas();
                venda.setData(LocalDate.now());
                venda.setFormaPagamento("Dinheiro");
                venda.setValorTotal(comanda.getPreco() * comanda.getQuantidade());
                venda.setNumeroDaMesa(mesaAtual.getId());
                vendasDao.save(venda);
                comandaDao.delete(comanda);
            }
        }

        //resetar os dados daquela mesa
        for (Mesa mesa : mesaDao.findAll()) {
            if (mesa.getId() == mesaAtual.getId()) {
                mesa.reinicializarAtributos();
                mesaDao.save(mesa);
                mesaControl.mesa = mesa;
                mesaControl.setMesas(mesaDao.findAll());
            }
        }

    }

    public void criarLista(Integer idMesa) {
        System.out.println(idMesa);
        listaTempDaMesa.clear();
        for (Comanda comanda : comandaDao.findAll()) {
            if (comanda.getIdDaMesa() == idMesa) {
                ItemDoCardapio temp = new ItemDoCardapio();
                temp.setName(comanda.getNome());
                temp.setPrice(comanda.getPreco());
                temp.setQuantity(comanda.getQuantidade());
                listaTempDaMesa.add(temp);
            }
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

    public void resetarLista() {
        this.listaTempDaMesa.clear();
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

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

}
