package message;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import message.Leave_message_activity.addNewMessage;
import message.sel_loc_msg_activity.LoadAllMessages;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.careyourself.R;

import MySQL_message.JSONParser;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class search_result_msg extends Activity {

	private ActionBar  bar = null; 
	private String _where = "error";
	private String _keyword = "error";
	private String url_search_messages = "error";
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	private ArrayList<Message_model> message_list_data = new ArrayList<Message_model>();
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
	
	
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search_result_msg);  
	    bar = getActionBar();  //获取ActionBar的对象，从这个方法也可知action bar是activity的一个属性
      bar.setDisplayHomeAsUpEnabled(true); 
      
      
      processView();

      if(haveInternet())
		{
    	  Log.v("=====>", "sel_loc_msg_activity haveInternet()");
      new Search_Message().execute();
      
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
        default: 
             break; 
        } 
        return super.onOptionsItemSelected(item); 
    } 
  	
  	private void processView()
  	{

        title_text = (EditText) findViewById(R.id.title_input);
        content_text = (EditText) findViewById(R.id.content_input);
        message_listView = (ListView) findViewById(R.id.messgae_result_listview);
        message_title = (TextView) findViewById(R.id.message_result_title);
  		
  		Bundle bundle = this.getIntent().getExtras();
  		_where = bundle.getString("LOC_where");
  		_keyword = bundle.getString("Keyword");
  		Log.v("=====>", "IN search_result_msg");
  		Log.v("=====>", "WHERE:"+_where);
  		Log.v("=====>", "KEYWORD:"+_keyword);
  		
  		if(_where.equals("north"))
  		{
  			message_title.setText("搜尋\""+_keyword+"\"於[北部]");
  			url_search_messages = "http://careyourself.netau.net/careyourself/location/search/search_messages_north.php";
  		}
  		else if(_where.equals("south"))
  		{
  			message_title.setText("搜尋\""+_keyword+"\"於[南部]");
  			url_search_messages = "http://careyourself.netau.net/careyourself/location/search/search_messages_south.php";
  		}
  		else if(_where.equals("east"))
  		{
  			message_title.setText("搜尋\""+_keyword+"\"於[東部]");
  			url_search_messages = "http://careyourself.netau.net/careyourself/location/search/search_messages_east.php";
  		}
  		else if(_where.equals("medium"))
  		{
  			message_title.setText("搜尋\""+_keyword+"\"於[中部]");
  			url_search_messages = "http://careyourself.netau.net/careyourself/location/search/search_messages_medium.php";
  		}


        
  	  }
  		
  	//}
  	
	/**
     * Background Async Task to Search
     * */
    public class Search_Message extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(search_result_msg.this);
            pDialog.setMessage("Posting messages...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        /**
         * Adding messages
         * */
        protected String doInBackground(String... args) {
            try {
				_keyword=toUtf8(_keyword);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("keyword", _keyword));
 
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jParser.makeHttpRequest(url_search_messages,
                    "POST", params, pDialog);
 
            // check log cat fro response
            Log.d("=====>", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {

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
                    // successfully get searched product
                	Log.v("=====>", "success searching messages");
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
            // dismiss the dialog once done
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                	Resources res =getResources();
                	m_adapter = new Message_Adapter(
                			search_result_msg.this,
	                        message_list_data, 
	                        res);
                	
                    message_listView.setAdapter(m_adapter);
                    Log.v("=====>", "DB FIN setAdapter");
                }
            });
        }}
    
    public static String toUtf8(String str) throws UnsupportedEncodingException {
    	return new String(str.getBytes("UTF-8"),"UTF-8");
    	}

    
    
  	
	
}
