package michal.jablonski.exampleapplication.ui.main.fragments.adapter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import michal.jablonski.exampleapplication.R;
import michal.jablonski.exampleapplication.rest.models.DataModel;
import michal.jablonski.exampleapplication.rest.models.dailyMotionModel.DailyMotionModel;

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ViewHolder> {

    private List<DataModel> list;
    private ItemClickListener clickListener;

    public MainFragmentAdapter(List<DataModel> list, ItemClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DataModel dataModel = list.get(position);

        String nickname = dataModel.getNickname();
        holder.itemTextView.setText(nickname);

        if (dataModel instanceof DailyMotionModel) {
            setTextColor(R.color.colorDailyMotion, holder.itemTextView);
        } else {
            setTextColor(R.color.colorGitHub, holder.itemTextView);
        }

        String imageUrl = dataModel.getAvatarUrl();
        loadImage(holder.itemImageView, imageUrl);
    }

    private void setTextColor(int color, TextView itemTextView) {
        itemTextView.setBackgroundColor(ContextCompat.getColor(itemTextView.getContext(), color));
    }

    private void loadImage(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            loadBg(itemMainFrame);
        }

        @BindView(R.id.item_image_view)
        ImageView itemImageView;

        @BindView(R.id.item_text_view)
        TextView itemTextView;

        @BindView(R.id.item_main_frame)
        View itemMainFrame;

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(this, getAdapterPosition());
        }

        private void loadBg(View mainFrame) {
            Random random = new Random();
            int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
            mainFrame.setBackgroundColor(color);
        }

        public ImageView getItemImageView() {
            return itemImageView;
        }

        public TextView getItemTextView() {
            return itemTextView;
        }

        public View getItemMainFrame() {
            return itemMainFrame;
        }

        public View getRootView() {
            return itemView;
        }
    }

    public interface ItemClickListener {
        void onItemClick(ViewHolder view, int adapterPosition);
    }
}