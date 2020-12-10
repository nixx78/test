package lv.nixx.poc.mockito.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lv.nixx.poc.mockito.domain.Account;
import lv.nixx.poc.mockito.domain.Customer;
import lv.nixx.poc.mockito.domain.Deposit;
import lv.nixx.poc.mockito.domain.Payment;

/**
 * BWService implementation, contains methods to update entities on BW side or create active operation - payment
 * 
 * @author nixx
 */

public class BWServiceImpl implements BWService {
	
	Logger logger = LoggerFactory.getLogger(BWServiceImpl.class.getName());

	@Override
	public String createPayment(String paymentReference, Payment paymentDetails) {
		log(paymentReference, paymentDetails);
		
		return paymentReference + "." + System.currentTimeMillis();
	}
	
	public void log(String paymentReference, Payment paymentDetails) {
		logger.debug("create payment, reference [" + paymentReference + "] details:\n" + paymentDetails.toString() );
	}	

	@Override
	public void updateCustomer(Customer customer) {
		logger.debug("customer [" + customer + "] update");
	}

	@Override
	public void updateAccount(Account account) {
		logger.debug("account [" + account + "] update");
	}

	@Override
	public void updateDeposit(Deposit deposit) {
		logger.debug("deposit [" + deposit + "] update");
	}

}
