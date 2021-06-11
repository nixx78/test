package lv.nixx.poc.cucumber.stepdef;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lv.nixx.poc.cucumber.domain.MonthStatistic;
import lv.nixx.poc.cucumber.domain.Transaction;
import lv.nixx.poc.cucumber.service.TransactionDao;
import lv.nixx.poc.cucumber.service.TransactionReportService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

public class TransactionMonthlyReportSteps {

    private Map<String, Map<String, MonthStatistic>> actualReport;

    @Spy
    @InjectMocks
    private TransactionReportService service;

    @Mock
    private TransactionDao dao;

    private final TransactionTestContext transactionTestContext;

    public TransactionMonthlyReportSteps(TransactionTestContext transactionTestContext) {
        this.transactionTestContext = transactionTestContext;
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Given("transaction service is available")
    public void transactionServiceCreated() {
        System.out.println("TransactionMonthlyReportSteps:transactionServiceCreated");
        Collection<Transaction> txn = transactionTestContext.txns;

        doReturn(txn).when(dao).getTransactions(any(Date.class), any(Date.class));
        service.setDao(dao);
    }


    @When("create monthly report with date range: from {string} to {string}")
    public void createReport(String dateFrom, String dateTo) throws ParseException {
        final DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date df = format.parse(dateFrom);
        Date dt = format.parse(dateTo);

        this.actualReport = service.createByMonthReport(dt, df);
        assertNotNull(this.actualReport);
    }

    @Then("expect report with following data for {string}")
    public void checkReportMonth(String month, List<Map<String, String>> table) {
        Map<String, MonthStatistic> actualMonth = actualReport.get(month);
        assertNotNull("Data for month: " + month + "should exists", actualMonth);

        for (Map<String, String> row : table) {
            final String curr = row.get("currency");
            final int expectedCount = Integer.parseInt(row.get("count"));
            final BigDecimal expectedAmount = new BigDecimal(row.get("amount"));

            MonthStatistic currStat = actualMonth.get(curr);
            assertNotNull("Statistic for currency: " + curr + "month: " + month + "should exists", currStat);

            assertEquals(expectedCount, currStat.getTxnCount());
            assertThat(expectedAmount, comparesEqualTo(currStat.getAmount()));
        }
    }


}
