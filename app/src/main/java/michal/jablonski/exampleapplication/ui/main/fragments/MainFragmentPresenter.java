package michal.jablonski.exampleapplication.ui.main.fragments;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import michal.jablonski.exampleapplication.core.Core;
import michal.jablonski.exampleapplication.rest.models.DataModel;
import michal.jablonski.exampleapplication.ui.base.BasePresenter;
import michal.jablonski.exampleapplication.ui.main.fragments.adapter.MainFragmentAdapter;

public class MainFragmentPresenter extends BasePresenter<MainFragment> {

    private static final String KEY_BUNDLE_ARRAY = "data_list";

    @Inject
    Observable<List<DataModel>> dataObservable;

    private final ArrayList<DataModel> dataList;

    interface Controller {

        void initRecyclerView(List<DataModel> list);

        void setPullToRefreshListener();

        void updateList();

        void showProgress();

        void hideProgress();

        void showErrorMessage(String message);

        void startDetailsActivity(MainFragmentAdapter.ViewHolder view, DataModel dataModel);

    }

    protected MainFragmentPresenter(MainFragment controller) {
        super(controller);
        Core.injectApiComponent(this);

        dataList = new ArrayList<>();
        getController().initRecyclerView(dataList);
        getController().setPullToRefreshListener();
    }

    protected void updateData(Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            loadSavedData(bundle);
        } else {
            downloadNewData();
        }
    }

    private void loadSavedData(Bundle bundle) {
        ArrayList<DataModel> dataArray = (ArrayList<DataModel>) bundle.getSerializable(KEY_BUNDLE_ARRAY);
        if (dataArray != null && !dataArray.isEmpty()) {
            dataList.clear();
            dataList.addAll(dataArray);
            getController().updateList();
        } else {
            downloadNewData();
        }
    }

    protected void saveInstance(Bundle outState) {
        outState.putSerializable(KEY_BUNDLE_ARRAY, dataList);
    }

    protected void downloadNewData() {
        Disposable disposable =
                dataObservable.
                        doOnSubscribe(d -> {
                            getController().showProgress();
                            dataList.clear();
                            getController().updateList();
                        })
                        .doOnComplete(() -> {
                            getController().hideProgress();
                            getController().updateList();
                        })
                        .subscribe(this::addDataToList, this::onDownloadingErrorOccurred);

        addToSubscribers(disposable);

    }

    private void addDataToList(List<DataModel> dataModels) {
        dataList.addAll(dataModels);
    }

    private void onDownloadingErrorOccurred(Throwable throwable) {
        getController().updateList();
        unSubscribeAllDisposables();
        getController().hideProgress();
        getController().showErrorMessage(throwable.getMessage());
    }


    public void onItemClicked(MainFragmentAdapter.ViewHolder view, int adapterPosition) {
        try {
            DataModel dataModel = dataList.get(adapterPosition);
            getController().startDetailsActivity(view, dataModel);
        } catch (IndexOutOfBoundsException ignored) {
        }
    }
}
