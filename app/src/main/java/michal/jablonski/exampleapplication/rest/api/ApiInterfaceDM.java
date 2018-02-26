package michal.jablonski.exampleapplication.rest.api;

import io.reactivex.Observable;
import michal.jablonski.exampleapplication.rest.models.dailyMotionModel.BaseDailyMotionModel;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceDM {

    String FIELDS = "fields";
    String USERS = "users";

    @GET(USERS)
    Observable<BaseDailyMotionModel> getUsers(@Query(FIELDS) String fields);
}
