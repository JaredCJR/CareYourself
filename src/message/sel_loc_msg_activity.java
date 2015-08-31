package message;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import body.Body_handler;
import body.Choose_body;

import com.example.careyourself.R;

import MySQL_message.JSONParser;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class sel_loc_msg_activity extends Activity {
	
	private ActionBar  bar = null;
	private TextView msg_title = null;
	String _where = "error";
	// Progress Dialog
    private ProgressDialog pDialog;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    private ArrayList<Message_model> message_list_data = new ArrayList<Message_model>();
    // url to get all products list
    //private static String url_all_messages = "http://59.127.45.75/careyourself/get_all_messages.php";
    private static String url_all_messages = "http://careyourself.netau.net/careyourself/get_all_messages.php";
    
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_PID = "pid";
    private static final String TAG_TITLE = "title";
    private static final String TAG_TIME = "send_time";
    private static final String TAG_messages = "my_messages";//json array name
    
     // products JSONArray
    JSONArray JSON_messages_array = null;
    
	private String value = "";
	private ListView message_listView;
	//private View mView;
    private Message_Adapter m_adapter;
    private EditText title_text,content_text;
    private TextView message_title;

	@Override
	  public void onCreate(Bundle savedInstanceState) {
	Log.v("=====>", "set sel_loc_msg_activity onCreate INIT");
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.sel_loc_msg_activity);  
	    bar = getActionBar();  //获取ActionBar的对象，从这个方法也可知action bar是activity的一个属性
      bar.setDisplayHomeAsUpEnabled(true); 
      Log.v("=====>", "set sel_loc_msg_activity action bar");
      

      processViews();
      
      Bundle bundle = this.getIntent().getExtras();
      _where = bundle.getString("LOC_where");
      
      msg_title = (TextView)findViewById(R.id.leave_message_title);
      
      Log.v("LOC_where=>", _where);
      
      if(_where.equals("north"))
      {
      	//Toast.makeText(this,"north",Toast.LENGTH_SHORT).show();
    	  Log.v("=====>", "交流區[北部]");
      	msg_title.setText("交流區[北部]");
      	url_all_messages = "http://careyourself.netau.net/careyourself/location/get_all/get_all_messages_north.php";
      }
      else if (_where.equals("east"))
      {
      	//Toast.makeText(this,"east",Toast.LENGTH_SHORT).show();
    	  Log.v("=====>", "交流區[東部]");
      	msg_title.setText("交流區[東部]");
      	url_all_messages = "http://careyourself.netau.net/careyourself/location/get_all/get_all_messages_east.php";
      }
      else if (_where.equals("medium"))
      {
      	//Toast.makeText(this,"medium",Toast.LENGTH_SHORT).show();
    	  Log.v("=====>", "交流區[中部]");
      	msg_title.setText("交流區[中部]");
      	url_all_messages = "http://careyourself.netau.net/careyourself/location/get_all/get_all_messages_medium.php";
      }
      else if (_where.equals("south"))
      {
      	//Toast.makeText(this,"south",Toast.LENGTH_SHORT).show();
    	  Log.v("=====>", "交流區[南部]");
      	msg_title.setText("交流區[南部]");
      	url_all_messages = "http://careyourself.netau.net/careyourself/location/get_all/get_all_messages_south.php";
      }
      else
    	  Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();  
      
      
      Log.v("=====>", "sel_loc_msg_activity before haveInternet()");
      if(haveInternet())
		{
    	  Log.v("=====>", "sel_loc_msg_activity haveInternet()");
      new LoadAllMessages().execute();
		}
		else
		{
		Toast.makeText(this,"找不到網路 =(",Toast.LENGTH_LONG).show();
		message_title.setText("Oh..找不到網路");
		}
      
	  }

	
	
	@Override 
  public boolean onOptionsItemSelected(MenuItem item) {  
      switch(item.getItemId()){ 
      case android.R.id.home: //对用户按home icon的处理，本例只需关闭activity，就可返回上一activity，即主activity。
          
           finish(); 
           return true; 
  	  case R.id.action_search:
		//Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.setClass(sel_loc_msg_activity.this, search_msg.class);
		Bundle bundle = new Bundle();
		bundle.putString("LOC_where", _where);
		intent.putExtras(bundle);
		startActivity(intent); 
		return true;
		
      default: 
           break; 
      } 
      return super.onOptionsItemSelected(item); 
  } 
	
	
	
	
	private void processViews() {
        title_text = (EditText) findViewById(R.id.title_input);
        content_text = (EditText) findViewById(R.id.content_input);
        message_listView = (ListView) findViewById(R.id.messgae_listview);
        message_title = (TextView) findViewById(R.id.leave_message_title);
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
		/**
	     * Background Async Task to Load all product by making HTTP Request
	     * */
	    public class LoadAllMessages extends AsyncTask<String, String, String> {
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(sel_loc_msg_activity.this);
	            pDialog.setMessage("Loading! Please wait...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            Log.v("=====>", "sel_loc_msg_activity onPreExecute()");
	            pDialog.show();
	        }
	        /**
	         * getting All messages from url
	         * */
	        protected String doInBackground(String... args) {
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            // getting JSON string from URL
	            JSONObject json = jParser.makeHttpRequest(url_all_messages, "GET", params, pDialog);
	            // Check your log cat for JSON reponse
	            Log.v("=====>", "All Messages: " + json.toString());
	            
	            try {
	                // Checking for SUCCESS TAG
	                int success = json.getInt(TAG_SUCCESS);
	 
	                if (success == 1) {
	                    // products found
	                    // Getting Array of Messages
	                	JSON_messages_array = json.getJSONArray(TAG_messages);
	                	message_list_data.clear();
	 
	                    // looping through All Products
	                    for (int i = (JSON_messages_array.length()-1); i >= 0; i--) {
	                        JSONObject c = JSON_messages_array.getJSONObject(i);
	 
	                        // Storing each json item in variable
	                        String title = c.getString(TAG_TITLE);
	                        String content = c.getString(TAG_CONTENT);
	                        String send_time = c.getString(TAG_TIME);
	                        
	                     // creating a new message
	                        Message_model one = new Message_model(0,send_time,title,content);
	                        // adding HashList to ArrayList
	                        message_list_data.add(one);
	                    }
	                } else {
	                    // no products found
	                	Log.v("=====>", "no products found");
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
	            // dismiss the dialog after getting all products
	            pDialog.dismiss();
	            // updating UI from Background Thread
	            runOnUiThread(new Runnable() {
	                public void run() {
	                    /**
	                     * Updating parsed JSON data into ListView
	                     * */
	                	Resources res =getResources();
	                	m_adapter = new Message_Adapter(
	                			sel_loc_msg_activity.this,
		                        message_list_data, 
		                        res);
	                	
	                    message_listView.setAdapter(m_adapter);
	                    Log.v("=====>", "DB FIN setAdapter");
	                }
	            });
	        }
	    }
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	MenuInflater inflater = getMenuInflater();
	    	inflater.inflate(R.menu.search_loc_message, menu);
	    	return super.onCreateOptionsMenu(menu);
	    }

	
}

