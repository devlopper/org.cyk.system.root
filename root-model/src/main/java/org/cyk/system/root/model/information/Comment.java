package org.cyk.system.root.model.information;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;

@Getter @Setter @Entity @NoArgsConstructor
public class Comment extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 8167875049554197503L;

	@ManyToOne private CommentCollection collection;
	
	@ManyToOne private CommentType type;
	
	@Column(length=1024 * 1) private String message;
	
	@Column(name="thedate") @Temporal(TemporalType.TIMESTAMP) private Date date;
	
	@ManyToOne private Person person;
	
}
