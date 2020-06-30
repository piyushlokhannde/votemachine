package com.piyush.blockchain.votemachine.domain.processingunit;

import java.util.HashMap;
import java.util.Map;


public class VoteCount {

    Map<String, Integer> map = new HashMap<>();

    public Integer
    getTotalVotesForCandidate(String candidiateID) {
        return map.get(candidiateID);
    }

    public void incrementVoteCount(String candidiateID) {
        map.computeIfAbsent(candidiateID, k-> 0);
        map.computeIfPresent(candidiateID,  (key, val)->  val+1);
    }
}
