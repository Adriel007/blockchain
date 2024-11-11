package com.blockchain.services;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Wallet {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Wallet() {
        generateKeyPair();
    }

    private void generateKeyPair() {
        try {
            if (this.privateKey == null && this.publicKey == null) {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                keyGen.initialize(256, random);
                KeyPair keyPair = keyGen.generateKeyPair();
                this.privateKey = keyPair.getPrivate();
                this.publicKey = keyPair.getPublic();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPrivateKey() {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public String getPublicKey() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public void setPrivateKey(String privateKeyEncoded) throws Exception {
        if (this.privateKey == null) {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            byte[] decodedKey = Base64.getDecoder().decode(privateKeyEncoded);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
            this.privateKey = keyFactory.generatePrivate(keySpec);
        } else {
            throw new IllegalStateException("Private key already exists and cannot be set again.");
        }
    }

    public void setPublicKey(String publicKeyEncoded) throws Exception {
        if (this.publicKey == null) {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            byte[] decodedKey = Base64.getDecoder().decode(publicKeyEncoded);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
            this.publicKey = keyFactory.generatePublic(keySpec);
        } else {
            throw new IllegalStateException("Public key already exists and cannot be set again.");
        }
    }

    public byte[] signData(String data) {
        try {
            Signature signature = Signature.getInstance("SHA256withECDSA");
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifySignature(String data, byte[] signatureBytes, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withECDSA");
            signature.initVerify(publicKey);
            signature.update(data.getBytes());
            return signature.verify(signatureBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
