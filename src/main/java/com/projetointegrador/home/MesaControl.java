package com.projetointegrador.home;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component("mesaControl")
@SessionScope
public class MesaControl implements Serializable {

    private static final long serialVersionUID = 1L;

    private Mesa mesa = new Mesa();
    private List<Mesa> mesas;
    private Mesa mesaTemp;

    @Autowired
    private MesaDao mesaDao;

    @PostConstruct
    public void iniciar() {
        mesas = mesaDao.findAll();
        mesaTemp = new Mesa();
    }

    public MesaControl() {
        mesaTemp = new Mesa();
    }

    public void adicionarMesa() {
        mesa.reinicializarAtributos();
        boolean existe = false;
    
        for (Mesa m : mesas) {
            if (mesa.getNumero() == m.getNumero()) {
                existe = true;
            }
        }
    
        if (!existe) {
            mesaDao.save(mesa);
            mesas.add(mesa); 
            System.out.println("Mesa cadastrada com sucesso");
        }
        mesa = new Mesa();
    }

    public void atualizarDadosDaMesa() {
        Optional<Mesa> mesaOptional = mesaDao.findById(mesa.getNumero());
        Mesa mesaToUpdate = mesaOptional.get();
        mesaToUpdate.setOcupantes(mesa.getOcupantes());
        mesaToUpdate.setCupom(mesa.getCupom());
        mesaToUpdate.setTempoChegada(mesa.getTempoChegada());
        mesaToUpdate.setTotalPagar(mesa.getTotalPagar());
        mesaDao.save(mesaToUpdate);
        int index = mesas.indexOf(mesaToUpdate);
        mesas.set(index, mesaToUpdate);
        mesa.reinicializarAtributos();  
    }

    public Mesa getMesaTemp() {
        return mesaTemp;
    }

    public void setMesaTemp(Mesa mesaTemp) {
        this.mesaTemp = mesaTemp;
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