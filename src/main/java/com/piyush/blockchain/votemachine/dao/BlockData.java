package com.piyush.blockchain.votemachine.dao;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class BlockData {


    @Id
    private String id;
    private String hash;
    private String previousHash;
    private String candidateId;
    private String machineId;
    private Integer nonce;
    private Long timeStamp;
    private Integer blockNumber;
    private Integer blockCode;
    private Boolean validBlock;


}
