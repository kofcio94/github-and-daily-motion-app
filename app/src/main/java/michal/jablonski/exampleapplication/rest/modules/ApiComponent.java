package michal.jablonski.exampleapplication.rest.modules;

import javax.inject.Singleton;

import dagger.Component;
import michal.jablonski.exampleapplication.ui.main.fragments.MainFragmentPresenter;

@Singleton
@Component(modules = {HttpModule.class, ApiModule.class})
public interface ApiComponent {
    void inject(MainFragmentPresenter presenter);
}
