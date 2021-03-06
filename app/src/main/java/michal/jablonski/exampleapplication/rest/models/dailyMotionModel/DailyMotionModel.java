package michal.jablonski.exampleapplication.rest.models.dailyMotionModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import michal.jablonski.exampleapplication.rest.models.DataModel;

public class DailyMotionModel implements DataModel, Serializable {

    public enum Fields {
        avatar_360_url, username
    }

    @SerializedName("username")
    private String userName;

    @SerializedName("avatar_360_url")
    private String avatarUrl;

    @Override
    public String getNickname() {
        return userName;
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }

    private DailyMotionModel(Parcel in) {
        String[] data = new String[2];
        in.readStringArray(data);

        this.userName = data[0];
        this.avatarUrl = data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.userName, this.avatarUrl});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public DailyMotionModel createFromParcel(Parcel in) {
            return new DailyMotionModel(in);
        }

        public DailyMotionModel[] newArray(int size) {
            return new DailyMotionModel[size];
        }
    };
}
