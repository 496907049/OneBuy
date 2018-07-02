package com.app.onebuy.bean;

import android.content.Context;

import com.alibaba.fastjson.annotation.JSONField;
import com.app.onebuy.R;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.basis.Constants;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.RequestParams;

import java.io.File;

import my.LogUtil;
import my.SystemParamsUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;
import my.useful.DownAPKService;


public class UpdateBean extends BasisBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * APP版本号
     */
    @JSONField(name = "versions_number")
    private int number;
    /**
     * APP下载地址
     */
    @JSONField(name = "download_url")
    private String url;
    /**
     * APP版本名
     */
    @JSONField(name = "versions_name")
    private String name;
    /**
     * APP版本更新信息
     */
    @JSONField(name = "title")
    private String info;
    private String is_forced_upgrade = "";
    private int forced_upgrade_version;

    // private String time;

    public boolean isNeedUpdate(Context mContext) {
        if (this.getNumber() > SystemParamsUtils.getAPPVersonCode(mContext)) {
            return true;
        } else {
            return false;
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIs_forced_upgrade() {
        return is_forced_upgrade;
    }

    public boolean getIs_forced_upgradeBoolean() {
        return is_forced_upgrade.equals("1");
    }

    public void setIs_forced_upgrade(String is_forced_upgrade) {
        this.is_forced_upgrade = is_forced_upgrade;
    }

    public int getForced_upgrade_version() {
        return forced_upgrade_version;
    }

    public void setForced_upgrade_version(int forced_upgrade_version) {
        this.forced_upgrade_version = forced_upgrade_version;
    }

    public static void check(final Context mContext, final boolean isShowToast) {

        final KProgressHUD progressHUD = KProgressHUD.create(mContext, KProgressHUD.Style.SPIN_INDETERMINATE).setSize(100, 100).setCancellable(true);

        RequestParams params = new RequestParams();
        params.put("versions_number", SystemParamsUtils.getAPPVersonCode(mContext));
        if (isShowToast) {
            progressHUD.show();
        }
        HttpRestClient.get(Constants.URL_UPDATE, params, new MyHttpListener() {
            @Override
            public void onSuccess(int httpWhat, Object result) {
                UpdateBean updateBean = (UpdateBean) result;
                updateBean.checkUpdate(mContext, isShowToast);
            }

            @Override
            public void onFailure(int httpWhat, Object result) {
                if(isShowToast){
                    super.onFailure(httpWhat, result);
                }

            }

            @Override
            public void onFinish(int httpWhat) {
                progressHUD.dismiss();
            }
        }, 0, UpdateBean.class);

    }


    public void checkUpdate(Context mContext, boolean showToast) {

        LogUtil.i("getAPPVersonCode---->"+ SystemParamsUtils.getAPPVersonCode(mContext));
        LogUtil.i("getNumber---->"+getNumber());
        if (this.getNumber() > SystemParamsUtils.getAPPVersonCode(mContext)) {
            // showUpdateDialog(this, mUpdateBean);
            showUpdateDialog(mContext);
        } else {
            if (showToast)
                BasisApp.showToast(R.string.app_already_new);
        }
    }

    NormalDialog dialog;

    private void showUpdateDialog(final Context mContext) {
        dialog = new NormalDialog(mContext);
        OnBtnClickL l;
        final String filePath = Constants.DIR;
        final String fileName = "update.apk";
        File file = new File(filePath + fileName);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(filePath + fileName);
        if (file.exists()) {
            file.delete();
        }

        dialog.title("检测到新版本！");
        dialog.content("最新版本：V" + getName() + "\n更新内容\n"
                + getInfo());
        l = new OnBtnClickL() {

            @Override
            public void onBtnClick() {
                DownAPKService.DownloadAndShowNotifition(mContext, mContext
                        .getResources()
                        .getString(R.string.app_name) + getName() + ".apk", getUrl());
                dialog.dismiss();
            }
        };
//		builder.setPositiveButton("立即更新", l);
        if (getIs_forced_upgradeBoolean() && SystemParamsUtils.getAPPVersonCode(mContext) <= getForced_upgrade_version()) {
            dialog.btnNum(1).btnText("立即更新").setOnBtnClickL(l);
            dialog.setCancelable(false);
        } else {
            dialog.btnNum(2).btnText("立即更新", "以后再说").setOnBtnClickL(l, new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    dialog.dismiss();
                }
            });
        }
        if (getIs_forced_upgradeBoolean() && SystemParamsUtils.getAPPVersonCode(mContext) <= getForced_upgrade_version()) {
            dialog.setCancelable(false);
        }
        dialog.show();
    }

}
