package michal.jablonski.exampleapplication.rest.models.dailyMotionModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseDailyMotionModel {

    @SerializedName("list")
    private List<DailyMotionModel> models;

    public List<DailyMotionModel> getModels() {
        return models;
    }
}