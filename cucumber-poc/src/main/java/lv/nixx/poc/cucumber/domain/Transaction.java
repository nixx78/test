package lv.nixx.poc.cucumber.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	
	private long id;
	private String currency;
	private BigDecimal amount;
	private Date date;
	
}
