package com.piyush.blockchain.votemachine.domain.blockminer;

import com.piyush.blockchain.votemachine.domain.crypto.VotingMachineSignature;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TestClass {

    public static void main(String[] args) {

        VotingMachineSignature votingMachineSignature = new VotingMachineSignature();

        PrivateKey privateKey =  votingMachineSignature.getPrivateKey();
        PublicKey publicKey = votingMachineSignature.getPublicKey();

        String msg = "Piyush Lokhande";

        String encrypted = votingMachineSignature.encrytMessage(msg, publicKey);
        String decryptmsg = votingMachineSignature.decryptMessage(encrypted, privateKey);

        System.out.println(encrypted);
        System.out.println(decryptmsg);



    }
}
