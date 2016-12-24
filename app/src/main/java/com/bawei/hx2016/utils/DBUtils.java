package com.bawei.hx2016.utils;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * @author :   郗琛
 * @date :   2016/12/24
 */

public class DBUtils {
    private static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()    //本地数据的初始化
            .setDbName("friend") //设置数据库名
            .setDbVersion(1) //设置数据库版本,每次启动应用时将会检查该版本号,
            //发现数据库版本低于这里设置的值将进行数据库升级并触发DbUpgradeListener
            .setAllowTransaction(true)//设置是否开启事务,默认为false关闭事务
            //.setDbDir(null);//设置数据库.db文件存放的目录,默认为包名下databases目录下
            ;


    /**
     * dbManager唯一对象
     */
    private static DbManager db = x.getDb(daoConfig);

    private DBUtils() {
    }

    /**
     * 获取dbUtils实例
     *
     * @return
     */
    public static DbManager getDbUtilsInstance() {
        return db;
    }
}
