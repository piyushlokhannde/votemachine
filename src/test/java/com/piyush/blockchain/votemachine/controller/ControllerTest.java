package com.piyush.blockchain.votemachine.controller;

import com.piyush.blockchain.votemachine.controller.machine.data.VotingDataResponse;
import com.piyush.blockchain.votemachine.dao.BlockPersisterDAO;


import cucumber.api.java.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class ControllerTest {

    private final String SERVER_URL = "http://localhost";

    @LocalServerPort
    protected int port;


    protected static Map<Integer, VotingDataResponse> resultVotingResponse = new HashMap<>();


    protected RestTemplate restTemplate = new RestTemplate();

    @Autowired
    protected BlockPersisterDAO blockPersisterDAO;


    protected String getURL(String endpoint) {
        return SERVER_URL + ":" + port +"/"+endpoint;
    }








}
