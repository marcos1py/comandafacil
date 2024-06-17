package com.projetointegrador.compras;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
@Component("vendasControler")
@SessionScope
public class VendasControler {

    private DonutChartModel donutModel;


    private List<Vendas> listaDaVendas;
    private Date dataInicio;
    private Date dataFim;

    private String alimentoQueMaisSaiu;
    private String mesasQueMaisSaiu;

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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public void filtrarVendas() {
        listaDaVendas = vendasDao.findByDataInicioBetween(dataInicio, dataFim);
        escreverVendasEmArquivo(listaDaVendas);
    }
    
   private void escreverVendasEmArquivo(List<Vendas> listaDaVendas) {
        String nomeArquivo = "vendas_filtradas.txt";
        
        // Mapas para contagem de frequência
        Map<Integer, Integer> contadorMesa = new HashMap<>();
        Map<String, Integer> contadorAlimento = new HashMap<>();
        
        // Contagem de frequência
        for (Vendas venda : listaDaVendas) {
            // Contagem de número da mesa
            int mesa = venda.getNumeroDaMesa();
            contadorMesa.put(mesa, contadorMesa.getOrDefault(mesa, 0) + 1);
            
            // Contagem de alimento
            String alimento = venda.getAlimento();
            contadorAlimento.put(alimento, contadorAlimento.getOrDefault(alimento, 0) + 1);
        }
        
        // Encontrar o número da mesa mais frequente
        int mesaMaisFrequente = -1;
        int maiorFrequenciaMesa = 0;
        for (Map.Entry<Integer, Integer> entry : contadorMesa.entrySet()) {
            if (entry.getValue() > maiorFrequenciaMesa) {
                maiorFrequenciaMesa = entry.getValue();
                mesaMaisFrequente = entry.getKey();
            }
        }
        this.mesasQueMaisSaiu = Integer.toString(mesaMaisFrequente);
        
        // Encontrar o alimento mais frequente
        String alimentoMaisFrequente = null;
        int maiorFrequenciaAlimento = 0;
        for (Map.Entry<String, Integer> entry : contadorAlimento.entrySet()) {
            if (entry.getValue() > maiorFrequenciaAlimento) {
                maiorFrequenciaAlimento = entry.getValue();
                alimentoMaisFrequente = entry.getKey();
            }
        }
        
        // Escrever resultados no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            writer.write("Itens vendidos:\n");
            
            // Escrever informações sobre vendas
            for (Vendas venda : listaDaVendas) {
                writer.write("Alimento: " + venda.getAlimento() + "\n");
                writer.write("Valor da venda: " + venda.getValorTotal() + "\n");
                writer.write("Numero da mesa: " + venda.getNumeroDaMesa() + "\n");
                writer.write("\n");
            }
            
            // Escrever número da mesa mais frequente
            writer.write("Número da mesa mais frequente: " + mesaMaisFrequente + "\n");
            
            // Escrever alimento mais frequente
            writer.write("Alimento mais frequente: " + alimentoMaisFrequente + "\n");
            
            System.out.println("Arquivo " + nomeArquivo + " gerado com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

public String getAlimentoQueMaisSaiu() {
    return alimentoQueMaisSaiu;
}

public void setAlimentoQueMaisSaiu(String alimentoQueMaisSaiu) {
    this.alimentoQueMaisSaiu = alimentoQueMaisSaiu;
}

public String getMesasQueMaisSaiu() {
    return mesasQueMaisSaiu;
}

public void setMesasQueMaisSaiu(String mesasQueMaisSaiu) {
    this.mesasQueMaisSaiu = mesasQueMaisSaiu;
}
    
}