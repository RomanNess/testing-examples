package biz.cosee.mockitoexamples.mockito;

import biz.cosee.mockitoexamples.api.UserController;
import biz.cosee.mockitoexamples.model.User;
import biz.cosee.mockitoexamples.service.UserRepository;
import biz.cosee.mockitoexamples.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class OldSchoolMockitoTests {

    private UserController userController;

    private UserService userServiceSpy;

    private UserRepository userRepositoryMock;

    @Before
    public void hideInitializationInMethod() {
        userRepositoryMock = Mockito.mock(UserRepository.class);

        userServiceSpy = new UserService(userRepositoryMock);
        userServiceSpy = Mockito.spy(userServiceSpy);

        userController = new UserController(userServiceSpy);

        doReturn(new User(42L, "oldschool")).when(userRepositoryMock).getOne(eq(42L));
    }

    @Test
    public void itWorks() {
        User user = userController.getUser(42L);

        assertThat(user).isEqualTo(new User(42L, "oldschool"));
        verify(userServiceSpy).getUser(eq(42L));
        verify(userRepositoryMock).getOne(eq(42L));
    }
}
