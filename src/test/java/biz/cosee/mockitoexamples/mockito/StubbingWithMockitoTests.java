package biz.cosee.mockitoexamples.mockito;

import biz.cosee.mockitoexamples.model.User;
import biz.cosee.mockitoexamples.service.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StubbingWithMockitoTests {

    @Mock
    private UserRepository userRepository;

    @Test
    public void stubRepositoryWithMock() {
        when(userRepository.getOne(any(Long.class))).thenAnswer(i -> {
            Long userId = i.getArgument(0);
            return new User(userId, "user"+userId);
        });

        assertThat(userRepository.getOne(23L).getUsername()).isEqualToIgnoringCase("user23");
        assertThat(userRepository.getOne(42L).getUsername()).isEqualToIgnoringCase("user42");
    }
}
