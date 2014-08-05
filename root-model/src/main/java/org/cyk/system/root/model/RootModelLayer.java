package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.annotation.ModelLayer;

@ModelLayer @Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class RootModelLayer extends AbstractModelLayer implements Serializable {

    private static RootModelLayer INSTANCE;
    
    private AbstractMethod<String, Object[]> i18nMethod;
    
    @Override
    protected void initialisation() {
        INSTANCE = this;
        super.initialisation();
    }
    
    public static RootModelLayer getInstance() {
        if(INSTANCE==null)
            INSTANCE = new RootModelLayer();
        return INSTANCE;
    }
    
    public String i18n(String id,Object...parameters){
        List<Object> c =  new ArrayList<>();
        c.add(id);
        if(parameters!=null)
            c.addAll(Arrays.asList(parameters));
       
        return i18nMethod==null?id:i18nMethod.execute(c.toArray());
    }
    
    public String i18n(Enum<?> anEnum){
        return i18n("enum."+anEnum.getClass().getSimpleName().toLowerCase()+"."+anEnum.name().toString().toLowerCase());
    }
    
}
