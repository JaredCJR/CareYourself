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
 
// ��ƥ\�����O
public class Message_model_db_event {
    // ���W��    
    public static final String TABLE_NAME = "message_data";
 
    // �s��������W�١A�T�w����
    public static final String KEY_ID = "_id";
 
    // �䥦������W��
    public static final String TIME_COLUMN = "send_time";
    public static final String TITLE_COLUMN = "title";
    public static final String CONTENT_COLUMN = "content";

 
    // �ϥΤW���ŧi���ܼƫإߪ�檺SQL���O
    public static final String CREATE_TABLE = 
            "CREATE TABLE " + TABLE_NAME + " (" + 
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIME_COLUMN + " INTEGER NOT NULL, " +
            TITLE_COLUMN + " TEXT NOT NULL, " +
            CONTENT_COLUMN + " TEXT NOT NULL )" ;
 
    // ��Ʈw����    
    private SQLiteDatabase db;
 
    // �غc�l�A�@�몺���γ����ݭn�ק�
    public Message_model_db_event(Context context) {
    	Log.v("=====>", "DB constructor INIT");
        db = MyDBHelper.getDatabase(context);
        Log.v("=====>", "DB constructor FIN");
    }
 
    // ������Ʈw�A�@�몺���γ����ݭn�ק�
    public void close() {
        db.close();
    }
 
    // �s�W�Ѽƫ��w������
    public Message_model insert(Message_model item) {
        // �إ߷ǳƷs�W��ƪ�ContentValues����
        ContentValues cv = new ContentValues();     
 
        cv.put(TIME_COLUMN, item.getMessage_time());
        cv.put(TITLE_COLUMN, item.getMessage_title());
        cv.put(CONTENT_COLUMN, item.getMessage_content());
 
        // �s�W�@����ƨè��o�s��
        // �Ĥ@�ӰѼƬO���W��
        // �ĤG�ӰѼƬO�S�����w���Ȫ��w�]��
        // �ĤT�ӰѼƬO�]�˷s�W��ƪ�ContentValues����
        long id = db.insert(TABLE_NAME, null, cv);
 
        // �]�w�s��
        item.setId(id);
        // �^�ǵ��G
        return item;
    }
 
    // �ק�Ѽƫ��w������
    public boolean update(Message_model item) {
        // �إ߷ǳƭק��ƪ�ContentValues����
        ContentValues cv = new ContentValues();
 
        // �[�JContentValues����]�˪��ק���
        // �Ĥ@�ӰѼƬO���W�١A �ĤG�ӰѼƬO��쪺���        
        cv.put(TIME_COLUMN, item.getMessage_time());
        cv.put(TITLE_COLUMN, item.getMessage_title());
        cv.put(CONTENT_COLUMN, item.getMessage_content());
        
        // �]�w�ק��ƪ����󬰽s��
        // �榡���u���W�١׸�ơv
        String where = KEY_ID + "=" + item.getId();
 
        // ����ק��ƨæ^�ǭק諸��Ƽƶq�O�_���\
        return db.update(TABLE_NAME, cv, where, null) > 0;         
    }
 
    // �R���Ѽƫ��w�s�������
    public boolean delete(long id){
        // �]�w���󬰽s���A�榡���u���W��=��ơv
        String where = KEY_ID + "=" + id;
        // �R�����w�s����ƨæ^�ǧR���O�_���\
        return db.delete(TABLE_NAME, where , null) > 0;
    }
 
    // Ū���Ҧ��O�Ƹ��
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
 
    // ���o���w�s������ƪ���
    public Message_model get(long id) {
        // �ǳƦ^�ǵ��G�Ϊ�����
    	Message_model item = null;
        // �ϥνs�����d�߱���
        String where = KEY_ID + "=" + id;
        // ����d��
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
 
        // �p�G���d�ߵ��G
        if (result.moveToFirst()) {
            // Ū���]�ˤ@����ƪ�����
            item = getRecord(result);
        }
 
        // ����Cursor����
        result.close();
        // �^�ǵ��G
        return item;
    }
 
    // ��Cursor�ثe����ƥ]�ˬ�����
    public Message_model getRecord(Cursor cursor) {
        // �ǳƦ^�ǵ��G�Ϊ�����
    	Message_model result = new Message_model();
 
        result.setId(cursor.getLong(0));
        result.setMessage_date(cursor.getString(1));
        result.setMessage_title(cursor.getString(2));
        result.setMessage_content(cursor.getString(3));
        // �^�ǵ��G
        return result;
    }
 
    
    // ���o��Ƽƶq
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
 
        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
 
        return result;
    }
 
    // �إ߽d�Ҹ��
    public void sample() {
    	Message_model sample1 = new Message_model(0,"2013/10/20 20:08:13","�o��APP�Ӧn�ΤF�I","�گu���˨��g���A²����A�n�ΡI");
    	Message_model sample2 = new Message_model(0,"2013/10/23 20:08:13","�@���D�`�i�R���p����!","�o���W�r�s�u�j�����v�A�S�s\n�@�u���L�v�A�O�@���D�`�i�R\n���p���C");
    	Message_model sample3 = new Message_model(0,"2013/11/20 20:08:13","�@���D�`�nť�����֡I","�nť���z���I");
    	Message_model sample4 = new Message_model(0,"2014/10/20 20:08:13","�x�s�b��Ʈw�����","����Asql�nť���z���I�nť���z���I�nť���z���I�nť���z���I�nť���z���I�nť���z���I�nť���z���I");
 
        insert(sample1);
        insert(sample2);
        insert(sample3);
        insert(sample4);
    }
 
}