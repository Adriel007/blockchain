package com.blockchain.services;

import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Client {
    private static final int SALT_LENGTH = 16;
    private static final int HASH_ITERATIONS = 65536;
    private static final int HASH_KEY_LENGTH = 256;

    private String username;
    private String passwordHash;
    private byte[] salt;
    private List<String> recoveryWords;
    private List<Wallet> wallets;

    public Client(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.username = username;
        this.salt = generateSalt();
        this.passwordHash = hashPassword(password, salt);
        this.recoveryWords = generateRecoveryWords();
        this.wallets = new ArrayList<>();
    }

    private List<String> generateRecoveryWords() {
        try {
            SecureRandom random = new SecureRandom();
            byte[] entropy = new byte[16];
            random.nextBytes(entropy);

            return MnemonicCode.INSTANCE.toMnemonic(entropy);
        } catch (MnemonicException.MnemonicLengthException e) {
            throw new RuntimeException("Error to generate mnemonic words", e);
        }
    }

    public void addWallet(Wallet wallet) {
        this.wallets.add(wallet);
    }

    public boolean recoverClient(List<String> inputRecoveryWords) {
        return this.recoveryWords.equals(inputRecoveryWords);
    }

    public boolean verifyPassword(String inputPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return this.passwordHash.equals(hashPassword(inputPassword, this.salt));
    }

    private String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, HASH_ITERATIONS, HASH_KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public List<String> getRecoveryWords() {
        return recoveryWords;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.passwordHash = hashPassword(password, this.salt);
    }
}
