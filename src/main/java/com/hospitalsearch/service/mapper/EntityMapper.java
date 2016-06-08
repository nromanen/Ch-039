package com.hospitalsearch.service.mapper;

/**
 *
 * @param <Entity>
 * @param <Bean>
 */

public interface EntityMapper<Entity, Bean> {
    default Bean convertToBean(Entity entity) {
        return convertToBean(entity, null);
    }

    default Bean convertToBean(Entity entity, Bean bean) {
        return convertToBean(entity);
    }
}
