package org.cyk.system.root.model.information;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @Entity @NoArgsConstructor
public class Comment extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 8167875049554197503L;

	@ManyToOne private CommentCollection collection;
	
	@Column(length=1024 * 1)
	private String message;
	
	private Date date;
}
