package lv.nixx.poc.cucumber.stepdef;

import java.util.Collection;

import lv.nixx.poc.cucumber.transaction.Transaction;
import lv.nixx.poc.cucumber.transaction.TransactionReport;

public class TransactionTestContext {
	
	public Collection<Transaction> txns;
	public TransactionReport actualReport;

}
