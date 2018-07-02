package com.app.onebuy.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.NewsListData;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import butterknife.BindView;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class NewsTextDetailActivity extends BasisActivity {


	@BindView(R.id.text_content)
	TextView text_content;

	String id;
	NewsListData mDetailBean;

	boolean isSingle = false;


	public static void toDetail(Context mContext,String id){
		Intent intent = new Intent(mContext,NewsH5DetailActivity.class);
		intent.putExtra("id",id);
		mContext.startActivity(intent);
	}
	public static void toDetail(Context mContext,String id,boolean isSingle){
		Intent intent = new Intent(mContext,NewsH5DetailActivity.class);
		intent.putExtra("id",id);
		intent.putExtra("single",true);
		mContext.startActivity(intent);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		super.initViews();
		setContentView(R.layout.news_text_detail);
		setTitle("文章详情");
		setTitleLeftButton(null);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.initData(savedInstanceState);
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(mContext);
		ImageLoader.getInstance().init(configuration);

		isSingle = getIntent().getBooleanExtra("single",false);
		id = getIntent().getStringExtra("id");
		getDetail();
	}


	void getDetail(){
		RequestParams params = new RequestParams();
		String url = Constants.URL_NEWS_DETAIL;
		String paramsP = "article_id";
		if(isSingle){
			url = Constants.URL_NEWS_ARTICLEPAGE;
			paramsP = "module";
		}
		showProgress();
		params.put(paramsP, id);
		HttpRestClient.post(url, params, new MyHttpListener() {
					@Override
					public void onSuccess(int httpWhat, Object result) {
						mDetailBean = (NewsListData) result;
						setView();
					}

					@Override
					public void onFinish(int httpWhat) {
						dismissProgress();
					}
				},
				0, NewsListData.class);
	}

	private void setView() {
		if(mDetailBean == null)return;

		setTitle(mDetailBean.getTitle());

		CharSequence charSequence = null;
		Html.ImageGetter imageGetter = new UrlImageGetter(text_content,mContext);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
			charSequence = Html.fromHtml(mDetailBean.getContent(),Html.FROM_HTML_MODE_LEGACY,imageGetter,null);
		} else {
			charSequence = Html.fromHtml(mDetailBean.getContent(),imageGetter,null);
		}
		text_content.setText(charSequence);
	}

	/**
	 * Created by Administrator on 2016/10/29 0029.
	 */
	public class UrlImageGetter implements Html.ImageGetter {
		Context c;
		TextView container;

		public UrlImageGetter(TextView t,Context c){
			this.c = c;
			this.container = t;
		}

		public Drawable getDrawable(String source){
			final UrlDrawable urlDrawable = new UrlDrawable();
			ImageLoader.getInstance().loadImage(source,new SimpleImageLoadingListener(){
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage){

					ViewGroup.LayoutParams params = container.getLayoutParams();
					params.width = ViewGroup.LayoutParams.MATCH_PARENT;
					container.setLayoutParams(params);

					float scaleWidth = ((float) container.getMeasuredWidth())/loadedImage.getWidth();

					Matrix matrix = new Matrix();
					matrix.postScale(scaleWidth,scaleWidth);

					loadedImage = Bitmap.createBitmap(loadedImage,0,0,loadedImage.getWidth(),loadedImage.getHeight(),matrix,true);
					urlDrawable.bitmap = loadedImage;
					urlDrawable.setBounds(0,0,loadedImage.getWidth(),loadedImage.getHeight());
					container.invalidate();
					container.setText(container.getText());
				}
			});
			return urlDrawable;
		}

		public class UrlDrawable extends BitmapDrawable {
			protected Bitmap bitmap;

			@Override
			public void draw(Canvas canvas) {
				// override the draw to facilitate refresh function later
				if (bitmap != null) {
					canvas.drawBitmap(bitmap, 0, 0, getPaint());
				}
			}
		}
	}


}
