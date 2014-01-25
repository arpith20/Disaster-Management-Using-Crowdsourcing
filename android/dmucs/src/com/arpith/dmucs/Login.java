package com.arpith.dmucs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
 // This method is called once the menu is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.action_settings:
        // Launch settings activity
        Intent i = new Intent(this, Preference.class);
        startActivity(i);
        break;
      case R.id.advance_action_settings:
    	  Intent i2 = new Intent(this, APreference.class);
          startActivity(i2);
          break;
      }
      return true;
    } 
    
}