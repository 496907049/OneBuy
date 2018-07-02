package com.app.onebuy.bean;


import android.content.Context;
import android.text.TextUtils;

import com.app.onebuy.basis.Constants;

import java.util.List;

import my.FileUtils;
import my.LogUtil;
import my.MySharedPreferences;


public class LoginBean extends BasisBean implements Cloneable {
    private static final long serialVersionUID = 1L;

    public static final int ERRO_CODE_DEVICE = -3001;


    public static final int LOGIN_TYPE_PERSON = 0;
    public static final int LOGIN_TYPE_FA = 1;

    public static final String FILE_CACHE = Constants.DIR_FILE
            + "/login.data";
    private static LoginBean sLoginBean;
    /**
     * user_id : 621803681
     * province_id : 4
     * city_id : 60
     * area_id : 0
     * user_type : user
     * mobile : 13695049660
     * email : hwc123@139.com
     * realname : 黄伟聪11
     * nickname : 天天
     * birthday : 1985-10-30
     * weixin_account :
     * head_id : 79
     * sso_head_url : http://wx.qlogo.cn/mmopen/ZJBDyGvvibTxLmVb4RIyjlEX4IwHhUBYusnHrMbo8HxvQCxBLVeMiazSldfKtI8rjggNg30icPk8E4fFYIKE08U9Q/0.jpg
     * sex : 1
     * description :
     * app_type : system
     * cardid :
     * cardid_photo_id : 0
     * service_id : 0
     * create_time : 2016-07-11 16:33:22
     * status : 1
     * province_name :
     * city_name :
     * area_name :
     * is_set_password : true
     * head_url : http://api.ebuy.xm-news.com/uploads/file/20180419/c19729263a6336a3bcc4adb1f92328d1.jpeg
     * sex_desc : 男
     * bind_info : {"is_bind_weibo":false,"nickname_weibo":"","is_bind_weixin":false,"nickname_weixin":"","is_bind_qq":false,"nickname_qq":""}
     * token : 095659f37431cf2a11b36fb3995b78d7
     */

    private String user_id;
    private String province_id;
    private String city_id;
    private String area_id;
    private String user_type;
    private String mobile;
    private String email;
    private String realname;
    private String nickname;
    private String birthday;
    private String weixin_account;
    private String head_id;
    private String sso_head_url;
    private String sex;
    private String description;
    private String app_type;
    private String cardid;
    private String cardid_photo_id;
    private String service_id;
    private String create_time;
    private String status;
    private String province_name;
    private String city_name;
    private String area_name;
    private boolean is_set_password;
    private String head_url;
    private String sex_desc;
    private BindInfoBean bind_info;
    private String token;
    private UserAccountInfoBean account_info;

    public LoginBean() {
        super();
        // sLoginBean = this;
    }

    public static boolean isLogin() {
		return getInstance() != null && !TextUtils.isEmpty(sLoginBean.getUser_id());
//        return getInstance() != null && getInstance().getUser() != null;
//        return false;
    }

    public static LoginBean getInstance() {
        if (sLoginBean == null)
            sLoginBean = getFromCache();
        return sLoginBean;
    }

    public static LoginBean getFromCache() {
        LoginBean data;
        data = (LoginBean) FileUtils.readObject(FILE_CACHE);
        if (data != null) {
            sLoginBean = data;
        }
        LogUtil.i("login---->getFromCache---" + (data == null));
        return data;
    }

    public void save() {
        sLoginBean = this;
        FileUtils.cacheObject(FILE_CACHE, this);
        // FileSaveHandler.saveObject(FILE_CACHE, this);
        LogUtil.i("login---->save---" + FILE_CACHE);
    }

    private static void delete() {
        FileUtils.cacheObject(FILE_CACHE, null);
        // FileSaveHandler.removeObject(FILE_CACHE);
    }

	public static void logout() {
		delete();
		sLoginBean = null;
	}

    public static void logout(Context mContext) {
        delete();
        sLoginBean = null;
        MySharedPreferences mSp = new MySharedPreferences(mContext);
        mSp.putIsLogined(false);
        mSp.putPassword("");
        mSp.putUser("");
    }

    public static void logPast(Context mContext) {
        delete();
        sLoginBean = null;
        MySharedPreferences mSp = new MySharedPreferences(mContext);
        mSp.putIsLogined(false);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUserId() {
        if (isLogin()) return getInstance().getUser_id();
        return "";
    }
    public static String getUserToken() {
        if (isLogin()) return getInstance().getToken();
        return "";
    }

    public UserAccountInfoBean getAccount_info() {
        return account_info;
    }

    public void setAccount_info(UserAccountInfoBean account_info) {
        this.account_info = account_info;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getWeixin_account() {
        return weixin_account;
    }

    public void setWeixin_account(String weixin_account) {
        this.weixin_account = weixin_account;
    }

    public String getHead_id() {
        return head_id;
    }

    public void setHead_id(String head_id) {
        this.head_id = head_id;
    }

    public String getSso_head_url() {
        return sso_head_url;
    }

    public void setSso_head_url(String sso_head_url) {
        this.sso_head_url = sso_head_url;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getCardid_photo_id() {
        return cardid_photo_id;
    }

    public void setCardid_photo_id(String cardid_photo_id) {
        this.cardid_photo_id = cardid_photo_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public boolean isIs_set_password() {
        return is_set_password;
    }

    public void setIs_set_password(boolean is_set_password) {
        this.is_set_password = is_set_password;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getSex_desc() {
        return sex_desc;
    }

    public void setSex_desc(String sex_desc) {
        this.sex_desc = sex_desc;
    }

    public BindInfoBean getBind_info() {
        return bind_info;
    }

    public void setBind_info(BindInfoBean bind_info) {
        this.bind_info = bind_info;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public static class BindInfoBean extends BasisBean{
        /**
         * is_bind_weibo : false
         * nickname_weibo :
         * is_bind_weixin : false
         * nickname_weixin :
         * is_bind_qq : false
         * nickname_qq :
         */

        private boolean is_bind_weibo;
        private String nickname_weibo;
        private boolean is_bind_weixin;
        private String nickname_weixin;
        private boolean is_bind_qq;
        private String nickname_qq;

        public boolean isIs_bind_weibo() {
            return is_bind_weibo;
        }

        public void setIs_bind_weibo(boolean is_bind_weibo) {
            this.is_bind_weibo = is_bind_weibo;
        }

        public String getNickname_weibo() {
            return nickname_weibo;
        }

        public void setNickname_weibo(String nickname_weibo) {
            this.nickname_weibo = nickname_weibo;
        }

        public boolean isIs_bind_weixin() {
            return is_bind_weixin;
        }

        public void setIs_bind_weixin(boolean is_bind_weixin) {
            this.is_bind_weixin = is_bind_weixin;
        }

        public String getNickname_weixin() {
            return nickname_weixin;
        }

        public void setNickname_weixin(String nickname_weixin) {
            this.nickname_weixin = nickname_weixin;
        }

        public boolean isIs_bind_qq() {
            return is_bind_qq;
        }

        public void setIs_bind_qq(boolean is_bind_qq) {
            this.is_bind_qq = is_bind_qq;
        }

        public String getNickname_qq() {
            return nickname_qq;
        }

        public void setNickname_qq(String nickname_qq) {
            this.nickname_qq = nickname_qq;
        }
    }
}
