package lv.nixx.poc.cucumber.service;

import lombok.Setter;
import lv.nixx.poc.cucumber.domain.Transaction;
import lv.nixx.poc.cucumber.domain.Transactions;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Setter
public class TransactionService {

    private TransactionDao transactionDao;

    public TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public Transactions getTransactions(Date date) {
        Collection<Transaction> transactions = transactionDao.getTransactions(date, date);

        final BigDecimal total = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Transactions(transactions, total);
    }


}
