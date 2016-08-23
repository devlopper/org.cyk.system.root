package org.cyk.system.root.business.api.geography;

import java.util.Collection;
import java.util.List;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeBusiness;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;

public interface LocalityBusiness extends AbstractDataTreeBusiness<Locality,LocalityType> {

	Collection<Locality> instanciateMany(LocalityType type,List<String[]> list);
 
}
