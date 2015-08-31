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


  @Override//onAttach �u���b���ҲĤ@����ܮɳQ�I�s�A���|�ǤJ���� Activity �A�ҥH�o�ɭԥi�H�I�s�������}��k�h���o�ȡC
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    Log.d("=====>", "loc_msg_frg onAttach");
  }

  @Override//onCreateView �Ψӫإ� layout �ConActivityCreated �O�b���� Activity onCreate����I�s�A�i�H�ΨӨ��o layout ��������C�o��Ӥ�k�b�C���ϥΪ��I��ӭ��Үɳ��|�Q�I�s�C
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