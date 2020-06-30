package com.piyush.blockchain.votemachine.domain.blockminer;


public interface VotingData {

    String getCandidateId();
    String getMachineId();

    static VotingData getBlockChainVotingData(Integer candidateId, Integer machineNumber) {
        return new BlockChainVotingData(candidateId, machineNumber);
    }

}
