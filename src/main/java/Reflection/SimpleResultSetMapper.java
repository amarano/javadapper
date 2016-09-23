package Reflection;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by Angelo on 9/6/2016.
 */
public class SimpleResultSetMapper<T> implements ResultSetMapper<T> {

    private final ResultSetMapping<T> mapping;
    private String[] columns;

    public SimpleResultSetMapper(ResultSetMapping<T> mapping) {
        this.mapping = mapping;
    }


    void initialize(ResultSet set) throws SQLException {
        ResultSetMetaData metaData = set.getMetaData();
        columns = new String[metaData.getColumnCount()];
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            columns[i] = metaData.getColumnName(i);
        }
    }

    public T fromRow(ResultSet set, Class<T> c) throws SQLException {
        if (columns == null) {
            initialize(set);
        }

        try {
            T t = c.getConstructor(null).newInstance();
            for (int i = 0; i < columns.length; i++) {
                String columnName = columns[i];
                Method setter = mapping.setterFor(columnName, c);
                Object obj = set.getObject(i);
                setter.invoke(t, setter.getParameterTypes()[0].cast(obj));
                return t;
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }
}
