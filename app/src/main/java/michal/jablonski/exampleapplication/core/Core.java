package michal.jablonski.exampleapplication.core;

import android.app.Application;

import michal.jablonski.exampleapplication.rest.modules.ApiComponent;
import michal.jablonski.exampleapplication.rest.modules.DaggerApiComponent;
import michal.jablonski.exampleapplication.ui.main.fragments.MainFragmentPresenter;

public class Core extends Application {

    private static ApiComponent apiComponent;

    public static void injectApiComponent(MainFragmentPresenter presenter) {
        apiComponent.inject(presenter);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        apiComponent = DaggerApiComponent.create();
    }
}
