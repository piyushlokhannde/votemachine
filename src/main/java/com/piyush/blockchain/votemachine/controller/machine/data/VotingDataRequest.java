package com.piyush.blockchain.votemachine.controller.machine.data;

import lombok.Data;

@Data
public class VotingDataRequest {
    private Integer candidateId;
    private Integer machineId;
}
