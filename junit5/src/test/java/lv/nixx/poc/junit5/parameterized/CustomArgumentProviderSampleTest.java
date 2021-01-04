package lv.nixx.poc.junit5.parameterized;

import lv.nixx.poc.junit5.service.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Custom Argument provider sample")
class CustomArgumentProviderSampleTest {

    @ParameterizedTest
    @ArgumentsSource(CustomArgumentProvider.class)
    void transactionDescriptionTest(String expectedDescription, Transaction transaction) {
        assertEquals(expectedDescription, transaction.getDescription());
    }

    private static class CustomArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of("1:Account1:12.34", new Transaction()
                            .setId("1")
                            .setAccountId("Account1")
                            .setAmount(BigDecimal.valueOf(12.34))
                    ),
                    Arguments.of("2:Account2:10.0", new Transaction()
                            .setId("2")
                            .setAccountId("Account2")
                            .setAmount(BigDecimal.valueOf(10.00))
                    )
            );
        }
    }


}

