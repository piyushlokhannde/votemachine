package com.piyush.blockchain.votemachine.domain.blockminer;

import java.security.PublicKey;

public interface VotingData {

    String getCandidateId();
    String getMachineId();

    static VotingData getBlockChainVotingData(Integer candidateId, Integer machineNumber) {
        return new BlockChainVotingData(candidateId, machineNumber);
    }

    static VotingData getBlockChainEncryptedData(VotingData votingData, PublicKey publicKey) {
       return  new BlockChainVotingEncryptedData(votingData, publicKey);
    }
}
