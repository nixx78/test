package lv.nixx.poc.mockito.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lv.nixx.poc.mockito.domain.*;
import lv.nixx.poc.mockito.exception.IntegrationException;
import lv.nixx.poc.mockito.exception.UpdateException;
import lv.nixx.poc.mockito.dao.BankSystemDAO;
import lv.nixx.poc.mockito.dao.BankSystemDAOImpl;
import lv.nixx.poc.mockito.service.BWService;
import lv.nixx.poc.mockito.service.BWServiceImpl;

/**
 * Integrator implementation class, businness methods bodies.
 * Class contains references to BWService and BankSystemDAO objects.
 * 
 * @author nixx
 */
public class IntegratorImpl implements Integrator {
	
	private Logger logger = LoggerFactory.getLogger( IntegratorImpl.class.getName() );
	
	private BWService bwService;
	private BankSystemDAO dao;
	
	public IntegratorImpl() {
		this.bwService = new BWServiceImpl();
		this.dao = new BankSystemDAOImpl();
	}

	public IntegratorImpl(BWService bwService, BankSystemDAO dao) {
		this.bwService = bwService;
		this.dao = dao;
	}


	@Override
	public String processPayment(Payment payment) {
		
		String paymentReference = dao.getPaymentReferenceNumber(payment.id);
		logger.debug("receive payment reference [" + paymentReference + "]");
		
		String ref = bwService.createPayment(paymentReference, payment);
		logger.debug("payment created, reference [" + ref + "]");
		
		return ref;
	}

	@Override
	public void connectCustomerToBW(String customerID) throws IntegrationException {
		
		try {
			updateCustomerDetails(customerID);
			updateAccounts(customerID);
			updateDeposit(customerID);
		} catch (Exception ex){
			throw new IntegrationException(ex);
		}

	}

	private void updateCustomerDetails(String customerID) throws UpdateException {
		Customer cust = dao.getCustomerDetalis(customerID);
		logger.debug("receive from DAO info about customer [" + cust + "]");
		
		bwService.updateCustomer(cust);
	}

	private void updateAccounts(String customerID) throws UpdateException {

		Account[] accounts = dao.getAccounts(customerID);

		logger.debug("receive from DAO info about [" + accounts.length + "] accounts");
		for (int i = 0; i < accounts.length; i++) {
			bwService.updateAccount(accounts[i]);
		}
	}

	private void updateDeposit(String customerID) throws UpdateException {
		
		Deposit[] deposits = dao.getDeposits(customerID);
		logger.debug("receive from DAO info about [" + deposits.length + "] deposits");
		for (int i = 0; i < deposits.length; i++) {
			bwService.updateDeposit(deposits[i]);
		}
	}


	public void setBwService(BWService bwService) {
		this.bwService = bwService;
	}

	public void setDao(BankSystemDAO dao) {
		this.dao = dao;
	}

	public BWService getBwService() {
		return bwService;
	}

	public BankSystemDAO getDao() {
		return dao;
	}
	
}
