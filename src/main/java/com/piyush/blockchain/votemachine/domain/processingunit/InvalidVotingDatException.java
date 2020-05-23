package com.piyush.blockchain.votemachine.domain.processingunit;

public class InvalidVotingDatException  extends  Exception {

    public InvalidVotingDatException(String msg) {
        super(msg);
    }
}
