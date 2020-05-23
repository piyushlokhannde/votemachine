package com.piyush.blockchain.votemachine.domain.votingmahcine;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class Candidate {

    private final int candidateId;
    private final String candidateName;
}
