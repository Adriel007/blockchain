package com.blockchain.services;

public class Minerador {
    private int dificuldade;

    public Minerador(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    // Método de mineração que resolve o "problema" baseado na dificuldade
    public void mineBlock(Block block) {
        String target = new String(new char[dificuldade]).replace('\0', '0'); // alvo (string de zeros)

        while (!block.getHash().substring(0, dificuldade).equals(target)) {
            block.setTimestamp(System.currentTimeMillis()); // atualiza o timestamp
            block.setHash(block.calculateHash()); // recalcula o hash até o alvo ser atingido
        }
        System.out.println("Bloco minerado: " + block.getHash());
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }
}
