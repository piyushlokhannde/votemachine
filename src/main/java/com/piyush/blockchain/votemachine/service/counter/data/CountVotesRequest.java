package com.piyush.blockchain.votemachine.service.counter.data;


import lombok.Data;

@Data
public class CountVotesRequest {

    private String privateKey;
    private String machineNumber;
}
