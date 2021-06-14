package lv.nixx.poc.cucumber.service;

import lombok.Setter;
import lv.nixx.poc.cucumber.domain.Transaction;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Setter
public class TransactionDaoImpl implements TransactionDao {

	private Collection<Transaction> expectedTransaction;

	@Override
	public Collection<Transaction> getTransactions(Date from, Date to) {

		return expectedTransaction.stream()
				.filter( t-> t.getDate().compareTo(from) >=0 && t.getDate().compareTo(to)<=0)
				.collect(Collectors.toList());
	}

}
