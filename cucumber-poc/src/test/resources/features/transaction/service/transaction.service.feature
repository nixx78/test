@TestProfile1
Feature: Transaction report service test count by Amount

  Background:
    Given Transactions exists:
      | id | currency | amount | date       |
      | 1  | USD      | 10.00  | 13/06/2021 |
      | 2  | EUR      | 20.01  | 11/06/2021 |
      | 3  | EUR      | 1.53   | 13/06/2021 |
      | 4  | EUR      | 2.06   | 16/06/2021 |

  Scenario: Get transactions from service for date

    When request transactions from service for date: "13/06/2021"
    Then expect transaction with total amount 11.53:
      | id | currency | amount | date       |
      | 1  | USD      | 10.00  | 13/06/2021 |
      | 3  | EUR      | 1.53   | 13/06/2021 |
    And expect transaction with id 1 and the following details:
      | currency | USD        |
      | amount   | 10.00      |
      | date     | 13/06/2021 |

