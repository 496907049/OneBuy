package my;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.app.onebuy.R;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;


/**
 * Created by zhengyy on 2017/7/27.
 */

public class DialogUtils {

    public static NormalDialog DialogTwo(Context context, String title, String msg,
                                           String positiveButtonText, String negativeButtonText,
                                           final OnBtnClickL listenerOk, final OnBtnClickL listenerCancel) {

        final NormalDialog dialog = new NormalDialog(context);

        if (!TextUtils.isEmpty(title)) {
            dialog.isTitleShow(true);
            dialog.title(title);
        }else{
            dialog.isTitleShow(false);
        }
        dialog.content(msg);
        dialog.btnNum(2).btnText(positiveButtonText, negativeButtonText);
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                listenerOk.onBtnClick();
                dialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                if (listenerCancel != null) {
                    listenerCancel.onBtnClick();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public static NormalDialog DialogTwo(Context context, int titleResid, int msgResid,
                                           int positiveButtonTextResid, int negativeButtonTextResid,
                                           final OnBtnClickL listenerOk, final OnBtnClickL listenerCancel) {
        String title = context.getResources().getString(titleResid);
        if(titleResid == 0 || titleResid == -1){
            title = "";
        }else{
            title = context.getResources().getString(titleResid);
        }
        String msg = context.getResources().getString(msgResid);
        String positiveButtonTex = context.getResources().getString(positiveButtonTextResid);
        String negativeButtonText = context.getResources().getString(negativeButtonTextResid);
        final NormalDialog dialog = DialogTwo(context,title,msg,positiveButtonTex,negativeButtonText,listenerOk,listenerCancel);
        return dialog;
    }

    public static NormalDialog DialogOne(Context context, String title, String msg, String btntext, final OnBtnClickL listener) {
        final NormalDialog dialog = new NormalDialog(context);
        if (!TextUtils.isEmpty(title)) {
            dialog.isTitleShow(true);
            dialog.title(title);
        }else{
            dialog.isTitleShow(false);
        }
        dialog.content(msg);
        dialog.btnNum(1).btnText(btntext);
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                if(listener != null){
                    listener.onBtnClick();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }


    public static NormalDialog DialogOkMsg(Context context, String msg, final OnBtnClickL listener) {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.isTitleShow(false);
        dialog.content(msg);
        dialog.btnNum(1).btnText(context.getString(R.string.app_ok));
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                if(listener != null){
                    listener.onBtnClick();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public static NormalDialog DialogOKmsgFinish(final Activity context,
                                                   String msg) {

        final NormalDialog dialog = new NormalDialog(context);
        dialog.isTitleShow(false);
        dialog.content(msg);
        dialog.btnNum(1).btnText(context.getString(R.string.app_ok));
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
                context.finish();
            }
        });
        dialog.show();
        return dialog;
    }

}
