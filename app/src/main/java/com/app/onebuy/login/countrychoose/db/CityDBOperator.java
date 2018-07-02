package com.app.onebuy.login.countrychoose.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.onebuy.login.countrychoose.CountryListDate;

import java.util.ArrayList;


public class CityDBOperator {
	private final static String TAG = "CityDBOperator";
	private static CityDBOperator instance = null;
	private CityDBHelper dbHelper;

	/**
	 * 下载服务数据库访问类，该类提供数据库操作的基本方法.记得创建使用完后需要调用closeDb()
	 * 
	 * @param context
	 */
	public static CityDBOperator getInstance(Context context) {
		if (instance == null) {
			instance = new CityDBOperator(context);
		}
		return instance;
	}

	public CityDBOperator(Context context) {
		dbHelper = new CityDBHelper(context);
	}

	/**
	 * 关闭数据库
	 */
	public void closeDb() {
		dbHelper.close();
	}

	/**
	 * 查询所有记录
	 * 
	 * @return
	 */
	public ArrayList<CountryListDate> getAllCityListData() {
		ArrayList<CountryListDate> list = new ArrayList<CountryListDate>();
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		String sql = "select ids, en_name, cn_name, pinyin_first, pinyin, domain, tel_code,time_diff from citylist_table order by pinyin_first";
		Cursor cursor = null;
		try {
			cursor = database.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				CountryListDate info = new CountryListDate();
				info.setId(cursor.getString(0));
				info.setEn_name(cursor.getString(1));
				info.setCn_name(cursor.getString(2));
				info.setPinyin_first(cursor.getString(3));
				info.setPinyin(cursor.getString(4));
				info.setDomain(cursor.getString(5));
				info.setTel_code(cursor.getString(6));
				info.setTime_diff(cursor.getString(7));

				list.add(info);
			}
		} catch (Exception e) {
			Log.e(TAG, "getAllCityListData Error:" + e);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return list;
	}
	
	/**
	 * 根据关键字查询记录
	 * 
	 * @return
	 */
	public ArrayList<CountryListDate> getCityListDataByKeywor(String key) {
		ArrayList<CountryListDate> list = new ArrayList<CountryListDate>();
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		String sql = "select ids, en_name, cn_name, pinyin_first, pinyin, domain, tel_code,time_diff  from citylist_table where pinyin like ? or pinyinfirst like ? or c_city_name like ? order by  c_ishot desc,c_abc";
		Cursor cursor = null;
		try {
			cursor = database.rawQuery(sql, new String[]{"%%" +key + "%%","%%" +key + "%%","%%" +key + "%%"});
			while (cursor.moveToNext()) {
				CountryListDate info = new CountryListDate();
				info.setId(cursor.getString(0));
				info.setEn_name(cursor.getString(1));
				info.setCn_name(cursor.getString(2));
				info.setPinyin_first(cursor.getString(3));
				info.setPinyin(cursor.getString(4));
				info.setDomain(cursor.getString(5));
				info.setTel_code(cursor.getString(6));
				info.setTime_diff(cursor.getString(7));

				list.add(info);
			}
		} catch (Exception e) {
			Log.e(TAG, "getAllCityListData Error:" + e);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return list;
	}

	/**
	 * 1.增加多条记录
	 * 
	 * @param beans
	 */
	public void addCityListBean(ArrayList<CountryListDate> beans) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		for (CountryListDate bean : beans) {
			String sql = "insert into citylist_table(ids, en_name, cn_name, pinyin_first, pinyin, domain, tel_code,time_diff ) values "
					+ "(?,?,?,?,?,?,?,?)";
			Object[] bindArgs = { bean.getId(), bean.getEn_name(), bean.getCn_name(),bean.getPinyin_first(),bean.getPinyin(),
					bean.getDomain(), bean.getTel_code(), bean.getTime_diff() };
			database.execSQL(sql, bindArgs);
		}
	}

	/**
	 * 1.增加一条记录
	 * 
	 */
	public void addCityListData(CountryListDate bean) {
//		SQLiteDatabase database = dbHelper.getWritableDatabase();
//		String sql = "insert into citylist_table(ids, c_city_name, c_abc, pinyin, pinyinfirst, c_ishot, c_longitude, c_latitude) values "
//				+ "(?,?,?,?,?,?,?,?)";
//		Object[] bindArgs = { bean.ids, bean.c_city_name, bean.c_abc,bean.pinyin,bean.pinyinFirst,
//				bean.c_ishot ? 1 : 0, bean.c_latitude, bean.c_longitude };
//		database.execSQL(sql, bindArgs);
	}

	/**
	 * 2.删除一条记录
	 * 
	 */
	public boolean deleteByCityIds(String id) {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		return database.delete("citylist_table", "ids=?", new String[] { id }) > 0;
	}

	// /**
	// * 3.查询一条记录是否存在
	// *
	// * @param urlstr
	// * @return
	// */
	// public boolean isHasDownloadTaskByUrl(String urlstr) {
	// int count = 0;
	// Cursor cursor = null;
	// try {
	// SQLiteDatabase database = dbHelper.getReadableDatabase();
	// String sql = "select count(*) from download_info where url=?";
	// cursor = database.rawQuery(sql, new String[] { urlstr });
	// cursor.moveToFirst();
	// count = cursor.getInt(0);
	// } catch (Exception e) {
	// Log.e(TAG, "isHasTask Error:" + e);
	// } finally {
	// if (cursor != null) {
	// cursor.close();
	// }
	// }
	// return count != 0;
	// }

	/**
	 * 4.根据ids查询一条记录
	 * 
	 * @return
	 */
	public CountryListDate getCityListDataById(String ids) {
		// ArrayList<CountryListDate> list = new ArrayList<CountryListDate>();
//		SQLiteDatabase database = dbHelper.getReadableDatabase();
//		String sql = "select ids, c_city_name, c_abc, pinyin, c_ishot, c_longitude, c_latitude from citylist_table where ids=?";
//		Cursor cursor = null;
//		try {
//			cursor = database.rawQuery(sql, new String[] { ids });
//			while (cursor.moveToNext()) {
//				CountryListDate info = new CountryListDate();
//				info.ids = cursor.getString(0);
//				info.c_city_name = cursor.getString(1);
//				info.c_abc = cursor.getString(2);
//				info.pinyin = cursor.getString(3);
//				info.c_ishot = (cursor.getInt(4) == 1);
//				info.c_longitude = cursor.getFloat(5);
//				info.c_latitude = cursor.getFloat(6);
//				return info;
//				// list.add(info);
//			}
//		} catch (Exception e) {
//			Log.e(TAG, "getCityListDataById Error:" + e);
//		} finally {
//			if (cursor != null) {
//				cursor.close();
//			}
//		}
		return null;
	}
}
