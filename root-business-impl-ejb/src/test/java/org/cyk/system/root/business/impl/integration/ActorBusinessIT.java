package org.cyk.system.root.business.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.ActorBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.model.Actor;

public class ActorBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    private static final String CODE = "1"; 
    
    @Inject private ActorBusiness actorBusiness;
    
    @Override
    protected void businesses() {
    	
    	Actor actor = actorBusiness.instanciateOneRandomly();
    	actor.setCode(CODE);
    	actorBusiness.create(actor);
        assertThat("Actor created", actor.getIdentifier()!=null);
    	
        actor = actorBusiness.find(CODE);
        assertThat("Actor found by global identifier code", actor!=null);
        
        Long actorCount = actorBusiness.countAll(),fileCount = inject(FileBusiness.class).countAll();
    	actorBusiness.delete(actor);
    	assertThat("Actor count", actorBusiness.countAll()==actorCount-1);
    	assertThat("File count", inject(FileBusiness.class).countAll()==fileCount-1);
    }

}
