package me.arpith.dmucscom;

import android.net.Uri;
import android.provider.BaseColumns;

public class Constant implements BaseColumns{
	//profile constant members
	public static final String AUTHORITY = "me.arpith.dmucscom";
	public static final Uri PROFILE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/profiles");
	public static final Uri TWEET_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/tweets");
	
	public static final String PROFILE = "PROFILE";
	public static final String USERNAME="USERNAME";
	public static final String GENDER="GENDER";
	public static final String EMAIL="EMAIL";
	public static final String IMAGE="IMAGE";
	public static final String ISPENDING="ISPENDING";
	public static final String PHONENB="PHONENM";
	public static final String MACADDR = "MACADDR";
	
	//tweet constant members
	public static final String TWEET="TWEET";
	public static final String AUTHORID="AUTHORID";
	public static final String AUTHOR="AUTHOR";
	public static final String MES="Message";
	public static final String TIMESTAMP="TIMESTAMP";
	public static final String DATETIME = "DATETIME";
}
