@super-cool-test
Feature: Java E2E proof-of-concept

  As members of the Java team, we want to manage the members of the Java team and have the option to add
  new ones

  Scenario: TC-001 Verify that Java E2E proof-of-concept is working
    Given The Java team members page is visible
    When  A Java team member attempts to add a new member with first name 'Testy' and last name 'Testich'
    Then  The Java team members page should be updated, with the new member having handle 'ttestich'
