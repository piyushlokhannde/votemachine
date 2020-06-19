package com.piyush.blockchain.votemachine.domain.processingunit;

import com.piyush.blockchain.votemachine.domain.blockminer.Block;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;



public interface BlockPersister {

    Block getLatestBlock();
    void addBlock(Block block, PublicKey publicKey);
    Block findBlock(String blockNumber);
    void persist(Block block);
    List<Block> findAllBlocks();
    List<Block> findAllBlocks(PrivateKey privateKey);
    List<Block> findAllBlockTillBlockNumber(int blockNumber, PrivateKey privateKey);



}
