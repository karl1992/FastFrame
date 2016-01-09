package com.wells.fastframe.toolbox;

import com.wells.fastframe.app.Constant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager {

	private SQLiteDatabase mSQLiteDatabase = null;
	private DatabaseHelper mDatabaseHelper = null;
	private Context mContext = null;
	private static DBManager dbConn = null;
	private Cursor cursor;

	private static String createSql = "CREATE TABLE tally(id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL, reimType TEXT, projname TEXT, reimMoney TEXT, reimDesc TEXT);";

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(createSql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			onCreate(db);
		}
	}

	private DBManager(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public static DBManager getInstance(Context mContext) {
		if (null == dbConn) {
			dbConn = new DBManager(mContext);
		}
		return dbConn;
	}

	public void open() {
		mDatabaseHelper = new DatabaseHelper(mContext);
		mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
	}

	public void close() {
		if (null != mDatabaseHelper) {
			mDatabaseHelper.close();
		}
		if (null != cursor) {
			cursor.close();
		}
	}

	public long insert(String tableName, String nullColumn, ContentValues contentValues) {
		try {
			return mSQLiteDatabase.insert(tableName, nullColumn, contentValues);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 通过主键ID删除数据
	 * 
	 * @param tableName
	 * @param key
	 * @param id
	 * @return
	 */
	public long deleteById(String tableName, String key, int id) {
		try {
			return mSQLiteDatabase.delete(tableName, key + " = " + id, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 查找表的数据
	 * 
	 * @param tableName
	 * @param columns
	 *            如果返回所有类则填null
	 * @return
	 * @throws Exception
	 */
	public Cursor findAll(String tableName, String[] columns) throws Exception {
		try {
			cursor = mSQLiteDatabase.query(tableName, columns, null, null, null, null, null);
			// cursor.moveToFirst();
			return cursor;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 
	 * 通过主键ID查找数据
	 * 
	 * @param tableName
	 * @param key
	 * @param id
	 * @param columns
	 * @return
	 * @throws Exception
	 */
	public Cursor findById(String tableName, String key, int id, String[] columns) throws Exception {
		try {
			return mSQLiteDatabase.query(tableName, columns, key + " = " + id, null, null, null, null);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 通过条件查找数据
	 * @param tableName
	 * @param names
	 * @param values
	 * @param columns
	 * @param orderColumn
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Cursor find(String tableName, String[] names, String[] values, String[] columns, String orderColumn, String limit) throws Exception {
		try {
			StringBuffer selection = new StringBuffer();
			for (int i = 0; i < names.length; i++) {
				selection.append(names[i]);
				selection.append(" = ?");
				if (i != names.length - 1) {
					selection.append(",");
				}
			}
			cursor = mSQLiteDatabase.query(true, tableName, columns, selection.toString(), values, null, null, orderColumn, limit);
			cursor.moveToFirst();
			return cursor;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 更新数据
	 * @param tableName
	 * @param names
	 * @param values
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public boolean udpate(String tableName, String[] names, String[] values, ContentValues args) throws Exception {
		try {
			StringBuffer selection = new StringBuffer();
			for (int i = 0; i < names.length; i++) {
				selection.append(names[i]);
				selection.append(" = ?");
				if (i != names.length - 1) {
					selection.append(",");
				}
			}
			return mSQLiteDatabase.update(tableName, args, selection.toString(), values) > 0;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 执行sql
	 * @param sql
	 */
	public void executeSql(String sql) {
		mSQLiteDatabase.execSQL(sql);
	}
	
	

}
