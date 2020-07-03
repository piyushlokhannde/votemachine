package com.piyush.blockchain.votemachine.controller.counter;


import com.piyush.blockchain.votemachine.controller.ControllerTest;
import com.piyush.blockchain.votemachine.controller.machine.data.VotingDataRequest;
import com.piyush.blockchain.votemachine.controller.machine.data.VotingDataResponse;
import com.piyush.blockchain.votemachine.dao.CounterConfigData;
import com.piyush.blockchain.votemachine.dao.CounterRepository;
import com.piyush.blockchain.votemachine.domain.processingunit.VoteCount;
import com.piyush.blockchain.votemachine.domain.processingunit.VotingStats;
import com.piyush.blockchain.votemachine.service.counter.data.CountVotesRequest;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static com.piyush.blockchain.votemachine.domain.TestUtil.SECOND_CANDIDATE_ID;
import static com.piyush.blockchain.votemachine.domain.TestUtil.machineNumber;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class CounterStepDefinition extends ControllerTest {

    @Autowired
    CounterRepository counterRepository;



    @And("^vote for candidate (\\d+) is (\\d+)$")
    public void voteForCandidateIs(int candidate, int votes) {
        CountVotesRequest countVotesRequest =  new CountVotesRequest();
        countVotesRequest.setMachineNumber(String.valueOf(machineNumber));
        countVotesRequest.setPrivateKey(getPrivateKey());
        try {
            ResponseEntity<VoteCount> responseEntity  = restTemplate
                    .postForEntity(getURL("vote/count"), countVotesRequest, VoteCount.class);
            VoteCount voteCount = responseEntity.getBody();

            int votesForCandiate = voteCount.getTotalVotesForCandidate(String.valueOf(candidate));
            assertThat(votesForCandiate, is(equalTo(votes)));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @And("^vote for candidate (\\d+) is (\\d+) till block (\\d+)$")
    public void voteForCandidateIsTillBlock(int candidate, int votes, int blockNumber)  {
        CountVotesRequest countVotesRequest =  new CountVotesRequest();
        countVotesRequest.setMachineNumber(String.valueOf(machineNumber));
        countVotesRequest.setPrivateKey(getPrivateKey());
        try {
            ResponseEntity<VoteCount> responseEntity  = restTemplate
                    .postForEntity(getURL("vote/count/"+blockNumber), countVotesRequest, VoteCount.class);
            VoteCount voteCount = responseEntity.getBody();

            int votesForCandiate = voteCount.getTotalVotesForCandidate(String.valueOf(candidate));
            assertThat(votesForCandiate, is(equalTo(votes)));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    private String getPrivateKey() {
        CounterConfigData counterConfigData = counterRepository.findById(Integer.valueOf(machineNumber))
                .orElseThrow(RuntimeException::new);
        return counterConfigData.getPrivateKey();
    }



}
