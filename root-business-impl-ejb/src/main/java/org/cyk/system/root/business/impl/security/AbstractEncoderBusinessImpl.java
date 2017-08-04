package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import org.cyk.system.root.business.api.security.AbstractEncoderBusiness;

public abstract class AbstractEncoderBusinessImpl<SOURCE,DESTINATION,OPTIONS> extends AbstractEncoderDecoderBusinessImpl<SOURCE,DESTINATION,OPTIONS> implements AbstractEncoderBusiness<SOURCE,DESTINATION,OPTIONS>,Serializable {

	private static final long serialVersionUID = -4553708311085832491L;

}
