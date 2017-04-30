package org.cyk.system.root.persistence.api.security;

import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.security.UserAccountLock;
import org.cyk.system.root.persistence.api.TypedDao;

public interface UserAccountLockDao extends TypedDao<UserAccountLock> {

	UserAccountLock readUnlockDateIsNullByUserAccount(UserAccount aUserAccount);
 
}
