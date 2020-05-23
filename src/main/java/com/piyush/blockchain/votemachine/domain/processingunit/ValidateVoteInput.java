package com.piyush.blockchain.votemachine.domain.processingunit;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class ValidateVoteInput {

    private final String machineNumber;
    private final String blockNumber;
    private final String candidateVote;
    private final int blockCode;

}
