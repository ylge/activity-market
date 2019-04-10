package com.geyl.util;




import com.geyl.bean.model.system.SysMenu;

import java.util.Comparator;

/**
 * Created by Mr.Yangxiufeng on 2017/8/8.
 * Time:9:49
 * ProjectName:Common-admin
 * <p>升序</p>
 */
public class MenuComparator implements Comparator<SysMenu> {
    @Override
    public int compare(SysMenu o1, SysMenu o2) {
        if (o1 == null && o2 == null){
            return 0;
        }
        if (o1 != null && o2 == null){
            return -1;
        }
        if (o1 == null && o2 != null){
            return 1;
        }
        if (o1.getSort().compareTo(o2.getSort())>0){
            return 1;
        }
        return -1;
    }
}
