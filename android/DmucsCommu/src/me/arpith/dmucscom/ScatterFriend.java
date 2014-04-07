package me.arpith.dmucscom;

import itu.beddernet.approuter.IBeddernetService;
import itu.beddernet.approuter.IBeddernetServiceCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

public class ScatterFriend extends Activity implements ServiceConnection {
	private static final String TAG = ScatterFriend.class.getSimpleName();

	public static String applicationIdentifier = "dk.itu.spvc.bluefriends";
	public static long applicationIdentifierHash;

	private ServiceConnection sc = this;
	protected static IBeddernetService beddernetService;
	//protected static Profile myProfile = new Profile();
	
	//message identifiers
	protected static final String TWEET = "TWEET";
	protected static final String MESSAGE = "MESSAGE";
	
	DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MMM");
	Calendar cal = Calendar.getInstance();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//add mySelf to the list for testing purpose,should be removed later
		bind(); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}


	private void bind() {
		Intent bindIntent = new Intent(IBeddernetService.class.getName());
		boolean b = this.bindService(bindIntent, this, Context.BIND_AUTO_CREATE);
		if(b)Log.d(TAG, "successfully bound to beddernet");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (beddernetService != null) {
			try {
				beddernetService.unregisterCallback(callback,applicationIdentifier);
			} catch (RemoteException e) {
				Log.e(TAG, "Console could't unregister callback", e);
			}
			unbindService(sc);
		}
	}

	@Override
	protected void onResume() {
		if (beddernetService == null) {
			Log.d(TAG, "the service connection is null - rebooting");
			// onCreate(null);
			bind();
		}
		super.onResume();
	}


	@Override
	public void onServiceDisconnected(ComponentName name) {
	}

	@Override
	public void onServiceConnected(ComponentName className, IBinder service) {
		try {
			Log.d(TAG, "Service connected:" + service.getInterfaceDescriptor());
		} catch (RemoteException e) {
			e.printStackTrace();
			Log.d(TAG, "Service connected but something fucked up");
		}
		beddernetService = IBeddernetService.Stub.asInterface(service);
		if (beddernetService == null)
			Log.e(TAG, "MyService is nul!!?!");
		// Synchronously
		try {
			applicationIdentifierHash = beddernetService.registerCallback(callback, applicationIdentifier);
			Log.d(TAG, "AIH received from server on register: "+ applicationIdentifierHash);
		} catch (RemoteException e) {

			Log.e(TAG,"Remote exception from service while registering callback: "+ e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean insertToDB(String Author,String mes){
		Long now = Long.valueOf(System.currentTimeMillis());
		ContentValues cv = new ContentValues();
		cv.put(Constant.AUTHOR, Author);
		cv.put(Constant.MES, mes);
		cv.put(Constant.DATETIME, " at"+dateFormat.format(cal.getTime()));
		cv.put(Constant.TIMESTAMP, now);
		Uri newUri = getContentResolver().insert(Constant.TWEET_CONTENT_URI, cv);
		if(newUri != null)return true;
		else Log.i(TAG, "Can not insert tweet to database");
		return false;
	}
	
	public IBeddernetServiceCallback callback = new IBeddernetServiceCallback.Stub(){
		@Override
		public long getApplicationIdentifierHash() throws RemoteException {
			return applicationIdentifierHash;
		}

		@Override
		public void update(String senderAddress, byte[] message)throws RemoteException {
			String[] mes = new String(message).split("\\|");
			String type = mes[0];
			if(type.equals(TWEET)){
				insertToDB(mes[1], mes[2]);
			}
			else if(type.equals(MESSAGE)){
				//TODO: Implement various other types of messages
			}
		}

		@Override
		public void updateWithSendersApplicationIdentifierHash(
				String senderAddress, long senderApplicationIdentifierHash,
				byte[] message) throws RemoteException {
			update(senderAddress, message);
		}
	};

}