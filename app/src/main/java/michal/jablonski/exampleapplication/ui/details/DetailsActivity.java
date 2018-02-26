package michal.jablonski.exampleapplication.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.transition.ArcMotion;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import michal.jablonski.exampleapplication.R;
import michal.jablonski.exampleapplication.rest.models.DataModel;
import michal.jablonski.exampleapplication.ui.base.BaseActivity;
import michal.jablonski.exampleapplication.ui.transitionUtils.SharedEnter;
import michal.jablonski.exampleapplication.ui.transitionUtils.SharedReturn;

public class DetailsActivity extends BaseActivity implements DetailsPresenter.Controller {

    private final float ANGLE = 50f;

    @BindView(R.id.container)
    protected LinearLayout container;

    @BindView(R.id.item_image_view)
    protected ImageView itemImageView;

    @BindView(R.id.item_text_view)
    protected TextView itemTextView;

    private DetailsPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        presenter = new DetailsPresenter(this);
    }

    @Override
    protected void onDestroy() {
        presenter.unBind();
        super.onDestroy();
    }

    @Override
    public void setUpSharedTransitions() {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(ANGLE);
        arcMotion.setMinimumVerticalAngle(ANGLE);

        Interpolator easeInOut =
                AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        SharedEnter sharedEnter = new SharedEnter();
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        SharedReturn sharedReturn = new SharedReturn();
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);

        sharedEnter.addTarget(container);
        sharedReturn.addTarget(container);

        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);
    }

    @Override
    public DataModel getDataModel() {
        Intent intent = getIntent();
        return intent.getParcelableExtra(DataModel.class.getSimpleName());
    }

    @Override
    public void loadImage(String url) {
        Glide.with(this)
                .load(url)
                .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                .into(itemImageView);
    }

    @Override
    public void setNicknameText(String nickname) {
        itemTextView.setText(nickname);
    }

    @Override
    public void setProperColorForDMInstance() {
        int color = ContextCompat.getColor(this, R.color.colorDailyMotion);
        itemTextView.setBackgroundColor(color);
        container.setBackgroundColor(color);
    }

    @Override
    public void setProperColorForGHInstance() {
        int color = ContextCompat.getColor(this, R.color.colorGitHub);
        itemTextView.setBackgroundColor(color);
        container.setBackgroundColor(color);
    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }
}
