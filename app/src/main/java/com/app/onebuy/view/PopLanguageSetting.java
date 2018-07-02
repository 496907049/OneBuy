package com.app.onebuy.view;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.ChangeLanguageHelper;
import com.app.onebuy.home.HomeTabActivity;
import com.flyco.dialog.widget.popup.base.BasePopup;
import my.MySharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopLanguageSetting extends BasePopup<PopLanguageSetting> {

    @BindView(R.id.text_auto)
    TextView text_auto;
    @BindView(R.id.text_english)
    TextView text_english;
    @BindView(R.id.text_chinese)
    TextView text_chinese;

    Activity mActivity;

    public PopLanguageSetting(Activity context) {
        super(context);
//            setCanceledOnTouchOutside(false);
        mActivity = context;
    }

    @Override
    public View onCreatePopupView() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.pop_language_setting, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        text_auto.setOnClickListener(onClickListener);
        text_english.setOnClickListener(onClickListener);
        text_chinese.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int type = Integer.valueOf((String) view.getTag());
            if (type == new MySharedPreferences(mActivity).getAppLanguage()) {
                dismiss();
                return;
            }
            ChangeLanguageHelper.changeLanguage(type);
            resetApp();
        }
    };


    public void resetApp() {
        Intent intent = new Intent(mActivity, HomeTabActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//				| Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
        mActivity.finish();
    }
}