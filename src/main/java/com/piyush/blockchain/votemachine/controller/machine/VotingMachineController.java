package com.piyush.blockchain.votemachine.controller.machine;


import com.piyush.blockchain.votemachine.controller.machine.data.VotingDataRequest;
import com.piyush.blockchain.votemachine.controller.machine.data.VotingDataResponse;
import com.piyush.blockchain.votemachine.domain.blockminer.VotingData;
import com.piyush.blockchain.votemachine.domain.processingunit.ProcessedVote;
import com.piyush.blockchain.votemachine.service.machine.VotingMachineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VotingMachineController {

    @Autowired
    VotingMachineService votingMachineService;


    @PostMapping(path="vote", consumes = "application/json", produces = "application/json")
    public VotingDataResponse saveVote(@RequestBody VotingDataRequest request) {
       VotingData votingData =  VotingData.getBlockChainVotingData(request.getCandidateId(), request.getMachineId());
       ProcessedVote processedVote = votingMachineService.castTheVote(votingData);
       ModelMapper modelMapper = new ModelMapper();
       return  modelMapper.map(processedVote, VotingDataResponse.class);
    }


}
