package lv.nixx.poc.junit5.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Transaction {

    private String id;
    private String accountId;
    private BigDecimal amount;
    private Date date;

    public Transaction(String id, String accountId, BigDecimal amount, Date date) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.date = date;
    }

    public String getDescription() {
        return id + ":" + accountId + ":" + amount;
    }

}
