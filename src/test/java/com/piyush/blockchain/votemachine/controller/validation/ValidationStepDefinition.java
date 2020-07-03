package com.piyush.blockchain.votemachine.controller.validation;

import com.piyush.blockchain.votemachine.controller.ControllerTest;


import com.piyush.blockchain.votemachine.controller.machine.data.VotingDataResponse;
import com.piyush.blockchain.votemachine.controller.validation.data.BlockValidRequest;
import com.piyush.blockchain.votemachine.controller.validation.data.ValidateVoteInputRequest;

import com.piyush.blockchain.votemachine.domain.blockminer.Block;
import com.piyush.blockchain.votemachine.domain.processingunit.VotingStats;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import static com.piyush.blockchain.votemachine.domain.TestUtil.FIRST_CANDIDATE_ID;
import static com.piyush.blockchain.votemachine.domain.TestUtil.machineNumber;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@Slf4j
public class ValidationStepDefinition extends ControllerTest {

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
            assertTrue(votingDataResponseResponse.equals(votingDataResponse));
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
            assertFalse(votingDataResponseResponse.equals(votingDataResponse));

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Then("^voter marked the block valid block (\\d+)$")
    public void voterMarkedTheBlockValidBlock(int blockNumber)  {
        VotingDataResponse votingDataResponse = resultVotingResponse.get(blockNumber);
        BlockValidRequest blockValidRequest =  new BlockValidRequest();
        blockValidRequest.setBlockCode(votingDataResponse.getBlockCode());
        blockValidRequest.setBlockNumber(String.valueOf(votingDataResponse.getBlockNumber()));
        blockValidRequest.setMachineNumber(String.valueOf(votingDataResponse.getMachineNumber()));
        try {
            restTemplate.postForEntity(getURL("vote/valid"), blockValidRequest, Void.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    @And("^block is marked with valid with block number (\\d+)$")
    public void blockIsMarkedWithValidWithBlockNumber(int blockNumber) {
       Block block  = super.blockPersisterDAO.findBlock(String.valueOf(blockNumber));
        assertTrue(block.isValidBlock());
    }

    @And("^total votes are (\\d+) and validate votes are (\\d+)$")
    public void totalVotesAreAndValidateVotesAre(int totoalVotes, int validatedVotes)  {

        try {
            ResponseEntity<VotingStats> responseEntity = restTemplate
                    .getForEntity(getURL("vote/stats/"+machineNumber), VotingStats.class);
            VotingStats votingStats = responseEntity.getBody();
            assertThat(totoalVotes, is(equalTo(Long.valueOf(votingStats.getTotalVotes()).intValue())));
            assertThat(validatedVotes, is(equalTo(Long.valueOf(votingStats.getValidatedVotes()).intValue())));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


}
