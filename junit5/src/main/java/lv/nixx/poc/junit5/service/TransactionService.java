package lv.nixx.poc.junit5.service;

import java.util.Collection;
import java.util.List;

public class TransactionService {

    public Collection<Transaction> getAllTransactions() {
        return List.of(
                new Transaction("1", "ACC1", 10.01, "01/04/2021"),
                new Transaction("2", "ACC2", 20.01, "02/04/2021"),
                new Transaction("3", "ACC4", 40.01, "03/04/2021"),
                new Transaction("4", "ACC1", 50.01, "04/04/2021"),
                new Transaction("5", "ACC2", 50.01, "05/04/2021")
        );
    }
}
