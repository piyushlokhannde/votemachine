Feature: Machine Functionality

Scenario: Adding the single vote to machine
    Given there are no votes in the machine
    When voter give vote to candidate with id 1
    Then the repository should have 1 vote


Scenario: Adding the Two votes to machine
    Given there are no votes in the machine
    When voter give vote to candidate with id 1
    And  voter give vote to candidate with id 1
    Then the repository should have 2 vote

Scenario: Adding the Four votes to machine with different candidate.
    Given there are no votes in the machine
    When voter give vote to candidate with id 1
    And  voter give vote to candidate with id 2
    And  voter give vote to candidate with id 1
    And  voter give vote to candidate with id 2
    Then the repository should have 4 vote

Scenario: Adding the votes to machine with valid and invalid candidate.
    Given there are no votes in the machine
    When voter give vote to candidate with id 1
    And  voter give vote to candidate with id 3
    Then the repository should have 1 vote