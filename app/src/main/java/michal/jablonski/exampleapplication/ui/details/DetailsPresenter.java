package michal.jablonski.exampleapplication.ui.details;

import michal.jablonski.exampleapplication.rest.models.DataModel;
import michal.jablonski.exampleapplication.rest.models.dailyMotionModel.DailyMotionModel;
import michal.jablonski.exampleapplication.ui.base.BasePresenter;

public class DetailsPresenter extends BasePresenter<DetailsActivity> {

    private DataModel dataModel;

    interface Controller {
        void setUpSharedTransitions();

        DataModel getDataModel();

        void loadImage(String url);

        void setNicknameText(String nickname);

        void setProperColorForDMInstance();

        void setProperColorForGHInstance();
    }

    public DetailsPresenter(DetailsActivity view) {
        super(view);
        getController().setUpSharedTransitions();
        dataModel = getController().getDataModel();
        getController().loadImage(dataModel.getAvatarUrl());
        getController().setNicknameText(dataModel.getNickname());

        if (dataModel instanceof DailyMotionModel) {
            getController().setProperColorForDMInstance();
        } else {
            getController().setProperColorForGHInstance();
        }
    }
}
