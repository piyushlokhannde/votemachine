package com.piyush.blockchain.votemachine.config;


import com.piyush.blockchain.votemachine.dao.MachineConfigData;
import com.piyush.blockchain.votemachine.dao.MachineConfigRepository;
import com.piyush.blockchain.votemachine.domain.blockminer.BlockMiner;
import com.piyush.blockchain.votemachine.domain.crypto.VotingMachineSignature;
import com.piyush.blockchain.votemachine.domain.processingunit.BlockPersister;
import com.piyush.blockchain.votemachine.domain.processingunit.ProcessingUnit;
import com.piyush.blockchain.votemachine.domain.votingmahcine.MachineConfig;
import com.piyush.blockchain.votemachine.domain.votingmahcine.VotingMachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.function.Function;

@Configuration
public class DomainIntegration {

    @Autowired
    BlockPersister blockPersister;

    @Autowired
    MachineConfigRepository machineConfigRepository;


   @Bean
   public Function<String, ProcessingUnit> processingUnitBeanFactory() {
        return machineNumber -> getProcessingUnit(machineNumber);
   }

    @Bean
    @Scope(value = "prototype")
    public ProcessingUnit getProcessingUnit(String machineNumber) {
        return new ProcessingUnit(getBlockMiner(machineNumber),
                Integer.valueOf(machineNumber), blockPersister);
    }


   // @Bean
   // public Function<String, BlockMiner> blockMinerBeanFactory() {
   //     return machineNumber -> getBlockMiner(machineNumber);
   // }


    @Bean
    @Scope(value = "prototype")
    public BlockMiner getBlockMiner(String machineNumber) {
       MachineConfigData machineConfigData = machineConfigRepository.findById(Integer.valueOf(machineNumber))
               .orElseThrow(IllegalArgumentException::new);
        return new BlockMiner(machineConfigData.getDifficulty());

    }

    @Bean
    public Function<String, VotingMachine> votingMachineBeanFactory() {
        return machineNumber -> getVotingMachine(machineNumber);
    }

    @Bean
    @Scope(value="prototype")
   public VotingMachine getVotingMachine(String machineNumber) {
        MachineConfigData machineConfigData = machineConfigRepository.findById(Integer.valueOf(machineNumber))
                .orElseThrow(IllegalArgumentException::new);
        return  new VotingMachine(machineConfigData, getProcessingUnit(machineNumber));
   }




}
