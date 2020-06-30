package com.piyush.blockchain.votemachine.controller.machine.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VotingDataResponse {

    private  String previousHash;
    private  String machineNumber;
    private  int nonce;
    private  LocalDateTime timeStamp;
    private  String hash;
    private  int blockNumber;
    private  int blockCode;
    private  boolean blockValid;

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (object instanceof VotingDataResponse) {
            VotingDataResponse otherObject = (VotingDataResponse)object;

            if(this.previousHash == null || otherObject.previousHash == null
                    || !this.previousHash.equals(otherObject.previousHash)) {
                return false;
            }

            if(this.machineNumber == null || otherObject.machineNumber == null
                    || !this.machineNumber.equals(otherObject.machineNumber)) {
                return false;
            }

            if(this.nonce != otherObject.nonce) {
                return false;
            }

            if(this.timeStamp == null || otherObject.timeStamp == null
                    || !this.timeStamp.equals(otherObject.timeStamp)) {
                return false;
            }

            if(this.hash == null || otherObject.hash == null
                    || !this.hash.equals(otherObject.hash)) {
                return false;
            }

            if(this.blockNumber != otherObject.blockNumber) {
                return false;
            }

            if(this.blockValid != otherObject.blockValid) {
                return false;
            }
        } else  {
            return false;
        }

        return  true;
    }

}
