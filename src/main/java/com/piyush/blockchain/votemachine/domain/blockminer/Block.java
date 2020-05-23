package com.piyush.blockchain.votemachine.domain.blockminer;


import com.piyush.blockchain.votemachine.domain.crypto.HashCalculator;
import lombok.*;



import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor(staticName = "of")
public class Block {



    private final String previousHash;
    private final VotingData data;
    private int nonce;
    private final LocalDateTime timeStamp;
    private String hash;
    private static char zero = '0';
    private final int blockNumber;
    private final int blockCode;
    private boolean validBlock;


    public void markBlockValid() {
        this.validBlock = true;
    }


    public String calculateHash(int tries) {


        long time = Timestamp.valueOf(timeStamp).getTime();
        String hashableData = previousHash + data.toString() + time + tries + blockNumber;
        this.nonce = tries;
        this.hash = HashCalculator.calculateHash(hashableData);

      //  System.out.println(hash.toString() + "   "+ this.nonce + " " + timeStamp+ "  "
         //       +  time+" "+ data.toString()+"  "+ hashableData);
        return this.hash;
    }



    public int getLeadingZerosCount() {
        for (int i = 0; i < this.getHash().length(); i++) {
            if (this.getHash().charAt(i) != this.zero) {
                return i;
            }
        }
        return this.getHash().length();
    }
}
