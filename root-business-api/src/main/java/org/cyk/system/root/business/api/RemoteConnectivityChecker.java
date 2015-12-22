package org.cyk.system.root.business.api;


public interface RemoteConnectivityChecker extends BusinessService {

    void echo(String message);
    
    String getDate();
    
    
    
}
