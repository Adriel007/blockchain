package com.blockchain.controller;

import com.blockchain.services.Block;
import com.blockchain.services.Blockchain;
import com.blockchain.services.Minerador;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/node")
public class Node {
    private Blockchain blockchain;

    public Node() {
        blockchain = new Blockchain();
    }

    @PostMapping("/addBlock")
    public String addBlock(@RequestBody String data) {
        Block newBlock = new Block(blockchain.getLatestBlock().getIndex() + 1, System.currentTimeMillis(), data, blockchain.getLatestBlock().getHash());
        Minerador minerador = new Minerador(4); // Dificuldade 4 (ajustável)
        minerador.mineBlock(newBlock);
        blockchain.addBlock(newBlock);
        return "Novo bloco adicionado!";
    }

    @GetMapping("/isValid")
    public String isValid() {
        return blockchain.isChainValid() ? "Blockchain é válida!" : "Blockchain está corrompida!";
    }

    @GetMapping("/blockchain")
    public List<Block> getBlockchain() {
        return blockchain.getChain(); // Retorna a lista completa de blocos
    }
}
