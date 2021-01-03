package lv.nixx.poc.cucumber.stepdef;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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
import lv.nixx.poc.cucumber.transaction.MonthStatistic;
import lv.nixx.poc.cucumber.transaction.Transaction;
import lv.nixx.poc.cucumber.transaction.TransactionDao;
import lv.nixx.poc.cucumber.transaction.TransactionReportService;

public class TransactionMonthlyReportSteps {
	
	private Map<String, Map<String, MonthStatistic>> actualReport;

	@Spy
	@InjectMocks
	private TransactionReportService service;

	@Mock
	private TransactionDao dao;
	
	private TransactionTestContext transactionTestContext;
	
	public TransactionMonthlyReportSteps(TransactionTestContext transactionTestContext) {
		this.transactionTestContext = transactionTestContext;
	}
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Given("^transaction service is available")
	public void transactionServiceCreated() throws Throwable {
		System.out.println("TransactionMonthlyReportSteps:transactionServiceCreated");
		Collection<Transaction> txn = transactionTestContext.txns;
		
		doReturn(txn).when(dao).getTransactions(any(Date.class), any(Date.class));
		service.setDao(dao);
	}
	

	@When("^create monthly report with date range: from \"([^\"]*)\" to \"([^\"]*)\"$")
	public void createReport(String dateFrom, String dateTo) throws ParseException {
		
		Date df = new SimpleDateFormat("dd/MM/yyyy").parse(dateFrom);
		Date dt = new SimpleDateFormat("dd/MM/yyyy").parse(dateTo);
		
		this.actualReport = service.createByMonthReport(dt, df);
		assertNotNull(this.actualReport);
	}
	
	@Then("^expect report with following data for \\\"([^\\\"]*)\\\"$")
	public void checkReportMonth(String month, DataTable table) {
		Map<String, MonthStatistic> actualMonth = actualReport.get(month);
		assertNotNull("Data for month: " + month + "should exists", actualMonth);
		
		for (Map<String, String> row : table.asMaps(String.class, String.class)) {
			final String curr = row.get("currency");
			final int expectedCount = new Integer(row.get("count"));
			final BigDecimal expectedAmount = new BigDecimal(row.get("amount"));
			
			MonthStatistic currStat = actualMonth.get(curr);
			assertNotNull("Statistic for currency: " + curr + "month: " + month + "should exists", currStat);
			
			assertEquals(expectedCount, currStat.getTxnCount());
			assertThat(expectedAmount, comparesEqualTo(currStat.getAmount()));
		}	
	}



}
