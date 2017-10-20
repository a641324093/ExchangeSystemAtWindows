/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author myy
 */
public class ResultSetUtils {

    /**
     * 得到结果集中所有记录的ID
     * @param rs
     * @param col_name
     * @return
     * @throws SQLException 
     */
    public static LinkedList getIDFromResultSet(ResultSet rs,String col_name) throws SQLException
    {
        LinkedList<Integer>list = new LinkedList<Integer>();
        while(rs.next())
        {
            Integer id =rs.getInt(col_name);
            list.add(id);
        }
        return list;
    }
    /**
     * 选出两个INT列表中共同拥有的Int
     * @param rs1
     * @param rs2
     * @param common_col
     * @return 若都为空则返回空，有一个为空，则返回另一个
     */
    public static LinkedList getCommonId(LinkedList<Integer>list1,LinkedList<Integer>list2) {
        if(list1==null&&list2!=null)
        {
            return list2;
        }
        if(list1!=null&&list2==null)
        {
            return list1;
        }
        if(list1==null&&list2==null)
        {
            return null;
        }
        LinkedList<Integer>list = new LinkedList<Integer>();
        for(Integer id1:list1)
        {
            for(Integer id2:list2)
            {
                if(id1.equals(id2))
                {
                    Integer id = Integer.valueOf(id1);
                    list.add(id);
                    break;
                }
            }
        }
        return list;
    }
}
