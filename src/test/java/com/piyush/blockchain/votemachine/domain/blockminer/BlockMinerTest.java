package com.piyush.blockchain.votemachine.domain.blockminer;

import com.piyush.blockchain.votemachine.domain.ApplicationDate;
import com.piyush.blockchain.votemachine.domain.blockminer.Block;
import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import org.junit.Test;

import static com.piyush.blockchain.votemachine.domain.TestUtil.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class BlockMinerTest {




    @Test
    public void testBlockMinerInitialBlock() {
        ApplicationDate.INSTANCE.setFixedTime(fixedTime);
        VotingData votingData = VotingData.getBlockChainVotingData(0, machineNumber);
        BlockMiner blockMiner = new BlockMiner(difficulty);
        Block block = blockMiner.mineBock(votingData, null, ApplicationDate.INSTANCE.getApplicationTime());       ;
        assertThat(block.getHash(),  is(notNullValue()));
        assertThat(block.getHash(),  is(equalTo(INITIAL_HASH_FIXED_DATE_DIFF_1)));
    }

    @Test
    public void testBlockMinerWithDifficultyTwo() {
        ApplicationDate.INSTANCE.setFixedTime(fixedTime);
        VotingData votingData = VotingData.getBlockChainVotingData(0, machineNumber);
        BlockMiner blockMiner = new BlockMiner(2);
        Block block = blockMiner.mineBock(votingData, null,  ApplicationDate.INSTANCE.getApplicationTime());       ;
        assertThat(block.getHash(),  is(notNullValue()));
        assertThat(block.getHash(),  is(equalTo(INITIAL_HASH_FIXED_DATE_DIFF_2)));
        assertThat(block.getBlockCode(), is(greaterThan(99)));

    }

    @Test
    public void testBlockMinerProduceSameHashForSameInput() {
        ApplicationDate.INSTANCE.setFixedTime(fixedTime);
        BlockMiner blockMiner1 = new BlockMiner(difficulty);
        VotingData votingData1 = VotingData.getBlockChainVotingData(0, machineNumber);
        Block block1 =  blockMiner1.mineBock(votingData1, null,ApplicationDate.INSTANCE.getApplicationTime());
        String firstHash = block1.getHash();


        BlockMiner blockMiner2 = new BlockMiner(difficulty);
        VotingData votingData2 = VotingData.getBlockChainVotingData(0, machineNumber);
        Block block2 =  blockMiner2.mineBock(votingData2, null ,ApplicationDate.INSTANCE.getApplicationTime());
        String secondHash = block2.getHash();
        assertThat(firstHash,  is(equalTo(secondHash)));
    }

    @Test
    public void testLatestHashAfterTwoTransactions() {

        ApplicationDate.INSTANCE.setFixedTime(fixedTime);
        BlockMiner blockMiner = new BlockMiner(difficulty);

        VotingData votingData1 = VotingData.getBlockChainVotingData(1, machineNumber);
        Block block1 =  blockMiner.mineBock(votingData1, null, ApplicationDate.INSTANCE.getApplicationTime());

        VotingData votingData2 = VotingData.getBlockChainVotingData(2, machineNumber);
        Block block2 = blockMiner.mineBock(votingData2, block1, ApplicationDate.INSTANCE.getApplicationTime());
        String secondHash = block2.getHash();

        assertThat(secondHash,  is(equalTo("00b3acfb556de1fec406776b37797df2ff148302fe56dd7fe5254bc9e53a6310")));

    }

    @Test
    public void testSuccessBlockGenerateBlockCode() {
        ApplicationDate.INSTANCE.setFixedTime(fixedTime);
        VotingData votingData = VotingData.getBlockChainVotingData(0, machineNumber);
        BlockMiner blockMiner = new BlockMiner(1);
        Block block = blockMiner.mineBock(votingData, null, ApplicationDate.INSTANCE.getApplicationTime());
        assertThat(block.getBlockCode(), is(greaterThan(99)));
    }


    @Test
    public void testBlockNumberIsGenerateForEachBlock() {

        ApplicationDate.INSTANCE.setFixedTime(fixedTime);
        BlockMiner blockMiner = new BlockMiner(difficulty);

        VotingData votingData1 = VotingData.getBlockChainVotingData(1, machineNumber);
        Block block1 =  blockMiner.mineBock(votingData1, null, ApplicationDate.INSTANCE.getApplicationTime());


        VotingData votingData2 = VotingData.getBlockChainVotingData(2, machineNumber);
        Block block2 = blockMiner.mineBock(votingData2, block1, ApplicationDate.INSTANCE.getApplicationTime());

        VotingData votingData3 = VotingData.getBlockChainVotingData(3, machineNumber);
        Block block3 = blockMiner.mineBock(votingData3, block2, ApplicationDate.INSTANCE.getApplicationTime());


        assertThat(block1.getBlockNumber(), is(equalTo(1)));
        assertThat(block2.getBlockNumber(), is(equalTo(2)));
        assertThat(block3.getBlockNumber(), is(equalTo(3)));


    }
}
