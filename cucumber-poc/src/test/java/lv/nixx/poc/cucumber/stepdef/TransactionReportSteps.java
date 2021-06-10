package lv.nixx.poc.cucumber.stepdef;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import lv.nixx.poc.cucumber.domain.CountBy;
import lv.nixx.poc.cucumber.domain.Transaction;
import lv.nixx.poc.cucumber.service.TransactionDao;
import lv.nixx.poc.cucumber.domain.TransactionReport;
import lv.nixx.poc.cucumber.service.TransactionReportService;

public class TransactionReportSteps {
	
	@Spy
	@InjectMocks
	private TransactionReportService service;
	
	@Mock
	private TransactionDao dao;
	
	private final TransactionTestContext transactionTestContext;
	
	public TransactionReportSteps(TransactionTestContext transactionTestContext) {
		this.transactionTestContext = transactionTestContext;
	}
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Given("^Transaction report service is available$")
	public void transactionServiceCreated() {
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
	
	@Then("expect report with following data:")
	public void checkReportRows(List<Map<String, String>> table) {
		
		Map<String, BigDecimal> expectedCounts = new HashMap<>();
		TransactionReport actualReport = this.transactionTestContext.actualReport;

		for (Map<String, String> row : table) {
			final String curr = row.get("currency");
			final String expectedCount = row.get("count");

			BigDecimal bd = null;
			if (actualReport.getCountField() == CountBy.Count) {
				long v = Long.parseLong(expectedCount);
				bd = BigDecimal.valueOf(v);
			} else if (actualReport.getCountField() == CountBy.Amount) {
				double v = Double.parseDouble(expectedCount);
				bd = BigDecimal.valueOf(v);
			}
			
			expectedCounts.put(curr, bd);
		}
		
		Map<String, BigDecimal> actualCounts = actualReport.getCurrency();
		
		assertThat(actualCounts.entrySet(), equalTo(expectedCounts.entrySet()));
	}

	@Then("expect total transaction count {int}")
	public void checkTotal(int count) {
		TransactionReport actualReport = this.transactionTestContext.actualReport;
		assertEquals("Incorrect transaction count", count, actualReport.getTotalOperationCount());
	}

	@Then("expect total amount {bigdecimal}")
	public void totalAmountExpect(BigDecimal totalAmount) {
		TransactionReport actualReport = this.transactionTestContext.actualReport;
		assertThat("Incorrect total amount", actualReport.getTotalAmount(), comparesEqualTo(totalAmount));
	}

}

