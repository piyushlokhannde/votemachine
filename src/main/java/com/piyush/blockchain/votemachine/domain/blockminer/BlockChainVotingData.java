package com.piyush.blockchain.votemachine.domain.blockminer;


public class BlockChainVotingData  implements VotingData {

    private Integer candidateId;
    private Integer machineId;


    public BlockChainVotingData(Integer candidateId,  Integer machineId) {
        this.candidateId = candidateId;
        this.machineId = machineId;
    }

    @Override
    public String toString() {
        return candidateId + machineId.toString();
    }

    @Override
    public String getCandidateId() {
        return candidateId.toString();
    }

    @Override
    public String getMachineId() {
        return machineId.toString();
    }
}
