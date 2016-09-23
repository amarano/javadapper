package Reflection;


import java.lang.annotation.Annotation;

/**
 * Created by Angelo on 9/6/2016.
 */
public class Column implements Annotation {

    private final String columnName;

    public Column(String columnName) {
        this.columnName = columnName;
    }

    public Class<? extends Annotation> annotationType() {
        return Column.class;
    }
}
