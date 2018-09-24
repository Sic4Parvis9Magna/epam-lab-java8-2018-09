package lambda.part1.exercise;

import org.junit.jupiter.api.Test;

import java.util.StringJoiner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"FieldCanBeLocal", "ConstantConditions", "unused"})
class Exercise2 {

    @FunctionalInterface
    private interface Multiplier<T> {

        T multiply(T value, int multiplier);

        default T twice(T t) {
            return multiply(t, 2);
        }
    }

    @Test
    void implementsIntegerMultiplierUsingAnonymousClass() {
        Multiplier<Integer> multiplier = null;
        multiplier = new Multiplier<Integer>() {
            @Override
            public Integer multiply(Integer value, int multiplier) {
                return value * multiplier;
            }
        };
        testIntegerMultiplier(multiplier);
    }

    @Test
    void implementsMultiplierUsingStatementLambda() {
        Multiplier<Integer> multiplier = null;
        multiplier = (v1, v2) ->{ return v1 * v2;};
        testIntegerMultiplier(multiplier);
    }

    @Test
    void implementsIntegerMultiplierUsingExpressionLambda() {
        Multiplier<Integer> multiplier = null;
        multiplier = (v1, v2) -> v1 * v2;
        testIntegerMultiplier(multiplier);
    }

    private void testIntegerMultiplier(Multiplier<Integer> multiplier) {
        assertThat(multiplier.multiply(3, 2), is(6));
        assertThat(multiplier.multiply(Integer.MIN_VALUE, 0), is(0));
        assertThat(multiplier.multiply(7, -1), is(-7));
        assertThat(multiplier.twice(5), is(10));
        assertThat(multiplier.twice(0), is(0));
    }

    private static String multiplyString(String string, int number) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number; ++i) {
            builder.append(string);
        }
        return builder.toString();
    }

    private static String multiply(String value, int multiplier) {
            if(multiplier == 0) return "";
            StringBuilder sb = new StringBuilder(value);
            for (int i= 1; i < multiplier; i++ )
                sb.append(value);
            return sb.toString();
    }

    @Test
    void implementsStringMultiplierUsingClassMethodReference() {
        Multiplier<String> multiplier = null;
        multiplier = Exercise2::multiply;
        assertThat(multiplier.multiply("a", 3), is("aaa"));
        assertThat(multiplier.multiply("qwerty", 0), is(emptyString()));
        assertThat(multiplier.twice("aA"), is("aAaA"));
        assertThat(multiplier.twice(""), is(emptyString()));
    }

    private final String delimiter = "-";

    private String stringSumWithDelimiter(String string, int number) {
        StringJoiner joiner = new StringJoiner(delimiter);
        for (int i = 0; i < number; ++i) {
            joiner.add(string);
        }
        String result = joiner.toString();
        return result.equals(delimiter) ? "" : result;
    }

    @Test
    void implementsStringMultiplierUsingObjectMethodReference() {
        Multiplier<String> multiplier = null;
        Exercise2 ex = new Exercise2();
        multiplier = ex::stringSumWithDelimiter;
        assertThat(multiplier.multiply("a", 3), is("a-a-a"));
        assertThat(multiplier.multiply("qwerty", 0), is(emptyString()));
        assertThat(multiplier.twice("A"), is("A-A"));
        assertThat(multiplier.twice(""), is(emptyString()));
    }
}
