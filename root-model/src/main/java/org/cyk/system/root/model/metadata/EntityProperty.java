package org.cyk.system.root.model.metadata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.language.programming.Script;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @javax.persistence.Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE) @Accessors(chain=true)
@Table(uniqueConstraints={@UniqueConstraint(columnNames={EntityProperty.FIELD_ENTITY,EntityProperty.FIELD_PROPERTY})})
public class EntityProperty extends AbstractIdentifiable implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

	/* Data */
	@ManyToOne @JoinColumn(name=COLUMN_ENTITY) @NotNull private Entity entity;
	@ManyToOne @JoinColumn(name=COLUMN_PROPERTY) @NotNull private Property property;
	
	/* Evaluation */
	@ManyToOne @JoinColumn(name=COLUMN_VALUE_GENERATOR_SCRIPT) private Script valueGeneratorScript;
	private Boolean evaluatableWhenInputChange;
	private Boolean evaluatableWhenPersist;
	
	/* Constraints */
	private Boolean nullable,updatable,computableByUser;
	private @Column(name=COLUMN_LENGTH) Integer length;
	private @Column(name=COLUMN_PRECISION) Integer precision;
	private @Column(name=COLUMN_SCALE) Integer scale;
	private Integer minimumNumberOfOccurence,maximumNumberOfOccurence;
	private Constant.Date.Part datePart;
	
	public EntityProperty setEntityFromCode(String code){
		this.entity = getFromCode(Entity.class, code);
		return this;
	}
	
	public EntityProperty setPropertyFromCode(String code){
		this.property = getFromCode(Property.class, code);
		return this;
	}
	
	public EntityProperty setValueGeneratorScriptFromCode(String code){
		this.valueGeneratorScript = getFromCode(Script.class, code);
		return this;
	}
	
	/**/
	
	public static final String FIELD_ENTITY = "entity";
	public static final String FIELD_PROPERTY = "property";
	public static final String FIELD_VALUE_GENERATOR_SCRIPT = "valueGeneratorScript";
	public static final String FIELD_EVALUATED_WHEN_INPUT_CHANGE = "evaluatedWhenInputChange";
	public static final String FIELD_EVALUATED_WHEN_PERSIST = "evaluatedWhenPersist";
	public static final String FIELD_LENGTH = "length";
	public static final String FIELD_PRECISION = "precision";
	public static final String FIELD_SCALE = "scale";
	
	public static final String COLUMN_ENTITY = FIELD_ENTITY;
	public static final String COLUMN_PROPERTY = COLUMN_NAME_UNKEYWORD+FIELD_PROPERTY;
	public static final String COLUMN_VALUE_GENERATOR_SCRIPT = FIELD_VALUE_GENERATOR_SCRIPT;

	public static final String COLUMN_LENGTH = COLUMN_NAME_UNKEYWORD+FIELD_LENGTH;
	public static final String COLUMN_PRECISION = COLUMN_NAME_UNKEYWORD+FIELD_PRECISION;
	public static final String COLUMN_SCALE = COLUMN_NAME_UNKEYWORD+FIELD_SCALE;
	
	public static class Filter extends AbstractIdentifiable.Filter<EntityProperty> implements Serializable{
		private static final long serialVersionUID = 1L;
    	
    }
}

