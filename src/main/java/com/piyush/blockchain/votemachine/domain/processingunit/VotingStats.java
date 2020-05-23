package com.piyush.blockchain.votemachine.domain.processingunit;

import lombok.Data;

@Data
public class VotingStats {

    private long totalVotes;
    private long validatedVotes;


}
