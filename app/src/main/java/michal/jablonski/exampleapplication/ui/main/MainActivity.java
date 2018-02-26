package michal.jablonski.exampleapplication.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import michal.jablonski.exampleapplication.R;
import michal.jablonski.exampleapplication.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainActivityPresenter.Controller {

    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenter(this);
    }

    @Override
    protected void onDestroy() {
        presenter.unBind();
        super.onDestroy();
    }
}
