package com.piyush.blockchain.votemachine.controller.machine;

import com.piyush.blockchain.votemachine.controller.ControllerTest;
import com.piyush.blockchain.votemachine.controller.machine.data.VotingDataRequest;
import com.piyush.blockchain.votemachine.controller.machine.data.VotingDataResponse;
import com.piyush.blockchain.votemachine.domain.blockminer.Block;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.piyush.blockchain.votemachine.domain.TestUtil.machineNumber;

@Slf4j
public class VotingMachineStepDefinition  extends ControllerTest{



    @Given("^there are no votes in the machine$")
    @Test
    public void thereAreNoVotesInTheMachine()  {
        blockPersisterDAO.clearAllData();
    }


    @Then("^the repository should have (\\d+) vote$")
    public void theRepositoryShouldHaveVote(int numberOfVotes) {
        List<Block> blockList =  blockPersisterDAO.findAllBlocks();
        if(!blockList.isEmpty() && blockList.size() != numberOfVotes) {
            log.error("Number of votes  are  not matching");
            throw new RuntimeException("Number of votes  are  not matching");
        }
    }


    @When("^voter give vote to candidate with id (\\d+)$")
    public void voterGiveVoteToCandidateWithId(int candidateId) {
        VotingDataRequest votingDataRequest = new VotingDataRequest();
        votingDataRequest.setMachineId(Integer.valueOf(machineNumber));
        votingDataRequest.setCandidateId(Integer.valueOf(candidateId));

        try {
            ResponseEntity<VotingDataResponse> responseEntity  = restTemplate
                    .postForEntity(getURL("vote"), votingDataRequest, VotingDataResponse.class);
           VotingDataResponse votingDataResponse = responseEntity.getBody();
            resultVotingResponse.put(votingDataResponse.getBlockNumber(), votingDataResponse);
        } catch (Exception e) {
            log.error(e.getMessage());
        }


    }


}
