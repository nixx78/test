package lv.nixx.poc.cucumber.stepdef;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import lv.nixx.poc.cucumber.domain.Transaction;
import lv.nixx.poc.cucumber.service.TransactionDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class GenericTransactionReportSteps {

    private static final Logger log = LoggerFactory.getLogger(GenericTransactionReportSteps.class);

    private final TransactionDaoImpl transactionDao;

    public GenericTransactionReportSteps(TransactionDaoImpl transactionDao) {
        this.transactionDao = transactionDao;
    }

    @DataTableType
    public Transaction transactionEntry(Map<String, String> row) throws ParseException {
        return new Transaction()
                .setId(Long.parseLong(row.get("id")))
                .setAmount(BigDecimal.valueOf(Double.parseDouble(row.get("amount"))))
                .setCurrency(row.get("currency"))
                .setDate(new SimpleDateFormat("dd/MM/yyyy").parse(row.get("date")));
    }

    @Given("Transactions exists:")
    public void transactionsExists(List<Transaction> expectedTransactions) {
        transactionDao.setExpectedTransaction(expectedTransactions);
        log.info("Transactions in DB: " + expectedTransactions);
    }

}
