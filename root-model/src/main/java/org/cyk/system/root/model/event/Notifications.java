package org.cyk.system.root.model.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;

@Getter
public class Notifications implements Serializable {

	private static final long serialVersionUID = 5957875793044994714L;

	private final Collection<Notification> collection = new ArrayList<>();
	
	
}
