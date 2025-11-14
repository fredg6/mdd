package com.orion.mdd.config;

import com.orion.mdd.model.BaseEntity;
import com.orion.mdd.model.User;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.ColumnOrderingStrategyStandard;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class PersistenceConfig extends ColumnOrderingStrategyStandard implements HibernatePropertiesCustomizer {
    @Override
    public List<Column> orderTableColumns(Table table, Metadata metadata) {
        var toReturn = new ArrayList<Column>();

        toReturn.add(getColumn(table, BaseEntity.ID_COLUMN_NAME));
        switch (table.getName()) {
            case User.TABLE_NAME -> {
                toReturn.add(getColumn(table, User.EMAIL_COLUMN_NAME));
                toReturn.add(getColumn(table, User.USERNAME_COLUMN_NAME));
                toReturn.add(getColumn(table, User.PASSWORD_COLUMN_NAME));
            }
        }
        toReturn.addLast(getColumn(table, BaseEntity.CREATED_AT_COLUMN_NAME));
        toReturn.addLast(getColumn(table, BaseEntity.UPDATED_AT_COLUMN_NAME));

        return toReturn;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.COLUMN_ORDERING_STRATEGY, this);
    }

    private Column getColumn(Table table, String columnName) {
        return table.getColumn(Identifier.toIdentifier(columnName));
    }
}