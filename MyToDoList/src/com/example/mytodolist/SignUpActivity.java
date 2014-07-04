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

public class SignUpActivity extends ActionBarActivity {
 
  EditText signUpName, signUpPassword, signUpFullName;
  TextView signUpError;
  
  // JSON parser class
  NetworkUtils networkUtils = new NetworkUtils();

  // Progress Dialog
  private ProgressDialog pDialog;    
  private UserDao userDao;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    signUpName = (EditText) findViewById(R.id.signUpName);
    signUpPassword = (EditText) findViewById(R.id.signUpPassword);
    signUpFullName = (EditText) findViewById(R.id.signUpFullName);
    signUpError = (TextView) findViewById(R.id.signUpError);
    
    userDao = new UserDao(this);
    userDao.open();
    
  //Đăng ký
    Button btnSignUp = (Button) findViewById(R.id.btnRegister);
    btnSignUp.setOnClickListener(new View.OnClickListener() {
      
      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        new SignUp().execute();
      }
    });
    
    //Chuyển sang trang đăng ký
    Button btnLinkToRegisterScreen = (Button) findViewById(R.id.btnLinkToLoginScreen);
    btnLinkToRegisterScreen.setOnClickListener(new View.OnClickListener() {
      
      @Override
      public void onClick(View v) {
        pDialog = ProgressDialog.show(SignUpActivity.this, "Loading...", "Please wait...", false, true);
        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(i);
        finish();
      }
      
    });
    
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.sign_up, menu);
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
  private class SignUp extends AsyncTask<String, String, String> {
    
    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      pDialog = ProgressDialog.show(SignUpActivity.this, "Loading...", "Please wait...", false, true);
    }

    /**
     * Getting user details in background thread
     * */
    @Override
    protected String doInBackground(String... params) {

      // Check for success tag
      boolean error;

      String username = signUpName.getText().toString();
      String password = signUpPassword.getText().toString();
      String fullName = signUpFullName.getText().toString();
      
      /*String deviceId = Secure.getString(getActivity().getContentResolver(),
                    Secure.ANDROID_ID);
      Log.d("android_id:", deviceId);*/
      
      try {
        // Building Parameters
        String[] keys = new String[] { "username", "password", "name"};

        String[] values = new String[] { username, password, fullName };

        // getting product details by making HTTP request
        // Note that product details url will use GET request
        JSONObject json = NetworkUtils.postJSONObjFromUrl(
            Constant.URL_USER_SIGNUP, keys, values);

        // check your log for json response
        Log.d("Json sign-up response : ", json.toString());

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
          Intent i = new Intent(SignUpActivity.this, TaskListActivity.class);
          startActivity(i);
          finish();
          
        } else {
          signUpError.setText(json.getString(Constant.TAG_MESSAGE));
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
