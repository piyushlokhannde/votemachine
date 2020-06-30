Feature: Validator Functionality

  Scenario: validate the exiting vote with first block
    Given there are no votes in the machine
    When voter give vote to candidate with id 1
    When voter give vote to candidate with id 2
    Then voter successfully validate the vote for block 1 and candidate with id 1


  Scenario: validate the exiting vote with second block
    Given there are no votes in the machine
    When voter give vote to candidate with id 2
    When voter give vote to candidate with id 1
    Then voter successfully validate the vote for block 2 and candidate with id 1


  Scenario: validate the exiting vote with second block
    Given there are no votes in the machine
    When voter give vote to candidate with id 2
    When voter give vote to candidate with id 2
    Then voter unsuccessfully validate the vote for block 2 and candidate with id 1