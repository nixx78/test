package lv.nixx.poc.cucumber.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class TransactionReport {

	private final CountBy countField;
	private Map<String, BigDecimal> currency = new HashMap<>();
	private int totalOperationCount;
	private BigDecimal totalAmount;

	public TransactionReport(CountBy countField) {
		this.countField = countField;
	}

}
