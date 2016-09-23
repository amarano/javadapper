package Reflection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Angelo on 9/6/2016.
 */
public interface ResultSetMapper<T> {
    T fromRow(ResultSet set, Class<T> c) throws SQLException;
}
