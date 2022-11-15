package lv.nixx.poc.assertj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Person {
    private Long id;
    private String name;
    private String surname;
    private Type type;

    enum Type {
        SIMPLE, VIP, STUDENT
    }

}
