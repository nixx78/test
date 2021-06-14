package lv.nixx.poc.cucumber.stepdef;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lv.nixx.poc.cucumber.domain.MonthStatistic;
import lv.nixx.poc.cucumber.service.TransactionReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionMonthlyReportSteps {

    private static final Logger log = LoggerFactory.getLogger(TransactionMonthlyReportSteps.class);

    private Map<String, Map<String, MonthStatistic>> actualReport;

    private final TransactionReportService service;

    public TransactionMonthlyReportSteps(TransactionReportService service) {
        this.service = service;
    }

    @When("create monthly report with date range: from {string} to {string}")
    public void createReport(String dateFrom, String dateTo) throws ParseException {
        final DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date fromDate = format.parse(dateFrom);
        Date toDate = format.parse(dateTo);

        this.actualReport = service.createByMonthReport(fromDate, toDate);
        log.info("Report created for dates, from [{}] to [{}]", fromDate, toDate);
        assertNotNull(this.actualReport);
    }

    @Then("expect report with following data for {string}")
    public void checkReportMonth(String month, List<Map<String, String>> table) {
        Map<String, MonthStatistic> actualMonth = actualReport.get(month);
        assertNotNull("Data for month: " + month + " should exists", actualMonth);

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
