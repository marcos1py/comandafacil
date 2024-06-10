    package com.projetointegrador.comanda;

    import javax.persistence.*;

    @Entity
    @Table(name = "itens_comanda")
    public class ItensComanda {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String descricao;

        private int quantidade;

        @Column(nullable = false)
        private double precoUnitario;

        // Getters and setters

        public double getSubtotal() {
            return quantidade * precoUnitario;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public double getPrecoUnitario() {
            return precoUnitario;
        }

        public void setPrecoUnitario(double precoUnitario) {
            this.precoUnitario = precoUnitario;
        }
    }
