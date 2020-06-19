package com.piyush.blockchain.votemachine.domain.votingmahcine;

import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import com.piyush.blockchain.votemachine.domain.processingunit.InvalidVotingDatException;
import com.piyush.blockchain.votemachine.domain.processingunit.ProcessedVote;
import com.piyush.blockchain.votemachine.domain.processingunit.ProcessingUnit;
import lombok.Getter;


import java.security.PublicKey;
import java.util.List;
import java.util.Objects;

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



    public ProcessedVote addVote(VotingData votingData)  throws InvalidVotingDatException {

       if(Objects.isNull(votingData.getCandidateId()) || !isCandidateExists(votingData.getCandidateId())) {
            throw new InvalidVotingDatException(String
                    .format("Candidate with id %s does not exists", votingData.getCandidateId()));
        }

        if(Objects.isNull(votingData.getMachineId()) || machineNumber != Integer.valueOf(votingData.getMachineId())) {
            throw new InvalidVotingDatException(String
                    .format("Machine with id %s does not exists", votingData.getMachineId()));
        }
        return processingUnit.processVote(votingData, publicKey);
    }


    private boolean isCandidateExists(String candidiateId) {
        Integer canID = Integer.valueOf(candidiateId);
        return this.candidates.stream().anyMatch(s -> s.getCandidateId() == canID);
    }
}
