package com.example.mytodolist;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mytodolist.dao.UserDao;
import com.example.mytodolist.model.User;
import com.example.mytodolist.util.Constant;
import com.example.mytodolist.util.NetworkUtils;

public class MainActivity extends ActionBarActivity {

  EditText loginName, loginPassword;
  TextView loginError;
  
  // JSON parser class
  NetworkUtils networkUtils = new NetworkUtils();

  // Progress Dialog
  private ProgressDialog pDialog;
  
  private UserDao userDao;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		loginName = (EditText) findViewById(R.id.loginName);
    loginPassword = (EditText) findViewById(R.id.loginPassword);
    loginError = (TextView) findViewById(R.id.loginError);
    
    userDao = new UserDao(this);
    userDao.open();
     
    //Đăng nhập
    Button btnLogin = (Button) findViewById(R.id.btnLogin);
    btnLogin.setOnClickListener(new View.OnClickListener() {
      
      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        new Login().execute();
      }
    });
    
    //Chuyển sang trang đăng ký
    Button btnLinkToRegisterScreen = (Button) findViewById(R.id.btnLinkToRegisterScreen);
    btnLinkToRegisterScreen.setOnClickListener(new View.OnClickListener() {
      
      @Override
      public void onClick(View v) {
        pDialog = ProgressDialog.show(MainActivity.this, "Loading...", "Please wait...", false, true);
        Intent i = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(i);     
        finish();
      }
      
    });
    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
						
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
   * Background Async Task to Get complete user details
   * */
  private class Login extends AsyncTask<String, String, String> {
    
    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      pDialog = ProgressDialog.show(MainActivity.this, "Loading...", "Please wait...", false, true);
    }

    /**
     * Getting user details in background thread
     * */
    @Override
    protected String doInBackground(String... params) {

      // Check for success tag
      boolean error;
      System.out.println(loginPassword.getText().toString());

      String username = loginName.getText().toString();
      String password = loginPassword.getText().toString();
      /*String deviceId = Secure.getString(getActivity().getContentResolver(),
                    Secure.ANDROID_ID);
      Log.d("android_id:", deviceId);*/
      
      try {
        // Building Parameters
        String[] keys = new String[] { "username", "password"};

        String[] values = new String[] { username, password };

        // getting product details by making HTTP request
        // Note that product details url will use GET request
        JSONObject json = NetworkUtils.postJSONObjFromUrl(
            Constant.URL_USER_lOGIN, keys, values);

        // check your log for json response
        Log.d("Single Product Details", json.toString());

        // json success tag
        error = json.getBoolean(Constant.TAG_ERROR);
        
        if (!error) {
         
          // get first User object from JSON Array
          JSONObject userObj = json.getJSONArray("user").getJSONObject(0); // JSON Array

          // Parse json to object
          User user = new User(userObj.getInt("uid"), userObj.getString("username"), userObj.getString("password"), userObj.getString("fullname"));
          if(userDao.getUser(user.getUserId()) == null) {
            userDao.createUser(user);
            userDao.closeDatabase();
          }
          
          Log.d("USER", user.toString());
          Intent i = new Intent(MainActivity.this, TaskListActivity.class);
          startActivity(i);
          finish();
        } else {
          loginError.setText("Incorrect username/password");
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }

      return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    @Override
    protected void onPostExecute(String file_url) {
      // dismiss the dialog once got all details
      pDialog.dismiss();
    }
  }
}
