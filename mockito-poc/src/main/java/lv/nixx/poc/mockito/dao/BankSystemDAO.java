package lv.nixx.poc.mockito.dao;

import lv.nixx.poc.mockito.domain.Account;
import lv.nixx.poc.mockito.domain.Customer;
import lv.nixx.poc.mockito.domain.Deposit;

/**
 * Data Access Object interface for Bank's interface, define methods to access info about entities
 * 
 * @author nixx
 */

public interface BankSystemDAO {
	
	public String getPaymentReferenceNumber(String paymentID);
	
	public Account[] getAccounts(String customerID);
	public Customer getCustomerDetalis(String customerID);
	public Deposit[] getDeposits(String customerID);
	
}
