package com.blockchain.services;


import java.security.MessageDigest;


public class Block {
    private int index; // índice do bloco na cadeia
    private long timestamp; // timestamp de criação
    private String data; // dados armazenados no bloco (pode ser uma transação, por exemplo)
    private String previousHash; // hash do bloco anterior, garantindo encadeamento
    private String hash; // hash do bloco atual, definido na criação do bloco

    public Block(int index, long timestamp, String data, String previousHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    // Método para calcular o hash com base nas informações do bloco
    public String calculateHash() {
        String input = index + Long.toString(timestamp) + data + previousHash;
        return applySHA256(input); // Função auxiliar para calcular o hash (definida abaixo)
    }

    // Método para aplicar o algoritmo SHA-256 (algoritmo de hash criptográfico)
    private String applySHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
