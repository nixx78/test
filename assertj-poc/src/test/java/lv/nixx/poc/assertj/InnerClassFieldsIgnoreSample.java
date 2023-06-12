package lv.nixx.poc.assertj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class InnerClassFieldsIgnoreSample {

    @Test
    void compareIgnoringFields() {

        Holder actualValue = new Holder(
                List.of(
                        new Field(1L, "Value1"),
                        new Field(2L, "Value2")),
                Map.of(
                        "f1", new MField(10L, "MValue1"),
                        "f2", new MField(20L, "MValue1")
                )
        );

        Holder expectedValue = new Holder(
                List.of(
                        new Field(21L, "Value1"),
                        new Field(22L, "Value2")),
                Map.of(
                        "f1", new MField(210L, "MValue1"),
                        "f2", new MField(220L, "MValue1")
                )
        );

        assertThat(actualValue)
                .usingRecursiveComparison()
                .ignoringFields("fields.id")
                .ignoringFieldsMatchingRegexes(".*mFieldId")
                .isEqualTo(expectedValue);

    }

    @AllArgsConstructor
    @Getter
    static class Field {
        long id;
        String value;
    }

    @AllArgsConstructor
    @Getter
    static class MField {
        long mFieldId;
        String value;
    }

    @AllArgsConstructor
    @Getter
    static class Holder {
        Collection<Field> fields;
        Map<String, MField> fieldsInMap;
    }

}
