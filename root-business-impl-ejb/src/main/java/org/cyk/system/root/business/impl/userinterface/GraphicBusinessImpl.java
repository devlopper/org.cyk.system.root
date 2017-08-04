package org.cyk.system.root.business.impl.userinterface;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import org.cyk.system.root.business.api.userinterface.GraphicBusiness;

public class GraphicBusinessImpl implements GraphicBusiness, Serializable {

	private static final long serialVersionUID = 4935957746828199499L;

	@Override
	public byte[] createFromText(String text,CreateFromTextOptions options) {
		/*
		 * Because font metrics is based on a graphics context, we need to
		 * create a small, temporary image so we can ascertain the width and
		 * height of the final image
		 */
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		Font font = options.getFont(); 
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		int width = fm.stringWidth(text);
		int height = fm.getHeight();
		g2d.dispose();

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING,RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_PURE);
		g2d.setFont(font);
		fm = g2d.getFontMetrics();
		g2d.setColor(options.getColor());
		//g2d.setBackground(Color.RED);
		g2d.drawString(text, 0, fm.getAscent());
		g2d.dispose();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, options.getType(), baos);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	@Override
	public byte[] createFromText(String text) {
		CreateFromTextOptions options = new CreateFromTextOptions();
		return createFromText(text, options);
	}

}
