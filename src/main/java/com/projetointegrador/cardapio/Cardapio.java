package com.projetointegrador.cardapio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    
    @OneToMany(mappedBy = "cardapio", cascade = CascadeType.ALL)
    private List<ItemCardapio> itens = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ItemCardapio> getItens() {
        return itens;
    }

    public void setItens(List<ItemCardapio> itens) {
        this.itens = itens;
    }

    public void adicionarItem(ItemCardapio item) {
        itens.add(item);
        item.setCardapio(this);
    }

    public void removerItem(ItemCardapio item) {
        itens.remove(item);
        item.setCardapio(null);
    }
}
