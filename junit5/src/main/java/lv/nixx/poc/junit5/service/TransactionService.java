package lv.nixx.poc.junit5.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TransactionService {

    public Collection<Transaction> getAllTransactions() {
        try {
            return List.of(
                new Transaction("1", "ACC1", BigDecimal.valueOf(10.01),"01/04/2021"),
                new Transaction("2", "ACC2", BigDecimal.valueOf(20.01),"02/04/2021"),
                new Transaction("3", "ACC4", BigDecimal.valueOf(40.01),"03/04/2021"),
                new Transaction("4", "ACC1", BigDecimal.valueOf(50.01),"04/04/2021"),
                new Transaction("5", "ACC2", BigDecimal.valueOf(50.01),"05/04/2021")
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
