package message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message_model {
	private  String message_title="null";
    private  String message_content="null";
    private String message_date="yyyy/MM/dd HH:mm:ss";
    private long id;
    
    public Message_model() {
    	message_title = "null";
    	message_content = "null";
    	message_date = "yyyy/MM/dd HH:mm:ss";
    }
    
    public Message_model(long id, String datetime, String title,String content) {
        this.id = id;
        this.message_date = datetime;
        this.message_title = title;
        this.message_content = content;
    }
     
    /*********** Set Methods ******************/
    
    public void setId(long id) {
        this.id = id;
    }
     
    public void setMessage_title(String message_title)
    {
        this.message_title = message_title;
    }

     
    public void setMessage_content(String message_content)
    {
        this.message_content = message_content;
    }
    
    public void setMessage_date(String test_date)
    {
    	this.message_date = test_date;
    }
    
    /*public void setMessage_date()
    {
    	//先行定義時間格式
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	//取得現在時間
    	Date dt=new Date();
    	//透過SimpleDateFormat的format方法將Date轉為字串
    	this.message_date=sdf.format(dt);
    }*/
     
    /*********** Get Methods ****************/
    
    public long getId() {
        return id;
    }
     
    public String getMessage_title()
    {
        return this.message_title;
    }

 
    public String getMessage_content()
    {
        return this.message_content;
    }    
    
    public String getMessage_time()
    {
    	return this.message_date;
    }

}
