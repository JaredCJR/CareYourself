package message;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import MySQL_message.JSONParser;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import org.json.JSONArray;

import com.example.careyourself.MainActivity;
import com.example.careyourself.R;
import com.example.careyourself.R.id;
import com.example.careyourself.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Leave_message_activity extends Activity{

	private EditText title_text;
	private EditText content_text;
	private String titleText = "NEW";
	private String contentText = "NEW";
	private RadioButton btn_north;
	private RadioButton btn_medium;
	private RadioButton btn_south;
	private RadioButton btn_east;
	private RadioGroup loc_group;
	//private static String url_add_messages = "http://careyourself.netau.net/careyourself/create_messages.php";
	private static String url_add_messages = "http://careyourself.netau.net/careyourself/location/add/create_messages_north.php";
	
	private RadioGroup.OnCheckedChangeListener radiogpchange = new RadioGroup.OnCheckedChangeListener() {
		  @Override
		  public void onCheckedChanged(RadioGroup group, int checkedId) {
		   if (checkedId == btn_north.getId()) {
		    Toast.makeText(getApplicationContext(), "北部",Toast.LENGTH_SHORT).show();
		    url_add_messages = "http://careyourself.netau.net/careyourself/location/add/create_messages_north.php";
		   } else if (checkedId == btn_medium.getId()) {
		    Toast.makeText(getApplicationContext(), "中部",Toast.LENGTH_SHORT).show();
		    url_add_messages = "http://careyourself.netau.net/careyourself/location/add/create_messages_medium.php";
		   } else if (checkedId == btn_east.getId()) {
			Toast.makeText(getApplicationContext(), "東部",Toast.LENGTH_SHORT).show();
			url_add_messages = "http://careyourself.netau.net/careyourself/location/add/create_messages_east.php";
		  } else if (checkedId == btn_south.getId()){
			Toast.makeText(getApplicationContext(), "南部",Toast.LENGTH_SHORT).show();
			url_add_messages = "http://careyourself.netau.net/careyourself/location/add/create_messages_south.php";
		  }
		 };};
	
	private ProgressDialog pDialog;
	private static final String TAG_SUCCESS = "success";
	//private static String url_add_messages = "http://59.127.45.75/careyourself/create_messages.php";
	
	JSONParser jParser = new JSONParser();
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_message);

        processViews();
        
        loc_group.setOnCheckedChangeListener(radiogpchange);
        
	}
 
    private void processViews() {
    	title_text = (EditText) findViewById(R.id.title_input);
    	content_text = (EditText) findViewById(R.id.content_input);
    	btn_north = (RadioButton) findViewById(R.id.btn_north);
    	btn_medium = (RadioButton) findViewById(R.id.btn_medium);
    	btn_south = (RadioButton) findViewById(R.id.btn_south);
    	btn_east = (RadioButton) findViewById(R.id.btn_east);
    	loc_group = (RadioGroup) findViewById(R.id.loc_group);
    }
    
    
	

    
    
    
 // 點擊確定與取消按鈕都會呼叫這個方法
    public void onSubmit(View view) {
        // 確定按鈕
      if (view.getId() == R.id.btnConfirm) {
    	  if(haveInternet())
    	  {
      	    
      	    /*Intent result = new Intent("NULL");
      	    result.setClass(Leave_message_activity.this,MainActivity.class);
              startActivity(result);*/
      	      if("".equals(title_text.getText().toString().trim())){
      	          //標題輸入為空白或無輸入
      	      	Toast.makeText(this,"請輸入標題(診所名稱)！",Toast.LENGTH_SHORT).show();
            	}else{
               	        if("".equals(content_text.getText().toString().trim())){
               	            //內容輸入為空白或無輸入
               	    	    Toast.makeText(this,"請輸入內容！",Toast.LENGTH_SHORT).show();
                       	}else{
               		        new addNewMessage().execute();
                    	    }
           	 }
    	  }else    	  
    	  {
    		  Toast.makeText(this,"請檢查您的網路狀態！",Toast.LENGTH_SHORT).show();
    	  }

        }
        else if(view.getId() == R.id.btnCancel)
        {
            // 結束
        	//Toast.makeText(this,"真的很遺憾",Toast.LENGTH_SHORT).show();
            finish();
        }

    }
	/**
     * Background Async Task to Create new product
     * */
    public class addNewMessage extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Leave_message_activity.this);
            pDialog.setMessage("Posting messages...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        /**
         * Adding messages
         * */
        protected String doInBackground(String... args) {
            String titleText = title_text.getText().toString();
            String contentText = content_text.getText().toString();
            String send_time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
            try {
				titleText=toUtf8(titleText);
				contentText=toUtf8(contentText);
				send_time=toUtf8(send_time);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("title", titleText));
            params.add(new BasicNameValuePair("content", contentText));
            params.add(new BasicNameValuePair("send_time", send_time));
 
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jParser.makeHttpRequest(url_add_messages,
                    "POST", params, pDialog);
 
            // check log cat fro response
            Log.d("ADD Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully created product
                	Log.v("Adding message", "success adding messages");
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            finish();
        }}
    
    public static String toUtf8(String str) throws UnsupportedEncodingException {
    	return new String(str.getBytes("UTF-8"),"UTF-8");
    	}
    
	  private boolean haveInternet()
	  {
	  	boolean result = false;
	  	ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE); 
	  	NetworkInfo info=connManager.getActiveNetworkInfo();
	  	if (info == null || !info.isConnected())
	  	{
	  		result = false;
	  	}
	  	else 
	  	{
	  		if (!info.isAvailable())
	  		{
	  			result =false;
	  		}
	  		else
	  		{
	  			result = true;
	  		}
	  	}
	  	
	  	return result;
	  }
	
	}