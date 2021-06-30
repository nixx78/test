package lv.nixx.poc.cucumber.stepdef;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lv.nixx.poc.cucumber.domain.Transaction;
import lv.nixx.poc.cucumber.domain.Transactions;
import lv.nixx.poc.cucumber.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TransactionServiceSteps {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceSteps.class);

    private final TransactionService transactionService;
    private Transactions actualTransaction;

    public TransactionServiceSteps(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Before
    public void beforeStep(Scenario scenario) {
        Collection<String> sourceTagNames = scenario.getSourceTagNames();
        log.info("Scenario executed with tags: {}", sourceTagNames);
    }

    @When("request transactions from service for date: {string}")
    public void request_transactions_from_service_for_date(String date) throws ParseException {
        final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        actualTransaction = transactionService.getTransactions(df.parse(date));
        log.info("Transactions [{}] received from service", actualTransaction);
    }


    @Then("expect transaction with total amount {bigdecimal}:")
    public void expectTransactionWithTotalAmount(BigDecimal total, List<Transaction> expectedTransactions) {

        assertAll(
                () -> assertThat(actualTransaction.getTotal(), comparesEqualTo(total)),
                () -> assertArrayEquals(expectedTransactions.toArray(), actualTransaction.getAllTransactions().toArray())
        );
    }

    @Then("expect transaction with id {int} and the following details:")
    public void expectTransactionWithIdAndTheFollowingDetails(int id, DataTable dataTable) throws ParseException {

        Map<String, String> fieldsToValidate = dataTable.asMap(String.class, String.class);

        Holder h = new Holder(id)
                .setCurrency(fieldsToValidate.get("currency"))
                .setAmount(fieldsToValidate.get("amount"))
                .setDate(fieldsToValidate.get("date"));


        Transaction t = actualTransaction.getTransaction((long) id);
        assertAll(
                () -> assertEquals("Currency not valid, transaction id [" + id + "]", t.getCurrency(), h.currency),
                () -> assertThat("Amount not valid, transaction id [" + id + "]", t.getAmount(), comparesEqualTo(h.amount)),
                () -> assertThat("Date not valid, transaction id [" + id + "]", t.getDate(), comparesEqualTo(h.date))
        );

    }

    static class Holder {
        long id;
        String currency;
        BigDecimal amount;
        Date date;

        public Holder(long id) {
            this.id = id;
        }

        public Holder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Holder setAmount(String amount) {
            this.amount = new BigDecimal(amount);
            return this;
        }

        public Holder setDate(String date) throws ParseException {
            this.date = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            return this;
        }
    }

}
