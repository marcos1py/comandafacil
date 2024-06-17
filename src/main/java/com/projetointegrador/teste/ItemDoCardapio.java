package com.projetointegrador.teste;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.CriteriaBuilder.In;

import com.projetointegrador.home.Mesa;

import javax.persistence.*;

@Table(name = "item_do_cardapio")
@Entity
public class ItemDoCardapio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;
    private String description;
    private String image;
    private double price;
    private String category;
    public Integer quantity;

    @ManyToOne
    private Mesa mesa;

    public ItemDoCardapio() {
    }

    public ItemDoCardapio(int id, String code, String name, String description, String image, double price,
            String category) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.category = category;
    }

    @Override
    public ItemDoCardapio clone() {
        return new ItemDoCardapio(getId(), getCode(), getName(), getDescription(), getImage(), getPrice(),
                getCategory());
    }

    public void reinicia() {
        this.code = "";
        this.name = "";
        this.description = "";
        this.price = 0.0;
        this.category = "";
        this.quantity = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ItemDoCardapio other = (ItemDoCardapio) obj;
        if (code == null) {
            return other.code == null;
        } else {
            return code.equals(other.code);
        }
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Mesa getMesa() {
        return mesa;
    }
}
