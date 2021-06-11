package lv.nixx.poc.cucumber.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Transaction {
	
	private long id;
	private String currency;
	private BigDecimal amount;
	private Date date;
	
}
