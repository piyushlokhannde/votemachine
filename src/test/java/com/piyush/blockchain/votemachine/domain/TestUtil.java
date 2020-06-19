package com.piyush.blockchain.votemachine.domain;

import java.time.LocalDateTime;

public class TestUtil {

    public  static String INITIAL_HASH_FIXED_DATE_DIFF_1 = "06b7b438ce082b9c6c341fbb23a80ef55d81a15251e25bd0a1e30dbe4a849db0";
    public static String INITIAL_HASH_FIXED_DATE_DIFF_2 = "009b8c933e2388402d1c168c8130f3fb0d7fbbd5fdf8f422c8bc06bea7879199";
    public static LocalDateTime fixedTime  = LocalDateTime.of(2020, 1,1,1,1,1);

    public static int difficulty   =  1;
    public static int machineNumber = 1;

    public static int FIRST_CANDIDATE_ID  = 1;

    public static int SECOND_CANDIDATE_ID  = 2;
}
