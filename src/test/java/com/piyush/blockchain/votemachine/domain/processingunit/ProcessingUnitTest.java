package com.piyush.blockchain.votemachine.domain.processingunit;

import com.piyush.blockchain.votemachine.domain.ApplicationDate;
import com.piyush.blockchain.votemachine.domain.blockminer.BlockMiner;
import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import com.piyush.blockchain.votemachine.domain.crypto.VotingMachineSignature;
import com.piyush.blockchain.votemachine.domain.processingunit.*;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.PrivateKey;
import java.security.PublicKey;

import static com.piyush.blockchain.votemachine.domain.processingunit.ProcessingUnit.INVALID_VOTE_DATA_MSG;
import static com.piyush.blockchain.votemachine.domain.TestUtil.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

public class ProcessingUnitTest {

    private ProcessingUnit processingUnit;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        ApplicationDate.INSTANCE.setFixedTime(fixedTime);
        BlockMiner blockMiner = new BlockMiner(difficulty);
        VotingMachineSignature votingMachineSignature = new VotingMachineSignature();
        publicKey = votingMachineSignature.getPublicKey();
        privateKey = votingMachineSignature.getPrivateKey();
        BlockPersister blockPersister = new InMemoryblockPersister();
        processingUnit =  new ProcessingUnit(blockMiner, machineNumber, blockPersister);
    }



    @Test
    public void testInitialBlockForProcessingUnit() {
        ProcessedVote processedVote = processingUnit.getLatestBlock();
        assertThat(processedVote, is(nullValue()));
    }


    @Test
    public void testProcessingUnitForSingleVote() {
        VotingData votingData = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
        ProcessedVote vote = processingUnit.processVote(votingData, publicKey);
        String latestHash = vote.getHash();
        assertThat(vote.getBlockNumber(), is(equalTo(1)));
        assertThat(latestHash,
                is(equalTo("0432878175f32dbbb1d1aa6fe4b5be9c67a5167b1e4e90f3b612b1ae8b971a58")));
        assertThat(vote.getBlockNumber(), is(IsNull.notNullValue()));
    }


    @Test
    public void testValidateVoteForInvalidMachineNumber() throws InvalidVotingDatException {
        exception.expect(InvalidVotingDatException.class);
        exception.expectMessage(INVALID_VOTE_DATA_MSG);
        ValidateVoteInput validateVoteInput = ValidateVoteInput.of("2",
                "1", "1", 1);
        processingUnit.validateVote(validateVoteInput);
    }

    @Test
    public void testValidateVoteForInvalidBlockNumber() throws InvalidVotingDatException {
        exception.expect(InvalidVotingDatException.class);
        exception.expectMessage(INVALID_VOTE_DATA_MSG);
        ValidateVoteInput validateVoteInput = ValidateVoteInput.of("1",
                "3", "1", 1);
        processingUnit.validateVote(validateVoteInput);
    }

    @Test
    public void testValidateVoteForInvalidBlockCode() throws InvalidVotingDatException {

        VotingData votingData = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
        ProcessedVote vote = processingUnit.processVote(votingData, publicKey);

        exception.expect(InvalidVotingDatException.class);
        exception.expectMessage(INVALID_VOTE_DATA_MSG);
        ValidateVoteInput validateVoteInput = ValidateVoteInput.of("1",
                "1", "1", 1);
        processingUnit.validateVote(validateVoteInput);
    }

    @Test
    public void testValidateVoteForInvalidCandidateId() throws InvalidVotingDatException {

        VotingData votingData = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
        ProcessedVote vote = processingUnit.processVote(votingData, publicKey);

        ValidateVoteInput validateVoteInput = ValidateVoteInput.of("1",
                "1", "2", vote.getBlockCode());
        ProcessedVote validationVote =  processingUnit.validateVote(validateVoteInput);
        assertThat(vote.getTimeStamp(), is(equalTo(validationVote.getTimeStamp())));
        assertThat(vote.getPreviousHash(), is(equalTo(validationVote.getPreviousHash())));
        assertThat(vote.getNonce(), is(not(equalTo(validationVote.getNonce()))));
        assertThat(vote.getBlockNumber(), is(equalTo(validationVote.getBlockNumber())));
        assertThat(vote.getHash(), is(not(equalTo(validationVote.getHash()))));

    }

    @Test
    public void testValidateVoteForValidCandidateId() throws InvalidVotingDatException {
        VotingData votingData = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
        ProcessedVote vote = processingUnit.processVote(votingData, publicKey);

        ValidateVoteInput validateVoteInput = ValidateVoteInput.of("1",
                "1", "1", vote.getBlockCode());
        ProcessedVote validationVote =  processingUnit.validateVote(validateVoteInput);
        assertThat(vote.getTimeStamp(), is(equalTo(validationVote.getTimeStamp())));
        assertThat(vote.getPreviousHash(), is(equalTo(validationVote.getPreviousHash())));
        assertThat(vote.getNonce(), is(equalTo(validationVote.getNonce())));
        assertThat(vote.getBlockNumber(), is(equalTo(validationVote.getBlockNumber())));
        assertThat(vote.getHash(), is(equalTo(validationVote.getHash())));
    }

    @Test
    public void testMarkBlockValidated() throws InvalidVotingDatException {
        VotingData votingData = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
        ProcessedVote vote = processingUnit.processVote(votingData, publicKey);
        processingUnit.markBlockValid(String.valueOf(vote.getBlockNumber()),
                vote.getBlockCode());
        ProcessedVote processedVote = processingUnit.getLatestBlock();
        assertThat(processedVote.isBlockValid(), is(equalTo(Boolean.TRUE)));
    }

    @Test
    public void testFindValidateVotes() throws InvalidVotingDatException {

        VotingData votingData1 = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
        ProcessedVote vote1 = processingUnit.processVote(votingData1, publicKey);

        processingUnit.markBlockValid(String.valueOf(vote1.getBlockNumber()),
                vote1.getBlockCode());

        VotingData votingData2 = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
        ProcessedVote vote2 = processingUnit.processVote(votingData2, publicKey);


        processingUnit.markBlockValid(String.valueOf(vote2.getBlockNumber()),
                vote2.getBlockCode());

        VotingData votingData3 = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
        ProcessedVote vote3 = processingUnit.processVote(votingData3, publicKey);

        VotingStats votingStats = processingUnit.findVotingStats();

        assertThat(votingStats.getTotalVotes(), is(equalTo(3L)));
        assertThat(votingStats.getValidatedVotes(), is(equalTo(2L)));

    }

    @Test
    public void testCountTotalVotes() {
       VotingData votingData1 = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
       processingUnit.processVote(votingData1, publicKey);

       VotingData votingData2 = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
       processingUnit.processVote(votingData2, publicKey);

       VotingData votingData3 = VotingData.getBlockChainVotingData(SECOND_CANDIDATE_ID, machineNumber);
       processingUnit.processVote(votingData3, publicKey);

       VoteCount voteCount =  processingUnit.countVotes(privateKey);
       assertThat(voteCount.getTotalVotesForCandidate(String.valueOf(FIRST_CANDIDATE_ID)), is(equalTo(2)));
       assertThat(voteCount.getTotalVotesForCandidate(String.valueOf(SECOND_CANDIDATE_ID)), is(equalTo(1)));
    }


    @Test
    public void testCountVotesTillBlock() {

        VotingData votingData1 = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
        processingUnit.processVote(votingData1, publicKey);

        VotingData votingData2 = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
        processingUnit.processVote(votingData2, publicKey);

        VotingData votingData3 = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
        processingUnit.processVote(votingData3, publicKey);

        VotingData votingData4 = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
        processingUnit.processVote(votingData4, publicKey);

        VotingData votingData5 = VotingData.getBlockChainVotingData(FIRST_CANDIDATE_ID, machineNumber);
        processingUnit.processVote(votingData5, publicKey);


        int blockNumber = 1;
        VoteCount voteCount =  processingUnit.countVotesForTillBlock(privateKey, blockNumber);
        assertThat(voteCount.getTotalVotesForCandidate(String.valueOf(FIRST_CANDIDATE_ID)), is(equalTo(1)));

        int blockNumber2 = 2;
        VoteCount voteCount2 =  processingUnit.countVotesForTillBlock(privateKey, blockNumber2);
        assertThat(voteCount2.getTotalVotesForCandidate(String.valueOf(FIRST_CANDIDATE_ID)), is(equalTo(2)));

        int blockNumber5 = 5;
        VoteCount voteCount5 =  processingUnit.countVotesForTillBlock(privateKey, blockNumber5);
        assertThat(voteCount5.getTotalVotesForCandidate(String.valueOf(FIRST_CANDIDATE_ID)), is(equalTo(5)));

    }




}
