package org.smart4j.framework.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter02.helper.DatabaseHelper;
import org.smart4j.framework.utils.CollectionUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class DataBaseHelper {
    private static Logger LOGGER = LoggerFactory.getLogger(DataBaseHelper.class);
    private static ThreadLocal<Connection> CONNECTION_HOLDER;
    private static QueryRunner QUERY_RUNNER;
    private static BasicDataSource dataSource;

    static{
        CONNECTION_HOLDER = new ThreadLocal<Connection>();
        QUERY_RUNNER = new QueryRunner();

        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(ConfigHelper.getJdbcDriver());
        dataSource.setUrl(ConfigHelper.getJdbcUrl());
        dataSource.setUsername(ConfigHelper.getJdbcUsername());
        dataSource.setPassword(ConfigHelper.getJdbcPassword());
    }

    /**
     * Get Database Connection
     *@author Garwen
     *@date 2019/12/10 20:53
     *@params []
     *@return java.sql.Connection
     */
    public static Connection getConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if(conn == null){
            try {
                conn = dataSource.getConnection();
                CONNECTION_HOLDER.set(conn);
            } catch (SQLException e) {
                LOGGER.error("Get Connection Failure ", e);
            }
        }
        return conn;
    }

    /**
     * Query EntityList
     *@author Garwen
     *@date 2019/12/10 21:00
     *@params [entityClass, sql, params]
     *@return java.util.List<T>
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params){
        Connection conn = getConnection();
        List<T> result;
        try {
            result = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("Query List Failure ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Query Entity
     *@author Garwen
     *@date 2019/12/10 21:08
     *@params [entityClass, sql, params]
     *@return T
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params){
        Connection conn = getConnection();
        T value;
        try {
            value = QUERY_RUNNER.query(conn, sql,new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("Query Entity Failure ", e);
            throw new RuntimeException(e);
        }
        return value;
    }

    /**
     * 执行查询语句
     *@author Garwen
     *@date 2019/11/28 21:16
     *@params [sql, params]
     *@return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@throws
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params){
        List<Map<String, Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("QUERY EXECUTED FAILURE ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 执行更新语句
     *@author Garwen
     *@date 2019/11/28 21:28
     *@params [sql, params]
     *@return int
     *@throws
     */
    public static int executeUpdate(String sql, Object... params){
        int rows;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            LOGGER.error("Execute update failure ", e);
            throw new RuntimeException(e);
        }
        return rows;
    }

    /**
     * 插入实体
     *@author Garwen
     *@date 2019/11/28 22:00
     *@params [entityClass, filedMap]
     *@return boolean
     *@throws
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> filedMap){
        if(CollectionUtil.isEmpty(filedMap)){
            LOGGER.error("can not insert entity: fieldMap is empty.");
            return false;
        }

        String sql = "INSERT INTO " + getTableName(entityClass) +" ";
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for(String item:filedMap.keySet()){
            columns.append(item).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), columns.length(), ")");
        sql += columns + " VALUES " + values;

        Object[] params=filedMap.values().toArray();
        return 1==executeUpdate(sql, params);
    }

    /**
     * 更新实体
     *@author Garwen
     *@date 2019/11/28 22:29
     *@params [entityClass, id, fieldMap]
     *@return boolean
     *@throws
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("Update Entity failure: fieldMap is Empty.");
            return false;
        }

        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuilder columns = new StringBuilder();
        for(String item : fieldMap.keySet()){
            columns.append(item).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + "WHERE id=?";
        List<Object> paramList = new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        return executeUpdate(sql, paramList.toArray())==1;
    }

    /**
     *删除实体
     *@author Garwen
     *@date 2019/11/28 22:37
     *@params [entityClass, id]
     *@return boolean
     *@throws
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id){
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id=?";
        return 1== executeUpdate(sql, id);
    }

    /**
     * 获取实体表名
     *@author Garwen
     *@date 2019/11/28 21:39
     *@params [entityClass]
     *@return java.lang.String
     *@throws
     */
    private static String getTableName(Class<?> entityClass){
        return entityClass.getSimpleName().toLowerCase();
    }

    /**
     *执行sql文件
     *@creator Garwen
     *@date 2019-11-29 10:36
     *@param filepath
     *@return void
     *@throws
     */
    public static void executeSqlfile(String filepath){
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String sql;
            while((sql=reader.readLine())!=null){
                DatabaseHelper.executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("Execute sql failure ", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 开启事务
     *@author Garwen
     *@date 2019/12/10 21:18
     *@params []
     *@return void
     */
    public static void beginTransaction(){
        Connection conn = getConnection();
        if (conn!=null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("Set Transaction Failure ", e);
                throw new RuntimeException(e);
            }finally{
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     *@author Garwen
     *@date 2019/12/10 21:22
     *@params []
     *@return void
     */
    public static void commitTransaction(){
        Connection conn = getConnection();
        if (conn!=null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("Transaction commit Failure ", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 回滚事务
     *@author Garwen
     *@date 2019/12/10 21:34
     *@params []
     *@return void
     */
    public static void rollbackTransaction(){
        Connection conn = getConnection();
        if(conn!=null){
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("Rollback failure ", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

}
