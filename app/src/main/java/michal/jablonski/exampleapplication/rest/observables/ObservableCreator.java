package michal.jablonski.exampleapplication.rest.observables;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import michal.jablonski.exampleapplication.rest.api.ApiInterfaceDM;
import michal.jablonski.exampleapplication.rest.api.ApiInterfaceGH;
import michal.jablonski.exampleapplication.rest.models.DataModel;
import michal.jablonski.exampleapplication.rest.models.dailyMotionModel.DailyMotionModel;
import michal.jablonski.exampleapplication.rest.models.dailyMotionModel.DailyMotionModelUtil;

public class ObservableCreator {

    public static Observable<List<DataModel>> createGetUsersObservable(ApiInterfaceGH apiInterface) {
        return apiInterface
                .getUsers()
                .map(gitHubModels -> {
                    List<DataModel> models = new ArrayList<>();
                    models.addAll(gitHubModels);
                    return models;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<DataModel>> createGetUsersObservable(ApiInterfaceDM apiInterface) {
        return apiInterface
                .getUsers(DailyMotionModelUtil
                        .getFieldsQuery(
                                DailyMotionModel.Fields.avatar_360_url,
                                DailyMotionModel.Fields.username)
                )
                .map(baseDailyMotionModel -> {
                    List<DataModel> models = new ArrayList<>();
                    models.addAll(baseDailyMotionModel.getModels());
                    return models;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<DataModel>> mergeObservables(Observable<List<DataModel>> o1, Observable<List<DataModel>> o2) {
        return o1.mergeWith(o2);
    }
}