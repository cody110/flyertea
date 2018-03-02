package com.ideal.flyerteacafes.dal;

import com.ideal.flyerteacafes.model.entity.Userinfo;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;

import org.xutils.DbManager;
import org.xutils.db.Selector;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.List;

public class BaseHelper {
    public DbManager dbUtils;
    private static BaseHelper helper;

    public BaseHelper() {
        dbUtils = XutilsHelp.getDbUtils();
    }


    public static BaseHelper getInstance() {
        if (helper == null)
            helper = new BaseHelper();
        return helper;
    }

    public DbManager getDbUtils() {
        return dbUtils;
    }


    public <T> void saveListAll(List<?> list, Class<T> cls) {
        try {
            if (cls != null)
                deleteAll(cls);
            if (!DataUtils.isEmpty(list))
                dbUtils.save(list);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public <T> void saveBean(T bean) {
        try {
            if (bean != null)
                dbUtils.save(bean);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogFly.e(e.toString());
        }
    }

    public <T> List<T> getListAll(Class<T> cls) {
        List<T> list = null;
        try {
            list = dbUtils.findAll(cls);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    public <T> List<T> getList(Class<T> cls, String columnName, String op, Object value) {
        List<T> list = null;
        try {
            WhereBuilder wb = WhereBuilder.b();
            wb.and(columnName, op, value);
            list = dbUtils.selector(cls).where(wb).findAll();
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    public <T> List<T> getListLimit(Class<T> cls, String columnName, String op, Object value, int limit) {
        List<T> list = null;
        try {
            WhereBuilder wb = WhereBuilder.b();
            wb.and(columnName, op, value);
            list = dbUtils.selector(cls).limit(limit).where(wb).findAll();
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }


    public <T> T getBeanByFirst(Class<T> cls, String columnName, String op, Object value) {
        T bean = null;
        try {
            WhereBuilder wb = WhereBuilder.b();
            wb.and(columnName, op, value);
            bean = dbUtils.selector(cls).where(wb).findFirst();
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bean;
    }

    public <T> T getBeanByFirst(Class<T> cls) {
        T bean = null;
        try {
            bean = dbUtils.findFirst(cls);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bean;
    }


    public <T> void deleteAll(Class<T> cls) {
        try {
            dbUtils.delete(cls);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public <T> void delete(Class<T> cls, String columnName, String op, Object value) {
        try {
            dbUtils.delete(cls, WhereBuilder.b(columnName, op, value));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public <T> int getCount(Class<T> cls) {
        int count = 0;
        try {
            count = (int) dbUtils.selector(cls).count();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return count;
    }

}
