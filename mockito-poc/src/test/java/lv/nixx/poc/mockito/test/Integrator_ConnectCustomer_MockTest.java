package lv.nixx.poc.mockito.test;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import lv.nixx.poc.mockito.domain.*;
import lv.nixx.poc.mockito.exception.IntegrationException;
import lv.nixx.poc.mockito.exception.UpdateException;

import lv.nixx.poc.mockito.dao.BankSystemDAO;
import lv.nixx.poc.mockito.integration.IntegratorImpl;
import lv.nixx.poc.mockito.service.BWService;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test to test integration use case 'connectCustomer'. During testing, we use mock objects for DAO and for BWService  
 * 
 * @author nixx
 */
@RunWith(MockitoJUnitRunner.class)
public class Integrator_ConnectCustomer_MockTest {

	@Mock 
	BWService bwServiceMock;
	@Mock 
	BankSystemDAO daoMock;

	@InjectMocks
	private IntegratorImpl manager;

	@Test
	public void connectCustomerSuccess() throws Exception {
		
		// in the fist, we reset all expected values for mock, because we don't know, what values are 
		// set in the mocks in other methods
		reset(bwServiceMock, daoMock);
		
		final String customerID = "customerID10001";
		
		Customer customer = customerValueCreate(customerID);
		Account[] accounts = accountsValuesCreate(customerID);
		Deposit[] deposits = depositValuesCreate(customerID);
		
		// try connect customer
		manager.connectCustomerToBW(customerID);
		
		InOrder inOrder = inOrder(bwServiceMock, daoMock);

		// BankSystemDAO and BWService method calls verification
		inOrder.verify(daoMock, times(1)).getCustomerDetalis(customerID);
		inOrder.verify(bwServiceMock, times(1)).updateCustomer(customer);

		inOrder.verify(daoMock, times(1)).getAccounts(customerID);
		inOrder.verify(bwServiceMock, times(1)).updateAccount(accounts[0]);
		inOrder.verify(bwServiceMock, times(1)).updateAccount(accounts[1]);
		inOrder.verify(bwServiceMock, times(1)).updateAccount(accounts[2]);

		inOrder.verify(daoMock, times(1)).getDeposits(customerID);
		inOrder.verify(bwServiceMock, times(1)).updateDeposit(deposits[0]);
		inOrder.verify(bwServiceMock, times(1)).updateDeposit(deposits[1]);
		

		// we check that other methods not called
		verifyNoMoreInteractions(bwServiceMock, daoMock);
	}

	
	@Test(expected = IntegrationException.class)
	public void connectCustomerFail() throws IntegrationException, UpdateException{
		// in the fist, we reset all expected values for mock, because we don't know, what values are 
		// set in the mocks in other methods
		reset(bwServiceMock, daoMock);

		final String customerID = "customerID10001";
		Customer customer = customerValueCreate(customerID);

		doThrow( new UpdateException("error during customer update")).when(bwServiceMock).updateCustomer(customer);
		
		manager.connectCustomerToBW(customerID);
	}
	
	
	/**
	 * Method creates Customer instance and set this instance for method 'getCustomer' stub.
	 * 
	 * @param customerID - customer id for created Customer instance
	 * @return Customer instance, the same instance will be returned by method 'getCustomerDetails' mock
	 */
	private Customer customerValueCreate(String customerID) {
		Customer customer = new Customer(customerID, "Ivan", "Ivanov");
		
		when(daoMock.getCustomerDetalis(customerID)).thenReturn(customer);
		
		return customer;
	}
	
	
	/**
	 * 
	 * @param customerID
	 * @return
	 */
	private Account[] accountsValuesCreate(String customerID) {
		
		// set expected response value for Account
		Account[] accounts = new Account[]{ 
				new Account("account1", "card account", BigDecimal.valueOf(9.56)),
				new Account("account2", "current account", BigDecimal.valueOf(20.00)),
				new Account("account3", "card account", BigDecimal.valueOf(40.00))
		};
		
		when(daoMock.getAccounts(customerID)).thenReturn(accounts);
		
		return accounts;
	}
	
	private Deposit[] depositValuesCreate(String customerID){
		
		Deposit[] deposits = new Deposit[]{
				new Deposit("depID1","New Year deposit", BigDecimal.valueOf(1000), BigDecimal.valueOf(15.75) ),
				new Deposit("depID2","Simple deposit", BigDecimal.valueOf(2000), BigDecimal.valueOf(10.00) )
		};
		
		when(daoMock.getDeposits(customerID)).thenReturn(deposits);
		
		return deposits;
	}

}
