package lv.nixx.poc.cucumber.domain;

import lv.nixx.poc.cucumber.service.TransactionDao;
import lv.nixx.poc.cucumber.service.TransactionReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class TransactionReportServiceTest {

    //TODO go throw definitions https://cucumber.io/docs/guides/10-minute-tutorial/
    @Spy
    @InjectMocks
    private TransactionReportService service;

    @Mock
    private TransactionDao dao;

    @Test
    public void monthTransactionTest() throws ParseException {

        final DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        final Date dateFrom = df.parse("01.01.2018");
        final Date dateTo = df.parse("31.12.2018");

        doReturn(List.of(
                new Transaction(1, "GBP", BigDecimal.valueOf(10.10), df.parse("01.03.2016")),
                new Transaction(2, "USD", BigDecimal.valueOf(20.12), df.parse("01.08.2016")),
                new Transaction(3, "EUR", BigDecimal.valueOf(1.25), df.parse("01.10.2016")),
                new Transaction(4, "EUR", BigDecimal.valueOf(3.75), df.parse("03.10.2016")),
                new Transaction(5, "USD", BigDecimal.valueOf(5.8), df.parse("02.10.2016")),
                new Transaction(6, "EUR", BigDecimal.valueOf(40.14), df.parse("01.12.2016"))
        )).when(dao).getTransactions(eq(dateFrom), eq(dateTo));

        Map<String, Map<String, MonthStatistic>> report = service.createByMonthReport(dateFrom, dateTo);
        assertNotNull(report);
        assertEquals(4, report.size());

        for (Map.Entry<String, Map<String, MonthStatistic>> e : report.entrySet()) {
            String month = e.getKey();
            System.out.println(month);
            for (Map.Entry<String, MonthStatistic> monthStats : e.getValue().entrySet()) {
                String currency = monthStats.getKey();
                System.out.println("\t" + currency + "\n \t\t" + monthStats.getValue());
            }
        }

    }


}
