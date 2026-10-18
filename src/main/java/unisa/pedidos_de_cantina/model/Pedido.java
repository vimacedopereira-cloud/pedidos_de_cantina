package unisa.pedidos_de_cantina.model;

import java.time.LocalDateTime;

public class Pedido {

    private Long id;
    private Cliente cliente;
    private Produto produto;
    private Double precoRegistrado;
    private LocalDateTime data;
    private String situacao;

    // Construtor vazio
    public Pedido() {
    }

    // Construtor com todos os campos
    public Pedido(Long id, Cliente cliente, Produto produto, Double precoRegistrado, LocalDateTime data, String situacao) {
        this.id = id;
        this.cliente = cliente;
        this.produto = produto;
        this.precoRegistrado = precoRegistrado;
        this.data = data;
        this.situacao = situacao;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    
    public Double getPrecoRegistrado() { return precoRegistrado; }
    public void setPrecoRegistrado(Double precoRegistrado) { this.precoRegistrado = precoRegistrado; }
    
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    
    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }
}