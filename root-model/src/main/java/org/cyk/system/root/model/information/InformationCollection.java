package org.cyk.system.root.model.information;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;

@Getter @Setter @Entity @NoArgsConstructor
public class InformationCollection extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 8167875049554197503L;

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private Collection<File> files = new HashSet<>();
    
}
