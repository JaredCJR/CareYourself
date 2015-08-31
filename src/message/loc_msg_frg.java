package message;

import com.example.careyourself.MainActivity;
import com.example.careyourself.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class loc_msg_frg extends Fragment {

  private String value = "";
  private View mView;
  private ImageButton btn_north;
  private ImageButton btn_medium;
  private ImageButton btn_east;
  private ImageButton btn_south;


  @Override//onAttach 只有在頁籤第一次顯示時被呼叫，它會傳入母體 Activity ，所以這時候可以呼叫它的公開方法去取得值。
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    Log.d("=====>", "loc_msg_frg onAttach");
  }

  @Override//onCreateView 用來建立 layout 。onActivityCreated 是在母體 Activity onCreate之後呼叫，可以用來取得 layout 中的元件。這兩個方法在每次使用者點選該頁籤時都會被呼叫。
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  Log.d("=====>", "loc_msg_frg onCreateView");
	  mView = inflater.inflate(R.layout.loc_msg_frg, container, false);
	  processViews();
	  
	  
	  return mView;
  }
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.d("=====>", "loc_msg_frg onActivityCreated");

  }

	private void processViews() {
        btn_north = (ImageButton) mView.findViewById(R.id.btn_north);
        btn_south = (ImageButton) mView.findViewById(R.id.btn_south);
        btn_medium = (ImageButton) mView.findViewById(R.id.btn_medium);
        btn_east = (ImageButton) mView.findViewById(R.id.btn_east);
    }
	
	
}