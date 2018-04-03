package biz.cosee.mockitoexamples.exception;

import biz.cosee.mockitoexamples.service.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionTests {

    private static final RuntimeException CAUSE = new RuntimeException("something catched fire.");

    @Mock
    private UserService userService;

    @Before
    public void setupMocks() {
        doThrow(new SomethingWentWrongException("Get the fire extinguisher.", CAUSE))
                .when(userService).getUser(eq(1L));
    }

    @Test(expected = SomethingWentWrongException.class)
    public void minimal() {
        userService.getUser(1L);
    }

    @Test
    public void manual() {
        try {
            userService.getUser(1L);
            fail("expected SomethingWentWrongException");
        } catch (SomethingWentWrongException ex) {
            assertThat(ex.getMessage()).contains("fire extinguisher");
            Throwable cause = ex.getCause();
            assertThat(cause).isInstanceOf(RuntimeException.class);
            assertThat(cause).isEqualTo(CAUSE);
        }
    }

    @Test
    public void withAssertJ() {
        assertThatThrownBy(() -> userService.getUser(1L))
                .isInstanceOf(SomethingWentWrongException.class)
                .hasMessageContaining("fire extinguisher")
                .hasCauseExactlyInstanceOf(RuntimeException.class)
                .hasCause(CAUSE);
    }

    //////////
    @Rule
    public ExpectedException thrown = ExpectedException.none(); // must be public

    @Test
    public void withRule() {
        thrown.expect(SomethingWentWrongException.class);
        thrown.expectMessage(contains("fire extinguisher"));
        thrown.expectCause(is(CAUSE));

        userService.getUser(1L);
    }
}