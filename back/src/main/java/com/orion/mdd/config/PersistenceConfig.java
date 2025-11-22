package com.orion.mdd.config;

import com.orion.mdd.model.BaseEntity;
import com.orion.mdd.model.Post;
import com.orion.mdd.model.Topic;
import com.orion.mdd.model.User;
import com.orion.mdd.security.model.RefreshToken;
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
    private final List<String> joinTableNames = List.of(User.SUBSCRIPTION_JOIN_TABLE_NAME);

    @Override
    public List<Column> orderTableColumns(Table table, Metadata metadata) {
        var toReturn = new ArrayList<Column>();

        if (joinTableNames.contains(table.getName())) {
            toReturn.addAll(table.getColumns());
        } else {
            toReturn.add(getColumn(table, BaseEntity.ID_COLUMN_NAME));
            switch (table.getName()) {
                case User.TABLE_NAME -> {
                    toReturn.add(getColumn(table, User.EMAIL_COLUMN_NAME));
                    toReturn.add(getColumn(table, User.USERNAME_COLUMN_NAME));
                    toReturn.add(getColumn(table, User.PASSWORD_COLUMN_NAME));
                }
                case Topic.TABLE_NAME -> {
                    toReturn.add(getColumn(table, Topic.TITLE_COLUMN_NAME));
                    toReturn.add(getColumn(table, Topic.DESCRIPTION_COLUMN_NAME));
                }
                case Post.TABLE_NAME -> {
                    toReturn.add(getColumn(table, Post.TOPIC_ID_COLUMN_NAME));
                    toReturn.add(getColumn(table, Post.TITLE_COLUMN_NAME));
                    toReturn.add(getColumn(table, Post.CONTENT_COLUMN_NAME));
                    toReturn.add(getColumn(table, Post.CREATED_AT_COLUMN_NAME));
                    toReturn.add(getColumn(table, Post.CREATED_BY_COLUMN_NAME));
                }
                case RefreshToken.TABLE_NAME -> {
                    toReturn.add(getColumn(table, RefreshToken.USER_ID_COLUMN_NAME));
                    toReturn.add(getColumn(table, RefreshToken.TOKEN_COLUMN_NAME));
                    toReturn.add(getColumn(table, RefreshToken.EXPIRY_DATE_COLUMN_NAME));
                }
            }
        }

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