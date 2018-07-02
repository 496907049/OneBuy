package com.app.onebuy.show;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.basis.GlideImageLoaderForimgpickup;
import com.app.onebuy.bean.BasicBeanStr;
import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.DialogUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class AddShowActivity extends BasisActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener{

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    ArrayList<ImageItem> mListBean = new ArrayList<>();
    private int maxImgCount = 3;               //允许选择图片最大数
    ImagePickerAdapter adapter;

    @BindView(R.id.edit_conent)
    EditText edit_conent;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.tv_max_length)
    TextView mTvMaxLength;
    String period_id;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.show_addshow_activity);
        setTitle(R.string.home_shows);
        setTitleLeftButton(null);

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        edit_conent.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                mTvMaxLength.setText(""+s.length()+"/500");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 输入前的监听

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听

            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        period_id = getIntent().getStringExtra("id");
        if (TextUtils.isEmpty(period_id)) {
            finish();
            return;
        }

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoaderForimgpickup());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(3);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        mListBean = new ArrayList<>();
        adapter = new ImagePickerAdapter(mContext, mListBean, maxImgCount);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);


    }

    @OnClick(R.id.btn_ok)
    public void submit() {
        String content = edit_conent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast(R.string.comment_please_enter_conent);
            return;
        }

        RequestParams params = new RequestParams();
        params.add("period_id", period_id);
        params.add("content", content);
        try {
            for (int i = 0, l = mListBean.size(); i < l; i++) {
                params.put("file[" + i + "]", new File(mListBean.get(i).path));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        showProgress();
        HttpRestClient.post(Constants.URL_COMMENT_SUBMIT, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        setResult(Activity.RESULT_OK);
                        DialogUtils.DialogOKmsgFinish(mContext,((BasicBeanStr)result).getStatusInfo());
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                            hideLoading();
                    }
                },
                0, BasicBeanStr.class);

    }

    public static int REQUEST_ADDIMG = 11;

    ArrayList<ImageItem> images = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    mListBean.addAll(images);
                    adapter.setImages(mListBean);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    mListBean.clear();
                    mListBean.addAll(images);
                    adapter.setImages(mListBean);
                }
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                ImagePicker.getInstance().setSelectLimit(maxImgCount - mListBean.size());
                Intent intent1 = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                break;
            default:
                // 打开预览
                /* 如果需要进入选择的时候显示已经选中的图片，
                 * 详情请查看ImagePickerActivity
                    * */
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }
}
