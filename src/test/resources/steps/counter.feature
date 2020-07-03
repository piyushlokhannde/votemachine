Feature: Vote Count Functionality

  Scenario: count the vote for machine
    Given there are no votes in the machine
    When voter give vote to candidate with id 1
    When voter give vote to candidate with id 2
    When voter give vote to candidate with id 2
    Then vote for candidate 1 is 1
    And vote for candidate 2 is 2


  Scenario: count the vote for machine till block number
    Given there are no votes in the machine
    When voter give vote to candidate with id 1
    When voter give vote to candidate with id 2
    When voter give vote to candidate with id 2
    When voter give vote to candidate with id 2
    Then vote for candidate 1 is 1 till block 2
    And vote for candidate 2 is 2 till block 3
    And vote for candidate 2 is 3 till block 4



