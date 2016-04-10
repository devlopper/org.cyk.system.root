package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.cyk.utility.common.computation.ExecutionProgress;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface BusinessService {

	@Getter @Setter @NoArgsConstructor
	public static class BusinessServiceCallArguments<OBJECT> implements Serializable{
		private static final long serialVersionUID = 7151479991050865862L;
		
		private Collection<OBJECT> objects;
		private Set<String> previousServiceIdentifiers;
		protected ExecutionProgress executionProgress;
		
		public BusinessServiceCallArguments(Collection<OBJECT> objects){
			setObjects(objects);
		}
		
		public void setObjects(Collection<OBJECT> objects){
			this.objects = objects;
			if(this.objects != null){
				if(executionProgress!=null){
					executionProgress.setTotalAmountOfWorkUsing100AsBase(new Double(this.objects.size()));
					executionProgress.clear();
				}
			}
		}
		
		public Set<String> getPreviousServiceIdentifiers(){
			if(previousServiceIdentifiers==null)
				previousServiceIdentifiers = new LinkedHashSet<>();
			return previousServiceIdentifiers;
		}
		
		public void addPreviousServiceIdentifiers(String...identifiers){
			if(identifiers!=null)
				for(String identifier : identifiers)
					getPreviousServiceIdentifiers().add(identifier);
		}
		
		public Boolean containPreviousServiceIdentifier(String identifier){
			return getPreviousServiceIdentifiers().contains(identifier);
		}
		
	}
	
}
