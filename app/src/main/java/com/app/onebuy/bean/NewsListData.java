package com.app.onebuy.bean;

public class NewsListData extends BasisBean {

	/**
	 * page_id : 1
	 * module : about_us
	 * title : 关于我们
	 * content : <p style="margin-top: 0px; margin-bottom: 0px; text-rendering: optimizeLegibility; font-feature-settings: &#39;kern&#39; 1; font-kerning: normal; color: rgb(153, 153, 153); font-family: 微软雅黑; font-size: 12px; white-space: normal; background-color: rgb(249, 249, 249);">客服热线：400-0000-000</p><p style="margin-top: 0px; margin-bottom: 0px; text-rendering: optimizeLegibility; font-feature-settings: &#39;kern&#39; 1; font-kerning: normal; color: rgb(153, 153, 153); font-family: 微软雅黑; font-size: 12px; white-space: normal; background-color: rgb(249, 249, 249);">客服邮箱：service@site.com</p><p style="margin-top: 0px; margin-bottom: 0px; text-rendering: optimizeLegibility; font-feature-settings: &#39;kern&#39; 1; font-kerning: normal; color: rgb(153, 153, 153); font-family: 微软雅黑; font-size: 12px; white-space: normal; background-color: rgb(249, 249, 249);">当前版本：v 1.0.0</p><p><br/></p>
	 * en_content : <p>asdfadf</p>
	 * edit_time : 1523622972
	 * create_time : 2018-04-12 14:30:18
	 * status : 1
	 */

	private String page_id;
	private String module;
	private String title;
	private String content;
	private String en_content;
	private int edit_time;
	private String create_time;
	private int status;

	private String article_id;

	public String getPage_id() {
		return page_id;
	}

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public void setPage_id(String page_id) {
		this.page_id = page_id;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEn_content() {
		return en_content;
	}

	public void setEn_content(String en_content) {
		this.en_content = en_content;
	}

	public int getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(int edit_time) {
		this.edit_time = edit_time;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
