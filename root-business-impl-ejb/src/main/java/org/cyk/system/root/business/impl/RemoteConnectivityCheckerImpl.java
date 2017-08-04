package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.cyk.system.root.business.api.RemoteConnectivityChecker;

@Stateless(mappedName="RemoteConnectivityChecker") @Remote
public class RemoteConnectivityCheckerImpl extends AbstractBusinessServiceImpl implements RemoteConnectivityChecker ,Serializable {

	private static final long serialVersionUID = -4219622996262337807L;

	@Override
    public void echo(String message) {
		System.out.println("Echo to server : "+message);
    }

    @Override
    public String getDate() {
        return new Date().toString();
    }

}
