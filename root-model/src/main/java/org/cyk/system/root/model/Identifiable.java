package org.cyk.system.root.model;

/**
 * Any object that can be identifiable by a unique identifier.
 * @author Christian Yao Komenan
 * @param <IDENTIFIER>
 */
public interface Identifiable<IDENTIFIER> {

    /**
     * Get the identifier of the object.
     * @return Identifier of the object.
     */
    IDENTIFIER getIdentifier();
    
    void setIdentifier(IDENTIFIER anIdentifier);

    String getUiString();
}
