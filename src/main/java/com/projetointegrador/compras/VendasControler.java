package com.projetointegrador.compras;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;



@Component("vendasControler")
@SessionScope
public class VendasControler {
    
    private List<Vendas> listaDaVendas;

    @Autowired
    private VendasDao vendasDao;

    @PostConstruct
    public void iniciar() {
        listaDaVendas = vendasDao.findAll();
    }
    public void criarListaDAVENDAS1() {
        listaDaVendas = vendasDao.findAll();
    }
    public List<Vendas> getListaDaVendas() {
        return listaDaVendas;
    }
    public void setListaDaVendas(List<Vendas> listaDaVendas) {
        this.listaDaVendas = listaDaVendas;
    }

}
