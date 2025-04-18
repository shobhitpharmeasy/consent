package com.pharmeasy.consent.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * Exception thrown when a specific entity is not found.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class EntityNotFoundException
    extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String entityName;
    private final Serializable entityId;

    /**
     * Constructs a new EntityNotFoundException with the specified entity name and identifier.
     *
     * @param entityName the name of the entity
     * @param entityId   the identifier of the entity
     */
    public EntityNotFoundException(String entityName, Serializable entityId) {
        super(String.format("%s with ID %s not found", entityName, entityId));
        this.entityName = entityName;
        this.entityId = entityId;
    }

}
