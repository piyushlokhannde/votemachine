package com.piyush.blockchain.votemachine.service.machine;



import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import com.piyush.blockchain.votemachine.domain.processingunit.InvalidVotingDatException;
import com.piyush.blockchain.votemachine.domain.processingunit.ProcessedVote;
import com.piyush.blockchain.votemachine.domain.votingmahcine.VotingMachine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
@Slf4j
public class VotingMachineService {


    @Autowired
    private Function<String, VotingMachine> votingMachineFunction;


    public ProcessedVote castTheVote(VotingData votingData) {

        try {
            return votingMachineFunction.apply(votingData.getMachineId()).addVote(votingData);
        } catch (InvalidVotingDatException e) {
          log.error(String.format("Error in casting the vote %s", e.getMessage()), e);
          throw new RuntimeException("Error in casting your vote");
        }
    }

}
