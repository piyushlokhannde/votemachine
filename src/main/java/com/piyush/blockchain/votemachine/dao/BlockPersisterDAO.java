package com.piyush.blockchain.votemachine.dao;

import com.piyush.blockchain.votemachine.domain.blockminer.Block;
import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import com.piyush.blockchain.votemachine.domain.crypto.VotingMachineSignature;
import com.piyush.blockchain.votemachine.domain.processingunit.BlockPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.List;

import java.util.stream.Collectors;

@Repository
public class BlockPersisterDAO implements BlockPersister {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    VotingMachineSignature votingMachineSignature;

    private static String BLOCK_NUMBER = "blockNumber";




    @Override
    public Block getLatestBlock() {
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc(BLOCK_NUMBER)));
        return mongoTemplate.find(query, BlockData.class)
                .stream().findFirst().map(blockData ->convertToBlock(blockData)).orElse(null);
    }

    @Override
    public void addBlock(Block block, PublicKey publicKey) {
        BlockData blockData = convertToBlockData(block, publicKey);
        mongoTemplate.insert(blockData);

    }

    @Override
    public Block findBlock(String blockNumber) {
        Query query = new Query();
        query.addCriteria(Criteria.where(BLOCK_NUMBER).is(Integer.valueOf(blockNumber)));
        List<BlockData> blockDataList =  mongoTemplate.find(query, BlockData.class);
        return blockDataList
                .stream().findFirst().map(blockData1 ->convertToBlock(blockData1)).get();
    }

    @Override
    public void persist(Block block) {
        Query query = new Query();
        query.addCriteria(Criteria.where(BLOCK_NUMBER).in(block.getBlockNumber()));
        BlockData blockData = mongoTemplate.find(query, BlockData.class).stream().findFirst().get();
        blockData.setValidBlock(block.isValidBlock());
        mongoTemplate.save(blockData);

    }

    @Override
    public List<Block> findAllBlocks() {

        return mongoTemplate.findAll(BlockData.class)
                .stream().map(blockData -> convertToBlock(blockData)).collect(Collectors.toList());
    }

    @Override
    public List<Block> findAllBlocks(PrivateKey privateKey) {
        return mongoTemplate.findAll(BlockData.class)
                .stream().map(blockData -> convertToBlock(blockData, privateKey)).collect(Collectors.toList());
    }

    @Override
    public List<Block> findAllBlockTillBlockNumber(int blockNumber, PrivateKey privateKey) {
        Query query = new Query();
        query.addCriteria(Criteria.where(BLOCK_NUMBER).lte(Integer.valueOf(blockNumber)));
        return mongoTemplate.find(query, BlockData.class).stream()
                .map(blockData -> convertToBlock(blockData, privateKey)).collect(Collectors.toList());

    }

    public void clearAllData() {
        mongoTemplate.remove(BlockData.class).all();
    }


    private Block convertToBlock(BlockData blockData) {
       VotingData votingData = VotingData
               .getBlockChainVotingData(Integer.valueOf(0),
                       Integer.valueOf(blockData.getMachineId()));
        LocalDateTime localDateTime  = new Timestamp(blockData.getTimeStamp()).toLocalDateTime();
       Block block =  Block.of(blockData.getPreviousHash(), votingData,
               localDateTime, blockData.getBlockNumber() , blockData.getBlockCode());
        block.setValidBlock(blockData.getValidBlock());
        block.setHash(blockData.getHash());
        return block;

    }


    private Block convertToBlock(BlockData blockData, PrivateKey privateKey) {

      String candidateID = votingMachineSignature.decryptMessage(blockData.getCandidateId(), privateKey);
      VotingData votingData = VotingData
               .getBlockChainVotingData(Integer.valueOf(candidateID),
                       Integer.valueOf(blockData.getMachineId()));
        LocalDateTime localDateTime  = new Timestamp(blockData.getTimeStamp()).toLocalDateTime();
       return Block.of(blockData.getPreviousHash(), votingData,
               localDateTime, blockData.getBlockNumber() , blockData.getBlockCode());

    }

    private BlockData convertToBlockData(Block block, PublicKey publicKey) {

        BlockData blockData = new BlockData();
        blockData.setBlockCode(block.getBlockCode());
        blockData.setBlockNumber(block.getBlockNumber());
        blockData.setCandidateId(votingMachineSignature.encrytMessage(block.getData().getCandidateId(), publicKey));
        blockData.setHash(block.getHash());
        blockData.setBlockNumber(block.getBlockNumber());
        blockData.setBlockCode(block.getBlockCode());
        blockData.setMachineId(block.getData().getMachineId());
        blockData.setNonce(block.getNonce());
        blockData.setPreviousHash(block.getPreviousHash());
        blockData.setValidBlock(block.isValidBlock());
        blockData.setTimeStamp(Timestamp.valueOf(block.getTimeStamp()).getTime());
        return blockData;
    }
}
