package org.cyk.system.root.business.impl.file.report.jasper;

import java.awt.Color;
import java.io.Serializable;

import org.cyk.system.root.business.api.file.report.JasperReportBasedOnDynamicBuilderListener;
import org.cyk.system.root.business.impl.file.report.AbstractReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.userinterface.style.Border.Side;
import org.cyk.utility.common.annotation.user.interfaces.style.Alignment.Horizontal;
import org.cyk.utility.common.annotation.user.interfaces.style.Alignment.Vertical;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

public abstract class AbstractJasperReportBasedOnDynamicBuilder extends AbstractReportBasedOnDynamicBuilder implements JasperReportBasedOnDynamicBuilderListener,Serializable {

	private static final long serialVersionUID = -6397313866653430863L;
	
	protected Style buildStyle(org.cyk.system.root.model.userinterface.style.Style style) {
		StyleBuilder styleBuilder=new StyleBuilder(true);
		Font font;
		switch (style.getFont().getName()) {
		case VERDANA:
			switch(style.getFont().getStyle()){
			case BOLD:font = Font.VERDANA_MEDIUM_BOLD;break;
			default:font = Font.VERDANA_MEDIUM_BOLD;
			}
			break;
		default:font = Font.VERDANA_MEDIUM_BOLD;break;
		}
		styleBuilder.setFont(font);
		
		if(style.getBorder().getAll()!=null && !style.getBorder().getAll().getStyle().equals(org.cyk.system.root.model.userinterface.style.Border.Side.Style.NONE)){
			Border border;
			switch (style.getBorder().getAll().getStyle()) {
			case DOTTED:border = Border.DOTTED();break;
			case SOLID:border = Border.PEN_1_POINT();break;
			default:border = Border.PEN_1_POINT();break;
			}
			styleBuilder.setBorder(border);
			styleBuilder.setBorderColor(style.getBorder().getAll().getColor().systemColor());
		}
		borderSide(styleBuilder,style.getBorder().getLeft(),0);
		borderSide(styleBuilder,style.getBorder().getTop(),1);
		borderSide(styleBuilder,style.getBorder().getRight(),2);
		borderSide(styleBuilder,style.getBorder().getBottom(),3);
		
		Color color = style.getBackground().getColor().systemColor();
		if(color==null){
			styleBuilder.setTransparency(Transparency.TRANSPARENT);
		}else{
			styleBuilder.setTransparency(Transparency.OPAQUE);
			styleBuilder.setBackgroundColor(color);
		}
		
		styleBuilder.setTextColor(style.getText().getColor().systemColor());
		Horizontal horizontal = style.getText().getAlignment().getHorizontal();
		if(Horizontal.AUTO.equals(horizontal))
			horizontal = Horizontal.LEFT;
		switch(horizontal){
		case LEFT:styleBuilder.setHorizontalAlign(HorizontalAlign.LEFT);break;
		case MIDDLE:styleBuilder.setHorizontalAlign(HorizontalAlign.CENTER);break;
		case RIGHT:styleBuilder.setHorizontalAlign(HorizontalAlign.RIGHT);break;
		case JUSTIFY:styleBuilder.setHorizontalAlign(HorizontalAlign.JUSTIFY);break;
		default:break;
		}
		
		Vertical vertical = style.getText().getAlignment().getVertical();
		if(Vertical.AUTO.equals(vertical))
			vertical = Vertical.TOP;
		switch(vertical){
		case TOP:styleBuilder.setVerticalAlign(VerticalAlign.TOP);break;
		case MIDDLE:styleBuilder.setVerticalAlign(VerticalAlign.MIDDLE);break;
		case BOTTOM:styleBuilder.setVerticalAlign(VerticalAlign.BOTTOM);break;
		case JUSTIFY:styleBuilder.setVerticalAlign(VerticalAlign.JUSTIFIED);break;
		default:break;
		}
		
        return styleBuilder.build();
	}
	
	protected void borderSide(StyleBuilder styleBuilder,Side side,Integer index){
		if(side==null){
			
		}else{
			Border border;
			if(side.getSize()==1)
				border = Border.PEN_1_POINT();
			else if(side.getSize()==2)
				border = Border.PEN_2_POINT();
			else if(side.getSize()==4)
				border = Border.PEN_4_POINT();
			else
				border = Border.PEN_1_POINT();
			if(index==0)
				styleBuilder.setBorderLeft(border);
			else if(index==1)
				styleBuilder.setBorderTop(border);
			else if(index==2)
				styleBuilder.setBorderRight(border);
			else if(index==3)
				styleBuilder.setBorderBottom(border);
		}
	}

}
