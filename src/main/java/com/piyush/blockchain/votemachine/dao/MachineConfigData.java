package com.piyush.blockchain.votemachine.dao;

import com.piyush.blockchain.votemachine.domain.votingmahcine.Candidate;
import com.piyush.blockchain.votemachine.domain.votingmahcine.MachineConfig;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import java.security.KeyFactory;

import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


@Document
@Data
public class MachineConfigData implements MachineConfig {
    @Id
    private Integer machineNumber;


    private Integer difficulty;
    private String publicKey;
    private List<Candidate> candidateList;


    @Override
    public int getDifficulty() {
        return difficulty;
    }

    @Override
    public Integer getMachineNumber() {
        return machineNumber;
    }

    @Override
    public PublicKey getPubicKey() {
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(this.publicKey));
        KeyFactory keyFactory = null;
        PublicKey publicKey = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(pubKeySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e.getMessage());
        }

        return publicKey;
    }

    @Override
    public List<Candidate> getCandidateList() {
        return candidateList;
    }


}
