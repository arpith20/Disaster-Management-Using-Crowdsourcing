package me.arpith.dmucscom.provider;

import java.util.HashMap;

import me.arpith.dmucscom.Constant;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class ScatterFriendProvider extends ContentProvider {

	private static final String TAG = ScatterFriendProvider.class
			.getSimpleName();

	private static final String DB_NAME = "dmucs_tweet.db";
	private static final int DB_VERSION = 5;

	private static final String TWEET_TABLE = "TWEET_TABLE";

	private static HashMap<String, String> tweetsProjectionMap;

	private static final UriMatcher sUriMatcher;
	private static final int TWEETS = 1;
	private static final int TWEET_ID = 2;

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(Constant.AUTHORITY, "tweets", TWEETS);
		sUriMatcher.addURI(Constant.AUTHORITY, "tweets/#", TWEET_ID);

		tweetsProjectionMap = new HashMap<String, String>();
		tweetsProjectionMap.put(BaseColumns._ID, BaseColumns._ID);
		tweetsProjectionMap.put(Constant.AUTHORID, Constant.AUTHORID);
		tweetsProjectionMap.put(Constant.AUTHOR, Constant.AUTHOR);
		tweetsProjectionMap.put(Constant.MES, Constant.MES);
		tweetsProjectionMap.put(Constant.DATETIME, Constant.DATETIME);
		tweetsProjectionMap.put(Constant.TIMESTAMP, Constant.TIMESTAMP);

	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TWEET_TABLE + " (" + BaseColumns._ID
					+ " INTEGER PRIMARY KEY, " + Constant.AUTHORID
					+ " INTEGER, " + Constant.AUTHOR + " TEXT, " + Constant.MES
					+ " TEXT, " + Constant.DATETIME + " TEXT, "
					+ Constant.TIMESTAMP + " INTEGER" + ");");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + TWEET_TABLE);
			onCreate(db);
		}
	}

	DatabaseHelper helper;

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		helper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub

		String groupBy = null;
		String having = null;
		String orderBy = null;

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		Cursor c;
		SQLiteDatabase db;
		switch (sUriMatcher.match(uri)) {
		case TWEETS:
			qb.setTables(TWEET_TABLE);
			qb.setProjectionMap(tweetsProjectionMap);
			db = helper.getReadableDatabase();
			orderBy = "TIMESTAMP DESC";
			c = qb.query(db, projection, selection, selectionArgs, groupBy,
					having, orderBy);
			c.setNotificationUri(getContext().getContentResolver(), uri);
			break;
		case TWEET_ID:
			qb.setTables(TWEET_TABLE);
			qb.setProjectionMap(tweetsProjectionMap);
			qb.appendWhere(BaseColumns._ID + "=" + uri.getPathSegments().get(1));
			db = helper.getReadableDatabase();
			c = qb.query(db, projection, selection, selectionArgs, groupBy,
					having, orderBy);
			c.setNotificationUri(getContext().getContentResolver(), uri);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI 0 " + uri);
		}

		return c;
	}

	@Override
	public String getType(Uri uri) {
		Log.d(TAG, "in getType...." + uri);
		switch (sUriMatcher.match(uri)) {
		case TWEETS:
		case TWEET_ID:
			return null;

		default:
			throw new IllegalArgumentException("Unknown URI 11" + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = helper.getWritableDatabase();
		if (sUriMatcher.match(uri) == TWEETS) {
			long rowId = db.insert(TWEET_TABLE, Constant.TWEET, values);
			if (rowId > 0) {
				Uri noteUri = ContentUris.withAppendedId(
						Constant.TWEET_CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(noteUri, null);
				return noteUri;
			}
		}

		else {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = helper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
		case TWEETS:
			count = db.update(TWEET_TABLE, values, selection, selectionArgs);
			break;
		case TWEET_ID:
			String tweetId = uri.getPathSegments().get(1);
			count = db.update(TWEET_TABLE, values, BaseColumns._ID
					+ "="
					+ tweetId
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : ""), selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		return 0;
	}

}
