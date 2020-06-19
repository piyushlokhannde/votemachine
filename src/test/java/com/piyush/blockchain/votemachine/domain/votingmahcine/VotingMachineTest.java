package com.piyush.blockchain.votemachine.domain.votingmahcine;


import com.piyush.blockchain.votemachine.domain.ApplicationDate;
import com.piyush.blockchain.votemachine.domain.blockminer.BlockMiner;
import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import com.piyush.blockchain.votemachine.domain.processingunit.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static com.piyush.blockchain.votemachine.domain.TestUtil.fixedTime;
import static org.hamcrest.CoreMatchers.*;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class VotingMachineTest {

    private VotingMachine votingMachine;
    private MachineConfig machineConfig;
    private ProcessingUnit processingUnit;
    BlockPersister blockPersister;


    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
       ApplicationDate.INSTANCE.setFixedTime(fixedTime);
       machineConfig = new MachineConfigForTest();
       BlockMiner blockMiner = new BlockMiner(machineConfig.getDifficulty());
       blockPersister = new InMemoryblockPersister();
       processingUnit =  new ProcessingUnit(blockMiner, machineConfig.getMachineNumber(), blockPersister);
       votingMachine =  new VotingMachine(machineConfig, processingUnit);
    }

    @Test
    public void testInitializeVotingMachine() {
      assertThat(votingMachine.getMachineNumber(), equalTo(Integer.valueOf(1)));
      assertThat(votingMachine,  is(notNullValue()));
      assertThat(votingMachine.getProcessingUnit(),  is(notNullValue()));
      assertThat(votingMachine.getPublicKey(), is(notNullValue()));
      assertThat(votingMachine.getDifficulty(), is(equalTo(1)));
      assertThat(votingMachine.getCandidates().size(),  is(equalTo(3)));
    }


    @Test
    public void testMachineIdIsIncorrect() throws InvalidVotingDatException {
        exception.expect(InvalidVotingDatException.class);
        exception.expectMessage("Machine with id 111 does not exists");
        VotingData votingData = VotingData.getBlockChainVotingData(machineConfig
                .getCandidateList().get(0).getCandidateId(), 111);
        votingMachine.addVote(votingData);
    }


    @Test
    public void testCandidateIdIsIncorrect() throws InvalidVotingDatException {
        exception.expect(InvalidVotingDatException.class);
        exception.expectMessage("Candidate with id 111 does not exists");
        VotingData votingData = VotingData.getBlockChainVotingData(111, machineConfig.getMachineNumber());
        votingMachine.addVote(votingData);
    }

    @Test
    public void testAddVoteToVotingMachine() throws InvalidVotingDatException {
        VotingData votingData = VotingData.getBlockChainVotingData(2, machineConfig.getMachineNumber());
        ProcessedVote processedVote = votingMachine.addVote(votingData);
        String latestHash = processedVote.getHash();
        assertThat(processedVote, notNullValue());
        assertThat(latestHash, is(equalTo("005a9a2ae1d3af8581301e2348fd94b0c939e2a516a4a8798d4fd09b08c2944e")));
    }


}
