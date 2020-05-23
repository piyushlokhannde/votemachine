package com.piyush.blockchain.votemachine.domain.blockminer;


import com.piyush.blockchain.votemachine.domain.crypto.SignatureUtils;

import java.security.PublicKey;

public class BlockChainVotingEncryptedData implements VotingData {

    private String candidateId;
    private String machineId;


    public BlockChainVotingEncryptedData(VotingData votingData, PublicKey publicKey) {
        this.candidateId = SignatureUtils.encrytMessage(votingData.getCandidateId().toString() , publicKey);
        this.machineId = SignatureUtils.encrytMessage(votingData.getMachineId().toString() , publicKey);
    }

    @Override
    public String toString() {
        return candidateId + machineId;
    }

    @Override
    public String getCandidateId() {
        return candidateId;
    }

    @Override
    public String getMachineId() {
        return machineId;
    }
}
