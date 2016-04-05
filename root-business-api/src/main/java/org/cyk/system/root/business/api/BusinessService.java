package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.computation.ExecutionProgress;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface BusinessService {

	@Getter @Setter @NoArgsConstructor
	public static class BusinessServiceCallArguments<OBJECT> implements Serializable{
		private static final long serialVersionUID = 7151479991050865862L;
		
		private Collection<OBJECT> objects;
		protected ExecutionProgress executionProgress;
		
		public BusinessServiceCallArguments(Collection<OBJECT> objects){
			setObjects(objects);
		}
		
		public void setObjects(Collection<OBJECT> objects){
			this.objects = objects;
			if(this.objects != null){
				if(executionProgress!=null)
					executionProgress.setTotalAmountOfWorkUsing100AsBase(new Double(this.objects.size()));
			}
		}
		
	}
	
}
