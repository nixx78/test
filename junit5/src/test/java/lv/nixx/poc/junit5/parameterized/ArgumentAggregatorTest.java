package lv.nixx.poc.junit5.parameterized;

import lv.nixx.poc.junit5.service.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("CSV with argument aggregator sample")
class ArgumentAggregatorTest {

    @ParameterizedTest
    @CsvSource({
            "1:Account1:12.34, 1,Account1,12.34",
            "2:Account2:10.00, 2,Account2,10.00",
    })
    void transactionDescriptionTest(String expectedDescription, @AggregateWith(TxnAggregator.class) Transaction transaction) {
        assertEquals(expectedDescription, transaction.getDescription());
    }

    static class TxnAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Transaction()
                    .setId(accessor.getString(1))
                    .setAccountId(accessor.getString(2))
                    .setAmount(new BigDecimal(accessor.getString(3)));
        }
    }


}

