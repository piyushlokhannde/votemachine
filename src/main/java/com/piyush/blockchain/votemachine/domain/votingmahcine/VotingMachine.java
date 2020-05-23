package com.piyush.blockchain.votemachine.domain.votingmahcine;

import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import com.piyush.blockchain.votemachine.domain.processingunit.InvalidVotingDatException;
import com.piyush.blockchain.votemachine.domain.processingunit.ProcessingUnit;
import com.piyush.blockchain.votemachine.domain.votingmahcine.Candidate;
import com.piyush.blockchain.votemachine.domain.votingmahcine.MachineConfig;
import lombok.Getter;

import java.security.PublicKey;
import java.util.List;

@Getter
public class VotingMachine {

    private Integer machineNumber;
    private List<Candidate> candidates;
    private ProcessingUnit processingUnit;
    private int difficulty;
    private PublicKey publicKey;


    public  VotingMachine(MachineConfig machineConfig, ProcessingUnit processingUnit) {
        this.candidates = machineConfig.getCandidateList();
        this.machineNumber = machineConfig.getMachineNumber();
        this.processingUnit = processingUnit;
        this.difficulty = machineConfig.getDifficulty();
        this.publicKey = machineConfig.getPubicKey();

    }



    public boolean addVote(VotingData votingData)  throws InvalidVotingDatException {

       /* if(votingData.getCandidateId().equals(null) || !isCandidateExists(votingData.getCandidateId())) {
            throw new InvalidVotingDatException(String
                    .format("Candidate with id %s does not exists", votingData.getCandidateId().intValue()));
        }

        if(votingData.getMachineId().equals(null) || !machineNumber.equals(votingData.getMachineId())) {
            throw new InvalidVotingDatException(String
                    .format("Machine with id %s does not exists", votingData.getMachineId()));
        }*/
        return false;//processingUnit.processVote(votingData);
    }


    private boolean isCandidateExists(int candidiateId) {
        return this.candidates.stream().anyMatch(s -> s.getCandidateId() == candidiateId);
    }
}
