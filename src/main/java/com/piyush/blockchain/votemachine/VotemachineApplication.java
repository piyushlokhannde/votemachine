package com.piyush.blockchain.votemachine;

import com.piyush.blockchain.votemachine.dao.CounterConfigData;
import com.piyush.blockchain.votemachine.dao.CounterRepository;
import com.piyush.blockchain.votemachine.dao.MachineConfigData;
import com.piyush.blockchain.votemachine.dao.MachineConfigRepository;
import com.piyush.blockchain.votemachine.domain.crypto.VotingMachineSignature;
import com.piyush.blockchain.votemachine.domain.votingmahcine.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@SpringBootApplication
public class VotemachineApplication implements CommandLineRunner {

	@Autowired
	MachineConfigRepository machineConfigRepository;

	@Autowired
	CounterRepository counterRepository;

	@Autowired
	VotingMachineSignature votingMachineSignature;

	public static void main(String[] args) {
		SpringApplication.run(VotemachineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if(!machineConfigRepository.existsById(1)) {
			PublicKey publicKey = votingMachineSignature.getPublicKey();
			PrivateKey privateKey = votingMachineSignature.getPrivateKey();

			String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
			String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

			Candidate candidate1 = Candidate.of(1, "Candidate One");
			Candidate candidate2 = Candidate.of(2, "Candidate Two");

			List<Candidate> candidateList = new ArrayList<>();
			candidateList.add(candidate1);
			candidateList.add(candidate2);

			MachineConfigData machineConfigData = new  MachineConfigData();
			machineConfigData.setCandidateList(candidateList);
			machineConfigData.setDifficulty(2);
			machineConfigData.setMachineNumber(1);
			machineConfigData.setPublicKey(encodedPublicKey);
			machineConfigRepository.save(machineConfigData);

			CounterConfigData counterConfigData = new CounterConfigData();
			counterConfigData.setMachineNumber(1);
			counterConfigData.setPrivateKey(encodedPrivateKey);
			counterRepository.save(counterConfigData);
		}



	}
}
