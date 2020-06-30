package com.piyush.blockchain.votemachine.service.counter;

import com.piyush.blockchain.votemachine.dao.BlockPersisterDAO;
import com.piyush.blockchain.votemachine.dao.CounterConfigData;
import com.piyush.blockchain.votemachine.dao.CounterRepository;
import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import com.piyush.blockchain.votemachine.domain.processingunit.VoteCount;
import com.piyush.blockchain.votemachine.service.machine.VotingMachineService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.piyush.blockchain.votemachine.domain.TestUtil.FIRST_CANDIDATE_ID;
import static com.piyush.blockchain.votemachine.domain.TestUtil.SECOND_CANDIDATE_ID;
import static com.piyush.blockchain.votemachine.domain.TestUtil.machineNumber;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestVoteCounter {

    @Autowired
    private VoteCounter voteCounter;


    @Autowired
    private BlockPersisterDAO blockPersisterDAO;


    @Autowired
    VotingMachineService votingMachineService;

    @Autowired
    CounterRepository counterRepository;


    @Before
    public void init() {
        blockPersisterDAO.clearAllData();

    }


    @Test
    public void testCounterWithNoVotesPresent() {
        VoteCount voteCount =  voteCounter.countVotes(String.valueOf(machineNumber), getPrivateKey());

        assertThat(voteCount
                .getTotalVotesForCandidate(String.valueOf(FIRST_CANDIDATE_ID)), is(nullValue()));

    }

    @Test
    public void testCountWithAllVotesToOneCandidate() {
       VotingData votingData = VotingData
            .getBlockChainVotingData(FIRST_CANDIDATE_ID, Integer.valueOf(machineNumber));
      votingMachineService.castTheVote(votingData);
      votingMachineService.castTheVote(votingData);
      votingMachineService.castTheVote(votingData);
      votingMachineService.castTheVote(votingData);
      VoteCount voteCount =  voteCounter.countVotes(String.valueOf(machineNumber), getPrivateKey());
      assertThat(voteCount
                .getTotalVotesForCandidate(String.valueOf(FIRST_CANDIDATE_ID)), is(Integer.valueOf(4)));
    }



    @Test
    public void testMultipleCandidateVoteCount() {
        VotingData votingData = VotingData
                .getBlockChainVotingData(FIRST_CANDIDATE_ID, Integer.valueOf(machineNumber));
        votingMachineService.castTheVote(votingData);
        votingMachineService.castTheVote(votingData);

        VotingData votingData1 = VotingData
                .getBlockChainVotingData(SECOND_CANDIDATE_ID, Integer.valueOf(machineNumber));
        votingMachineService.castTheVote(votingData1);
        votingMachineService.castTheVote(votingData1);
        VoteCount voteCount =  voteCounter.countVotes(String.valueOf(machineNumber), getPrivateKey());
        assertThat(voteCount
                .getTotalVotesForCandidate(String.valueOf(FIRST_CANDIDATE_ID)), is(Integer.valueOf(2)));
        assertThat(voteCount
                .getTotalVotesForCandidate(String.valueOf(SECOND_CANDIDATE_ID)), is(Integer.valueOf(2)));
    }


    @Test
    public void testCountVotesForTillBlock() {
        VotingData votingData = VotingData
                .getBlockChainVotingData(FIRST_CANDIDATE_ID, Integer.valueOf(machineNumber));
        votingMachineService.castTheVote(votingData);
        votingMachineService.castTheVote(votingData);
        VoteCount voteCount =  voteCounter
                .countVotesForTillBlock(String.valueOf(machineNumber), getPrivateKey(), 1);
        assertThat(voteCount
                .getTotalVotesForCandidate(String.valueOf(FIRST_CANDIDATE_ID)), is(Integer.valueOf(1)));

        assertThat(voteCount
                .getTotalVotesForCandidate(String.valueOf(SECOND_CANDIDATE_ID)), is(nullValue()));

    }


    @Test
    public void testCountVotesForTillBlockForMultipleVoters() {
        VotingData votingData = VotingData
                .getBlockChainVotingData(FIRST_CANDIDATE_ID, Integer.valueOf(machineNumber));
        votingMachineService.castTheVote(votingData);
        votingMachineService.castTheVote(votingData);


        VotingData votingData1 = VotingData
                .getBlockChainVotingData(SECOND_CANDIDATE_ID, Integer.valueOf(machineNumber));
        votingMachineService.castTheVote(votingData1);
        votingMachineService.castTheVote(votingData1);


        VoteCount voteCount =  voteCounter
                .countVotesForTillBlock(String.valueOf(machineNumber), getPrivateKey(), 3);
        assertThat(voteCount
                .getTotalVotesForCandidate(String.valueOf(FIRST_CANDIDATE_ID)), is(Integer.valueOf(2)));

        assertThat(voteCount
                .getTotalVotesForCandidate(String.valueOf(SECOND_CANDIDATE_ID)), is(Integer.valueOf(1)));

    }


    private String getPrivateKey() {
        CounterConfigData counterConfigData = counterRepository.findById(Integer.valueOf(machineNumber))
                .orElseThrow(RuntimeException::new);
       return counterConfigData.getPrivateKey();
    }

}
