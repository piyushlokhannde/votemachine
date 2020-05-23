package com.piyush.blockchain.votemachine.domain.blockminer;


import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class BlockMiner {

    private int difficulty;

    public BlockMiner(int difficulty) {
        this.difficulty = difficulty;
    }

    public Block mineBock(VotingData votingData, Block previousBlock, LocalDateTime timeStamp) {

       Block block = Block.of(this.getHash(previousBlock), votingData,
               timeStamp, getBlockNumber(previousBlock) , generateBlockcode());
        int tries = 0;
        boolean blockMined = false;
        while (!blockMined) {
            block.calculateHash(tries);
            if (block.getLeadingZerosCount() < this.difficulty) {
                tries++;
            } else  {
                blockMined = true;
            }
        }
        return block;
    }


    private String getHash(Block previousBlock) {
        if(previousBlock != null) {
            return previousBlock.getHash();
        } else  {
            return  "";
        }
    }

    private int getBlockNumber(Block previousBlock) {
        if(previousBlock !=null) {
            return  previousBlock.getBlockNumber()+1;
        } else  {
            return 0;
        }
    }

    private int generateBlockcode() {
        return ThreadLocalRandom.current().nextInt(100, 1000);
    }
}
