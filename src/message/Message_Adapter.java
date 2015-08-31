package message;

import java.util.ArrayList;

import com.example.careyourself.MainActivity;
import com.example.careyourself.R;
import com.example.careyourself.R.id;
import com.example.careyourself.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class Message_Adapter extends BaseAdapter   implements OnClickListener {
          
         /*********** Declare Used Variables *********/
         private Activity activity;
	     //private Fragment frg;
         private ArrayList data;
         private static LayoutInflater inflater=null;
         public Resources res;
         Message_model tempValues=null;
         int i=0;
          
         /*************  CustomAdapter Constructor *****************/
         public Message_Adapter(Activity a, ArrayList d,Resources resLocal) {
              
                /********** Take passed values **********/
                 activity = a;
                 data=d;
                 res = resLocal;
              
                 /***********  Layout inflator to call external xml layout () ***********/
                  inflater = ( LayoutInflater )activity.
                                              getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              
         }
      
         /******** What is the size of Passed Arraylist Size ************/
         public int getCount() {
              
             if(data.size()<=0)
                 return 1;
             return data.size();
         }
      
         public Object getItem(int position) {
             return position;
         }
      
         public long getItemId(int position) {
             return position;
         }
          
         /********* Create a holder Class to contain inflated xml file elements *********/
         public static class ViewHolder{
              
             public TextView text_title;
             public TextView text_content;
             public TextView text_date;
             //public ImageView image;
      
         }
      
         /****** Depends upon data size called for each row , Create each ListView row *****/
         public View getView(int position, View convertView, ViewGroup parent) {
              
             View vi = convertView;
             ViewHolder holder;
              
             if(convertView==null){
                  
                 /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
                 vi = inflater.inflate(R.layout.message_item, null);
                  
                 /****** View Holder Object to contain tabitem.xml file elements ******/
 
                 holder = new ViewHolder();
                 holder.text_title = (TextView) vi.findViewById(R.id.text_message_title);
                 holder.text_content=(TextView)vi.findViewById(R.id.text_message_content);
                 holder.text_date=(TextView)vi.findViewById(R.id.text_message_date);
                  
                /************  Set holder with LayoutInflater ************/
                 vi.setTag( holder );
             }
             else 
                 holder=(ViewHolder)vi.getTag();
              
             if(data.size()<=0)
             {
                 holder.text_title.setText("No Data");
                  
             }
             else
             {
                 /***** Get each Model object from Arraylist ********/
                 tempValues=null;
                 tempValues = ( Message_model ) data.get( position );
                  
                 /************  Set Model values in Holder elements ***********/
 
                  holder.text_title.setText( tempValues.getMessage_title() );
                  holder.text_content.setText( tempValues.getMessage_content() );
                  holder.text_date.setText( tempValues.getMessage_time() );
                  
                   /*holder.image.setImageResource(
                               res.getIdentifier(
                               "com.androidexample.customlistview:drawable/"+tempValues.getImage()
                               ,null,null));*/
                   
                  /******** Set Item Click Listner for LayoutInflater for each row *******/
 
                  vi.setOnClickListener(new OnItemClickListener( position ));
             }
             return vi;
         }
          
         @Override
         public void onClick(View v) {
                 Log.v("CustomAdapter", "=====Row button clicked=====");
         }
          
         /********* Called when Item click in ListView ************/
         private class OnItemClickListener  implements OnClickListener{           
             private int mPosition;
              
             OnItemClickListener(int position){
                  mPosition = position;
             }
              
             @Override
             public void onClick(View arg0) {
 
        
            	 MainActivity sct = (MainActivity)activity;
 
              /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/
 
                 //sct.on_MessageItem_Click(mPosition);
             }               
         }   
     }