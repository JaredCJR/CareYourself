package com.example.careyourself;

import java.util.ArrayList;

import bmi.BMI_fragment;
import bmi.BMI_result;
import body.Choose_body;
import body.main_fragment;

import message.Leave_message_activity;
import message.loc_msg_frg;
import message.sel_loc_msg_activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.ListFragment;


public class MainActivity extends FragmentActivity  implements TabListener{
	
	private ViewPager mViewPager;
	private TabFragmentPagerAdapter mAdapter;
	public static final int MAX_TAB_SIZE = 3;
	public static final String ARGUMENTS_NAME = "args";
	Fragment frg0 = new main_fragment();
	Fragment frg1 = new BMI_fragment();
	//Fragment frg2 = new message_fragment();
	Fragment frg2 = new loc_msg_frg();


@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);  
    
    findViewById(); 
    initView();

  }
  
  
  private void findViewById() {  
      mViewPager = (ViewPager) this.findViewById(R.id.pager);  
      
  }  

  private void initView() {   
      final ActionBar mActionBar = getActionBar();  
        
      mActionBar.setDisplayHomeAsUpEnabled(false);  
        
      mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);  
        
      mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());  
      mViewPager.setAdapter(mAdapter);  
      mViewPager.setOnPageChangeListener(new OnPageChangeListener() {  
            
          @Override  
          public void onPageSelected(int SelectTab) {   
              mActionBar.setSelectedNavigationItem(SelectTab);  
          }  
            
          @Override  
          public void onPageScrolled(int SelectTab, float arg1, int arg2) {    
          }  
            
          @Override  
          public void onPageScrollStateChanged(int SelectTab) {  
          }  
      }
      
    		  );  

      //﹍て ActionBar  
      Tab main_tab = mActionBar.newTab();  
      main_tab.setText(getString(R.string.title_tab1)).setTabListener(this);  
      mActionBar.addTab(main_tab);  
      
      Tab bmi_tab = mActionBar.newTab();  
      bmi_tab.setText(getString(R.string.title_tab2)).setTabListener(this);  
      mActionBar.addTab(bmi_tab);  
      
      Tab message_tab = mActionBar.newTab();  
      message_tab.setText(getString(R.string.title_tab3)).setTabListener(this);  
      mActionBar.addTab(message_tab);  

  }  
  
   
  public static class TabFragmentPagerAdapter extends FragmentPagerAdapter{
	  
      public TabFragmentPagerAdapter(FragmentManager fm) {  
          super(fm);  
      } 

      @Override  
      public Fragment getItem(int SelectTab) {  
          Fragment now = null;  
          
          
          
          switch (SelectTab) {  
          case 0:  
              now = new main_fragment();  
              break;  
          case 1:  
              now = new BMI_fragment();  
              break;  
          case 2:  
              //now = new message_fragment(); 
        	  now = new loc_msg_frg();
              break;  
          default:  
              now = new main_fragment();  
                
              Bundle args = new Bundle();  
              args.putString(ARGUMENTS_NAME, "TAB"+(SelectTab+1));  
              now.setArguments(args);  
                
              break;  
          }  
          return now;  
      }  
      
      @Override  
      public int getCount() {  
            
          return MAX_TAB_SIZE;  
      }

  }  
  
  @Override  
  public boolean onCreateOptionsMenu(Menu menu) {  
      getMenuInflater().inflate(R.menu.main, menu);  
      return true;  
  }  
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      // Handle presses on the action bar items
      switch (item.getItemId()) {
      
          case R.id.action_google_map:
    	  
        	  Uri uri = Uri.parse("geo:0,0?q=禘┮ 洛皘"); 
        	  Intent it = new Intent(Intent.ACTION_VIEW, uri);  
        	  startActivity(it);  
              return true;

          case R.id.action_leave_message:
        	  
        	  Intent intent = new Intent("com.example.careyourself.ADD_messagePage");
        	  intent.setClass(this, Leave_message_activity.class);
        	  startActivity(intent);
        	  Log.d("=====>", "Leave_message  INIT_DONE");
              return true;
              
          case R.id.action_about:
              
        	  Toast.makeText(this,"Ver 0.1 By 癬ㄓ毕 钉",Toast.LENGTH_SHORT).show();
        	  return true;
        	  
          default:
              return super.onOptionsItemSelected(item);
              
      }
  }
  

  
  @Override  
  public void onTabSelected(Tab tab, FragmentTransaction ft) {  
      mViewPager.setCurrentItem(tab.getPosition());  
  } 
  
  @Override  
  public void onTabUnselected(Tab tab, FragmentTransaction ft) {  
  }  
  
  @Override  
  public void onTabReselected(Tab tab, FragmentTransaction ft) {    
  }  
  

  public void onSubmit(View view) {
	  
	  Intent choosing_body = new Intent();
	  choosing_body.setClass(MainActivity.this, Choose_body.class);
	  Bundle bundle = new Bundle();
	  
	  
    if (view.getId() == R.id.ankle_Button) {
    	
  	      //Toast.makeText(this,"ankle button",Toast.LENGTH_SHORT).show();
  	      bundle.putString("KEY_where", "ankle");
      }
    else if (view.getId() == R.id.head_Button)
    {
    	  //Toast.makeText(this,"head button",Toast.LENGTH_SHORT).show();
    	  bundle.putString("KEY_where", "head"); 
    }
    else if (view.getId() == R.id.hand_Button)
    {
    	  //Toast.makeText(this,"head button",Toast.LENGTH_SHORT).show();
    	  bundle.putString("KEY_where", "hand"); 
    }
    else if (view.getId() == R.id.belly_Button)
    {
    	  //Toast.makeText(this,"belly button",Toast.LENGTH_SHORT).show();
    	  bundle.putString("KEY_where", "belly"); 
    }
      choosing_body.putExtras(bundle);
      startActivity(choosing_body);
  }
  
  public void onSelected(View view) {
	  
	  Intent sel_loc = new Intent();
	  sel_loc.setClass(MainActivity.this, sel_loc_msg_activity.class);
	  Bundle bundle = new Bundle();
	  
	  
    if (view.getId() == R.id.btn_north) {
    	
  	      Toast.makeText(this,"场",Toast.LENGTH_SHORT).show();
  	      bundle.putString("LOC_where", "north");
      }
    else if (view.getId() == R.id.btn_medium)
    {
    	  Toast.makeText(this,"い场",Toast.LENGTH_SHORT).show();
    	  bundle.putString("LOC_where", "medium"); 
    }
    else if (view.getId() == R.id.btn_south)
    {
    	  Toast.makeText(this,"玭场",Toast.LENGTH_SHORT).show();
    	  bundle.putString("LOC_where", "south"); 
    }
    else if (view.getId() == R.id.btn_east)
    {
    	  Toast.makeText(this,"狥场",Toast.LENGTH_SHORT).show();
    	  bundle.putString("LOC_where", "east"); 
    }
      sel_loc.putExtras(bundle);
      Log.v("=====>", "select zone to msg");
      startActivity(sel_loc);
  }
/*
 * 
 *   public void BMI_Result_Submit(View view) {

	  Intent bmi_result = new Intent();
	  bmi_result.setClass(MainActivity.this, BMI_result.class);
	  Bundle bundle = new Bundle();
	  bundle.putString("KEY_weight", BMI_fragment);
	  startActivity(bmi_result);

  }
 */

  
  
}//class MainActivity END

