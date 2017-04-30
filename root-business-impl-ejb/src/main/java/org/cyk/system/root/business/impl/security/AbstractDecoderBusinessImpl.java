package org.cyk.system.root.business.impl.security;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import org.cyk.system.root.business.api.security.AbstractDecoderBusiness;

public abstract class AbstractDecoderBusinessImpl<SOURCE,DESTINATION,OPTIONS> extends AbstractEncoderDecoderBusinessImpl<SOURCE,DESTINATION,OPTIONS> implements AbstractDecoderBusiness<SOURCE,DESTINATION,OPTIONS>,Serializable {

	private static final long serialVersionUID = -4553708311085832491L;

	@Override
	public DESTINATION execute(BufferedImage data) {
		return execute(data, defaultOptions());
	}
	
}
