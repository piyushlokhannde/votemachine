package com.piyush.blockchain.votemachine.domain.votingmahcine;


import com.piyush.blockchain.votemachine.domain.ApplicationDate;
import com.piyush.blockchain.votemachine.domain.blockminer.BlockMiner;
import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import com.piyush.blockchain.votemachine.domain.processingunit.BlockPersister;
import com.piyush.blockchain.votemachine.domain.processingunit.InvalidVotingDatException;
import com.piyush.blockchain.votemachine.domain.processingunit.ProcessingUnit;
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
       blockPersister = new BlockPersister();
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
        boolean isVoteAdded  = votingMachine.addVote(votingData);
        String latestHash = null;//processingUnit.getLatestHash();
        assertThat(isVoteAdded, is(equalTo(true)));
        assertThat(latestHash, is(equalTo("0f96844c51d621787a4f784ea39e1aadc5f37fd7aa226e6337cf7690d3f81b6e")));
    }


}
