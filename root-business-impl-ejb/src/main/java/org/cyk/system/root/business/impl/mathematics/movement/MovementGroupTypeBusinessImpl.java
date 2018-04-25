package org.cyk.system.root.business.impl.mathematics.movement;
import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.movement.MovementGroupTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.mathematics.movement.MovementGroupType;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementGroupTypeDao;
 
public class MovementGroupTypeBusinessImpl extends AbstractDataTreeTypeBusinessImpl<MovementGroupType,MovementGroupTypeDao> implements MovementGroupTypeBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public MovementGroupTypeBusinessImpl(MovementGroupTypeDao dao) {
        super(dao);
    } 
	
	/**/
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeTypeBusinessImpl.BuilderOneDimensionArray<MovementGroupType> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(MovementGroupType.class);
			
		}
		
	}
	
}
