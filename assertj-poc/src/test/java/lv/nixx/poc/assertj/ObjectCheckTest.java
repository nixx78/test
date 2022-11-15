package lv.nixx.poc.assertj;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.util.List;

import static lv.nixx.poc.assertj.Person.Type.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.in;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;


//TODO https://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html

class ObjectCheckTest {

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

        assertThat(persons).filteredOn(onlyVipPersons)
                .containsOnly(vip1, vip2);

        assertThat(persons).extracting("id")
                .contains(102L, 103L)
                .doesNotContain(10L);

        assertThat(persons).extracting("id", "name")
                .contains(
                        tuple(102L, "Name.Value"),
                        tuple(103L, "Name.Vip2")
                );

    }

    Condition<Person> onlyVipPersons = new Condition<>() {
        @Override
        public boolean matches(Person person) {
            return person.getType().equals(VIP);
        }
    };


}
