package lv.nixx.poc.cucumber.domain;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@ToString
public class Transactions {

    final private Map<Long, Transaction> txnMap;
    final private BigDecimal total;

    public Transactions(Collection<Transaction> txns, BigDecimal total) {
        this.txnMap = txns.stream()
                .collect(Collectors.toMap(Transaction::getId, Function.identity()));
        this.total = total;
    }

    public Transaction getTransaction(Long id) {
        return txnMap.get(id);
    }

    public Collection<Transaction> getAllTransactions() {
        return txnMap.values();
    }
}
