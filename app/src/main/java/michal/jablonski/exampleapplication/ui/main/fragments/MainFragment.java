package michal.jablonski.exampleapplication.ui.main.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import michal.jablonski.exampleapplication.R;
import michal.jablonski.exampleapplication.rest.models.DataModel;
import michal.jablonski.exampleapplication.ui.details.DetailsActivity;
import michal.jablonski.exampleapplication.ui.main.fragments.adapter.MainFragmentAdapter;

public class MainFragment extends Fragment
        implements MainFragmentPresenter.Controller, MainFragmentAdapter.ItemClickListener {

    private MainFragmentPresenter presenter;

    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_view)
    protected SwipeRefreshLayout swipeRefreshView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, inflatedView);
        presenter = new MainFragmentPresenter(this);
        presenter.updateData(savedInstanceState);
        return inflatedView;
    }

    @Override
    public void onDestroy() {
        presenter.unBind();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveInstance(outState);
    }

    @Override
    public void initRecyclerView(List<DataModel> list) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        MainFragmentAdapter adapter = new MainFragmentAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setPullToRefreshListener() {
        swipeRefreshView.setOnRefreshListener(() -> presenter.downloadNewData());
    }

    @Override
    public void updateList() {
        LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void showProgress() {
        swipeRefreshView.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshView.setRefreshing(false);
    }

    @Override
    public void showErrorMessage(String message) {
        Snackbar.make(swipeRefreshView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void startDetailsActivity(MainFragmentAdapter.ViewHolder view, DataModel dataModel) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(DataModel.class.getSimpleName(), dataModel);

        Pair<View, String> p1 = Pair.create(view.getItemImageView(), getString(R.string.transition_dialog_root));
        Pair<View, String> p2 = Pair.create(view.getItemTextView(), getString(R.string.transition_dialog_iv));

        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(getActivity(), p1, p2);

        startActivity(intent, options.toBundle());
    }

    @Override
    public void onItemClick(MainFragmentAdapter.ViewHolder view, int adapterPosition) {
        presenter.onItemClicked(view, adapterPosition);
    }
}
