package com.piyush.blockchain.votemachine.service.counter;

import com.piyush.blockchain.votemachine.domain.crypto.VotingMachineSignature;
import com.piyush.blockchain.votemachine.domain.processingunit.ProcessingUnit;
import com.piyush.blockchain.votemachine.domain.processingunit.VoteCount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.function.Function;

@Service
@Slf4j
public class VoteCounter {

    @Autowired
    private Function<String, ProcessingUnit> processingUnitBeanFactory;

    @Autowired
    VotingMachineSignature votingMachineSignature;

    public VoteCount countVotes(String machineNumber, String privateKey) {
        return processingUnitBeanFactory.apply(machineNumber)
                .countVotes(votingMachineSignature.getPrivateKey(privateKey));
    }


    public VoteCount countVotesForTillBlock(String machineNumber, String privateKey,  int blockNumber) {
        return this.processingUnitBeanFactory.apply(machineNumber)
                .countVotesForTillBlock(votingMachineSignature.getPrivateKey(privateKey), blockNumber);
    }
}
