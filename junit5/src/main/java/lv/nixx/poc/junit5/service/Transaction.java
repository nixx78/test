package lv.nixx.poc.junit5.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	
	private String id;
	private String accountId;
	private BigDecimal amount;
	private Date date;


}
