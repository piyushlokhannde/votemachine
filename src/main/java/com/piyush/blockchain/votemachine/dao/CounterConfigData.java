package com.piyush.blockchain.votemachine.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class CounterConfigData {

    @Id
    private Integer machineNumber;
    private String privateKey;
}
