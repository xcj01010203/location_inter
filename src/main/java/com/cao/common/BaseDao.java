package com.cao.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * 数据访问层基类
 */
public class BaseDao<T> {

    private final String TABLE_NAME_FIELD = "TABLE_NAME";
    private final String ID_FIELD = "id";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * 查询方法
     *
     * @param sql  标准sql
     * @param args object参数数组
     * @param clz  返回list泛型定义class
     * @return
     */
    public List<T> query(String sql, Object[] args, Class<T> clz) {

        RowMapper<T> rm = BeanPropertyRowMapper.newInstance(clz);
        List<T> list;
        if (args == null) {
            list = this.getJdbcTemplate().query(sql, rm);
        } else {
            list = this.getJdbcTemplate().query(sql, args, rm);
        }

        return list;
    }

    /**
     * 带分页的查询
     *
     * @param querySql 查询语句
     * @param args     参数
     * @param clz      返回的实体类
     * @param page     分页条件
     * @return
     */
    public Page query(String querySql, Object[] args, Page page, Class<T> clz) {
        int totalRows = this.getResultCount(querySql, args);

        if (page != null) {
            querySql += "limit " + page.getOffset() + "," + page.getPageSize();
        }

        RowMapper<T> rm = BeanPropertyRowMapper.newInstance(clz);
        List<T> list;
        if (args == null) {
            list = this.getJdbcTemplate().query(querySql, rm);
        } else {
            list = this.getJdbcTemplate().query(querySql, args, rm);
        }

        page.setResult(list);
        page.setTotalRows(totalRows);

        return page;
    }

    /**
     * 获取单个对象
     *
     * @param sql
     * @param params
     * @param classT
     * @return
     * @throws Exception
     */
    public T queryForObject(String sql, Object[] params, Class<T> classT) throws Exception {
        RowMapper<T> rm = BeanPropertyRowMapper.newInstance(classT);
        T t = null;
        if (this.getResultCount(sql, params) == 1) {
            t = this.jdbcTemplate.queryForObject(sql, params, rm);
        }
        return t;

    }

    /**
     * 获取查询结果数量
     *
     * @param sql
     * @param args
     * @return
     */
    public int getResultCount(String sql, Object[] args) {
        sql = "select count(*) from (" + sql + ") res";
        return this.jdbcTemplate.queryForObject(sql, args, Integer.class);

    }

    /**
     * 获取get方法，例如getName()
     */
    private String genGetMethodName(String fieldName) {
        byte[] fieldNameItems = fieldName.getBytes();
        fieldNameItems[0] = (byte) ((char) fieldNameItems[0] - 'a' + 'A');
        String methodName = "get" + new String(fieldNameItems);
        return methodName;
    }

    /**
     * 自动insert方法 obj必须为一个与数据库表相对应的一个实体类并且字段名也必须和数据库的字段名相同。
     * 实体类中必须有一个名为TABLE_NAME属性，并赋值为相对应的表名。
     * 例如:<code>public static String TABLE_NAME="fieldName";</code>
     * 如果id属性为空，则系统会自动赋值
     *
     * @param obj
     * @return 影响的行数
     */
    public int addOne(Object obj) throws Exception {
        if (obj == null) {
            return 0;
        }

        Class<?> clz = obj.getClass();

        // 获取实体类的所有属性，返回Field数组
        Field fieldTableName = clz.getField(this.TABLE_NAME_FIELD);
        if (fieldTableName == null) {
            return 0;
        }

        String tableName = (String) fieldTableName.get(obj);

        //拼接sql语句
        Field[] fields = clz.getDeclaredFields();
        StringBuffer insertSql = new StringBuffer();
        insertSql.append("insert into ");
        insertSql.append(tableName);
        insertSql.append(" (");

        List<Object> list = new ArrayList<Object>();

        for (Field field : fields) {

            if (this.TABLE_NAME_FIELD.equals(field.getName()) || field.getName().startsWith("E_")) {
                continue;
            }

            Method m = obj.getClass().getMethod(this.genGetMethodName(field.getName()));

            Object val = m.invoke(obj);

            //TODO 为id赋默认值
            if (this.ID_FIELD.equals(field.getName()) && val == null) {
//                val = "uuid";
                continue;
            }

            insertSql.append(field.getName());
            insertSql.append(",");
            list.add(val);
        }
        insertSql = new StringBuffer(insertSql.substring(0, insertSql.length() - 1));
        insertSql.append(" ) values (");

        Object[] objArray = list.toArray();
        for (int i = 0; i < list.size(); i++) {
            insertSql.append(" ?,");
        }
        insertSql = new StringBuffer(insertSql.substring(0, insertSql.length() - 1));
        insertSql.append(" ) ");

        return this.jdbcTemplate.update(insertSql.toString(), objArray);

    }

