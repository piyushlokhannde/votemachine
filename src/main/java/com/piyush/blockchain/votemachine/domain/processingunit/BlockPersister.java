package com.piyush.blockchain.votemachine.domain.processingunit;

import com.piyush.blockchain.votemachine.domain.blockminer.Block;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BlockPersister {

    private List<Block> blockChain = new ArrayList<>();

    public Block getLatestBlock() {
        if(!blockChain.isEmpty()) {
            return  blockChain.get(blockChain.size()-1);
        } else  {
            return null;
        }

    }

    public void addBlock(Block block, PublicKey publicKey) {
        this.blockChain.add(block);
    }

    public Block findBlock(String blockNumber) {
        return blockChain.stream().filter(x -> Integer.valueOf(blockNumber).equals(x.getBlockNumber()))
                .findFirst().orElse(null);
    }


    public void persist(Block block) {
    }

    public List<Block> findAllBlocks() {
        return  blockChain;
    }

    public List<Block> findAllBlocks(PrivateKey privateKey) {
        return  blockChain;
    }

    public List<Block> findAllBlockTillBlockNumber(int blockNumber, PrivateKey privateKey) {
        return  blockChain.stream()
                .filter(block -> block.getBlockNumber() <= blockNumber).collect(Collectors.toList());
    }
}
