package org.cyk.system.root.ui.web.primefaces.api.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.Commandable;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public abstract class AbstractSearchPage<RESULT,ENTITY extends AbstractIdentifiable,BUSINESS extends TypedBusiness<ENTITY>> extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 2069491746669275213L;

	protected BUSINESS business;
	
	private LazyDataModel<RESULT> dataModel;
	private Integer resultsCount=2;
	@Getter private UICommandable searchCommandable;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		contentTitle = text("library");
		business.getDataReadConfig().setMaximumResultCount(2l);
		
		searchCommandable = UIProvider.getInstance().createCommandable(null, "command.search", IconType.ACTION_SEARCH,null,null);
		searchCommandable.getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = 1L;
			@Override
			public void serve(UICommand command, Object parameter) {
				super.serve(command, parameter);
			}
		});
		

		searchCommandable.setShowLabel(Boolean.FALSE);
		((Commandable)searchCommandable).getButton().setType("button");
		((Commandable)searchCommandable).getButton().setOnclick("PF('songDataTable').filter();");
	}
	
	protected abstract Collection<RESULT> __load__(int first, int pageSize,String sortField, SortOrder sortOrder,Map<String, Object> filters);
	protected abstract Integer __count__(int first, int pageSize,String sortField, SortOrder sortOrder,Map<String, Object> filters);
	
	@Override
	public Boolean getShowContextualMenu() {
		return Boolean.TRUE;
	}
	
	public LazyDataModel<RESULT> getDataModel() {
		if(dataModel==null){
			dataModel = new LazyDataModel<RESULT>() {
				private static final long serialVersionUID = -5029807106028522292L;
				@Override
				public List<RESULT> load(int first, int pageSize,String sortField, SortOrder sortOrder,Map<String, Object> filters) {
					business.getDataReadConfig().setFirstResultIndex((long) first);
					Collection<RESULT> collection = __load__(first, pageSize, sortField, sortOrder, filters);
					List<RESULT> list = (List<RESULT>) (collection instanceof List?collection:new ArrayList<>(collection));
					resultsCount = __count__(first, pageSize, sortField, sortOrder, filters);
					return list;
				}
				
				@Override
				public int getRowCount() {
					return resultsCount;
				}
			};
		}
		return dataModel;
	}
	
	public Integer getMaximumResultsCount() {
		return business.getDataReadConfig().getMaximumResultCount().intValue();
	}
	
}