    /**
     * 批量新增
     * 实体类必须与数据库对应的表字段在个数和名称上完全相同
     * 实体类中必须有一个名为TABLE_NAME属性，并赋值为相对应的表名
     * 例如:<code>public static String TABLE_NAME="fieldName";</code>
     *
     * @param objList
     * @return
     * @throws Exception
     * @throws
     */
    public synchronized void addBatch(List<T> objList, Class<T> clz) throws Exception {
        //获取对应的数据库表名
        Field fieldTableName = clz.getField(this.TABLE_NAME_FIELD);
        String tableName = (String) fieldTableName.get(objList);

        //拼接sql语句
        Field[] fields = clz.getDeclaredFields();
        StringBuffer insertSql = new StringBuffer();
        insertSql.append("insert into ");
        insertSql.append(tableName);
        insertSql.append(" (");

        List<String> fieldNameList = new ArrayList<String>();

        for (Field field : fields) {
            if (this.TABLE_NAME_FIELD.equals(field.getName())) {
                continue;
            }
            insertSql.append(field.getName());
            insertSql.append(",");
            fieldNameList.add(field.getName());
        }

        insertSql = new StringBuffer(insertSql.substring(0, insertSql.length() - 1));
        insertSql.append(" ) values (");
        for (int i = 0; i < fieldNameList.size(); i++) {
            insertSql.append(" ?,");
        }

        insertSql = new StringBuffer(insertSql.substring(0, insertSql.length() - 1));
        insertSql.append(" ) ");


        //拼接要插入的值
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (T obj : objList) {
            Class<?> myClz = obj.getClass();
            Field[] myFields = myClz.getDeclaredFields();

            Object[] args = new Object[myFields.length - 1];    //把每个类中TABLE_NAME字段排除

            int index = 0;    //自定义一个值的数组下标，为了防止一下循环中continue情况的产生
            for (int i = 0; i < myFields.length; i++) {
                Field myField = myFields[i];
                if (this.TABLE_NAME_FIELD.equals(myField.getName()) || myField.getName().startsWith("E_")) {
                    continue;
                }

                Method m = myClz.getMethod(this.genGetMethodName(myField.getName()));

                Object val = m.invoke(obj);

                //TODO 为id赋默认值
                if (this.ID_FIELD.equals(myField.getName()) && val == null) {
//                    val = "uuid";
                }
                args[index] = val;
                index++;
            }

            batchArgs.add(args);
        }

        //调用批量新增方法
        this.jdbcTemplate.batchUpdate(insertSql.toString(), batchArgs);
    }

    /**
     * 自动update方法 obj必须为一个与数据库表相对应的一个实体类并且字段名也必须和数据库的字段名相同。
     * 实体类中必须有一个名为tableName属性，并赋值为相对应的表名，
     * 例如:<code>public static String TABLE_NAME="fieldName";</code>
     * 数据库和对应的对象中的逐渐名统一使用"id"
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public int updateOne(Object obj) throws Exception {

        if (null == obj) {
            return 0;
        }

        // 拿到该类
        Class<?> clz = obj.getClass();
        Field fieldTableName = clz.getField(this.TABLE_NAME_FIELD);
        String tableName = (String) fieldTableName.get(obj);

        // 获取实体类的所有属性，返回Field数组
        Field[] fields = clz.getDeclaredFields();
        StringBuffer updateSql = new StringBuffer();
        updateSql.append(" update ");
        updateSql.append(tableName);
        updateSql.append(" set ");

        List<Object> list = new ArrayList<Object>();

        for (Field field : fields) {

            if (this.TABLE_NAME_FIELD.equals(field.getName()) || field.getName().startsWith("E_")) {
                continue;
            }

            Method m = obj.getClass().getMethod(this.genGetMethodName(field.getName()));

            Object val = m.invoke(obj);
            updateSql.append(field.getName());
            updateSql.append(" = ?,");
            list.add(val);

        }
        updateSql = new StringBuffer(updateSql.substring(0, updateSql.length() - 1));
        updateSql.append("  where id = ?");
        list.add(obj.getClass().getMethod(this.genGetMethodName("id")).invoke(obj));
        Object[] objArray = list.toArray();

        return this.jdbcTemplate.update(updateSql.toString(), objArray);
    }

    /**
     * 批量更新
     * 实体类必须与数据库对应的表字段在个数和名称上完全相同
     * 实体类中必须有一个名为TABLE_NAME属性，并赋值为相对应的表名
     * 例如:<code>public static String TABLE_NAME="fieldName";</code>
     *
     * @param objList
     * @param clz
     * @return
     * @throws Exception
     */
    public void updateBatch(final List<T> objList, Class<T> clz) throws Exception {
        //获取对应的数据库表名
        Field fieldTableName = clz.getField(this.TABLE_NAME_FIELD);
        String tableName = (String) fieldTableName.get(objList);

        //拼接sql语句
        Field[] fields = clz.getDeclaredFields();
        StringBuffer updateSql = new StringBuffer();
        updateSql.append(" update ");
        updateSql.append(tableName);
        updateSql.append(" set ");


        for (Field field : fields) {
            if (this.TABLE_NAME_FIELD.equals(field.getName())) {
                continue;
            }
            updateSql.append(field.getName());
            updateSql.append(" = ?,");
        }

        updateSql = new StringBuffer(updateSql.substring(0, updateSql.length() - 1));
        updateSql.append(" where id = ? ");

        //拼接值
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (T obj : objList) {
            Class<?> myClz = obj.getClass();
            Field[] myFields = myClz.getDeclaredFields();

            Object[] args = new Object[myFields.length];

            int index = 0;    //自定义一个值的数组下标，为了防止以下循环中continue情况的产生
            for (int i = 0; i < myFields.length; i++) {
                Field myField = myFields[i];
                if (this.TABLE_NAME_FIELD.equals(myField.getName()) || myField.getName().startsWith("E_")) {
                    continue;
                }

                Method m = myClz.getMethod(this.genGetMethodName(myField.getName()));

                Object val = m.invoke(obj);
                args[index] = val;
                index++;
            }

            Method getIdMethod = myClz.getMethod(this.genGetMethodName("id"));
            Object idObj = getIdMethod.invoke(obj);

            args[myFields.length - 1] = idObj;

            batchArgs.add(args);
        }

        //调用批量更新方法
        this.jdbcTemplate.batchUpdate(updateSql.toString(), batchArgs);
    }

}
