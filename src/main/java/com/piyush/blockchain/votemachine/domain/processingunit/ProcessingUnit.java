package com.piyush.blockchain.votemachine.domain.processingunit;

import com.piyush.blockchain.votemachine.domain.*;
import com.piyush.blockchain.votemachine.domain.blockminer.Block;
import com.piyush.blockchain.votemachine.domain.blockminer.BlockMiner;
import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import org.apache.commons.lang3.ObjectUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProcessingUnit {

    private BlockMiner blockMiner;
    private BlockPersister blockPersister;
    private String machineId;
    public static String INVALID_VOTE_DATA_MSG = "Invalid Voting Data";



    public ProcessingUnit(BlockMiner blockMiner, int machineNumber, BlockPersister blockPersister) {
        this.blockMiner = blockMiner;
        this.machineId = Integer.valueOf(machineNumber).toString();
        this.blockPersister = blockPersister;
        VotingData votingData = VotingData.getBlockChainVotingData(0, machineNumber);
      //  processVote(votingData, null);
    }

    public ProcessedVote processVote(VotingData votingData, PublicKey publicKey) {
        Block block = blockMiner.mineBock(votingData, blockPersister.getLatestBlock(),
                ApplicationDate.INSTANCE.getApplicationTime());
        ProcessedVote processedVote = ProcessedVote.of(block.getPreviousHash(),
                machineId, block.getNonce(),
                block.getTimeStamp(), block.getHash(), block.getBlockNumber(), block.getBlockCode(),
                block.isValidBlock());
        blockPersister.addBlock(block, publicKey);
        return processedVote;
    }




    public ProcessedVote getLatestBlock() {
       Block block = blockPersister.getLatestBlock();

       if(Objects.nonNull(block)) {
           return ProcessedVote.of(block.getPreviousHash(),
                   this.machineId, block.getNonce(),
                   block.getTimeStamp(), block.getHash(), block.getBlockNumber(), block.getBlockCode(), block.isValidBlock());
       } else  {
           return  null;
       }


    }


    public ProcessedVote validateVote(ValidateVoteInput validateVoteInput) throws  InvalidVotingDatException {
        validateMachineNumber(validateVoteInput);
        validateBlockNumber(validateVoteInput.getBlockNumber());
        validateBlocKCode(validateVoteInput.getBlockNumber(), validateVoteInput.getBlockCode());
        VotingData votingData = VotingData
                .getBlockChainVotingData(Integer.valueOf(validateVoteInput.getCandidateVote()),
                        Integer.valueOf(validateVoteInput.getMachineNumber()));
        Block currentBlock = blockPersister.findBlock(validateVoteInput.getBlockNumber());
        Block previousBlock = blockPersister.findBlock(getPreviousBlockNumber(currentBlock.getBlockNumber()));
        Block verifiedBlock = blockMiner.mineBock(votingData, previousBlock,
                currentBlock.getTimeStamp());
        ProcessedVote verifiedVote= ProcessedVote.of(verifiedBlock.getPreviousHash(),
                machineId, verifiedBlock.getNonce(),
                verifiedBlock.getTimeStamp(), verifiedBlock.getHash(),
                verifiedBlock.getBlockNumber(), currentBlock.getBlockCode(), currentBlock.isValidBlock());

        return verifiedVote;
    }

    public void markBlockValid(String blockNumber, int blockCode) throws InvalidVotingDatException {
        validateBlockNumber(blockNumber);
        validateBlocKCode(blockNumber, blockCode);
        Block block = blockPersister.findBlock(String.valueOf(blockNumber));
        block.markBlockValid();
        blockPersister.persist(block);
    }

    public VotingStats findVotingStats() {

        List<Block> blockStream = blockPersister.findAllBlocks();
        VotingStats votingStats = new VotingStats();
        votingStats.setTotalVotes(blockStream.stream().count());
        votingStats.setValidatedVotes(blockStream.stream().filter(x-> x.isValidBlock()).count());
        return votingStats;
    }

    public VoteCount countVotes(PrivateKey privateKey) {
        VoteCount voteCount = new VoteCount();
        List<Block> blockStream = blockPersister.findAllBlocks(privateKey);
        blockStream.stream().forEach(block -> {
            voteCount.incrementVoteCount(block.getData().getCandidateId());
        });
        return voteCount;
    }

    public VoteCount countVotesForTillBlock(PrivateKey privateKey, int blockNumber) {
        VoteCount voteCount = new VoteCount();
        List<Block> blockStream = blockPersister.findAllBlockTillBlockNumber(blockNumber, privateKey);
        blockStream.stream().forEach(block -> {
            voteCount.incrementVoteCount(block.getData().getCandidateId());
        });
        return voteCount;
    }

    private String getPreviousBlockNumber(int blockNumber) {
        return Integer.valueOf(blockNumber-1).toString() ;
    }




    private void validateMachineNumber(ValidateVoteInput validateVoteInput) throws InvalidVotingDatException {
        if(!validateVoteInput.getMachineNumber().equals(this.machineId)) {
            throw new InvalidVotingDatException(INVALID_VOTE_DATA_MSG);
        }
    }

    private void validateBlockNumber(String blockNumber) throws InvalidVotingDatException {
      Block block = blockPersister.findBlock(blockNumber);
        if(block == null) {
            throw new InvalidVotingDatException(INVALID_VOTE_DATA_MSG);
        }
    }

    private void validateBlocKCode(String blockNumber, int blockCode) throws InvalidVotingDatException {
        Block block = blockPersister.findBlock(blockNumber);
        if(block.getBlockCode() != blockCode) {
            throw new InvalidVotingDatException(INVALID_VOTE_DATA_MSG);
        }
    }


}
