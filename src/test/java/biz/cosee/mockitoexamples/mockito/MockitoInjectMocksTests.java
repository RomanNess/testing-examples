package biz.cosee.mockitoexamples.mockito;

import biz.cosee.mockitoexamples.api.UserController;
import biz.cosee.mockitoexamples.model.User;
import biz.cosee.mockitoexamples.service.UserRepository;
import biz.cosee.mockitoexamples.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MockitoInjectMocksTests {

    @Autowired
    private UserController userController;

    @SpyBean
    private UserService userServiceSpy;

    @MockBean
    private UserRepository userRepositoryMock;

    @Before
    public void setupMocks() {
        doReturn(new User(42L, "newguy")).when(userRepositoryMock).getOne(eq(42L));
    }

    // All of this also works without beans, with @InjectMocks, @Spy, @Mock
    @Test
    public void methodsOnSpyAreReallyCalled() {
        userServiceSpy.getUser(42L);
        verify(userServiceSpy).getUser(eq(42L));   // can call verify on a spy like on a mock
    }

    @Test
    public void argumentCaptorOnSpy() {
        User user = userController.getUser(42L);

        ArgumentCaptor<Long> userIdCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userServiceSpy).getUser(userIdCaptor.capture());

        Long calledUserId = userIdCaptor.getValue();
        assertThat(calledUserId).isEqualTo(42L);

        verify(userRepositoryMock, times(1)).getOne(anyLong());
        assertThat(user).isEqualTo(new User(42L, "newguy"));
    }

    @Test
    public void mockMethodOnSpy() {
        doThrow(new RuntimeException()).when(userServiceSpy).getUser(anyLong());

        assertThatThrownBy(() -> userController.getUser(42L))
                .isInstanceOf(RuntimeException.class);
    }
}