Feature: Transaction monthly report service test

  Background: 
      Given Transactions exists:
      |id		| currency			|amount	|date		|
  	  |1		| GBP				|10.10	|01/03/2016	|
  	  |2		| USD				|20.12	|01/08/2016	|
  	  |3		| EUR				|1.23	|01/10/2016	|
  	  |4		| EUR				|3.75	|03/10/2016	|
  	  |5		| USD				|5.8	|02/10/2016	|
  	  |6		| EUR				|40.14	|12/12/2016	|
  	  Given transaction service is available
      

  Scenario: Create monthly transaction report
    When create monthly report with date range: from "01/01/2016" to "31/12/2016"

    Then expect report with following data for "MARCH"
    	|currency|count	 |amount	|
    	|GBP	 |1		 |10.10		|

    Then expect report with following data for "AUGUST"
    	|currency|count	 |amount	|
    	|USD	 |1		 |20.12		|
    
    Then expect report with following data for "OCTOBER"
    	|currency|count	 |amount	|
    	|EUR	 |2		 |4.98		|
    	|USD	 |1		 |5.8		|

    Then expect report with following data for "DECEMBER"
    	|currency|count	 |amount	|
    	|EUR	 |1		 |40.14		|

    	