package lv.nixx.poc.cucumber.stepdef;

import io.cucumber.java.en.Given;
import lv.nixx.poc.cucumber.domain.Transaction;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class GenericTransactionReportSteps {

    final TransactionTestContext transactionTestContext;

    public GenericTransactionReportSteps(TransactionTestContext transactionTestContext) {
        this.transactionTestContext = transactionTestContext;
    }

    @Given("^Transactions exists:$")
    public void transactionsExists(List<Map<String, String>> table) {
        Collection<Transaction> c = new ArrayList<>();

        for (Map<String, String> row : table) {
            Transaction t = new Transaction();
            t.setId(Long.parseLong(row.get("id")));
            t.setAmount(BigDecimal.valueOf(Double.parseDouble(row.get("amount"))));
            t.setCurrency(row.get("currency"));
			String date = row.get("date");
			try {
				t.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(date));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Can't parse date:" + date);
            }
            c.add(t);
        }
        transactionTestContext.txns = c;
        System.out.println("Transactions exists: " + c);
    }

}
