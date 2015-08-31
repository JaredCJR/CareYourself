package MySQL_message;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper {

	// 資料庫名稱
	public static final String DATABASE_NAME = "my_message_data.db";
	// 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
	public static final int VERSION = 1;//必>=1
	// 資料庫物件，固定的欄位變數
	private static SQLiteDatabase database = null;

	// 建構子，在一般的應用都不需要修改
	public MyDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		Log.v("=====>", "MyDBHelper constructor FIN");
	}

	// 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
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
		// 建立應用程式需要的表格
		db.execSQL(Message_model_db_event.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.v("=====>", "更新SQL INIT");

		
		// 刪除原有的表格
		db.execSQL("DROP TABLE IF EXISTS " + Message_model_db_event.TABLE_NAME);
		// 呼叫onCreate建立新版的表格
		onCreate(db);
		Log.v("=====>", "更新SQL FIN");
	}

}
