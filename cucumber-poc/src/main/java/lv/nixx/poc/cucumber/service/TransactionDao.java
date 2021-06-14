package lv.nixx.poc.cucumber.service;

import lv.nixx.poc.cucumber.domain.Transaction;

import java.util.Collection;
import java.util.Date;

public interface TransactionDao {
    Collection<Transaction> getTransactions(Date from, Date to);
}
