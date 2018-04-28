package org.cyk.system.root.model.information;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.Script;
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

	@ManyToOne @JoinColumn(name=COLUMN_ENTITY) @NotNull private Entity entity;
	@ManyToOne @JoinColumn(name=COLUMN_PROPERTY) @NotNull private Property property;
	@ManyToOne @JoinColumn(name=COLUMN_VALUE_GENERATOR_SCRIPT) private Script valueGeneratorScript;
	
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
	
	public static final String COLUMN_ENTITY = FIELD_ENTITY;
	public static final String COLUMN_PROPERTY = FIELD_PROPERTY;
	public static final String COLUMN_VALUE_GENERATOR_SCRIPT = FIELD_VALUE_GENERATOR_SCRIPT;

	public static class Filter extends AbstractIdentifiable.Filter<EntityProperty> implements Serializable{
		private static final long serialVersionUID = 1L;
    	
    }
}

