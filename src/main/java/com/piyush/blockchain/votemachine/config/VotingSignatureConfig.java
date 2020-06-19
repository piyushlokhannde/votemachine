package com.piyush.blockchain.votemachine.config;

import com.piyush.blockchain.votemachine.domain.crypto.VotingMachineSignature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class VotingSignatureConfig {

    @Bean
    public VotingMachineSignature getVotingMachineSignature() {
        return new VotingMachineSignature();
    }
}
