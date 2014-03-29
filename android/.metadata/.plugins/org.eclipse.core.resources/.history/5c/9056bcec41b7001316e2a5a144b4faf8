package me.arpith.dmucscom;

import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ScattActivity extends ScatterFriend{
	public static final String TAG = ScattActivity.class.getSimpleName();
	private EditText writeTweetView;
	private TextView numOfMembers;
	private String[] devices={};
	private boolean abort=false;
	
	String UserName= "Arpith";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
     	setContentView(R.layout.main);
        writeTweetView = (EditText)findViewById(R.id.writeTweet);
        ListView tweetList = (ListView)findViewById(R.id.tweetList);
        
        numOfMembers=(TextView)findViewById(R.id.numOfMembers);
        
        String[] proj = new String[]{
        		BaseColumns._ID,
        		Constant.AUTHORID,
        		Constant.AUTHOR,
        		Constant.MES,
        		Constant.DATETIME
        };
        
        showNumofMem();
        
    	Cursor c = managedQuery(Constant.TWEET_CONTENT_URI, proj, null, null,null);
    	SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.tweetitem, c, 
    				new String[]{Constant.AUTHOR,Constant.DATETIME ,Constant.MES}, 
    				new int[]{R.id.tweetAuthor,R.id.tweetTimeStamp,R.id.tweetMes});
     	tweetList.setAdapter(adapter);
  
		Cursor tweets = getContentResolver().query(Constant.TWEET_CONTENT_URI, proj, null, null, null);
		if(tweets.getCount() == 0)
			insertToDB("Disaster Recovery","You may now start chatting with people around you..");
	}
	
	public void writeTweet(View v){
		String myTweet = writeTweetView.getText().toString();
		if(!myTweet.equals("")  ){
			boolean insert = insertToDB(UserName,myTweet);
			if(insert)writeTweetView.setText("");
		}
		try {
			//All messages to be sent out must be separated with "|" between units
			//A tweet is composed of three units(identifier+name+content)

			myTweet = TWEET +"|"+ UserName+"|"+ myTweet;
			devices = beddernetService.getDevices(applicationIdentifier);

			beddernetService.sendMulticast(devices, null, 
					myTweet.getBytes(), applicationIdentifier);
			
		} catch (RemoteException e){
			Log.e(TAG,"unable to get peer devices, are you out of network?");
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		abort=true;
	}
	
	public void showNumofMem(){
		new Thread(new Runnable(){
			@Override
			public void run() {
				Log.d(TAG,"updating number of members");
				while(!abort){
					try {
						if(beddernetService!=null)
							devices = beddernetService.getDevices(applicationIdentifier);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					runOnUiThread(new Runnable(){
						@Override
						public void run() {
							numOfMembers.setText(devices.length+" ");
						}	
					});
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	
		}).start();
	}
	
}
