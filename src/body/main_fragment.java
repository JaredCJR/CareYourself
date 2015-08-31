package body;



import com.example.careyourself.MainActivity;
import com.example.careyourself.R;
import com.example.careyourself.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class main_fragment extends Fragment {
	
	private String value = "";
	
	private View mView;


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d("=====>", "main_Fragment onAttach");
		MainActivity mainActivity = (MainActivity)activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("=====>", "main_Fragment onCreateView");
		processViews();
		return inflater.inflate(R.layout.frg_main, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d("=====>", "main_Fragment onActivityCreated");
	}
	
	private void processViews() {
		
	}
    
 
	
}

