package com.piyush.blockchain.votemachine.service.votingmachine;

import com.piyush.blockchain.votemachine.dao.BlockPersisterDAO;
import com.piyush.blockchain.votemachine.domain.blockminer.Block;
import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import com.piyush.blockchain.votemachine.domain.processingunit.ProcessedVote;
import com.piyush.blockchain.votemachine.service.VotingMachineService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.piyush.blockchain.votemachine.domain.TestUtil.FIRST_CANDIDATE_ID;
import static com.piyush.blockchain.votemachine.domain.TestUtil.machineNumber;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestVotingMachineService {


    @Autowired
    VotingMachineService votingMachineService;

    @Autowired
    private BlockPersisterDAO blockPersisterDAO;

    @Before
    public void init() {
        blockPersisterDAO.clearAllData();
    }


    @Test
    public void testCastVoteWithValidData() {
        VotingData votingData = VotingData
                .getBlockChainVotingData(FIRST_CANDIDATE_ID, Integer.valueOf(machineNumber));
        ProcessedVote processedVote = votingMachineService.castTheVote(votingData);
        assertThat("Block Number are not matching", processedVote.getBlockNumber() == 1);
    }


    @Test
    public void testValidateChain() {
        VotingData votingData = VotingData
                .getBlockChainVotingData(FIRST_CANDIDATE_ID, Integer.valueOf(machineNumber));
        votingMachineService.castTheVote(votingData);
        votingMachineService.castTheVote(votingData);
        votingMachineService.castTheVote(votingData);
        votingMachineService.castTheVote(votingData);
        List<Block> blockList = blockPersisterDAO.findAllBlocks();
        assertThat(blockList.get(3).getPreviousHash(), is(notNullValue()));
        assertThat(blockList.get(2).getPreviousHash(), is(notNullValue()));
        assertThat(blockList.get(1).getPreviousHash(), is(notNullValue()));
        assertThat(blockList.get(3).getPreviousHash(), is(equalTo(blockList.get(2).getHash())));
        assertThat(blockList.get(2).getPreviousHash(), is(equalTo(blockList.get(1).getHash())));
        assertThat(blockList.get(1).getPreviousHash(), is(equalTo(blockList.get(0).getHash())));

    }
}
