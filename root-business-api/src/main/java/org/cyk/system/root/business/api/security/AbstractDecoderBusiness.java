package org.cyk.system.root.business.api.security;

import java.awt.image.BufferedImage;

public interface AbstractDecoderBusiness<SOURCE,DESTINATION,OPTIONS> extends AbstractEncoderDecoderBusiness<SOURCE,DESTINATION,OPTIONS> {

	DESTINATION execute(BufferedImage data,OPTIONS options);

	DESTINATION execute(BufferedImage data);

}
