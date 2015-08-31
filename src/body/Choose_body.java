package body;

import com.example.careyourself.R;
import com.example.careyourself.R.id;
import com.example.careyourself.R.layout;

import android.app.ActionBar;
import android.util.Log;
import android.view.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Choose_body extends Activity {
	
	private ActionBar  bar = null; 
	private ListView listView;
	private String[] list = {"空"};
	private String[] list_A = {"扭傷","抽筋","骨折","瘀青"};//腳
	private String[] list_B = {"噎到","流鼻血","撞掉牙齒","中暑","眼睛異物","鼻子異物"};//頭
	private String[] list_C = {"腹瀉","緩和月經","心臟病"};//身體
	private String[] list_D = {"動物咬傷","骨折","瘀青"};//手
	private ArrayAdapter<String> listAdapter;

	
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.choose_body);  
	    bar = getActionBar();  //获取ActionBar的对象，从这个方法也可知action bar是activity的一个属性
        bar.setDisplayHomeAsUpEnabled(true); 
        
        

        
        
        Bundle bundle = this.getIntent().getExtras();
        String _where = bundle.getString("KEY_where");
        
        Log.v("KEY_where=>", _where);
        
        if(_where.equals("ankle"))
        {
        	//Toast.makeText(this,"list_A",Toast.LENGTH_SHORT).show();
        	list = list_A;
        }
        else if (_where.equals("head"))
        {
        	//Toast.makeText(this,"list_B",Toast.LENGTH_SHORT).show();
        	list = list_B;
        }
        else if (_where.equals("belly"))
        {
        	//Toast.makeText(this,"list_C",Toast.LENGTH_SHORT).show();
        	list = list_C;
        }
        else if (_where.equals("hand"))
        {
        	//Toast.makeText(this,"list_C",Toast.LENGTH_SHORT).show();
        	list = list_D;
        }
        
        
        listView = (ListView)findViewById(R.id.choosing_body_listView);
        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
               Toast.makeText(getApplicationContext(),"你選擇的是"+list[position], Toast.LENGTH_SHORT).show();

               
           	   Intent choosing_body_handler = new Intent();
        	   choosing_body_handler.setClass(Choose_body.this, Body_handler.class);
        	   Bundle bundle = new Bundle();
               bundle.putString("KEY_symptom", list[position]);
               choosing_body_handler.putExtras(bundle);
               startActivity(choosing_body_handler);
               //Choose_body.this.finish(); 
            }
        });


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
	
}
