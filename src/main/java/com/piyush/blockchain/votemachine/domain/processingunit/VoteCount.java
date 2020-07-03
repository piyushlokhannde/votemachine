package com.piyush.blockchain.votemachine.domain.processingunit;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class VoteCount {

    Map<String, Integer> map = new HashMap<String, Integer>();

    public Integer
    getTotalVotesForCandidate(String candidiateID) {
        return map.get(candidiateID);
    }

    public void incrementVoteCount(String candidiateID) {
        map.computeIfAbsent(candidiateID, k-> 0);
        map.computeIfPresent(candidiateID,  (key, val)->  val+1);
    }
}
