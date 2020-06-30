package com.piyush.blockchain.votemachine.controller.validation;


import com.piyush.blockchain.votemachine.controller.machine.data.VotingDataResponse;
import com.piyush.blockchain.votemachine.controller.validation.data.BlockValidRequest;
import com.piyush.blockchain.votemachine.controller.validation.data.ValidateVoteInputRequest;
import com.piyush.blockchain.votemachine.domain.processingunit.ProcessedVote;
import com.piyush.blockchain.votemachine.domain.processingunit.ValidateVoteInput;
import com.piyush.blockchain.votemachine.service.validation.VoteValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteValidatorController {

    @Autowired
    VoteValidator voteValidator;

    @PostMapping(path="vote/valid/block", consumes = "application/json", produces = "application/json")
    public VotingDataResponse validateVote(@RequestBody ValidateVoteInputRequest request) {
        ValidateVoteInput validateVoteInput = ValidateVoteInput
                .of(request.getMachineNumber(), request.getBlockNumber(),
                        request.getCandidateVote(), request.getBlockCode());
        ProcessedVote processedVote = voteValidator.validateVote(validateVoteInput);
        ModelMapper modelMapper = new ModelMapper();
        return  modelMapper.map(processedVote, VotingDataResponse.class);
    }


    @PostMapping(path="vote/valid", consumes = "application/json", produces = "application/json")
    public void markBlockValid(@RequestBody BlockValidRequest request) {
        voteValidator.markBlockValid(request.getBlockNumber(), request.getBlockCode(), request.getMachineNumber());
    }



}
