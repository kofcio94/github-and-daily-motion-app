package michal.jablonski.exampleapplication.ui.base;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class BasePresenter<T> {

    private T controller = null;

    private List<Disposable> disposables;

    public BasePresenter(T controller) {
        this.controller = controller;
        disposables = new ArrayList<>();
    }

    protected T getController() {
        return controller;
    }

    protected void unSubscribeAllDisposables() {
        if (disposables == null) return;

        for (Disposable disposable : disposables) {
            if (disposable != null && !disposable.isDisposed())
                disposable.dispose();
        }
    }

    protected void addToSubscribers(Disposable... disposables) {
        if (this.disposables == null) return;
        if (disposables == null) return;

        for (Disposable disposable : disposables) {
            if (disposable != null)
                this.disposables.add(disposable);
        }

    }

    public void unBind() {
        unSubscribeAllDisposables();
    }
}
