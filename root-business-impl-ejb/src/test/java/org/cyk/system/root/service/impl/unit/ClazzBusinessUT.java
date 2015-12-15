package org.cyk.system.root.service.impl.unit;

import java.util.Collection;

import org.cyk.system.root.business.api.ClazzBusiness;
import org.cyk.system.root.business.impl.ClazzBusinessImpl;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.mockito.InjectMocks;

public class ClazzBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private ClazzBusinessImpl clazzBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(clazzBusiness);
		ClazzBusiness.LISTENERS.add(new ClazzBusiness.ClazzBusinessListener.Adapter(){
			private static final long serialVersionUID = -6563167908087619179L;
			@Override
			public Object getParentOf(Object object) {
				
				if(object instanceof A)
					return null;
				if(object instanceof B)
					return ((B)object).v;
				if(object instanceof C)
					return ((C)object).v;
				if(object instanceof D)
					return ((D)object).v;
				if(object instanceof E)
					return ((E)object).v;
				return super.getParentOf(object);
			}
		});
	}
	
	@Override
	protected void _execute_() {
		super._execute_();

		System.out.println(clazzBusiness.findPathOf(A.class, new A()));
		System.out.println(clazzBusiness.findPathOf(A.class, new B()));
		System.out.println(clazzBusiness.findPathOf(A.class, new C()));
		System.out.println(clazzBusiness.findPathOf(A.class, new D()));
		
		System.out.println(clazzBusiness.findPathOf(A.class, new E()));
		
		System.out.println(clazzBusiness.findPathOf(C.class, new E()));
	}
	
	/**/
	
	private static class A{
		@Override
		public String toString() {
			return "A";
		}
	}
	private static class B{
		private A v = new A();
		@Override
		public String toString() {
			return "B";
		}
	}
	private static class C{
		private B v = new B();
		@Override
		public String toString() {
			return "C";
		}
	}
	private static class D{
		private C v = new C();
		@Override
		public String toString() {
			return "D";
		}
	}
	private static class E{
		private D v = new D();
		@Override
		public String toString() {
			return "E";
		}
	}
	
	

}
