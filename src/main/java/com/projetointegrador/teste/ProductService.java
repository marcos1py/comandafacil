package com.projetointegrador.teste;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
@Named
@ApplicationScoped
public class ProductService {

    private List<ItemDoCardapio> itensDoCardapio;

    @PostConstruct
    public void init() {
        itensDoCardapio = new ArrayList<>();
        itensDoCardapio.add(new ItemDoCardapio(1000, "f230fh0g3", "BomBom", "ItemDoCardapio Description", "bamboo-watch.jpg", 65,
                "Bebidas"));
        itensDoCardapio.add(new ItemDoCardapio(1001, "nvklal433", "cachorro quente", "ItemDoCardapio Description", "black-watch.jpg", 72,
                "Bebidas"));
        itensDoCardapio.add(new ItemDoCardapio(1002, "zz21cz3c1", "pastel", "ItemDoCardapio Description", "blue-band.jpg", 79,
                "Alcool"));
        itensDoCardapio.add(new ItemDoCardapio(1003, "244wgerg2", "agua", "ItemDoCardapio Description", "blue-t-shirt.jpg", 29,
                "Almoco"));
        itensDoCardapio.add(new ItemDoCardapio(1004, "h456wer53", "coca", "ItemDoCardapio Description", "bracelet.jpg", 15,
                "Bebidas"));
    }

    public List<ItemDoCardapio> getProducts() {
        return new ArrayList<>(itensDoCardapio);
    }

    public List<ItemDoCardapio> getProducts(int size) {

        if (size > itensDoCardapio.size()) {
            Random rand = new Random();

            List<ItemDoCardapio> randomList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                int randomIndex = rand.nextInt(itensDoCardapio.size());
                randomList.add(itensDoCardapio.get(randomIndex));
            }

            return randomList;
        }

        else {
            return new ArrayList<>(itensDoCardapio.subList(0, size));
        }

    }

    public List<ItemDoCardapio> getClonedProducts(int size) {
        List<ItemDoCardapio> results = new ArrayList<>();
        List<ItemDoCardapio> originals = getProducts(size);
        for (ItemDoCardapio original : originals) {
            results.add(original.clone());
        }

        for (ItemDoCardapio product : results) {
            product.setCode(UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        }

        return results;
    }
}