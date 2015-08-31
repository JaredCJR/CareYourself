package MySQL_message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import message.Message_model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
 
// 資料功能類別
public class Message_model_db_event {
    // 表格名稱    
    public static final String TABLE_NAME = "message_data";
 
    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";
 
    // 其它表格欄位名稱
    public static final String TIME_COLUMN = "send_time";
    public static final String TITLE_COLUMN = "title";
    public static final String CONTENT_COLUMN = "content";

 
    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE = 
            "CREATE TABLE " + TABLE_NAME + " (" + 
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIME_COLUMN + " INTEGER NOT NULL, " +
            TITLE_COLUMN + " TEXT NOT NULL, " +
            CONTENT_COLUMN + " TEXT NOT NULL )" ;
 
    // 資料庫物件    
    private SQLiteDatabase db;
 
    // 建構子，一般的應用都不需要修改
    public Message_model_db_event(Context context) {
    	Log.v("=====>", "DB constructor INIT");
        db = MyDBHelper.getDatabase(context);
        Log.v("=====>", "DB constructor FIN");
    }
 
    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }
 
    // 新增參數指定的物件
    public Message_model insert(Message_model item) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();     
 
        cv.put(TIME_COLUMN, item.getMessage_time());
        cv.put(TITLE_COLUMN, item.getMessage_title());
        cv.put(CONTENT_COLUMN, item.getMessage_content());
 
        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);
 
        // 設定編號
        item.setId(id);
        // 回傳結果
        return item;
    }
 
    // 修改參數指定的物件
    public boolean update(Message_model item) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();
 
        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料        
        cv.put(TIME_COLUMN, item.getMessage_time());
        cv.put(TITLE_COLUMN, item.getMessage_title());
        cv.put(CONTENT_COLUMN, item.getMessage_content());
        
        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + item.getId();
 
        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;         
    }
 
    // 刪除參數指定編號的資料
    public boolean delete(long id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }
 
    // 讀取所有記事資料
    public ArrayList<Message_model> getAll() {
    	ArrayList<Message_model> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);
 
        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }
 
        cursor.close();
        return result;
    }
 
    // 取得指定編號的資料物件
    public Message_model get(long id) {
        // 準備回傳結果用的物件
    	Message_model item = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
 
        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            item = getRecord(result);
        }
 
        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return item;
    }
 
    // 把Cursor目前的資料包裝為物件
    public Message_model getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
    	Message_model result = new Message_model();
 
        result.setId(cursor.getLong(0));
        result.setMessage_date(cursor.getString(1));
        result.setMessage_title(cursor.getString(2));
        result.setMessage_content(cursor.getString(3));
        // 回傳結果
        return result;
    }
 
    
    // 取得資料數量
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
 
        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
 
        return result;
    }
 
    // 建立範例資料
    public void sample() {
    	Message_model sample1 = new Message_model(0,"2013/10/20 20:08:13","這個APP太好用了！","我真的親身經歷，簡單明瞭好用！");
    	Message_model sample2 = new Message_model(0,"2013/10/23 20:08:13","一隻非常可愛的小狗狗!","她的名字叫「大熱狗」，又叫\n作「奶嘴」，是一隻非常可愛\n的小狗。");
    	Message_model sample3 = new Message_model(0,"2013/11/20 20:08:13","一首非常好聽的音樂！","好聽到爆炸！");
    	Message_model sample4 = new Message_model(0,"2014/10/20 20:08:13","儲存在資料庫的資料","對阿，sql好聽到爆炸！好聽到爆炸！好聽到爆炸！好聽到爆炸！好聽到爆炸！好聽到爆炸！好聽到爆炸！");
 
        insert(sample1);
        insert(sample2);
        insert(sample3);
        insert(sample4);
    }
 
}