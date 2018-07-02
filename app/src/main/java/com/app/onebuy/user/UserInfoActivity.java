package com.app.onebuy.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.basis.GlideImageLoaderForimgpickup;
import com.app.onebuy.bean.LoginBean;
import com.app.onebuy.bean.UserPhotoUploadBean;
import com.app.onebuy.util.StringEditActivity;
import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import my.ActivityTool;
import my.DialogUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class UserInfoActivity extends BasisActivity {


    @BindView(R.id.img_photo)
    RoundedImageView img_photo;

    @BindView(R.id.text_mobile)
    TextView text_mobile;
    @BindView(R.id.text_nickname)
    TextView text_nickname;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.user_info_activity);
        setTitle(R.string.user_userinfo);
        setTitleLeftButton(null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoaderForimgpickup());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        setView();

    }

    private void getList(){
        RequestParams params = new RequestParams();
        showProgress();
        HttpRestClient.get(Constants.URL_USER_USERINFO, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        UserPhotoUploadBean bean = (UserPhotoUploadBean) result;
                        showToast(((UserPhotoUploadBean)result).getStatusInfo());
                        LoginBean loginBean = LoginBean.getInstance();
                        loginBean.setHead_url(bean.getHead_url());
                        loginBean.save();
                        setResult(Activity.RESULT_OK);
                        setView();
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        hideLoading();
                    }
                },
                0, UserPhotoUploadBean.class);
    }

    private void setView() {
        LoginBean loginBean = LoginBean.getInstance();
        Glide.with(mContext).load(loginBean.getHead_url()).asBitmap().placeholder(R.drawable.user_icon_default).into(img_photo);

        if(TextUtils.isEmpty(loginBean.getNickname())){
            text_nickname.setText(loginBean.getMobile());
        }else {
            text_nickname.setText(loginBean.getNickname());
        }
        text_mobile.setText(loginBean.getMobile());
    }



    @OnClick(R.id.view_modifypwd)
    void modifyPwd() {
        ActivityTool.skipActivity(mContext, ModifyPwdActivity.class);
    }

    @OnClick(R.id.view_modifymobile)
    void modifyMobile() {
        ActivityTool.skipActivityForResult(mContext, ModifyMobileActivity.class,REQUEST_MODIFY_MOBILE);
    }

    @OnClick(R.id.img_photo)
    void photo() {
        Intent intent = new Intent(mContext, ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_ADDIMG);
    }

    @OnClick(R.id.view_modifyname)
    void modifyname() {
        StringEditActivity.ToStringEditActivity(mContext,REQUEST_MODIFY_NAME,LoginBean.getInstance().getNickname(),getString(R.string.edit_new_nick_name),"",getString(R.string.reset_nick_name));
    }


    public static int REQUEST_ADDIMG = 11;
    public static int REQUEST_MODIFY_MOBILE = 12;
    public static int REQUEST_MODIFY_NAME = 13;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (data != null && requestCode == REQUEST_ADDIMG) {
//                for(int i = 0,l = images.size(); i< l; i++){
//                    mListBean.add(images.get(i));
//                    if(mListBean.size() >=3){
//                        break;
//                    }
//                }
              String  imgPath = images.get(0).path;
                Glide.with(mContext).load(imgPath).asBitmap().placeholder(R.drawable.user_icon_default).into(img_photo);
                postPoto(imgPath);
            }
            return;
        }else if(requestCode == REQUEST_MODIFY_NAME){
            if(Activity.RESULT_OK == resultCode){
                String name = data.getStringExtra("string");
                modifyName(name);
            }
        }else{
            setView();
        }
    }

    private void postPoto(String imgPath) {
        RequestParams params = new RequestParams();
        showProgress();
        try {
            params.put("file", new File(imgPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HttpRestClient.post(Constants.URL_USER_UPLOAD_PHOTO, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {

                        UserPhotoUploadBean bean = (UserPhotoUploadBean) result;
                            showToast(((UserPhotoUploadBean)result).getStatusInfo());
                        LoginBean loginBean = LoginBean.getInstance();
                        loginBean.setHead_url(bean.getHead_url());
                        loginBean.save();
                        setResult(Activity.RESULT_OK);
                        setView();
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        hideLoading();
                    }
                },
                0, UserPhotoUploadBean.class);
    }

    private void modifyName(final String name) {
//            final String name = edit_name.getText().toString();
        if(TextUtils.isEmpty(name)){
            showToast(getString(R.string.edit_nick_name));
            return;
        }
        if(name.equals(LoginBean.getInstance().getNickname())){
            return;
        }
        RequestParams params = new RequestParams();
        showProgress();
        params.put("nickname",name);
//        HttpRestClient.post(Constants.URL_USER_MODIFY_PHOTO, params, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                LogUtil.i("onSuccess----->"+responseString);
//            }
//        });
        HttpRestClient.post(Constants.URL_USER_MODIFY_USERINFO, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {

//                        UserPhotoUploadBean bean = (UserPhotoUploadBean) result;
                        LoginBean loginBean = LoginBean.getInstance();
                        loginBean.setNickname(name);
                        loginBean.save();
                        setResult(Activity.RESULT_OK);
                        setView();
                        DialogUtils.DialogOkMsg(mContext,((UserPhotoUploadBean)result).getStatusInfo(),null);
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        hideLoading();
                    }
                },
                0, UserPhotoUploadBean.class);
    }
}
