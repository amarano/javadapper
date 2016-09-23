package Reflection;

import java.lang.reflect.Method;

/**
 * Created by Angelo on 9/6/2016.
 */
public interface ResultSetMapping<T> {

    Method setterFor(String columnName, Class<T> c);
}
