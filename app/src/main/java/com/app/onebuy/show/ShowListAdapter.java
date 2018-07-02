package com.app.onebuy.show;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.bean.CommentListBean;
import com.app.onebuy.bean.CommentListData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class ShowListAdapter extends RecyclerView.Adapter<ShowListAdapter.ViewHolder> {

    Activity mContext;
    CommentListBean mListBean;

        public ShowListAdapter(Activity mContext,CommentListBean mListBean) {
            this.mContext = mContext;
            this.mListBean = mListBean;
        }
        public void setData(CommentListBean mListBean){
            this.mListBean = mListBean;
            notifyDataSetChanged();
        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_list_item, viewGroup, false);
            return new ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            CommentListData data = mListBean.getList().get(position);
            viewHolder.text_name.setText(data.getNickname());
            viewHolder.text_content.setText(data.getContent());
            viewHolder.text_time.setText(data.getDate());
            viewHolder.text_title.setText("【" + data.getPeriod_num() +mContext.getString(R.string.stage)+ "】"+data.getGoods_name());
            viewHolder.list_item.setTag(data);
//            viewHolder.list_item.setBackgroundResource(R.drawable.item_shadow_bluelight_selector);
            viewHolder.recyclerview.setAdapter(new ShowImgGridAdapter(mContext,data.getImages_list()));

            BasisApp.loadImgAsbitmap(mContext,data.getHead_url(),viewHolder.img_photo,R.mipmap.img_item_small_default);

        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mListBean == null ? 0 : mListBean.getList().size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.list_item)
            public View list_item;
            @BindView(R.id.text_name)
            public TextView text_name;
            @BindView(R.id.text_time)
            public TextView text_time;
            @BindView(R.id.text_title)
            public TextView text_title;
            @BindView(R.id.text_content)
            public TextView text_content;
            @BindView(R.id.img_photo)
            public ImageView img_photo;
            @BindView(R.id.recyclerview)
            public RecyclerView recyclerview;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);

                GridLayoutManager layoutManager = new GridLayoutManager(mContext,3);
                recyclerview .setLayoutManager(layoutManager);
            }

            @OnClick(R.id.list_item)
            public void onItemClick(View v) {
//                    AppListData data = (AppListData) v.getTag();
//                    data.toDetail(mContext);
            }

            @OnLongClick(R.id.list_item)
            public boolean onItemLongClick(View v){
                if(mContext instanceof MyShowActivity){
                    CommentListData data = (CommentListData) v.getTag();
                    ((MyShowActivity)mContext).onCommentDelete(data);
                }
                return  true;
            }
        }
    }