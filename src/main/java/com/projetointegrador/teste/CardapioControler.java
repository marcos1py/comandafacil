package com.projetointegrador.teste;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.projetointegrador.compras.Vendas;
import com.projetointegrador.compras.VendasDao;
import com.projetointegrador.home.Mesa;
import com.projetointegrador.home.MesaControl;
import com.projetointegrador.home.MesaDao;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.shaded.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.Paths;


@Component("cardapioControler")
@SessionScope
public class CardapioControler implements Serializable {
    public int quatidadeTemp;
    private static final long serialVersionUID = 1L;
    private List<ItemDoCardapio> itensDoCardapio;
    private List<ItemDoCardapio> selecionarItensDoCardapio;
    public static List<ItemDoCardapio> listaTempDaMesa; // Lista temporária para os itens da mesa
    private ItemDoCardapio itemDoCardapio;
    private List<ItemDoCardapio> filteredItensCardapio; // Lista filtrada para exibir resultados da busca
    private String searchKeyword;
    public static int numeroDaMesa;
    

    private UploadedFile file;
    private ItemDoCardapio selecionarItemDoCardapio;


    private static List<Vendas> listaDeVendas;

    public static int valorTotalTELA;

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
        this.listaDeVendas = vendasDao.findAll();
    }

    
    public void handleFileUpload(FileUploadEvent event) {
        this.file = event.getFile();
        if (file != null) {
            System.out.println("File uploaded: " + file.getFileName());
        } else {
            System.out.println("File upload failed, file is null.");
        }
    }
    
    public void upload() {

        if (file != null) {
            System.out.println("File uploaded: " + file.getFileName());
        } else {
            System.out.println("File upload failed, file is null.");
        }
    }
    
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    

    public void addItensMesaTemp(Mesa mesa) {
        Integer idDaMesa = mesa.getId();
        boolean itemUpdated = false;

        for (Comanda comanda : comandaDao.findAll()) {
            if (comanda.getIdDaMesa().equals(idDaMesa) && comanda.getNome().equals(itemDoCardapio.getName())) {
                comanda.setQuantidade(comanda.getQuantidade() + itemDoCardapio.getQuantity());
                comandaDao.save(comanda);
                calcularValorTotalTELA();
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

    public void criarListaDeVendas() {
        this.listaDeVendas = vendasDao.findAll();
    }

    public void calcularValorTotalTELA() {
        int valorTotal = 0;
        valorTotalTELA = 0;
        for (ItemDoCardapio item : listaTempDaMesa) {
            valorTotal += item.getPrice() * item.getQuantity();
        }
        valorTotalTELA = valorTotal;
    }

    public void finalizarVenda(Mesa mesaAtual) {

        for (Comanda comanda : comandaDao.findAll()) {
            if (comanda.getIdDaMesa() == mesaAtual.getId()) {
                Vendas venda = new Vendas();
                LocalDateTime agora = LocalDateTime.now();
                venda.setDataFinal(agora);
                venda.setDataInicio(mesaAtual.getTempoChegada());

                venda.setFormaPagamento(comanda.getTipoPagamento());
                venda.setAlimento(comanda.getNome());
                venda.setValorTotal(comanda.getPreco() * comanda.getQuantidade());
                venda.setNumeroDaMesa(mesaAtual.getNumero());
                venda.getFormaPagamento();
                vendasDao.save(venda);
                setListaDeVendas(vendasDao.findAll());
                ;
                comandaDao.delete(comanda);
            }
        }

        // resetar os dados daquela mesa
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

    public static List<Vendas> getListaDeVendas() {
        return listaDeVendas;
    }

    public static void setListaDeVendas(List<Vendas> listaDeVendas) {
        CardapioControler.listaDeVendas = listaDeVendas;
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
            this.selecionarItemDoCardapio.setImage("comida.jpg");
            this.selecionarItemDoCardapio.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            this.itensDoCardapio.add(this.selecionarItemDoCardapio);
            itemCardapioDao1.save(this.selecionarItemDoCardapio);
            atualizarItensCardapio();
        } else {
            itemCardapioDao1.save(this.selecionarItemDoCardapio);
        }

    }

    public void deleteProduct() {
        itemCardapioDao1.delete(this.selecionarItemDoCardapio); // Remover do banco de dados
        this.itensDoCardapio.remove(this.selecionarItemDoCardapio);
        this.selecionarItensDoCardapio.remove(this.selecionarItemDoCardapio);
        filteredItensCardapio.remove(this.selecionarItemDoCardapio); // Remover da lista filtrada também

        this.selecionarItemDoCardapio = null;
    }

    public void deleteItemDaMesa(ItemDoCardapio item) {
        for (Comanda comanda : comandaDao.findAll()) {
            if (comanda.getIdDaMesa() == mesaControl.getMesa().getId() && comanda.getNome().equals(item.getName())) {
                comandaDao.delete(comanda);
                break;
            }
        }
        listaTempDaMesa.remove(item);
        calcularValorTotalTELA();
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

    // Getters e setters para valorTotalTELA, se necessário
    public int getValorTotalTELA() {
        return valorTotalTELA;
    }

    public void setValorTotalTELA(int valorTotalTELA) {
        this.valorTotalTELA = valorTotalTELA;
    }

    public void deleteSelectedProducts() {
        for (ItemDoCardapio item : this.selecionarItensDoCardapio) {
            itemCardapioDao1.delete(item); // Remover do banco de dados
        }
        this.itensDoCardapio.removeAll(this.selecionarItensDoCardapio);
        this.selecionarItensDoCardapio = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Products Removed"));
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
