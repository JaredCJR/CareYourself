package MySQL_message;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper {

	// ��Ʈw�W��
	public static final String DATABASE_NAME = "my_message_data.db";
	// ��Ʈw�����A��Ƶ��c���ܪ��ɭԭn���o�ӼƦr�A�q�`�O�[�@
	public static final int VERSION = 1;//��>=1
	// ��Ʈw����A�T�w������ܼ�
	private static SQLiteDatabase database = null;

	// �غc�l�A�b�@�몺���γ����ݭn�ק�
	public MyDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		Log.v("=====>", "MyDBHelper constructor FIN");
	}

	// �ݭn��Ʈw������I�s�o�Ӥ�k�A�o�Ӥ�k�b�@�몺���γ����ݭn�ק�
	public static SQLiteDatabase getDatabase(Context context) {
		Log.v("=====>", "MyDBHelper getDatabase INIT");
		if (database == null || !database.isOpen()) {
			Log.v("=====>", "MyDBHelper getDatabase if INIT");
			MyDBHelper dbhelper = new MyDBHelper(context, DATABASE_NAME, null, VERSION);
			Log.v("=====>", "MyDBHelper getDatabase new MyDBHelper FIN");
			database = dbhelper.getWritableDatabase();
			Log.v("=====>", "MyDBHelper getDatabase FIN");
		}
		Log.v("=====>", "MyDBHelper getDatabase before return");
		return database;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// �إ����ε{���ݭn�����
		db.execSQL(Message_model_db_event.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.v("=====>", "��sSQL INIT");

		
		// �R���즳�����
		db.execSQL("DROP TABLE IF EXISTS " + Message_model_db_event.TABLE_NAME);
		// �I�sonCreate�إ߷s�������
		onCreate(db);
		Log.v("=====>", "��sSQL FIN");
	}

}
