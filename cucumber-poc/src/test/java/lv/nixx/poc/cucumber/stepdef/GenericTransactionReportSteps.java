package lv.nixx.poc.cucumber.stepdef;



import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import lv.nixx.poc.cucumber.domain.Transaction;

public class GenericTransactionReportSteps {

	final TransactionTestContext transactionTestContext;
	
	public GenericTransactionReportSteps(TransactionTestContext transactionTestContext) {
		this.transactionTestContext = transactionTestContext;
	}

	@Given("^Transactions exists:$")
	public void transactionsExists(DataTable table) {
		Collection<Transaction> c = new ArrayList<>();
		for (Map<String, String> row : table.asMaps(String.class, String.class)) {
			Transaction t = new Transaction();
			t.setId(Long.parseLong(row.get("id")));
			t.setAmount(BigDecimal.valueOf(Double.parseDouble(row.get("amount"))));
			t.setCurrency(row.get("currency"));
			try {
				t.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(row.get("date")));
			} catch (ParseException e) {
			}
			c.add(t);
		}
		transactionTestContext.txns = c;
		System.out.println("Transactions exists: " + c);
	}

}
