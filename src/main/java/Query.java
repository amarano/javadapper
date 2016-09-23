import Reflection.ResultSetMapper;
import Statements.NamedParameterStatement;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by Angelo on 8/30/2016.
 */
public class Query {

    public static <T> Stream<T> Query(Connection connection,
                                      final String sql,
                                      HashMap<String, Object> parameters,
                                      final ResultSetMapper<T> mapper,
                                      final Class<T> c) throws SQLException, InvalidArgumentException {

        PreparedStatement statement = connection.prepareStatement(sql);
        NamedParameterStatement namedParameterStatement = new NamedParameterStatement(statement);

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            namedParameterStatement.addParameter(entry.getKey(), entry.getValue());
        }

        try {
            final ResultSet resultSet = namedParameterStatement.executeQuery();


            Iterator<T> it = new Iterator<T>() {
                public boolean hasNext() {
                    try {
                        return resultSet.next();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                public T next() {
                    try {
                        return mapper.fromRow(resultSet, c);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };

            Iterable<T> iterable = () -> it;
            return StreamSupport.stream(iterable.spliterator(), false);

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return Stream.empty();
    }

}
