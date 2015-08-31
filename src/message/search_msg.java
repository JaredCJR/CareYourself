package message;

import com.example.careyourself.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class search_msg extends Activity {

	private ActionBar  bar = null; 
	private TextView search_title;
	private String _where = "error";
	private ImageButton search_btn = null;
	private EditText keyword = null;
	private String key = "error";
	
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search_msg);  
	    bar = getActionBar();  //获取ActionBar的对象，从这个方法也可知action bar是activity的一个属性
      bar.setDisplayHomeAsUpEnabled(true); 
      
      
      processView();

      
      if(_where.equals("north"))
      {
      	//Toast.makeText(this,"north",Toast.LENGTH_SHORT).show();
    	  Log.v("=====>", "搜[北部]:");
    	  search_title.setText("搜[北部]:");
      }
      else if (_where.equals("east"))
      {
      	//Toast.makeText(this,"east",Toast.LENGTH_SHORT).show();
    	  Log.v("=====>", "搜[東部]:");
    	  search_title.setText("搜[東部]:");
      }
      else if (_where.equals("medium"))
      {
      	//Toast.makeText(this,"medium",Toast.LENGTH_SHORT).show();
    	  Log.v("=====>", "搜[中部]:");
    	  search_title.setText("搜[中部]:");
      }
      else if (_where.equals("south"))
      {
      	//Toast.makeText(this,"south",Toast.LENGTH_SHORT).show();
    	  Log.v("=====>", "搜[南部]:");
    	  search_title.setText("搜[南部]:");
      }
      else
    	  Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();  
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
  	
  	public void processView()
  	{
  		search_title = (TextView)findViewById(R.id.search_title);
        Bundle bundle = this.getIntent().getExtras();
        _where = bundle.getString("LOC_where");
        search_btn = (ImageButton)findViewById(R.id.confirm_search_btn);
        keyword = (EditText)findViewById(R.id.keyword_editText);
        keyword.requestFocus();
  	}
  	public void search_Submit(View view) 
  	{

		Intent intent = new Intent();
		intent.setClass(search_msg.this, search_result_msg.class);
		Bundle bundle = new Bundle();
		bundle.putString("LOC_where", _where);
		
		key = keyword.getText().toString();
		Log.v("=====>", "搜尋："+key);
		bundle.putString("Keyword", key);
		
		intent.putExtras(bundle);
		startActivity(intent); 
		finish();
  	}
  	
	
}
