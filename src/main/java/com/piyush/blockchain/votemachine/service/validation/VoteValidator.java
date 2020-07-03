package com.piyush.blockchain.votemachine.service.validation;

import com.piyush.blockchain.votemachine.domain.processingunit.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
@Slf4j
public class VoteValidator {

    @Autowired
    private Function<String, ProcessingUnit> processingUnitBeanFactory;



    public ProcessedVote validateVote(ValidateVoteInput validateVoteInput)  {
        try {
            return  processingUnitBeanFactory.apply(validateVoteInput.getMachineNumber()).validateVote(validateVoteInput);
        } catch (Exception e) {
           log.error(e.getMessage());
           throw  new RuntimeException(e.getMessage());
        }
    }

    public void markBlockValid(String blockNumber, int blockCode, String machineNumber) {
        try {
            processingUnitBeanFactory.apply(machineNumber).markBlockValid(blockNumber, blockCode);
        } catch (InvalidVotingDatException e) {
            log.error(e.getMessage());
            throw  new RuntimeException(e.getMessage());
        }
    }

    public VotingStats findVotingStats(String machineNumber) {
        return   processingUnitBeanFactory.apply(machineNumber).findVotingStats();
    }




}
