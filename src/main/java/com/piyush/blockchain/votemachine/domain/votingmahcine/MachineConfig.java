package com.piyush.blockchain.votemachine.domain.votingmahcine;


import java.security.PublicKey;
import java.util.List;



public interface MachineConfig {
     int getDifficulty();
     Integer getMachineNumber();
     PublicKey getPubicKey();
     List<Candidate> getCandidateList();
}
