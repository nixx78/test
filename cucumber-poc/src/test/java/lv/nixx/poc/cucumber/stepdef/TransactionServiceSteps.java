package lv.nixx.poc.cucumber.stepdef;

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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TransactionServiceSteps {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceSteps.class);

    private final TransactionService transactionService;
    private Transactions actualTransaction;

    public TransactionServiceSteps(TransactionService transactionService) {
        this.transactionService = transactionService;
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
                ()-> assertThat(actualTransaction.getTotal(), comparesEqualTo(total)),
                ()-> assertArrayEquals(expectedTransactions.toArray(), actualTransaction.getTxns().toArray())
        );

    }
}
