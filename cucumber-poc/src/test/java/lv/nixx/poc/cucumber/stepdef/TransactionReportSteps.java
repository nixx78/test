package lv.nixx.poc.cucumber.stepdef;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lv.nixx.poc.cucumber.domain.CountBy;
import lv.nixx.poc.cucumber.domain.TransactionReport;
import lv.nixx.poc.cucumber.service.TransactionReportService;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionReportSteps {

    private final TransactionReportService service;

    private final TransactionTestContext transactionTestContext;

    public TransactionReportSteps(TransactionTestContext transactionTestContext, TransactionReportService service) {
        this.transactionTestContext = transactionTestContext;
        this.service = service;
    }

    @When("^create report with date range: from \\\"([^\\\"]*)\\\" to \\\"([^\\\"]*)\\\" and count by field \\\"([^\\\"]*)\\\"$")
    public void createReport(String dateFrom, String dateTo, CountBy countBy) throws ParseException {

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Date df = format.parse(dateFrom);
        Date dt = format.parse(dateTo);

        TransactionReport report = service.createReport(df, dt, countBy);
        assertNotNull(report);

        this.transactionTestContext.actualReport = report;
    }

    @Then("expect report with following data:")
    public void checkReportRows(List<Map<String, String>> expectedCounts) {

        Map<String, BigDecimal> collect = expectedCounts
                .stream()
                .collect(toMap(k -> k.get("currency"), v -> new BigDecimal(v.get("count"))));

        TransactionReport actualReport = this.transactionTestContext.actualReport;
        assertThat(actualReport.getCurrency().entrySet(), equalTo(collect.entrySet()));
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

