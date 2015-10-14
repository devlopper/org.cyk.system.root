package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @Entity @NoArgsConstructor
public class EvaluationItem  extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;

	@ManyToOne @NotNull private Evaluation evaluation;
	
	@Column(precision=5,scale=FLOAT_SCALE,nullable=false) @NotNull private BigDecimal value;

	public EvaluationItem(Evaluation evaluation,String code, String name) {
		super(code, name, null, null);
		this.evaluation = evaluation;
	}
	
	public static final String FIELD_EVALUATION = "evaluation";
	
}