package lv.nixx.poc.cucumber.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthStatistic {
	private String currency;
	private int txnCount;
	private BigDecimal amount;
}
