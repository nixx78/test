package lv.nixx.poc.assertj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TransactionDTO {

    private String id;
    private String accountId;
    private BigDecimal amount;
    private Date date;

    public TransactionDTO(String id, String accountId, Double amount, String date) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount == null ? null : BigDecimal.valueOf(amount);
        try {
            this.date = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error creating transaction", e);
        }
    }

    public String getDescription() {
        return id + ":" + accountId + ":" + amount;
    }

}
