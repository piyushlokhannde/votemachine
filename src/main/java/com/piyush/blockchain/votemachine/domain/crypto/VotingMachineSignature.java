package com.piyush.blockchain.votemachine.domain.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

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


    public PrivateKey getPrivateKey(String privateKeyString) {
        try {

           PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString));
           KeyFactory kf = KeyFactory.getInstance("RSA");
           return kf.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public String encrytMessage(String msg, PublicKey publicKey) {
        if(publicKey == null) {
            return msg;
        }
        String encryptMsg = null;
        try {

            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptMsg =  Base64.getEncoder().encodeToString(cipher.doFinal(msg.getBytes()));
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e.getMessage());
        } catch (BadPaddingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e.getMessage());
        }

        return  encryptMsg;

    }


    public String decryptMessage(String encrptedMsg, PrivateKey privateKey) {
        String decryptMsg = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decryptMsg = new String(cipher.doFinal(Base64.getDecoder().decode(encrptedMsg.getBytes())));
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e.getMessage());
        } catch (BadPaddingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e.getMessage());
        }
        return  decryptMsg;
    }




}
