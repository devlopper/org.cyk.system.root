package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.cyk.system.root.business.api.RemoteConnectivityChecker;

@Stateless(mappedName="RemoteConnectivityChecker") @Remote
public class RemoteConnectivityCheckerImpl implements RemoteConnectivityChecker ,Serializable {

    @Override
    public void echo(String message) {
        System.out.println("Echo : "+message);
    }

    @Override
    public String getDate() {
        return new Date().toString();
    }

}
