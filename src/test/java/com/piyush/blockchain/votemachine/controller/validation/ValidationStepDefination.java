package com.piyush.blockchain.votemachine.controller.validation;

import com.piyush.blockchain.votemachine.controller.ControllerTest;


import com.piyush.blockchain.votemachine.controller.machine.data.VotingDataResponse;
import com.piyush.blockchain.votemachine.controller.validation.data.ValidateVoteInputRequest;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;


@Slf4j
public class ValidationStepDefination extends ControllerTest {

    @Then("^voter successfully validate the vote for block (\\d+) and candidate with id (\\d+)$")
    public void voterValidateTheVoteForBlock(int blockNumber, int candidateId) {

      VotingDataResponse votingDataResponse = resultVotingResponse.get(blockNumber);

        try {
            ValidateVoteInputRequest validateVoteInputRequest = new ValidateVoteInputRequest();
            validateVoteInputRequest.setBlockCode(votingDataResponse.getBlockCode());
            validateVoteInputRequest.setBlockNumber(String.valueOf(votingDataResponse.getBlockNumber()));
            validateVoteInputRequest.setCandidateVote(String.valueOf(candidateId));
            validateVoteInputRequest.setMachineNumber(votingDataResponse.getMachineNumber());
            ResponseEntity<VotingDataResponse> responseEntity  = restTemplate
                    .postForEntity(getURL("vote/valid/block"), validateVoteInputRequest, VotingDataResponse.class);
            VotingDataResponse votingDataResponseResponse = responseEntity.getBody();
            assertTrue(votingDataResponse.equals(votingDataResponse));

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }


    @Then("^voter unsuccessfully validate the vote for block (\\d+) and candidate with id (\\d+)$")
    public void voterUnsuccessfullyValidateTheVoteForBlockAndCandidateWithId(int blockNumber, int candidateId) throws Throwable {
        VotingDataResponse votingDataResponse = resultVotingResponse.get(blockNumber);

        try {
            ValidateVoteInputRequest validateVoteInputRequest = new ValidateVoteInputRequest();
            validateVoteInputRequest.setBlockCode(votingDataResponse.getBlockCode());
            validateVoteInputRequest.setBlockNumber(String.valueOf(votingDataResponse.getBlockNumber()));
            validateVoteInputRequest.setCandidateVote(String.valueOf(candidateId));
            validateVoteInputRequest.setMachineNumber(votingDataResponse.getMachineNumber());
            ResponseEntity<VotingDataResponse> responseEntity  = restTemplate
                    .postForEntity(getURL("vote/valid/block"), validateVoteInputRequest, VotingDataResponse.class);
            VotingDataResponse votingDataResponseResponse = responseEntity.getBody();
            assertFalse(votingDataResponse.equals(votingDataResponse));

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
