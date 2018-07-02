package com.app.onebuy.basis;

import android.os.Environment;

public class Constants {

//	 public static final boolean DEBUG = false;
	public static final boolean DEBUG = true;

	// 正式服务器
//	 public static final String URL_SERVICE = "http://api.ebuy.cc/";
	// 测试服务器
	 public static final String URL_SERVICE = "http://api.ebuy.suncco.com/";


	public static String URL_API = URL_SERVICE+"";

	//登录
	public static String URL_USER_LOGIN = URL_API+"user/login";
	//注册
	public static String URL_USER_REGIST = URL_API+"user/register";
	//发送短信
	public static String URL_USER_MSGCODE = URL_API+"user/get_smscode";
	//找回密码
	public static String URL_USER_FORGET_PWD= URL_API+"user/forget_password";
	//修改手机号码
	public static String URL_USER_MODIFY_MOBILE = URL_API+"user/change_mobile";
	//修改密码
	public static String URL_USER_MODIFY_PWD= URL_API+"user/change_password";
	//上传头像
	public static String URL_USER_UPLOAD_PHOTO  = URL_API+"user/upload_head";
	//获取用户信息
	public static String URL_USER_USERINFO = URL_API+"user/get_user_info";
	//修改用户信息
	public static String URL_USER_MODIFY_USERINFO = URL_API+"user/edit_info";

	//用户-账户信息
	public static String URL_USER_ACCOUNT_INFO = URL_API+"account/info";
	//用户-消费记录
	public static String URL_USER_SPENDHISTORY = URL_API+"account/index";
	//用户-获取手机号前缀
	public static String URL_USER_COUNTRY = URL_API+"user/get_mobile_prefix";


	//更新
	public static String URL_UPDATE= URL_API+"other/check_versions";

	//首页
	public static String URL_HOME_PAGE = URL_API+"index/index";
	//获取商品分类
	public static String URL_GOOD_TYPE = URL_API+"goods/get_category";
	//商品清单
	public static String URL_GOOD_INDEX = URL_API+"goods/index";
	//商品详情
	public static String URL_GOOD_DETAIL = URL_API+"goods/detail";
	//图文详情（后端命名：获取商品详情....)
	public static String URL_GET_CONTENT = URL_API+"goods/get_content";
	//最新揭晓
	public static String URL_GOOD_LOTTERY = URL_API+"goods/lottery";
	//计算详情   (接口：商品期详情)
	public static String URL_GOOD_PERIOD_INFO = URL_API+"goods/period_info";
	//参与记录
	public static String URL_GOOD_ORDER_LIST = URL_API+"goods/order_list";
	// 邀请有礼    （接口：推荐信息）
	public static String URL_RECOMMEND_INDEX = URL_API+"recommended/index";
	// 邀请有礼 -邀请战绩   （奖励清单）
	public static String URL_RECOMMEND_REWARD_LIST= URL_API+"recommended/reward_list";
	//往期中奖
	public static String URL_GOOD_LOTTERY_LIST= URL_API+"goods/lottery_list";
	//商品评价清单(晒单分享)
	public static String URL_GOOD_COMMENT_LIST= URL_API+"goods/comment_list";
	//首页更多商品
	public static String URL_GOOD_LIST= URL_API+"index/goods_list";
	//热门搜索词
	public static String URL_KEY_WORDS= URL_API+"goods/get_search_keyword";
	//订单-提交订单
	public static String URL_ORDER_SUBMIT= URL_API+"order/submit";
	//订单-我的中奖-参与记录
	public static String URL_ORDER_INDEX = URL_API+"order/index";
	//订单-配置收货地址
	public static String URL_ORDER_SETADDRESS = URL_API+"order/set_address";
	//订单-确认收货
	public static String URL_ORDER_RECEIVED = URL_API+"order/confirm_delivery";

	//文章-新闻列表
	public static String URL_NEWS_LIST = URL_API+"article/index";
	//文章-单页文章
	public static String URL_NEWS_ARTICLEPAGE = URL_API+"article/page";
	//文章-新闻详情
	public static String URL_NEWS_DETAIL = URL_API+"Article/articleContent";
	//地址-添加收货地址
	public static String URL_ADDRESS_ADD = URL_API+"address/add";
	//地址-修改收货地址
	public static String URL_ADDRESS_MODIFY = URL_API+"address/edit";
	//地址-我的收货地址
	public static String URL_ADDRESS_MYADDRESS = URL_API+"address/index";
	//地址-查看地址信息
	public static String URL_ADDRESS_INFO = URL_API+"address/detail";
	//地址-删除收货地址
	public static String URL_ADDRESS_DELETE = URL_API+"address/delete";
	//评价晒单-最新晒单
	public static String URL_COMMENT_INDEX = URL_API+"comment/index";
	//评价晒单-提交评价
	public static String URL_COMMENT_SUBMIT = URL_API+"comment/submit";
	//评价晒单-我的评价-我的晒单
	public static String URL_COMMENT_MY_COMMENTS = URL_API+"comment/my";
	//评价晒单-删除评价
	public static String URL_COMMENT_DELETE = URL_API+"comment/delete";



	public static String FILE_PREFIX = "file://";
	public static String DRAWABLE_PREFIX = "drawable://";
	public static int PAGE_SIZE = 15;

	public static final String TEL = "";

	public static final long TIME_DELAY = 500;

	public static final String URL_SERVICE_NETTEST = "http://www.baidu.com";

	/**
	 * 程序目录
	 */
	public static final String DIR = Environment.getExternalStorageDirectory()
			+ "/OneBuy/";

	public static final String DIR_DOWNLOAD = DIR + "download/";
	public static final String DIR_TEMP = DIR + "temp/";
	public static final String DIR_LOG = DIR + "log/";
	public static final String DIR_FILE = DIR + "file/";

}
