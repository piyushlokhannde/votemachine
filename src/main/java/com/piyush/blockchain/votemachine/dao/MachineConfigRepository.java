package com.piyush.blockchain.votemachine.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MachineConfigRepository extends MongoRepository<MachineConfigData, Integer> {
}
