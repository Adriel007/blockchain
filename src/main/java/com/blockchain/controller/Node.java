package com.blockchain.controller;

import com.blockchain.services.Block;
import com.blockchain.services.Blockchain;
import com.blockchain.services.Minerador;
import com.blockchain.services.Wallet;
import com.blockchain.services.Client;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
        Block newBlock = new Block(blockchain.getLatestBlock().getIndex() + 1, System.currentTimeMillis(), data,
                blockchain.getLatestBlock().getHash());
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

    @GetMapping("/block/{index}")
    public ResponseEntity<?> getBlockByIndex(@PathVariable int index) {
        Block block = blockchain.getBlockByIndex(index);
        if (block == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bloco não encontrado para o índice fornecido.");
        }
        return ResponseEntity.ok(block);
    }

    @GetMapping("/createWallet")
    public ResponseEntity<?> createWallet(@RequestParam("name") String name,
            @RequestParam("password") String password) {
        try {
            Client client = new Client("username", "password");
            Wallet wallet = new Wallet();
            client.addWallet(wallet);
            return ResponseEntity.ok(client);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.err.println("Erro ao criar o cliente: " + e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao criar o cliente.");
        }
    }
    /*
     * TERMINAR
     * 
     * @PostMapping("/accessWallet")
     * public ResponseEntity<?> accessWallet(@RequestParam("name") String name,
     * 
     * @RequestParam("password") String password) {
     * try {
     * Client client = new Client("username", "password");
     * return ResponseEntity.ok(client);
     * } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
     * System.err.println("Erro ao criar o cliente: " + e.getMessage());
     * return ResponseEntity.badRequest().body("Erro ao criar o cliente.");
     * }
     * 
     * }
     */

}
