package com.app.onebuy.show;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.other.PhotoScanActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowImgGridAdapter extends RecyclerView.Adapter<ShowImgGridAdapter.ViewHolder> {

    Activity mContext;
    List<String> mListBean;

    public ShowImgGridAdapter(Activity mContext, List<String> mListBean) {
        this.mContext = mContext;
        this.mListBean = mListBean;
    }

    public void setData(List<String> mListBean) {
        this.mListBean = mListBean;
        notifyDataSetChanged();
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_img_grid_item, viewGroup, false);
        return new ViewHolder(view);
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String data = mListBean.get(position);
        BasisApp.loadImg(mContext, data, viewHolder.img_photo, R.mipmap.img_item_small_default);
        viewHolder.list_item.setTag(position);
//            viewHolder.list_item.setBackgroundResource(R.drawable.item_shadow_bluelight_selector);

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mListBean == null ? 0 : mListBean.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_item)
        public View list_item;
        @BindView(R.id.img_photo)
        public ImageView img_photo;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.list_item)
        void onItemClick(View v) {
            int position = (int) v.getTag();
            PhotoScanActivity.toImgScanActivityt(mContext, (ArrayList<String>) mListBean, position);
        }

    }
}