# Crypto-EVM
EVM based on cryptography

Any voting system should have the following characteristics.
1) **confidentiality:** System should be confidential about the voter and there choices.
2) **Validation:** Voter should be able to validate the choices they made.
3) **Integrity:** Once all the choces are made, their should not be any temporing of the data recoreded in the system.

Although the current voting system(EVM) take cares of the confidentiality but hardly any thing for Validation and Integrity.
With the help of cryptography and blockachiain data structure, we can improve on the validation and integrity of the EVM's
The example presented here is an CEVM , based on the cryptography and  blockachiain data structure to demonstrate the implementation of the confidentiality, validation and integrity

**Features of CEVM:**
1) CEVM stored the voting data in blockchain format. Because of this, it is impossible to change the voting data stored in the system.
2) Voter can cast the vote, after making the choice, system will return the metadata about the choice. This metadata can be use for validating the already cast vote.
3) System stores the data using cryptographic in the persistent layer. Once it is added in to the database, only valid authority can decry-pt. The data and key can be made public for transparency.
4)  Voter can validate his vote using the metadata and mark the his vote as valid. System also gives the data about how many votes are validated.
5) Only authorized person can count the votes, by supplying the private key. 

# Use Case Scenario Overview: Crypto-EVM

![alt text](img/CEVM_use_case_diagram.jpg)

**Use Case of CEVM:**
1) Voter should be able to vote for candidate for his/her choice. After vote recorded in CEVM, it should return the following metadata about the choice made by the cotter </br>
     Hash of the previous block.</br>
     Identity of the machine where choice is recorded.</br>
     Nonce value for calculating hash.</br>
     Time when the vote is recorded;</br>
     Current hash of the block;</br>
     Block Number of the block generated;</br>
     Block Code, four digit code used by the voter, to validate the block in future. This is a secrete code generated by the machine only for the voter.</br>
     Block Valid,  value indicating if the current vote is valid.</br>
2) User should be able to validate the block, he/she made in the first use case. After voting is completed,  voter can check if his/her vote still exists in the machine and not being tampered. 
	Voter will send the following parameter to the voting machine.
		machineNumber  : Machine id  where vote is stored
                blockNumber : block number generated from the use case 1
		candidateVote : candidate id to which voter has voted in use case 1
		blockCode : valid block code generated in first use case 1
 	The machine will return the calculated hash of the data. So  if calculated hash is equal to printed hash then vote is correctly stored, else the blockchain is tampered.
3) After validating the block in the use case 2. User will mark the block as valid. System will change the property of the block isBlockValid =true
4) Voter can see the status of the blockchain for the machine. System will return the total validated votes and total votes for the system  
5)  Only authorized entity can count the vote by supplying the private key for the machine. System will return the votes per candidate. System should also provide the vote count till certain blocks.

# Testing and Running the Application:
Following steps are to run the appliction.

1)  Install mongodb from the link https://docs.mongodb.com/manual/administration/install-community/ and start the database.
2) Run the java class [VotemachineApplication.java](src/main/java/com/piyush/blockchain/votemachine/VotemachineApplication.java) 

The spring boot application will start on the port 8080. We can execute the following scenarios.

```yaml
Scenario: Voter cast cast the vote for candidate 1
          http://localhost:8080/vote
Request:  {
              "candidateId":1,
              "machineId":1
          }   
                 
Response: {
              "previousHash": "",
              "machineNumber": "1",
              "nonce": 47,
              "timeStamp": "2020-07-08T23:07:16.585",
              "hash": "00005130679645122f139dab17c3f1e16691e1ac6ce1755949929a455c5caa80",
              "blockNumber": 1,
              "blockCode": 639,
              "blockValid": false
          }
```
```yaml
Scenario: Voter cast cast the vote for candidate 2
Request:  {
    	      "candidateId":2,
              "machineId":1
          }
Response: {
	      "previousHash": "00005130679645122f139dab17c3f1e16691e1ac6ce1755949929a455c5caa80",
	      "machineNumber": "1",
	      "nonce": 29,
	      "timeStamp": "2020-07-08T23:09:59.177",
	      "hash": "00be53c76067ceae15703398c7cb5ffe61c917b4b6b6eab86b81ca385854ef3b",
	      "blockNumber": 2,
	      "blockCode": 229,
	      "blockValid": false
	  }





