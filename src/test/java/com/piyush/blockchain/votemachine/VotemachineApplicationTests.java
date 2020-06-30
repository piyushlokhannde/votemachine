package com.piyush.blockchain.votemachine;

import com.piyush.blockchain.votemachine.dao.BlockPersisterDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VotemachineApplicationTests {

	@Autowired
	private BlockPersisterDAO blockPersisterDAO;

	@Test
	void contextLoads() {
		System.out.println("dd");
	}

}
