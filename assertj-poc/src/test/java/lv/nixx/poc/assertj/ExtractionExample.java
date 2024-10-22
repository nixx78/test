package lv.nixx.poc.assertj;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class ExtractionExample {

    @Test
    void extractAndCheckTest() {

        Collection<Customer> actualValues = List.of(
                new CustomerImpl("Name1", "Surname1", BigDecimal.valueOf(100.01)),
                new CustomerImpl("Name2", "Surname2", BigDecimal.valueOf(200.02)),
                new CustomerImpl("Name3", "Surname3", BigDecimal.valueOf(300.00))
        );

        assertThat(actualValues)
                .extracting(Customer::name, Customer::surname, Customer::salary)
                .containsExactly(
                        tuple("Name1", "Surname1", BigDecimal.valueOf(100.01)),
                        tuple("Name2", "Surname2", BigDecimal.valueOf(200.02)),
                        tuple("Name3", "Surname3", BigDecimal.valueOf(300.00))
                );
    }


    interface Customer {
        String name();

        String surname();

        BigDecimal salary();
    }

    static class CustomerImpl implements Customer {

        final String name;
        final String surname;
        final BigDecimal salary;

        public CustomerImpl(String name, String surname, BigDecimal salary) {
            this.name = name;
            this.surname = surname;
            this.salary = salary;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public String surname() {
            return surname;
        }

        @Override
        public BigDecimal salary() {
            return salary;
        }
    }


}
