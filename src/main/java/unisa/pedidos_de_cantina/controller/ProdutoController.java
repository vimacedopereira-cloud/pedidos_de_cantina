package unisa.pedidos_de_cantina.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unisa.pedidos_de_cantina.model.Produto;
import unisa.pedidos_de_cantina.repository.ProdutoRepository;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public ResponseEntity<String> cadastrarProduto(@RequestBody Produto produto) {
        try {
            produtoRepository.cadastrar(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar produto.");
        }
    }

    // Rota GET atualizada usando o novo método listar do repositório
    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        try {
            List<Produto> produtos = produtoRepository.listar();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}