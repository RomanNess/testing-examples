package biz.cosee.mockitoexamples.assertj;

import biz.cosee.mockitoexamples.model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class AssertJGreaterHamcrestMatchers {

    private static final List<String> TO_ASSERT = Arrays.asList("alpha", "bravo", "charlie");

    @Test
    public void hamcrest() {
        org.hamcrest.MatcherAssert.assertThat(TO_ASSERT, is(notNullValue()));
        org.hamcrest.MatcherAssert.assertThat(TO_ASSERT, is(not(empty())));
        // no sub-sequence matchers :(
        org.hamcrest.MatcherAssert.assertThat(TO_ASSERT, contains("alpha", "bravo", "charlie"));
        // ???
    }

    @Test
    public void assertJ() {
        assertThat(TO_ASSERT).isNotNull()
                .isNotEmpty()
                .contains("alpha")
                .containsSubsequence("bravo", "charlie")
                .containsExactly("alpha", "bravo", "charlie")
                .doesNotContain("delta");
    }

    @Test
    public void fluentAssertions() {
        assertThat(TO_ASSERT)
                .extracting(e -> e.charAt(0))
                .containsExactly('a', 'b', 'c');
    }

    @Test
    public void softlyAssertions() {
        User user = new User(1337L, "leetman");
//        user = new User(42L, "answerman");    // demonstrate soft assertions

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(user.getId()).isEqualTo(1337L);
        softly.assertThat(user.getUsername()).isEqualToIgnoringCase("leetman");
        softly.assertAll(); // at some point you will forget this
    }
}
