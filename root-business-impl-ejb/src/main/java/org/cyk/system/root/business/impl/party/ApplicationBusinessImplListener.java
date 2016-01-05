package org.cyk.system.root.business.impl.party;

import java.io.Serializable;

import org.cyk.system.root.model.security.Installation;

public interface ApplicationBusinessImplListener{
	
	void installationStarted(Installation installation);
	void installationEnded(Installation installation);
	
	/**/
	public static class Adapter implements ApplicationBusinessImplListener,Serializable {
		private static final long serialVersionUID = -2983067114876599661L;
		@Override public void installationStarted(Installation installation) {}
		@Override public void installationEnded(Installation installation) {}
		
		/**/
		public static class Default extends Adapter implements Serializable {
			private static final long serialVersionUID = -8533811278793391794L;
			
		}
	}
}