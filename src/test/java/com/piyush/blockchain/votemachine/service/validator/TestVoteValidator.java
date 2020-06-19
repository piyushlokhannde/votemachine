package com.piyush.blockchain.votemachine.service.validator;

import com.piyush.blockchain.votemachine.dao.BlockPersisterDAO;
import com.piyush.blockchain.votemachine.domain.blockminer.Block;
import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import com.piyush.blockchain.votemachine.domain.processingunit.ProcessedVote;
import com.piyush.blockchain.votemachine.domain.processingunit.ValidateVoteInput;
import com.piyush.blockchain.votemachine.domain.processingunit.VotingStats;
import com.piyush.blockchain.votemachine.service.VotingMachineService;
import com.piyush.blockchain.votemachine.service.validation.VoteValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.piyush.blockchain.votemachine.domain.TestUtil.FIRST_CANDIDATE_ID;
import static com.piyush.blockchain.votemachine.domain.TestUtil.SECOND_CANDIDATE_ID;
import static com.piyush.blockchain.votemachine.domain.TestUtil.machineNumber;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestVoteValidator {


    @Autowired
    VoteValidator voteValidator;

    @Autowired
    private BlockPersisterDAO blockPersisterDAO;


    @Autowired
    VotingMachineService votingMachineService;

    private ProcessedVote processedVote1;
    private ProcessedVote processedVote2;
    private ProcessedVote processedVote3;
    private ProcessedVote processedVote4;

    @Before
    public  void init() {
        blockPersisterDAO.clearAllData();
        VotingData votingData = VotingData
                .getBlockChainVotingData(FIRST_CANDIDATE_ID, Integer.valueOf(machineNumber));
        processedVote1 = votingMachineService.castTheVote(votingData);
        processedVote2 = votingMachineService.castTheVote(votingData);
        processedVote3 = votingMachineService.castTheVote(votingData);
        processedVote4 = votingMachineService.castTheVote(votingData);
    }


    @Test
    public void testValidateVoteWithSuccess() {
        ValidateVoteInput validateVoteInput = ValidateVoteInput
                .of(processedVote3.getMachineNumber(),
                        String.valueOf(processedVote3.getBlockNumber()),
                        String.valueOf(FIRST_CANDIDATE_ID),
                        processedVote3.getBlockCode());
        ProcessedVote processedVote = voteValidator.validateVote(validateVoteInput);

        assertThat(processedVote.getHash(), is(equalTo(processedVote3.getHash())));
        assertThat(processedVote.getPreviousHash(), is(equalTo(processedVote3.getPreviousHash())));
        assertThat(processedVote.getBlockNumber(), is(equalTo(processedVote3.getBlockNumber())));
        assertThat(processedVote.getBlockCode(), is(equalTo(processedVote3.getBlockCode())));
        assertThat(processedVote.getTimeStamp(), is(equalTo(processedVote3.getTimeStamp())));
        assertThat(processedVote.getNonce(), is(equalTo(processedVote3.getNonce())));
        assertThat(processedVote.isBlockValid(), is(equalTo(processedVote3.isBlockValid())));
        assertThat(processedVote.getMachineNumber(), is(equalTo(processedVote3.getMachineNumber())));

    }


    @Test
    public void testValidateVoteWithIncorrectCandidateId() {
        ValidateVoteInput validateVoteInput = ValidateVoteInput
                .of(processedVote3.getMachineNumber(),
                        String.valueOf(processedVote3.getBlockNumber()),
                        String.valueOf(SECOND_CANDIDATE_ID),
                        processedVote3.getBlockCode());
        ProcessedVote processedVote = voteValidator.validateVote(validateVoteInput);

        assertThat(processedVote.getHash(), is(not(equalTo(processedVote3.getHash()))));
        assertThat(processedVote.getPreviousHash(), is(equalTo(processedVote3.getPreviousHash())));
        assertThat(processedVote.getBlockNumber(), is(equalTo(processedVote3.getBlockNumber())));
        assertThat(processedVote.getBlockCode(), is(equalTo(processedVote3.getBlockCode())));
        assertThat(processedVote.getTimeStamp(), is(equalTo(processedVote3.getTimeStamp())));
        assertThat(processedVote.getNonce(), is(not(equalTo(processedVote3.getNonce()))));
        assertThat(processedVote.isBlockValid(), is(equalTo(processedVote3.isBlockValid())));
        assertThat(processedVote.getMachineNumber(), is(equalTo(processedVote3.getMachineNumber())));

    }

    @Test
    public void testMarkBlockValid() {
        voteValidator.markBlockValid(String.valueOf(processedVote4.getBlockNumber()),
                processedVote4.getBlockCode(), processedVote4.getMachineNumber());

        Block block = blockPersisterDAO.findBlock(String.valueOf(processedVote4.getBlockNumber()));
        assertThat(block.isValidBlock(), is(equalTo(Boolean.TRUE)));

        Block block1 = blockPersisterDAO.findBlock(String.valueOf(processedVote3.getBlockNumber()));
        assertThat(block1.isValidBlock(), is(equalTo(Boolean.FALSE)));
    }

    @Test
    public void testVotingStats() {
        voteValidator.markBlockValid(String.valueOf(processedVote4.getBlockNumber()),
                processedVote4.getBlockCode(), processedVote4.getMachineNumber());
        voteValidator.markBlockValid(String.valueOf(processedVote2.getBlockNumber()),
                processedVote2.getBlockCode(), processedVote2.getMachineNumber());

        VotingStats votingStats = voteValidator.findVotingStats(processedVote4.getMachineNumber());
        assertThat(votingStats.getTotalVotes(), is(equalTo(4L)));
        assertThat(votingStats.getValidatedVotes(), is(equalTo(2L)));

    }


}
