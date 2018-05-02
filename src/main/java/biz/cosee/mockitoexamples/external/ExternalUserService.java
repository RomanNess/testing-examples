package biz.cosee.mockitoexamples.external;

import biz.cosee.mockitoexamples.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class ExternalUserService {

    private final String baseUrl;

    private final ObjectMapper objectMapper;

    private ExternalUserServiceRetrofitClient externalUserServiceRetrofitClient;

    @Autowired
    public ExternalUserService(@Value("${external.userService.baseUrl}") String baseUrl, ObjectMapper objectMapper) {
        this.baseUrl = baseUrl;
        this.objectMapper = objectMapper;
    }

    @VisibleForTesting
    @PostConstruct
    void intializeRetrofitClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        externalUserServiceRetrofitClient = retrofit.create(ExternalUserServiceRetrofitClient.class);
    }

    public List<User> getUsers() {
        try {
            Response<List<User>> response = externalUserServiceRetrofitClient.getUsers().execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                // fixme no real error handling here
                throw new ExternalServiceException("Failed to get users.");
            }
        } catch (IOException e) {
            throw new ExternalServiceException("Failed to get users.", e);
        }
    }
}
