package com.piyush.blockchain.votemachine.controller.validation.data;

import lombok.Data;

@Data
public class ValidateVoteInputRequest {

    private  String machineNumber;
    private  String blockNumber;
    private  String candidateVote;
    private  int blockCode;
}
