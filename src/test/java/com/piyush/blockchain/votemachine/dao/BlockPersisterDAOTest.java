package com.piyush.blockchain.votemachine.dao;


import com.piyush.blockchain.votemachine.domain.blockminer.Block;
import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import com.piyush.blockchain.votemachine.domain.crypto.VotingMachineSignature;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import static com.piyush.blockchain.votemachine.domain.TestUtil.FIRST_CANDIDATE_ID;
import static com.piyush.blockchain.votemachine.domain.TestUtil.fixedTime;
import static com.piyush.blockchain.votemachine.domain.TestUtil.machineNumber;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlockPersisterDAOTest {

    @Autowired
    private BlockPersisterDAO blockPersisterDAO;

    @Autowired
    VotingMachineSignature votingMachineSignature;


    @Autowired
    MongoTemplate mongoTemplate;




    @Before
    public void init() {
      blockPersisterDAO.clearAllData();
    }



    @Test
    public void testInsertBlockInDataBase() {
      VotingData votingData = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
       Block block = Block.of("previousHash", votingData,
          fixedTime, 1 , 1234);
       blockPersisterDAO.addBlock(block, votingMachineSignature.getPublicKey());
       Block block1 =  blockPersisterDAO.findBlock("1");
       assertThat(block1.getBlockNumber(), is(equalTo(block.getBlockNumber())));

    }


    @Test
    public void testLatestBockMethod() {
        VotingData votingData = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);


        Block block = Block.of("previousHash", votingData,
                fixedTime, 1 , 1234);
        blockPersisterDAO.addBlock(block, votingMachineSignature.getPublicKey());


        Block block1 = Block.of("previousHash", votingData,
                fixedTime, 2 , 1234);
        blockPersisterDAO.addBlock(block1, votingMachineSignature.getPublicKey());

        Block block2 = Block.of("previousHash", votingData,
                fixedTime, 3 , 1234);
        blockPersisterDAO.addBlock(block2, votingMachineSignature.getPublicKey());

        Block resultBlock = blockPersisterDAO.getLatestBlock();
        assertThat(resultBlock.getBlockNumber(), is(equalTo(3)));

    }

    @Test
    public void testPersistMethod() {
        VotingData votingData = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
        Block block = Block.of("previousHash", votingData,
                fixedTime, 1 , 1234);
        blockPersisterDAO.addBlock(block, votingMachineSignature.getPublicKey());
        block.setValidBlock(Boolean.TRUE);
        blockPersisterDAO.persist(block);

        Block block1 =  blockPersisterDAO.findBlock("1");
        assertThat(block1.isValidBlock(), is(equalTo(Boolean.TRUE)));
    }


    @Test
    public void testFindAllMethod() {
        VotingData votingData = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);


        Block block = Block.of("previousHash", votingData,
                fixedTime, 1 , 1234);
        blockPersisterDAO.addBlock(block, votingMachineSignature.getPublicKey());


        Block block1 = Block.of("previousHash", votingData,
                fixedTime, 2 , 1234);
        blockPersisterDAO.addBlock(block1, votingMachineSignature.getPublicKey());

        Block block2 = Block.of("previousHash", votingData,
                fixedTime, 3 , 1234);
        blockPersisterDAO.addBlock(block2, votingMachineSignature.getPublicKey());

        List<Block>  blockList  = blockPersisterDAO.findAllBlocks();
        assertThat(blockList, hasSize(3));
    }


    @Test
    public void testFindAllWithPrivateKeyMethod() {
        VotingData votingData = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);


        Block block = Block.of("previousHash", votingData,
                fixedTime, 1 , 1234);
        blockPersisterDAO.addBlock(block, votingMachineSignature.getPublicKey());


        Block block1 = Block.of("previousHash", votingData,
                fixedTime, 2 , 1234);
        blockPersisterDAO.addBlock(block1, votingMachineSignature.getPublicKey());

        Block block2 = Block.of("previousHash", votingData,
                fixedTime, 3 , 1234);
        blockPersisterDAO.addBlock(block2, votingMachineSignature.getPublicKey());

        List<Block>  blockList  = blockPersisterDAO.findAllBlocks(votingMachineSignature.getPrivateKey());
        assertThat(blockList, hasSize(3));

        assertThat(Integer.valueOf(blockList.get(0).getData().getCandidateId()), is(equalTo(Integer.valueOf(FIRST_CANDIDATE_ID))));
        assertThat(Integer.valueOf(blockList.get(1).getData().getCandidateId()), is(equalTo(Integer.valueOf(FIRST_CANDIDATE_ID))));
        assertThat(Integer.valueOf(blockList.get(2).getData().getCandidateId()), is(equalTo(Integer.valueOf(FIRST_CANDIDATE_ID))));
    }



    @Test
    public void testFindAllWithPrivateKeyAndBlockNumber() {
        VotingData votingData = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);


        Block block = Block.of("previousHash", votingData,
                fixedTime, 1 , 1234);
        blockPersisterDAO.addBlock(block, votingMachineSignature.getPublicKey());


        Block block1 = Block.of("previousHash", votingData,
                fixedTime, 2 , 1234);
        blockPersisterDAO.addBlock(block1, votingMachineSignature.getPublicKey());

        Block block2 = Block.of("previousHash", votingData,
                fixedTime, 3 , 1234);
        blockPersisterDAO.addBlock(block2, votingMachineSignature.getPublicKey());

        List<Block>  blockList  = blockPersisterDAO.findAllBlockTillBlockNumber(2,votingMachineSignature.getPrivateKey());
        assertThat(blockList, hasSize(2));
        assertThat(Integer.valueOf(blockList.get(0).getData().getCandidateId()), is(equalTo(Integer.valueOf(FIRST_CANDIDATE_ID))));
        assertThat(Integer.valueOf(blockList.get(1).getData().getCandidateId()), is(equalTo(Integer.valueOf(FIRST_CANDIDATE_ID))));

    }



}


