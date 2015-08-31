package bmi;



import java.util.Random;

import com.example.careyourself.R;
import android.app.ActionBar;
import android.util.Log;
import android.view.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;



public class BMI_result extends Activity{
	
	private ActionBar  bar = null; 
	private SharedPreferences pref_weight;
	private String weight = "error";
	private double pref_weight_1 = 0;
	private double pref_weight_2 = 0;
	private double pref_weight_3 = 0;
	private double pref_weight_4 = 0;
	private double pref_weight_5 = 0;
	private double pref_weight_6 = 0;
	private double pref_weight_7 = 0;
	private double pref_weight_8 = 0;
	//private double[] modified_weight_array = new double[8];
	//private float[] weight_mapping_px = new float[8];
	private weight_element[] weight_array = new weight_element[8];
	private LinearLayout chart_layout;
	private LinearLayout advice_layout;
    private Paint paint = new Paint();
    private double text_size = paint.getTextSize();
    private DrawView view;
    private int smallest=0;
    private int biggest = 0;

	
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.bmi_result);  
	    //setContentView(view);  
	    bar = getActionBar();
        bar.setDisplayHomeAsUpEnabled(true); 
        
        for(int i =0;i<weight_array.length;i++)
        {
        	weight_array[i] = new weight_element();
        }
        
        processView();
        
        Bundle bundle = this.getIntent().getExtras();
        weight = bundle.getString("KEY_weight");
        Log.v("=====>","get weight = "+weight);
        
        record_weight(weight);
        //get_recorded_weight();
        
	}
	
	public void processView()
	{
		pref_weight = getSharedPreferences("pref_weight", 0);
		chart_layout = (LinearLayout)findViewById(R.id.chart_canvas);
		advice_layout = (LinearLayout)findViewById(R.id.advice);
		
		random_advice();
		Toast.makeText(getApplicationContext(),"隨機小知識！", Toast.LENGTH_SHORT).show();

		/*
		 * for chart
		 */
        view=new DrawView(this);
        view.setMinimumHeight(550);
        view.setMinimumWidth(1080);
        view.invalidate();  
        chart_layout.addView(view);  

	}
	
	public void random_advice()
	{
		Random rand = new Random(System.currentTimeMillis());
		 int random = rand.nextInt(96) + 1;//minium=1  maximum=96
		 random%=6;
		 
		 switch(random)
		 {
		 case 0:
			 advice_layout.setBackgroundResource(R.drawable.advice_6);
			 break;
		 case 1:
			 advice_layout.setBackgroundResource(R.drawable.advice_1);
			 break;
		 case 2:
			 advice_layout.setBackgroundResource(R.drawable.advice_2);
			 break;
		 case 3:
			 advice_layout.setBackgroundResource(R.drawable.advice_3);
			 break;
		 case 4:
			 advice_layout.setBackgroundResource(R.drawable.advice_4);
			 break;
		 case 5:
			 advice_layout.setBackgroundResource(R.drawable.advice_5);
			 break;

		default:
			advice_layout.setBackgroundResource(R.drawable.advice_6);
			Toast.makeText(getApplicationContext(),"random notice", Toast.LENGTH_SHORT).show();
		 }
	}
	
	public void record_weight(String weight)
	{
		int index = pref_weight.getInt("index", 1);
		get_recorded_weight();
		
		switch(index)
		{
		case 1:
			pref_weight.edit().putString("weight_1", weight).commit();
			pref_weight.edit().putInt("index", 2).commit();
			break;
		case 2:
			pref_weight.edit().putString("weight_2", weight).commit();
			pref_weight.edit().putInt("index", 3).commit();
			break;
		case 3:
			pref_weight.edit().putString("weight_3", weight).commit();
			pref_weight.edit().putInt("index", 4).commit();
			break;
		case 4:
			pref_weight.edit().putString("weight_4", weight).commit();
			pref_weight.edit().putInt("index", 5).commit();
			break;
		case 5:
			pref_weight.edit().putString("weight_5", weight).commit();
			pref_weight.edit().putInt("index", 6).commit();
			break;
		case 6:
			pref_weight.edit().putString("weight_6", weight).commit();
			pref_weight.edit().putInt("index", 7).commit();
			break;
		case 7:
			pref_weight.edit().putString("weight_7", weight).commit();
			pref_weight.edit().putInt("index", 8).commit();
			break;
		case 8:
			if((pref_weight_8 == 0))
			{
			}
			else
			{
				pref_weight.edit().putString("weight_1", String.valueOf(pref_weight_2)).commit();
				pref_weight.edit().putString("weight_2", String.valueOf(pref_weight_3)).commit();
				pref_weight.edit().putString("weight_3", String.valueOf(pref_weight_4)).commit();
				pref_weight.edit().putString("weight_4", String.valueOf(pref_weight_5)).commit();
				pref_weight.edit().putString("weight_5", String.valueOf(pref_weight_6)).commit();
				pref_weight.edit().putString("weight_6", String.valueOf(pref_weight_7)).commit();
				pref_weight.edit().putString("weight_7", String.valueOf(pref_weight_8)).commit();

			}
			pref_weight.edit().putString("weight_8", weight).commit();
			pref_weight_8 =  Double.parseDouble(pref_weight.getString("weight_8", "0"));
			pref_weight.edit().putInt("index", 8).commit();
			break;
		default:
			Log.v("=====>","record_weight error");
		}
		get_recorded_weight();
		
		Log.v("=====>","pref_weight_1 : "+String.valueOf(pref_weight_1));
		Log.v("=====>","pref_weight_2 : "+String.valueOf(pref_weight_2));
		Log.v("=====>","pref_weight_3 : "+String.valueOf(pref_weight_3));
		Log.v("=====>","pref_weight_4 : "+String.valueOf(pref_weight_4));
		Log.v("=====>","pref_weight_5 : "+String.valueOf(pref_weight_5));
		Log.v("=====>","pref_weight_6 : "+String.valueOf(pref_weight_6));
		Log.v("=====>","pref_weight_7 : "+String.valueOf(pref_weight_7));
		Log.v("=====>","pref_weight_8 : "+String.valueOf(pref_weight_8));
		
		try
		{
			weight_array[0].set_original_weight(pref_weight_1);
			weight_array[1].set_original_weight(pref_weight_2);
			weight_array[2].set_original_weight(pref_weight_3);
			weight_array[3].set_original_weight(pref_weight_4);
			weight_array[4].set_original_weight(pref_weight_5);
			weight_array[5].set_original_weight(pref_weight_6);
			weight_array[6].set_original_weight(pref_weight_7);
			weight_array[7].set_original_weight(pref_weight_8);
		}
		catch(Exception e)
		{
			Log.v("=====>","exception "+e.getMessage());
		}
		

		
		for(int i =0;i<weight_array.length;i++)
		{
			weight_array[i].set_index((i+1));
			weight_array[i].set_cut_weight(weight_array[i].get_original_weight());
		}



		
		cut_weight_bubbleSort(weight_array);//index = 0 is the smallest cut_weight
		
		for(int i =0;i<weight_array.length;i++)
		{
			Log.v("=====>","cut_weight"+i+" : "+String.valueOf(weight_array[i].get_cut_weight()));
		}
		
		int temp = (int)weight_array[0].get_cut_weight();
		smallest = temp;
		temp  %= 10;
		smallest -=temp;
		Log.v("=====>","smallest " +String.valueOf(smallest));
		biggest = smallest +20;
		
		for(int i =0;i<weight_array.length;i++)
		{
			weight_array[i].set_cut_weight( (weight_array[i].get_cut_weight()-smallest) );
		}
	}
	
	public void get_recorded_weight()
	{
		pref_weight_1 =  Double.parseDouble(pref_weight.getString("weight_1", "0"));
		pref_weight_2 =  Double.parseDouble(pref_weight.getString("weight_2", "0"));
		pref_weight_3 =  Double.parseDouble(pref_weight.getString("weight_3", "0"));
		pref_weight_4 =  Double.parseDouble(pref_weight.getString("weight_4", "0"));
		pref_weight_5 =  Double.parseDouble(pref_weight.getString("weight_5", "0"));
		pref_weight_6 =  Double.parseDouble(pref_weight.getString("weight_6", "0"));
		pref_weight_7 =  Double.parseDouble(pref_weight.getString("weight_7", "0"));
		pref_weight_8 =  Double.parseDouble(pref_weight.getString("weight_8", "0"));

	}
	
	@Override 
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch(item.getItemId()){ 
        case android.R.id.home: 
            
             finish(); 
             return true; 
             
        
        default: 
             break; 
        } 
        return super.onOptionsItemSelected(item); 
    } 
	
    public class DrawView extends View{

        public DrawView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
           
            
            paint.setAntiAlias(true);									// 設置畫筆的鋸齒效果。 true是去除。
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(8);
            paint.setColor(Color.BLACK);	
            
            paint.setTextSize((float)(text_size*3.5));//Text size
            canvas.drawLine(980, 450, 1000, 470, paint);//X軸箭頭
            canvas.drawLine(80, 470, 1001, 470, paint);//X軸  
            canvas.drawLine(980, 490, 1000, 470, paint);//X軸箭頭
            

            
            canvas.drawLine(60, 70, 80, 50, paint);//Y軸箭頭
            canvas.drawLine(80, 470, 80, 51, paint);//Y軸
            canvas.drawLine(100, 70, 80, 50, paint);//Y軸箭頭
            
            paint.setColor(Color.WHITE);
            canvas.drawLine(200, 500, 850 , 500, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            canvas.drawText("過去", 90, 525, paint);  
            canvas.drawText("現在", 900, 525, paint);
            


    			for(int i =0;i<weight_array.length;i++)
    			{
    				Log.v("=====>","modified_weight_array (cut) "+i+" : "+String.valueOf(weight_array[i].get_cut_weight()));
    				weight_array[i].set_mapping_y_px( mapping_y_px(weight_array[i].get_cut_weight()) );
    
    			}
    			draw_chart(canvas);
            
        }
        
        private void draw_chart(Canvas canvas)
        {
            int init_y = 470;
            int init_x = 40;
            int x_between = 115;
        	
            paint.setColor(Color.BLACK);
            canvas.drawText(String.valueOf(smallest)+"kg", 1, 490, paint);  
            canvas.drawText(String.valueOf(biggest)+"kg", 1, 45, paint);  
            
            index_bubbleSort( weight_array );
            

            
        	for(int i = 0;i<weight_array.length;i++)
        	{
        		weight_array[i].set_mapping_x_px((init_x+(i+1)*x_between));
        		paint.setStyle(Paint.Style.STROKE);
        		paint.setStrokeWidth(8);
        		paint.setColor(Color.RED);	
        		canvas.drawCircle(weight_array[i].get_mapping_x_px(), weight_array[i].get_mapping_y_px(), 12, paint);
        		
        		paint.setStyle(Paint.Style.FILL);
        		paint.setColor(Color.DKGRAY);
        		canvas.drawText(String.valueOf(weight_array[i].get_original_weight()), (weight_array[i].get_mapping_x_px()-60), (weight_array[i].get_mapping_y_px()-20), paint);  

        		//Log.v("=====>","see x "+i+" "+ weight_array[i].get_mapping_x_px() );
        		//Log.v("=====>","see y "+i+" "+ weight_array[i].get_mapping_y_px() );

        	}
        	for(int i = 0;i<weight_array.length;i++)
        	{
        		if(i!=7)//畫點與點之間的線
        		{
            		paint.setStrokeWidth(4);
            		paint.setColor(Color.BLACK);
            		float x1 = weight_array[i].get_mapping_x_px();
            		float y1 = weight_array[i].get_mapping_y_px();
            		float x2 = weight_array[i+1].get_mapping_x_px();
            		float y2 = weight_array[i+1].get_mapping_y_px();
            		
            		canvas.drawLine(x1, y1, x2 , y2, paint);
        		}
        	}
        	
        }
}
    
    public void NO_chart()
    {
    	Toast.makeText(this,"不合理的範圍，沒有圖!",Toast.LENGTH_SHORT).show();
    }
    
    public float mapping_y_px(double weight_px)//Y軸420px  給20KG  =>每KG 21px
    {
    	weight_px= 470-(21*weight_px);
    	return (float)weight_px;
    }

        public void cut_weight_bubbleSort(weight_element[] array)
        {
            for (int i = array.length - 1; i > 0; --i)
                for (int j = 0; j < i; ++j)
                    if (array[j].get_cut_weight() > array[j + 1].get_cut_weight())
                        Swap(array, j, j + 1);
        }
        
        public void index_bubbleSort(weight_element[] array)
        {
            for (int i = array.length - 1; i > 0; --i)
                for (int j = 0; j < i; ++j)
                    if (array[j].get_index() > array[j + 1].get_index())
                        Swap(array, j, j + 1);
        }
     
        private void Swap(weight_element[] array, int indexA, int indexB)
        {
            weight_element tmp = array[indexA];
            array[indexA] = array[indexB];
            array[indexB] = tmp;
        }
    
    
}
