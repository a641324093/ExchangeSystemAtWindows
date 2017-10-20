
package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author myy
 */
public class StringUtils {
    
    /**
     * 妫?娴嬪瓧绗︿覆鏄惁涓虹┖鎴杗ull
     * @param str
     * @return 鏄垯杩斿洖true
     */
    public static boolean isEmptyOrNull(String str)
    {
        if(str==null)
        {
            return true;
        }
        if(str.trim().equals(""))
        {
            return true;
        }
        return false;
    }
    
    public static boolean isTel(String msg)
    {
        if(msg==null)
        {
            return false;
        }
        Pattern p1 = Pattern.compile("^[0-9]{11}");
                Matcher m1 = p1.matcher(msg);
                if (m1.matches()) {
                    return true;
                } else {
                    return false;
                }
    }
    
    /**
     * 缩略字符串的长度，多出的用...代替
     * @param str
     * @param len
     * @return 
     */
    public static String fitString(String str,int len)
    {
        if(StringUtils.isEmptyOrNull(str))
        {
            return null;
        }
        int str_len = str.length();
        //字符串长度比要求的短
        if(str_len<len)
        {
            len=str_len;
        }
        str=str.substring(0, len);
        //字符串长度比要求长度长
        if(str_len>len)
        {
            str = str+"...";
        }
        return str;
    }
}
