package unisa.pedidos_de_cantina.model;

public class Produto {

    private Long id;
    private String nome;
    private Double preco;
    private Integer estoqueDisponivel;

    public Produto() {
    }

    public Produto(Long id, String nome, Double preco, Integer estoqueDisponivel) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoqueDisponivel = estoqueDisponivel;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }
    public Integer getEstoqueDisponivel() { return estoqueDisponivel; }
    public void setEstoqueDisponivel(Integer estoqueDisponivel) { this.estoqueDisponivel = estoqueDisponivel; }
}