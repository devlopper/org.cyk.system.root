package org.cyk.system.root.model.information;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @Entity @NoArgsConstructor
public class CommentCollection extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 8167875049554197503L;

}
