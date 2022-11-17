package lv.nixx.poc.assertj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Long id;
    private Collection<Transaction> transaction;

    public BigDecimal totalTxnAmount() {
        return transaction.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
