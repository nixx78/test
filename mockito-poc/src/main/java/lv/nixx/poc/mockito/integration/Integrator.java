package lv.nixx.poc.mockito.integration;

import lv.nixx.poc.mockito.domain.Payment;
import lv.nixx.poc.mockito.exception.IntegrationException;

/**
 * Integrator interface, provides business methods to create operations
 * in system BW
 *  
 * @author nixx
 *
 */
public interface Integrator {
	
	public String processPayment(Payment paymentDetails);
	public void connectCustomerToBW(String customerID) throws IntegrationException;
	
}
