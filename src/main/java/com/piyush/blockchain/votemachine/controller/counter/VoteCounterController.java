package com.piyush.blockchain.votemachine.controller.counter;


import com.piyush.blockchain.votemachine.domain.processingunit.VoteCount;
import com.piyush.blockchain.votemachine.service.counter.VoteCounter;
import com.piyush.blockchain.votemachine.service.counter.data.CountVotesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteCounterController {

    @Autowired
    VoteCounter voteCounter;



    @PostMapping(path="vote/count", consumes = "application/json", produces = "application/json")
    public VoteCount countVotes(@RequestBody CountVotesRequest request) {
      return  voteCounter.countVotes(request.getMachineNumber(), request.getPrivateKey());
    }

    @PostMapping(path="vote/count/{blockNumber}", consumes = "application/json", produces = "application/json")
    public VoteCount countVotesTillBlock(@RequestBody CountVotesRequest request,
                                         @PathVariable("blockNumber") Integer blockNumber) {
        return  voteCounter.countVotesForTillBlock(request.getMachineNumber(), request.getPrivateKey(), blockNumber);
    }
}
