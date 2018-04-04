package biz.cosee.mockitoexamples.mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)  // no longer necessary to call MockitoAnnotations.initMocks(..)
public class MockitoInjectMocksPojoTests {

    @InjectMocks    // it works with POJOs
    private Wrapper wrapper = new Wrapper();

    @Mock
    private Impl impl;

    @Test
    public void run() {
        doReturn(42).when(impl).run();
        assertThat(wrapper.run()).isEqualTo(42);
    }

    //////////////// some POJOs
    private class Wrapper {
        private Impl impl;  // implementation does not even allow to set this field

        int run() {
            return impl.run();
        }
    }

    private class Impl {
        int run() {
            throw new RuntimeException("Implementation is broken.");
        }
    }
}
