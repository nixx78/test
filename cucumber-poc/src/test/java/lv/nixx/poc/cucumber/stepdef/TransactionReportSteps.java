package lv.nixx.poc.cucumber.stepdef;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lv.nixx.poc.cucumber.transaction.CountBy;
import lv.nixx.poc.cucumber.transaction.Transaction;
import lv.nixx.poc.cucumber.transaction.TransactionDao;
import lv.nixx.poc.cucumber.transaction.TransactionReport;
import lv.nixx.poc.cucumber.transaction.TransactionReportService;

public class TransactionReportSteps {
	
	@Spy
	@InjectMocks
	private TransactionReportService service;
	
	@Mock
	private TransactionDao dao;
	
	private TransactionTestContext transactionTestContext;
	
	public TransactionReportSteps(TransactionTestContext transactionTestContext) {
		this.transactionTestContext = transactionTestContext;
	}
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Given("^Transaction report service is available$")
	public void transactionServiceCreated() throws Throwable {
		
		System.out.println("Transaction report service is available$$$$$");

		service = new TransactionReportService();
		Collection<Transaction> txn = transactionTestContext.txns;
		doReturn(txn).when(dao).getTransactions(any(Date.class), any(Date.class));
		
		service.setDao(dao);
	}
	
	@When("^create report with date range: from \\\"([^\\\"]*)\\\" to \\\"([^\\\"]*)\\\" and count by field \\\"([^\\\"]*)\\\"$")
	public void createReport(String dateFrom, String dateTo, String countBy) throws ParseException {
		
		Date df = new SimpleDateFormat("dd/MM/yyyy").parse(dateFrom);
		Date dt = new SimpleDateFormat("dd/MM/yyyy").parse(dateTo);
		
		TransactionReport report = service.createReport(df, dt, CountBy.valueOf(countBy));
		assertNotNull(report);
		
		this.transactionTestContext.actualReport = report;
	}
	
	@Then("^expect report with following data:$")
	public void checkReportRows(DataTable table) {
		
		Map<String, BigDecimal> expectedCounts = new HashMap<>();
		TransactionReport actualReport = this.transactionTestContext.actualReport;
		
		for (Map<String, String> row : table.asMaps(String.class, String.class)) {
			final String curr = row.get("currency");
			final String expectedCount = row.get("count");

			BigDecimal bd = null;
			if (actualReport.getCountField() == CountBy.Count) {
				Long v = Long.valueOf(expectedCount);
				bd = BigDecimal.valueOf(v);
			} else if (actualReport.getCountField() == CountBy.Amount) {
				Double v = Double.valueOf(expectedCount);
				bd = BigDecimal.valueOf(v);
			}
			
			expectedCounts.put(curr, bd);
		}
		
		Map<String, BigDecimal> actualCounts = actualReport.getCurrency();
		
		assertThat(actualCounts.entrySet(), equalTo(expectedCounts.entrySet()));
	}
	
		   
	@Then("^expect total transaction count (\\d+)$")
	public void checkTotal(int count) {
		TransactionReport actualReport = this.transactionTestContext.actualReport;
		assertEquals(count, actualReport.getTotalOperationCount());
	}
	

}

