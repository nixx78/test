package lv.nixx.poc.assertj;

import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static lv.nixx.poc.assertj.Person.Type.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.in;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;


//TODO https://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html

class ObjectCheckTest {

    private final List<Account> accounts = List.of(
            new Account(1L, List.of(
                    new Transaction("10", "ACC1", 10.01, "01/04/2021"),
                    new Transaction("11", "ACC1", 10.02, "01/04/2021"),
                    new Transaction("12", "ACC1", 10.03, "01/04/2021")
            )),
            new Account(2L, List.of(
                    new Transaction("20", "ACC2", 0.01, "01/05/2021"),
                    new Transaction("21", "ACC2", 30.02, "01/06/2021"),
                    new Transaction("22", "ACC2", 13.03, "01/04/2021")
            )),
            new Account(3L, List.of(
                    new Transaction("30", "ACC3", 5.01, "01/04/2021"),
                    new Transaction("31", "ACC3", 7.02, "01/08/2021"),
                    new Transaction("32", "ACC3", 50.03, "01/04/2021")
            ))
    );

    @Test
    void playground() {
        Person p = new Person(100L, "Name.Value", "Surname.Value", SIMPLE);
        assertThat(p.getName()).as("check person with id [%s] name", p.getId()).isEqualTo("Name.Value");
    }

    @Test
    void collectionCheckPlayground() {

        Person vip1 = new Person(102L, "Name.Value", "Surname.Value", VIP);
        Person vip2 = new Person(103L, "Name.Vip2", "Surname.Value", VIP);
        Person student1 = new Person(100L, "Name.Value", "Surname.Value", STUDENT);
        List<Person> persons = List.of(
                student1,
                new Person(101L, "Name.Value", "Surname.Value", SIMPLE),
                vip1,
                vip2
        );

        assertThat(persons).filteredOn("type", in(VIP, STUDENT))
                .containsOnly(vip1, vip2, student1);

        assertThat(persons).filteredOn("type", VIP)
                .containsOnly(vip1, vip2);

        assertThat(persons)
                .filteredOn("type", VIP)
                .filteredOn("name", "Name.Vip2")
                .containsOnly(vip2);

        Condition<Person> onlyVipPersons = new Condition<>() {
            @Override
            public boolean matches(Person person) {
                return person.getType().equals(VIP);
            }
        };

        assertThat(persons).filteredOn(onlyVipPersons)
                .containsOnly(vip1, vip2);

        assertThat(persons).extracting("id")
                .contains(102L, 103L)
                .doesNotContain(10L);

        assertThat(persons).extracting("id", Long.class
                )
                .contains(102L, 103L)
                .doesNotContain(10L);

        assertThat(persons).extracting("id", "name")
                .contains(
                        tuple(102L, "Name.Value"),
                        tuple(103L, "Name.Vip2")
                );

    }

    @Test
    void flatMapAndExtractingResultOfTest() {

        assertThat(accounts)
                .flatMap(Account::getTransaction)
                .filteredOn(t -> t.getAmount().compareTo(BigDecimal.valueOf(30)) > 0)
                .extracting("id", String.class)
                .contains("21", "32");

        assertThat(accounts)
                .flatExtracting("transaction")
                .doesNotContainNull();

        assertThat(accounts)
                .extractingResultOf("totalTxnAmount")
                .contains(BigDecimal.valueOf(30.06), BigDecimal.valueOf(43.06), BigDecimal.valueOf(62.06));

    }

    @Test
    void softAssertionTest() {
        Person p1 = new Person(100L, "Name.Value", "Surname.Value", SIMPLE);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(p1.getName()).as("Person name").isEqualTo("Name.Value");
        softly.assertThat(p1.getSurname()).as("Person surname").isEqualTo("Surname.Value");
        softly.assertThat(p1.getType()).as("Person type").isEqualTo(SIMPLE);

        softly.assertAll();
    }

    @Test
    void softAssertionWithAutoCloseableTest() {
        Person p1 = new Person(100L, "Name.Value", "Surname.Value", SIMPLE);

        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(p1.getName()).as("Person name").isEqualTo("Name.Value");
            softly.assertThat(p1.getSurname()).as("Person surname").isEqualTo("Surname.Value");
            softly.assertThat(p1.getType()).as("Person type").isEqualTo(SIMPLE);
        }
    }

    @Test
    void softAssertionAssertSoftlyTest() {
        Person p1 = new Person(100L, "Name.Value", "Surname.Value", SIMPLE);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(p1.getName()).as("Person name").isEqualTo("Name.Value");
            softly.assertThat(p1.getSurname()).as("Person surname").isEqualTo("Surname.Value");
            softly.assertThat(p1.getType()).as("Person type").isEqualTo(SIMPLE);
        });
    }




}
