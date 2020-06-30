package com.piyush.blockchain.votemachine.controller.validation.data;

import lombok.Data;

@Data
public class BlockValidRequest {

    private String blockNumber;
    private Integer blockCode;
    private String machineNumber;
}
