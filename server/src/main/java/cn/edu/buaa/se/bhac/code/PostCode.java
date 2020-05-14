package cn.edu.buaa.se.bhac.code;

import java.util.HashMap;

public enum  PostCode implements BaseCode
{
    SUCC_POST_ADD_TYPE0,
    SUCC_POST_ADD_TYPE1,
    ERR_POST_ADD,
    ERR_POST_PUT,
    SUCC_POST_ADD_TYPE2;
    
    public static HashMap<BaseCode,String> map = new HashMap<BaseCode,String>()
    {
        {
            put(SUCC_POST_ADD_TYPE0,"评论发布成功");
            put(SUCC_POST_ADD_TYPE1,"帖子发布成功");
            put(SUCC_POST_ADD_TYPE2,"公告发布成功");
            put(ERR_POST_ADD,"类型传入错误");
            put(ERR_POST_PUT,"尚不允许发布评价");
        }
    };
    
    @Override
    public String getMessage ()
    {
        return map.get(this);
    }
    
    @Override
    public boolean isSuccessful ()
    {
        return this.toString().startsWith("SUCC");
    }
}
