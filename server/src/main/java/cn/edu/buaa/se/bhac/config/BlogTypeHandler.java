package cn.edu.buaa.se.bhac.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;

public class BlogTypeHandler extends BaseTypeHandler<String>
{  // 指定字符集
    private static final String DEFAULT_CHARSET = "utf-8";
    
    @Override
    public void setNonNullParameter (PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException
    {
        ByteArrayInputStream bis;
        try
        {
            // 把String转化成byte流
            bis = new ByteArrayInputStream(parameter.getBytes(DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("Blob Encoding Error!");
        }
        ps.setBinaryStream(i, bis, parameter.length());
    }
    
    @Override
    public String getNullableResult (ResultSet rs, String columnName) throws SQLException
    {
        return getResult(rs.getBlob(columnName));
    }
    
    @Override
    public String getNullableResult (CallableStatement cs, int columnIndex) throws SQLException
    {
        return getResult(cs.getBlob(columnIndex));
    }
    
    @Override
    public String getNullableResult (ResultSet rs, int columnName) throws SQLException
    {
        return getResult(rs.getBlob(columnName));
        
    }
    
    private String getResult (Blob blob) throws SQLException
    {
        byte[] returnValue = null;
        if (null != blob)
        {
            returnValue = blob.getBytes(1, (int) blob.length());
        }
        try
        {
            // 把byte转化成string
            if (null != returnValue)
            {
                return new String(returnValue, DEFAULT_CHARSET);
            }
        } catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("Blob Encoding Error!");
        }
        return null;
    }
}
