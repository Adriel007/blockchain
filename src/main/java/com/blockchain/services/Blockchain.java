package com.blockchain.services;


import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;

    public Blockchain() {
        chain = new ArrayList<>();
        chain.add(createGenesisBlock()); // Gera o bloco inicial (genesis block)
    }

    public List<Block> getChain() {
        return chain;
    }

    public void setChain(List<Block> chain) {
        this.chain = chain;
    }

    // Cria o bloco inicial
    private Block createGenesisBlock() {
        return new Block(0, System.currentTimeMillis(), "Genesis Block", "0");
    }

    // Retorna o último bloco da cadeia
    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    // Adiciona um novo bloco à cadeia, após calcular o hash e validá-lo
    public void addBlock(Block newBlock) {

        newBlock.setPreviousHash(getLatestBlock().getHash());
        newBlock.setHash(newBlock.calculateHash());
        chain.add(newBlock);
    }

    // Valida a integridade da blockchain
    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            // Verifica se o hash foi alterado
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }
            // Verifica se o bloco atual aponta corretamente para o anterior
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }


}
