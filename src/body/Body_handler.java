package body;

import com.example.careyourself.R;
import com.example.careyourself.R.drawable;
import com.example.careyourself.R.id;
import com.example.careyourself.R.layout;
import com.example.careyourself.R.menu;

import message.Leave_message_activity;
import android.app.ActionBar;
import android.util.Log;
import android.view.*;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;



public class Body_handler extends Activity{
	
	private ActionBar  bar = null; 
	private ImageView body_handler = null;
	private String video_url = "https://www.youtube.com/watch?v=5eolDfmUtWI&feature=youtu.be";
	
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.body_handler);  
	    bar = getActionBar();  //获取ActionBar的对象，从这个方法也可知action bar是activity的一个属性
        bar.setDisplayHomeAsUpEnabled(true); 
        
        body_handler = (ImageView)findViewById(R.id.body_handler);
        
        Bundle bundle = this.getIntent().getExtras();
        String body_where = bundle.getString("KEY_symptom");
        
        Log.v("KEY_symptom=>", body_where);
        
        if(body_where.equals("扭傷"))
        {
        	video_url = "http://youtu.be/CRJdLPTjzwI?t=12s";
        	body_handler.setImageResource(R.drawable.ankle_sprain);
        }
        else if(body_where.equals("噎到"))
        {
        	video_url = "http://youtu.be/SwJlZnu05Cw?t=1m1s";
        	body_handler.setImageResource(R.drawable.choke);
        }
        else if(body_where.equals("流鼻血"))
        {
        	video_url = "http://youtu.be/V34aB5uheBg?t=17s";
        	body_handler.setImageResource(R.drawable.nosebleeds);
        }
        else if(body_where.equals("撞掉牙齒"))
        {
        	video_url = "http://youtu.be/9QWFF32dZ0Q?t=15s";
        	body_handler.setImageResource(R.drawable.knocked_teeth);
        }
        else if(body_where.equals("腹瀉"))
        {
        	video_url = "http://youtu.be/3iYG1NCw3Zc?t=18s";
        	body_handler.setImageResource(R.drawable.diarrhea);
        }
        else if(body_where.equals("動物咬傷"))
        {
        	video_url = "http://youtu.be/A6gmaRfo_PY?t=22s";
        	body_handler.setImageResource(R.drawable.animal_bite);
        }
        else if(body_where.equals("緩和月經"))
        {
        	video_url = "http://youtu.be/CKUvpyrHs7A";
        	body_handler.setImageResource(R.drawable.menstruation);
        }
        else if(body_where.equals("抽筋"))
        {
        	video_url = "http://youtu.be/pve_4lbN_50";
        	body_handler.setImageResource(R.drawable.cramp);
        }
        else if(body_where.equals("心臟病"))
        {
        	video_url = "http://youtu.be/-aN-zwPTIFY?t=1m16s";
        	body_handler.setImageResource(R.drawable.heart);
        }
        else if(body_where.equals("中暑"))
        {
        	video_url = "http://youtu.be/ORGoo-9I7Bc?t=1m25s";
        	body_handler.setImageResource(R.drawable.heatstroke);
        }
        else if(body_where.equals("骨折"))
        {
        	video_url = "http://youtu.be/wIFcIjnIsMo";
        	body_handler.setImageResource(R.drawable.fracture);
        }
        else if(body_where.equals("眼睛異物"))
        {
        	video_url = "https://www.youtube.com/watch?v=PwcynKWERZQ";
        	body_handler.setImageResource(R.drawable.eye_foreign_matter);
        }
        else if(body_where.equals("鼻子異物"))
        {
        	video_url = "此部分沒有推薦影片";
        	Toast.makeText(this,"此部分沒有推薦影片",Toast.LENGTH_SHORT).show();
        	body_handler.setImageResource(R.drawable.noise_foreign_body);
        }
        else if(body_where.equals("瘀青"))
        {
        	video_url = "https://www.youtube.com/watch?v=MW7-iUqoRD8";
        	body_handler.setImageResource(R.drawable.bruising);
        }
        else if(body_where.equals("燒燙傷"))
        {
        	video_url = "https://www.youtube.com/watch?v=TfXN6alRliU";
        	body_handler.setImageResource(R.drawable.burns);
        }
        else
        {
        	body_handler.setImageResource(R.drawable.handler_test2);
        }
	}
	
	@Override 
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch(item.getItemId()){ 
        case android.R.id.home: //对用户按home icon的处理，本例只需关闭activity，就可返回上一activity，即主activity。
            
             finish(); 
             return true; 
             
        case R.id.Video:
	    	  
      	  Toast.makeText(this,"僅供參考",Toast.LENGTH_SHORT).show();
      	  
          Intent intent = new Intent(Intent.ACTION_VIEW);
          intent.setPackage("com.google.android.youtube");    
          intent.setData(Uri.parse(video_url));

          if(video_url.equals("此部分沒有推薦影片"))
          {
        	  Toast.makeText(this,"此部分沒有推薦影片",Toast.LENGTH_SHORT).show();
          }
          else
          {
        	  startActivity(intent);
          }
            return true;
        default: 
             break; 
        } 
        return super.onOptionsItemSelected(item); 
    } 
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.body_handler, menu);
		return super.onCreateOptionsMenu(menu);
	}*/
	
	//http://stackoverflow.com/questions/9282122/android-4-0-text-on-the-action-bar-never-shows
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.body_handler, menu);

	    final MenuItem item = menu.findItem(R.id.Video);
	    item.getActionView().setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	onOptionsItemSelected(item);
	        }
	    });

	    return super.onCreateOptionsMenu(menu);
	}

	

}
