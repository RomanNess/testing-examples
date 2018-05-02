package biz.cosee.mockitoexamples.external;

import biz.cosee.mockitoexamples.model.User;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ExternalUserServiceRetrofitClient {

    @GET("/users")
    Call<List<User>> getUsers();
}
