package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeBusinessImpl;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.persistence.api.geography.LocalityDao;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;

import lombok.Getter;
import lombok.Setter;

public class LocalityBusinessImpl extends AbstractDataTreeBusinessImpl<Locality,LocalityDao,LocalityType> implements LocalityBusiness {
 
	private static final long serialVersionUID = 2801588592108008404L;

	@Inject
    public LocalityBusinessImpl(LocalityDao dao) {
        super(dao);
    } 
	
	public static class BuilderOneDimensionArray extends AbstractDataTreeBusinessImpl.BuilderOneDimensionArray<Locality> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Locality.class);
		}
		
	}
	
	@Getter @Setter
	public static class Details extends AbstractDataTreeBusinessImpl.Details<Locality,LocalityType> implements Serializable {

		private static final long serialVersionUID = -4747519269632371426L;

		@Input @InputText private String residentName;
		@IncludeInputs(layout=Layout.VERTICAL) private GlobalPositionDetails globalPosition = new GlobalPositionDetails();
		
		public Details(Locality locality) {
			super(locality);
		}
		
		@Override
		public void setMaster(Locality locality) {
			super.setMaster(locality);
			if(locality!=null){
				residentName = locality.getResidentName();
				if(globalPosition==null)
					globalPosition = new GlobalPositionDetails(locality.getGlobalPosition());
				else
					globalPosition.setMaster(locality.getGlobalPosition());
			}
		}
		
		public static final String FIELD_RESIDENT_NAME = "residentName";
		public static final String FIELD_GLOBAL_POSITION = "globalPosition";
	}

	
}
