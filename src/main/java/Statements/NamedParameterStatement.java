package Statements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Angelo on 8/30/2016.
 */
public class NamedParameterStatement {

    private PreparedStatement preparedStatement;
    private HashMap<String, Integer> parameterIndexes;

    private String parameterToken;

    public NamedParameterStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
        this.parameterToken = ":";
    }

    public NamedParameterStatement(PreparedStatement preparedStatement, String parameterToken) {
        this.preparedStatement = preparedStatement;
        this.parameterToken = parameterToken;
    }

    private void setIndexes() {
        String rawQuery = this.preparedStatement.toString();
        String pattern = String.format("(?!\\B'[^']*)(%s\\w+)(?![^']*'\\B)", this.parameterToken);
        Matcher m = Pattern.compile(pattern).matcher(rawQuery);
        int index = 0;
        while (m.find()) {
            parameterIndexes.put(m.group(), index);
            index++;
        }
    }

    public ResultSet executeQuery() throws SQLException {
        return this.preparedStatement.executeQuery();
    }

    public <T> void addParameter(String name, T value) {
        if (!this.parameterIndexes.containsKey(name)) {
            throw new IllegalArgumentException("name");
        }

        int index = this.parameterIndexes.get(name);
        try {
            if (value instanceof String)
                this.preparedStatement.setString(index, (String) value);
            if (value instanceof Integer) {
                this.preparedStatement.setInt(index, (Integer) value);
            }
        } catch (SQLException ex) {

        }
    }
}
