package bmi;

import java.text.NumberFormat;

import com.example.careyourself.MainActivity;
import com.example.careyourself.R;
import com.example.careyourself.R.id;
import com.example.careyourself.R.layout;
import com.example.careyourself.R.string;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class BMI_fragment extends Fragment {

  private String value = "0";
  private View mView;
  
  private TextView bmi_age;
  private EditText bmi_age_input;
  private TextView bmi_height;
  private EditText bmi_height_input;
  private TextView bmi_weight;
  private EditText bmi_weight_input;
  private TextView bmi_range;
  private TextView bmi_ideal_weight;
  private TextView bmi_bmi;
  private TextView bmi_situation;
	private RadioButton btn_man;
	private RadioButton btn_woman;
	private RadioGroup sex_group;
	private ImageButton bmi_btn;
  private String age = "";
  private int age_1 = 0;
  private String height = "";
  private double height_1 = 0;
  private String weight = "";
  private double weight_1 = 0;
  private String sex = "男";
  private String BMI = "";
  private double BMI_1 = -99;
  private String show_bmi ="";
  private double min_bmi = -1;
  private double max_bmi_1 = -1;
  private double max_bmi_2 = -1;
  private double ideal_bmi = -1;
  private String ideal_bmi_string = "error";
  private String show_ideal_weight = "error";
  private SharedPreferences pref_settings;

  @Override//onAttach 只有在頁籤第一次顯示時被呼叫，它會傳入母體 Activity ，所以這時候可以呼叫它的公開方法去取得值。
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    Log.d("=====>", "BMI_Fragment onAttach");
    MainActivity MainActivity = (MainActivity)activity;
  }

  @Override//onCreateView 用來建立 layout 。onActivityCreated 是在母體 Activity onCreate之後呼叫，可以用來取得 layout 中的元件。這兩個方法在每次使用者點選該頁籤時都會被呼叫。
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  mView = inflater.inflate(R.layout.frg_bmi, container, false);
	  Log.d("=====>", "BMI_Fragment onCreateView");
	  
	  processViews();
	  restore_pref();
	  
	  bmi_age_input.addTextChangedListener(Watcher);
	  bmi_height_input.addTextChangedListener(Watcher);
	  bmi_weight_input.addTextChangedListener(Watcher);
	  
      sex_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
          
          @Override  
          public void onCheckedChanged(RadioGroup group, int checkedId) {  
              RadioButton radbtn = (RadioButton) mView.findViewById(checkedId); 
              sex  = radbtn.getText().toString();
              Toast.makeText(getActivity().getApplicationContext(),sex, Toast.LENGTH_SHORT).show();
              
              pref_settings.edit().putString("sex", sex).commit();
              Log.v("=====>", "性別:"+sex);
              
              if(age_1>1)
              {
            	  determine_range(age_1,sex);
            	  if(weight_1>9 && height_1>9 && age_1>1 )
            	  {
            		  bmi_situation(min_bmi, max_bmi_1 , max_bmi_2,BMI_1);
            		  calc_ideal_bmi(min_bmi,max_bmi_1,height_1);
            	  }
              }
              
          }  
      });
      

	  bmi_btn.setOnClickListener(new OnClickListener() {
    	   
     @Override
     public void onClick(View v) {
      //取得EditText的輸入內容

    	 try {
    	      weight = bmi_weight_input.getText().toString();
    	      weight_1=Double.parseDouble(weight);
    	      
    	      if(weight_1 >20)
    	      {
        		  Intent bmi_result = new Intent();
        		  bmi_result.setClass(getActivity(), BMI_result.class);
        		  Bundle bundle = new Bundle();
        		  bundle.putString("KEY_weight", weight);
        		  bmi_result.putExtras(bundle);
        		  startActivity(bmi_result);
    	      }
    	      else
    	      {
    	    	  Toast.makeText(getActivity(),"你真的這麼輕?",Toast.LENGTH_SHORT).show();
    	      }
    	      
    		    
    		} catch (Exception e) {
    		    Log.v("=====>", e.getMessage());
    		    Log.v("=====>", "weight is null");
    		    
    		    Toast.makeText(getActivity(),"請輸入體重！",Toast.LENGTH_SHORT).show();
    		}


     }
    });
	  
	  
    //return inflater.inflate(R.layout.frg_bmi, container, false);
	  return mView;
  }
  
  
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.d("=====>", "BMI_Fragment onActivityCreated");
  }

  
  
	private void processViews() {
		  bmi_age=(TextView) mView.findViewById(R.id.bmi_age);
		  bmi_age_input=(EditText) mView.findViewById(R.id.bmi_age_input);
		  bmi_height=(TextView) mView.findViewById(R.id.bmi_height);
		  bmi_height_input=(EditText) mView.findViewById(R.id.bmi_height_input);
		  bmi_weight=(TextView) mView.findViewById(R.id.bmi_weight);
		  bmi_weight_input=(EditText) mView.findViewById(R.id.bmi_weight_input);
		  bmi_range=(TextView) mView.findViewById(R.id.bmi_range);
		  bmi_ideal_weight=(TextView) mView.findViewById(R.id.bmi_ideal_weight);
		  bmi_bmi=(TextView) mView.findViewById(R.id.bmi_bmi);
		  bmi_situation=(TextView) mView.findViewById(R.id.bmi_situation);
		  btn_man= (RadioButton) mView.findViewById(R.id.btn_man);
		  btn_woman= (RadioButton) mView.findViewById(R.id.btn_woman);
		  sex_group= (RadioGroup) mView.findViewById(R.id.sex_group);
		  bmi_btn= (ImageButton) mView.findViewById(R.id.bmi_btn);
		  
		  //取得SharedPreference設定("Preference"為設定檔的名稱)
		  pref_settings = getActivity().getSharedPreferences("Preference", 0);
	}
	
	public void restore_pref()
	{
		
		pref_settings = getActivity().getSharedPreferences("Preference", 0);


			String pref_sex = pref_settings.getString("sex", "empty");
			int pref_age = pref_settings.getInt("age", 0);
			String pref_height = pref_settings.getString("height", "0");
			
			if(pref_sex.equals("男"))
			{
				btn_man.setChecked(true);
			}
			else if(pref_sex.equals("女"))
			{
				btn_woman.setChecked(true);
			}
			else
			{
				Log.v("=====>", "No sex_pref");
			}
			
			if(pref_age > 0)
			{
				bmi_age_input.setText(String.valueOf(pref_age) );
			}
			else
			{
				Log.v("=====>", "No age_pref");
			}
			
			if(Double.parseDouble(pref_height)>99)
			{
				bmi_height_input.setText(pref_height);
			}
			else
			{
				Log.v("=====>", "No height_pref");
			}

	}
	
	private TextWatcher Watcher = new TextWatcher() {
		  @Override
		  public void afterTextChanged(Editable s) {

		  }
		  @Override
		  public void beforeTextChanged(CharSequence s, int start, int count,
		    int after) {
		  }
		  
		  @Override
		  public void onTextChanged(CharSequence s, int start, int before,
		    int count) {
		   Log.v("TAG", "onTextChanged--------------->");
		   if( bmi_age_input.length() >0)
		   {
			      age = bmi_age_input.getText().toString();
			      age_1=Integer.parseInt(age);
			      Log.v("=====>", "性別:"+sex);
			      Log.v("=====>", "age:"+age_1);
			      
				   //置入"OO"屬性的字串
			      pref_settings.edit().putInt("age", age_1).commit();
			   
			      if( bmi_height_input.length() >0)
			      {
				      height = bmi_height_input.getText().toString();
				      height_1=Double.parseDouble(height);
				      Log.v("=====>", "height:"+height_1);
				      
				      pref_settings.edit().putString("height", height).commit();
				      
				      if(bmi_weight_input.length() >0)
				      {
					      
					      weight = bmi_weight_input.getText().toString();
					      weight_1=Double.parseDouble(weight);
					      Log.v("=====>", "weight:"+weight_1);
					      Log.v("=====>", "=================");
					      
					      //pref_settings.edit().putString("weight", weight).commit();
					      

					      if(weight_1>9 && height_1>9 && age_1>1 )
					      {
						      //BMI_1 = (weight_1/(Math.pow((height_1/100), 2)));
						      try {
						    	  BMI_1 = new Double((weight_1/(Math.pow((height_1/100), 2))));
						    	} catch (NumberFormatException e) {
						    	  BMI_1 = -99; // your default value
						    	}
						     
						      //BMI=String.valueOf(BMI_1);
						      Log.v("=====>", BMI);
						      NumberFormat nf = NumberFormat.getInstance();
						      nf.setMaximumFractionDigits( 2 ); 
						      
						      try {
						    	  BMI_1 = Double.parseDouble(nf.format(BMI_1));
						    	} catch (NumberFormatException e) {
						    	  BMI_1 = -99; // your default value
						    	}
						      
						      if(BMI_1 ==-99)
						    	  {
						    	  show_bmi = getResources().getString(R.string.bmi_bmi)+"Invalid!";
						    	  }
						      else
						    	  {
						    	  show_bmi = getResources().getString(R.string.bmi_bmi)+BMI_1;
						    	  }
						      bmi_bmi.setText(show_bmi); 
						      
						      determine_range(age_1,sex);
						      bmi_situation(min_bmi, max_bmi_1 , max_bmi_2,BMI_1);
						      calc_ideal_bmi(min_bmi,max_bmi_1,height_1);
						      

					      }

				      }
			      }
		   }

		   }
		  
		};
		
		public void man_determine_bmi_range(int my_age)
		{
			switch(my_age) { 
		    case 2: 
		        min_bmi=15.2;
		        max_bmi_1=17.7;
		        max_bmi_2=19.0;
		        break; 
		    case 3: 
		        min_bmi=14.8;
		        max_bmi_1=17.7;
		        max_bmi_2=19.1; 
		        break; 
		    case 4: 
		        min_bmi=14.4;
		        max_bmi_1=17.7;
		        max_bmi_2=19.3; 
		        break; 
		    case 5: 
		        min_bmi=14.0;
		        max_bmi_1=17.7;
		        max_bmi_2=19.4; 
		        break; 
		    case 6: 
		        min_bmi=13.9;
		        max_bmi_1=17.9;
		        max_bmi_2=19.7; 
		        break; 
		    case 7: 
		        min_bmi=14.7;
		        max_bmi_1=18.6;
		        max_bmi_2=21.2; 
		        break; 
		    case 8: 
		        min_bmi=15.0;
		        max_bmi_1=19.3;
		        max_bmi_2=22.0; 
		        break; 
		    case 9: 
		        min_bmi=15.2;
		        max_bmi_1=19.7;
		        max_bmi_2=22.5; 
		        break; 
		    case 10: 
		        min_bmi=15.4;
		        max_bmi_1=20.3;
		        max_bmi_2=22.9; 
		        break; 
		    case 11: 
		        min_bmi=15.8;
		        max_bmi_1=21.0;
		        max_bmi_2=22.9; 
		        break; 
		    case 12: 
		        min_bmi=16.4;
		        max_bmi_1=21.5;
		        max_bmi_2=24.2; 
		        break; 
		    case 13: 
		        min_bmi=17.0;
		        max_bmi_1=22.2;
		        max_bmi_2=24.8; 
		        break; 
		    case 14: 
		        min_bmi=17.6;
		        max_bmi_1=22.7;
		        max_bmi_2=25.2; 
		        break; 
		    case 15: 
		        min_bmi=18.2;
		        max_bmi_1=23.1;
		        max_bmi_2=25.5; 
		        break; 
		    case 16: 
		        min_bmi=18.6;
		        max_bmi_1=23.4;
		        max_bmi_2=25.6; 
		        break; 
		    case 17: 
		        min_bmi=19.0;
		        max_bmi_1=23.6;
		        max_bmi_2=25.6; 
		        break; 
		    case 18: 
		        min_bmi=19.2;
		        max_bmi_1=23.7;
		        max_bmi_2=25.6; 
		        break; 
		    default: 
		        min_bmi=18.5;
		        max_bmi_1=25;
		        max_bmi_2=30;
		} 
		}
		
		public void woman_determine_bmi_range(int my_age)
		{
			switch(my_age) { 
		    case 2: 
		        min_bmi=14.9;
		        max_bmi_1=17.3;
		        max_bmi_2=18.3;
		        break; 
		    case 3: 
		        min_bmi=14.5;
		        max_bmi_1=17.2;
		        max_bmi_2=18.5; 
		        break; 
		    case 4: 
		        min_bmi=14.2;
		        max_bmi_1=17.1;
		        max_bmi_2=18.6; 
		        break; 
		    case 5: 
		        min_bmi=13.9;
		        max_bmi_1=17.1;
		        max_bmi_2=18.9; 
		        break; 
		    case 6: 
		        min_bmi=13.6;
		        max_bmi_1=17.2;
		        max_bmi_2=19.1; 
		        break; 
		    case 7: 
		        min_bmi=14.4;
		        max_bmi_1=18.0;
		        max_bmi_2=20.3; 
		        break; 
		    case 8: 
		        min_bmi=14.6;
		        max_bmi_1=18.8;
		        max_bmi_2=21.0; 
		        break; 
		    case 9: 
		        min_bmi=14.9;
		        max_bmi_1=19.3;
		        max_bmi_2=21.6; 
		        break; 
		    case 10: 
		        min_bmi=15.2;
		        max_bmi_1=20.1;
		        max_bmi_2=23.3; 
		        break; 
		    case 11: 
		        min_bmi=15.8;
		        max_bmi_1=20.9;
		        max_bmi_2=23.1; 
		        break; 
		    case 12: 
		        min_bmi=16.4;
		        max_bmi_1=21.6;
		        max_bmi_2=23.9; 
		        break; 
		    case 13: 
		        min_bmi=17.0;
		        max_bmi_1=22.7;
		        max_bmi_2=24.6; 
		        break; 
		    case 14: 
		        min_bmi=17.6;
		        max_bmi_1=22.7;
		        max_bmi_2=25.1; 
		        break; 
		    case 15: 
		        min_bmi=18.0;
		        max_bmi_1=22.7;
		        max_bmi_2=25.3; 
		        break; 
		    case 16: 
		        min_bmi=18.2;
		        max_bmi_1=22.7;
		        max_bmi_2=25.3; 
		        break; 
		    case 17: 
		        min_bmi=18.3;
		        max_bmi_1=22.7;
		        max_bmi_2=25.3; 
		        break; 
		    case 18: 
		        min_bmi=18.3;
		        max_bmi_1=22.7;
		        max_bmi_2=25.3; 
		        break; 
		    default: 
		        min_bmi=18.5;
		        max_bmi_1=25;
		        max_bmi_2=30;
		} 
		}
		
		public void determine_range(int age,String sex)
		{
			  if(sex.equals("男"))
			  {
				  Log.v("=====>", "決定範圍  性別:"+sex);
				  man_determine_bmi_range(age);
			  }
			  else if (sex.equals("女"))
			  {
				  Log.v("=====>", "決定範圍  性別:"+sex);
				  woman_determine_bmi_range(age);
			  }
			  bmi_range.setText(getResources().getString(R.string.bmi_range)+min_bmi+"～"+max_bmi_1);
		}
		
		public void bmi_situation(double min_bmi, double max_bmi_1 , double max_bmi_2,double bmi_1)
		{
			if(bmi_1 < min_bmi)
			{
				bmi_situation.setText(getResources().getString(R.string.bmi_situation)+"太瘦了！");
			}
			else if(bmi_1 < max_bmi_1)
			{
				bmi_situation.setText(getResources().getString(R.string.bmi_situation)+"健康！");
			}
			else if(bmi_1 < max_bmi_2)
			{
				bmi_situation.setText(getResources().getString(R.string.bmi_situation)+"多注意飲食！");
			}
			else if(bmi_1 > max_bmi_2)
			{
				bmi_situation.setText(getResources().getString(R.string.bmi_situation)+"肥胖！");
			}
		}
		
		public void calc_ideal_bmi(double min_bmi,double max_bmi_1,double height_1)
		{
			double ave_bmi = ((min_bmi+max_bmi_1)/2);
			double ideal_weight=ave_bmi*(Math.pow((height_1/100), 2));
			
		      try {
		    	  ideal_bmi = new Double(ideal_weight);
		    	} catch (NumberFormatException e) {
		    		ideal_bmi = -99; // your default value
		    	}
		     
		      //ideal_bmi_string = String.valueOf(ideal_bmi);
		      Log.v("=====>", ideal_bmi_string);
		      NumberFormat nf = NumberFormat.getInstance();
		      nf.setMaximumFractionDigits( 2 ); 
		      
		      try {
		    	  ideal_bmi = Double.parseDouble(nf.format(ideal_bmi));
		    	} catch (NumberFormatException e) {
		    		ideal_bmi = -99; // your default value
		    	}
		      
		      if(ideal_bmi ==-99)
		    	  {
		    	  show_ideal_weight = getResources().getString(R.string.bmi_ideal_weight)+"Invalid!";
		    	  }
		      else
		    	  {
		    	  show_ideal_weight = getResources().getString(R.string.bmi_ideal_weight)+ideal_bmi;
		    	  }
		      bmi_ideal_weight.setText(show_ideal_weight); 
		}


}