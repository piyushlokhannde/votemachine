package com.piyush.blockchain.votemachine.controller.votingmachine;


import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import com.piyush.blockchain.votemachine.domain.processingunit.ProcessedVote;
import com.piyush.blockchain.votemachine.service.VotingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VotingMachineController {

    @Autowired
    VotingMachineService votingMachineService;


    @PostMapping(path="vote", consumes = "application/json", produces = "application/json")
    public ProcessedVote saveVote(@RequestBody VotingData votingData) {
       return votingMachineService.castTheVote(votingData);
    }


}
