package lv.nixx.poc.cucumber.transaction;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TransactionReport {

	private CountBy countField;
	private Map<String, BigDecimal> currency = new HashMap<>();
	private int totalOperationCount;

	public TransactionReport(CountBy countField) {
		this.countField = countField;
	}

	public CountBy getCountField() {
		return countField;
	}

	public Map<String, BigDecimal> getCurrency() {
		return currency;
	}

	public void setCurrency(Map<String, BigDecimal> currency) {
		this.currency = currency;
	}

	public int getTotalOperationCount() {
		return totalOperationCount;
	}

	public void setTotalOperationCount(int totalOperationCount) {
		this.totalOperationCount = totalOperationCount;
	}
	
}
