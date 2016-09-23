package Reflection;

/**
 * Created by Angelo on 9/6/2016.
 */
public interface ResultSetMappingBuilder {
    ResultSetMapping build();

    ResultSetMappingBuilder addMapping(String columnName, String fieldName);
}
