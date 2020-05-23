package com.piyush.blockchain.votemachine.domain.processingunit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class ProcessedVote {
    private final String previousHash;
    private final String machineNumber;
    private final int nonce;
    private final LocalDateTime timeStamp;
    private final String hash;
    private final int blockNumber;
    private final int blockCode;
    private final boolean blockValid;



}
