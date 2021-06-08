Feature: Service test

  Background: 
    Given Users with passwords exists:
      | user1 | password1 |
      | user2 | password2 |
      | user3 | password3 |

  Scenario: Login and calculate
    Given Service is available
    When User user1 is login successfully
    Then Calculate: 1 + 2 expected 3
    Then Calculate: 4 + 2 expected 6
    Then Calculate: 4 + 4 expected 8

  Scenario Outline: Login and calculate using table
    Given Service is available
    When User user1 is login successfully
    Then Calculate: <a> + <b> expected <result>

    Examples: 
      | a | b | result |
      | 1 | 2 |      3 |
      | 3 | 4 |      7 |
      | 1 | 1 |      2 |
