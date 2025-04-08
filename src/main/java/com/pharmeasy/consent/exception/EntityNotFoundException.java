package com.pharmeasy.consent.exception;

/**
 * Exception thrown when a specific entity is not found.
 */
public class EntityNotFoundException
    extends RuntimeException {

    private final String entityName;
    private final Object entityId;

    /**
     * Constructs a new EntityNotFoundException with the specified entity name and identifier.
     *
     * @param entityName the name of the entity
     * @param entityId   the identifier of the entity
     */
    public EntityNotFoundException(String entityName, Object entityId) {
        super(String.format("%s with ID %s not found", entityName, entityId));
        this.entityName = entityName;
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public Object getEntityId() {
        return entityId;
    }
}
