package utils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author myy
 */
public class DoubleUtils {
 
    /**
     * 检测一个字符串是否为小数(包括整数)
     * @param msg
     * @return 若为空或不为小数返回false;
     */
    public static boolean isDouble(String msg)
    {
        if(msg==null)
        {
            return false;
        }
        Pattern p1 = Pattern.compile("^([0-9]|[-])[0-9]*([0-9]*|[.][0-9]*)");
                Matcher m1 = p1.matcher(msg);
                if (m1.matches()) {
                    return true;
                } else {
                    return false;
                }
    }
    
    /**
     * 判断科学记数类型的小数
     * @param msg
     * @return 
     */
    public static boolean  isStrDouble(String msg)
    {
        if(msg==null)
        {
            return false;
        }
        String r[]=msg.split("E");
        if(r.length!=2)
        {
            return false;
        }
        if(isDouble(r[0])==true&&isDouble(r[1])==true)
        {
            return true;
         }
        return false;
    }
    /**
     * 
     * *注意排名一样的情况
     * 按前大后小的顺序排序
     * @param dous 需要排序的数组
     * @return 返回排序好的数组
     */
    public static Double[] sortDoubles(Double[]dous)
    {
        int loc;
       Double temp,max;
       if(dous==null)
       {
           return null;
       }
       //大在前，小在后
       for(int i=0;i<dous.length;i++)
       {
           if(dous[i]==null)
           {
               continue;
           }
           max=dous[i];
           loc=i;
           for(int j=i;j<dous.length;j++)
           {
               if(dous[j]==null)
               {
                   continue;
               }
               if(dous[j]>max)
               {
                   loc=j;
                   max=dous[j];
               }
           }
           temp=dous[i];
           dous[i]=dous[loc];
           dous[loc]=temp;
       }
       return dous;
    }
    
    /**
     * 得带某数值在已排序好数组中的排序位置
     *
     * @param dous
     * @param dou
     * @param style 如果为“0”，则按降序排名，即数值越大，排名结果数值越小
     * 如果为非“0”值，则按升序排名，即数值越大，排名结果数值越大
     * @return 排序位置，不存在返回-1
     */
    public static Double getSortIndex(Double[]dous,Double dou,int style)
    {
        if(dou==null)
       {
           return -1.0;
       }
       for(int i=0;i<dous.length;i++)
       {
           if(dou.equals(dous[i]))
           {
               if(style==0)
               {
                   return (double)(i+1);
               }
               else
               {
                   return (double)dous.length-i;
               }
           }
       }
       return -1.0;
    }
    
    /**
     * 计算一个小数列表所有数的平均值
     * @param list
     * @return 
     */
    public static Double   getAverageOfList(List<Double>list)
    {
        Double result=0.0;
        for(Double num:list)
        {
            result+=num;
        }
        result = result/list.size();
        return result;
    }
    
    /**
     * 格式化小数 #.00 设置显示小数后两位
     * @param num
     * @param str_format
     * @return 
     */
    public static Double formateDouble(Double num,String str_format)
    {
        DecimalFormat format = new DecimalFormat(str_format);
        String str_num =format.format(num);
        if(StringUtils.isEmptyOrNull(str_num)==true||DoubleUtils.isDouble(str_num)==false)
        {
            if(DoubleUtils.isStrDouble(str_num)==false)
            {
            return null;
            }
        }
        Double dou_num =Double.valueOf(str_num);
        return dou_num;
    }
}
