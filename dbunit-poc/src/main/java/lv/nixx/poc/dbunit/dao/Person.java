package lv.nixx.poc.dbunit.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.HashSet;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
public class Person {
    private int id;
    private String name;
    private String surname;
    private Collection<Address> addresses = new HashSet<>();

    public void addAddress(Address a) {
        this.addresses.add(a);
    }
}
