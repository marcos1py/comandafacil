package com.projetointegrador.home;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component("mesaControl")
@SessionScope
public class MesaControl implements Serializable {

    private static final long serialVersionUID = 1L;

    private Mesa mesa = new Mesa();
    private List<Mesa> mesas;

    @Autowired
    private MesaDao mesaDao;

    @PostConstruct
    public void iniciar() {
        mesas = mesaDao.findAll();
    }

    public void salvarMesa() {
        System.out.println("MESA TESTE : " +  mesa);
        mesaDao.save(mesa);
        mesas = mesaDao.findAll();
        mesa = new Mesa();
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public void setMesas(List<Mesa> mesas) {
        this.mesas = mesas;
    }

    


}
