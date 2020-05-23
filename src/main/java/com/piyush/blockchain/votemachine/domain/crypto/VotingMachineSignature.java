package com.piyush.blockchain.votemachine.domain.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class VotingMachineSignature {

    private final static Logger LOG = LoggerFactory.getLogger(VotingMachineSignature.class);

    private Cipher cipher;
//https://mkyong.com/java/java-asymmetric-cryptography-example/
    private KeyFactory keyFactory;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public VotingMachineSignature() {
        KeyPairGenerator keyGen = null;
        try {
            this.cipher = Cipher.getInstance("RSA");
            keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(1024, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e.getMessage());
        } catch (NoSuchProviderException e) {
            LOG.error(e.getMessage());
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }







}
