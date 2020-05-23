package com.piyush.blockchain.votemachine.domain.votingmahcine;

import com.piyush.blockchain.votemachine.domain.TestUtil;
import com.piyush.blockchain.votemachine.domain.crypto.VotingMachineSignature;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class MachineConfigForTest implements  MachineConfig {


    public int getDifficulty() {
        return difficulty;
    }

    public Integer getMachineNumber() {
        return machineNumber;
    }

    public PublicKey getPubicKey() {
        return pubicKey;
    }

    public List<Candidate> getCandidateList() {
        return candidateList;
    }

    private int difficulty;
    private Integer machineNumber;
    private PublicKey pubicKey;
    private List<Candidate> candidateList;



    public MachineConfigForTest() {
        VotingMachineSignature votingMachineSignature = new VotingMachineSignature();
        this.difficulty = TestUtil.difficulty;
        this.machineNumber = TestUtil.machineNumber;
        this.pubicKey = votingMachineSignature.getPublicKey();
        this.candidateList = new ArrayList<>();
        candidateList.add(Candidate.of(1, "First Candidate"));
        candidateList.add(Candidate.of(2, "Second Candidate"));
        candidateList.add(Candidate.of(3, "Third Candidate"));
    }
}
