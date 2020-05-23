package com.piyush.blockchain.votemachine.domain;

import java.time.LocalDateTime;

public class TestUtil {

    public  static String INITIAL_HASH_FIXED_DATE_DIFF_1 = "07087e76266407d028da75a009c123bc543fcafbf9539df2ff30c48f4c83ac8a";
    public static String INITIAL_HASH_FIXED_DATE_DIFF_2 = "00c753f771f805086303d82ddeb0d992b112c7d383605f9c6073e4ce37330488";
    public static LocalDateTime fixedTime  = LocalDateTime.of(2020, 1,1,1,1,1);

    public static int difficulty   =  1;
    public static int machineNumber = 1;

    public static int FIRST_CANDIDATE_ID  = 1;

    public static int SECOND_CANDIDATE_ID  = 2;
}
