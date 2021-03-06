package lv.nixx.poc.junit5;

import lv.nixx.poc.junit5.service.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Transaction fields test")
class TransactionTest {

    @Test
    @DisplayName("Test all Transaction fields")
    void calculatorFullTest() {

        final Transaction t = new Transaction()
                .setId("txn.id")
                .setAccountId("account.id");

        assertAll("Transaction",
                () -> assertEquals("txn.id", t.getId(), "Txn id is incorrect"),
                () -> assertEquals("account.id", t.getAccountId(), "Account id is incorrect")
        );

    }

}

