package org.hswebframework.ezorm.rdb.mapping;

import lombok.Getter;
import org.hswebframework.ezorm.rdb.metadata.RDBColumnMetadata;
import org.hswebframework.ezorm.rdb.metadata.TableOrViewMetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultEntityColumnMapping implements EntityColumnMapping {

    private Map<String, String> propertyColumnMapping = new HashMap<>();

    private Map<String, String> columnPropertyMapping = new HashMap<>();

    @Getter
    private String id;

    @Getter
    private String name;

    @Getter
    private TableOrViewMetadata table;

    public void addMapping(String column, String property) {
        columnPropertyMapping.put(column, property);
        propertyColumnMapping.put(property, column);
    }

    public DefaultEntityColumnMapping(TableOrViewMetadata table, Class entityType) {
        this.id = getType().createFeatureId(entityType);
        this.name = getType().getName() + ":" + entityType.getSimpleName();
        this.table = table;
    }

    @Override
    public Optional<RDBColumnMetadata> getColumnByProperty(String property) {
        return Optional
                .ofNullable(propertyColumnMapping.get(property))
                .flatMap(table::findColumn);
    }

    @Override
    public Optional<String> getPropertyByColumnName(String columnName) {
        return Optional
                .ofNullable(columnPropertyMapping.get(columnName));
    }

    @Override
    public Optional<RDBColumnMetadata> getColumnByName(String columnName) {
        return table.findColumn(columnName);
    }

    @Override
    public Map<String, String> getColumnPropertyMapping() {
        return new HashMap<>(columnPropertyMapping);
    }
}
