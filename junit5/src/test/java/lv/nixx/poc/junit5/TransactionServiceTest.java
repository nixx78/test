package lv.nixx.poc.junit5;

import junit.framework.AssertionFailedError;
import lv.nixx.poc.junit5.service.Transaction;
import lv.nixx.poc.junit5.service.TransactionService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    TransactionService transactionService = new TransactionService();

    @Test
    public void test() throws ParseException {
        // @formatter:off
        new ResponseTestWrapper(transactionService.getAllTransactions())
                .expectedCount(5)
                .forAccount("ACC1")
                    .transactionById("1")
                        .exists()
                        .withExpectedAmount(10.01)
                        .withExpectedDate("01/04/2021")
                        .and()
                    .transactionById("NOT_EXISTING")
                        .notExists()
                        .andTest()
                .forAccount("ACC2")
                    .expectedCount(2)
                    .andTest()
                .forAccount("ACC3")
                   .expectedCount(3);
        // @formatter:on


    }

    class ResponseTestWrapper {

        final Map<String, Map<String, Transaction>> txns;

        ResponseTestWrapper(Collection<Transaction> allTransactions) {
            this.txns = allTransactions.stream()
                    .collect(Collectors.groupingBy(Transaction::getAccountId,
                            Collectors.toMap(Transaction::getId, Function.identity())));
        }

        ResponseTestWrapper expectedCount(long c) {
            assertEquals(c, txns.values()
                            .stream()
                            .mapToLong(t -> t.values().size())
                            .sum()
                    , "Wrong transaction count");

            return this;
        }

        AccountWrapper forAccount(String accountId) {
            return new AccountWrapper(accountId, this);
        }

        ResponseTestWrapper andTest() {
            return this;
        }

        class AccountWrapper {
            final String accountId;
            final ResponseTestWrapper testWrapper;
            final Optional<Map<String, Transaction>> ad;

            AccountWrapper(String accountId, ResponseTestWrapper testWrapper) {
                this.accountId = accountId;
                this.testWrapper = testWrapper;
                this.ad = Optional.ofNullable(txns.get(accountId));
            }

            TransactionWrapper transactionById(String id) {
                return new TransactionWrapper(this, id);
            }

            AccountWrapper expectedCount(int c) {
                assertEquals(c, ad.map(Map::size).orElseThrow( () -> new AssertionFailedError("Account with id:" + accountId + " not exists")), "Wrong txn count for account:" + accountId);
                return this;
            }

            ResponseTestWrapper andTest() {
                return testWrapper;
            }

            class TransactionWrapper {
                final AccountWrapper account;
                final Transaction txn;
                final String id;

                TransactionWrapper(AccountWrapper accountWrapper, String id) {
                    this.account = accountWrapper;
                    this.id = id;
                    this.txn = ad.map(t->t.get(id)).orElse(null);
                }

                TransactionWrapper exists() {
                    assertNotNull(txn, "Transaction with id: " + id + " not exists");
                    return this;
                }

                TransactionWrapper notExists() {
                    assertNull(txn, "Transaction with id: " + id + " must be null");
                    return this;
                }

                TransactionWrapper withExpectedAmount(double v) {
                    assertThat("Amount is wrong for id: " + txn.getId() + " account: " + txn.getAccountId(), txn.getAmount(), comparesEqualTo(BigDecimal.valueOf(v)));
                    return this;
                }

                TransactionWrapper withExpectedDate(String d) throws ParseException {
                    Date dt = new SimpleDateFormat("dd/MM/yyyy").parse(d);
                    assertEquals( dt, txn.getDate(), "Date is wrong for id: " + txn.getId() + " account: " + txn.getAccountId());
                    return this;
                }

                AccountWrapper and() {
                    return account;
                }

                public ResponseTestWrapper andTest() {
                    return account.testWrapper;
                }


            }
        }

    }


}
