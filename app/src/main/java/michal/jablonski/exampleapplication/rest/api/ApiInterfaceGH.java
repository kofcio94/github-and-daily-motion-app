package michal.jablonski.exampleapplication.rest.api;

import java.util.List;

import io.reactivex.Observable;
import michal.jablonski.exampleapplication.rest.models.gitHubModel.GitHubModel;
import retrofit2.http.GET;

public interface ApiInterfaceGH {

    String USERS = "users";

    @GET(USERS)
    Observable<List<GitHubModel>> getUsers();
}
