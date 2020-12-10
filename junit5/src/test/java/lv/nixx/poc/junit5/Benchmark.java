package lv.nixx.poc.junit5;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.*;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

@Target({ TYPE, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(BenchmarkExtension.class)
public @interface Benchmark { }