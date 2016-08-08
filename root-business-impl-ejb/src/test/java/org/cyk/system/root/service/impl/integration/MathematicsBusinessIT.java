package org.cyk.system.root.service.impl.integration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.MathematicsBusiness;
import org.cyk.system.root.business.api.mathematics.MathematicsBusiness.SortOptions;
import org.cyk.system.root.business.api.mathematics.Sortable;
import org.cyk.system.root.business.api.mathematics.ValueComparator;
import org.cyk.system.root.business.api.mathematics.ValueComparator.ValueReader;
import org.cyk.system.root.business.api.mathematics.WeightedValue;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.mathematics.Average;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.Rank;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class MathematicsBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() { 
        return createRootDeployment(); 
    }  
     
    @Inject private MathematicsBusiness mathematicsBusiness;
    @Inject private IntervalBusiness intervalBusiness;
    private Script averageScript; 
    private IntervalCollection intervalCollection1,intervalCollection2;
    
    private Student student1,student2,student3,student4;
    
    @Override
    protected void populate() {
    	create(averageScript = script(MathematicsBusiness.AVERAGE+" = Math.round("+MathematicsBusiness.DIVIDEND+" / "+MathematicsBusiness.DIVISOR+")"
    			+ ".toFixed(2)",MathematicsBusiness.AVERAGE));
    	
    	student1 = new Student("N1", "LN1");
    	student2 = new Student("N2", "LN2");
    	student3 = new Student("N3", "LN3");
    	student4 = new Student("N4", "LN4");
    	
    	intervalCollection1 = intervalManager("I1","0","6.99","I2","7","13.99","I3","14","20");
    	intervalCollection2 = intervalManager("I1","0","9.99","I2","10","20");
    }
    
    private Script script(String text,String...variables){
        Script script = new Script();
        script.setFile(new File());
        script.getFile().setBytes(text.getBytes());
        if(variables!=null)
        	for(String variable : variables)
        		;//script.getVariables().add(variable);
        
        return script;
    }
    
    protected IntervalCollection intervalManager(String...values){
    	IntervalCollection intervalManager = new IntervalCollection();
    	create(intervalManager);
    	for(int i=0;i<values.length;i=i+3){
    		Interval interval = new Interval();
    		interval.setCollection(intervalManager);
    		interval.setName(values[i]);
    		interval.getLow().setValue(new BigDecimal(values[i+1]));
    		interval.getHigh().setValue(new BigDecimal(values[i+2]));
    		create(interval);
    	}
    	return intervalManager;
    }

    
    @Override
    protected void _execute_() {
        super._execute_();
        Average average = null;
        List<WeightedValue> weightedValues = new LinkedList<WeightedValue>();
        weightedValues.add(new WeightedValue(new BigDecimal("1"), new BigDecimal("1")));
        weightedValues.add(new WeightedValue(new BigDecimal("2"), new BigDecimal("1")));
        weightedValues.add(new WeightedValue(new BigDecimal("3"), new BigDecimal("1")));
        
        assertAverage(weightedValues, null, new BigDecimal("2.00"));
        
        assertAverage(weightedValues, averageScript, new BigDecimal("2.00"));
        
        List<StudentExam> studentExams = new ArrayList<StudentExam>();
        
        weightedValues.clear();
        weightedValues.add(new WeightedValue(new BigDecimal("8.5"), new BigDecimal("1")));
        weightedValues.add(new WeightedValue(new BigDecimal("13.75"), new BigDecimal("1"))); 
        weightedValues.add(new WeightedValue(new BigDecimal("18"), new BigDecimal("2")));
        average = assertAverage(weightedValues, null, new BigDecimal("10.06"));
        studentExams.add(new StudentExam(student1, average.getValue()));
        
        weightedValues.clear();
        weightedValues.add(new WeightedValue(new BigDecimal("16"), new BigDecimal("1")));
        weightedValues.add(new WeightedValue(new BigDecimal("18"), new BigDecimal("2")));
        average = assertAverage(weightedValues, null, new BigDecimal("11.33"));
        studentExams.add(new StudentExam(student2, average.getValue()));
        
        weightedValues.clear();
        weightedValues.add(new WeightedValue(new BigDecimal("20"), new BigDecimal("1")));
        weightedValues.add(new WeightedValue(new BigDecimal("19"), new BigDecimal("2")));
        average = assertAverage(weightedValues, null, new BigDecimal("13.00"));
        studentExams.add(new StudentExam(student3, average.getValue()));
        
        weightedValues.clear();
        weightedValues.add(new WeightedValue(new BigDecimal("16"), new BigDecimal("1")));
        weightedValues.add(new WeightedValue(new BigDecimal("20"), new BigDecimal("2")));
        average = assertAverage(weightedValues, null, new BigDecimal("12.00"));
        studentExams.add(new StudentExam(student4, average.getValue()));
        /*
        System.out.println(studentExams);
        mathematicsBusiness.rank(StudentExam.class, MathematicsBusiness.RankType.SEQUENCE, studentExams, new RankableFieldComparator("average"), null);
        System.out.println(studentExams);
        mathematicsBusiness.rank(StudentExam.class, MathematicsBusiness.RankType.EXAEQUO, studentExams, new StudentExamComparator(), null);
        System.out.println(studentExams);
        */
        
        assertInterval(intervalCollection1, new BigDecimal("5"), 2, new BigDecimal("0"),new BigDecimal("6.99"));
        assertInterval(intervalCollection1, new BigDecimal("6.97"), 2, new BigDecimal("0"),new BigDecimal("6.99"));
        assertInterval(intervalCollection1, new BigDecimal("7"), 2, new BigDecimal("7"),new BigDecimal("13.99"));
        assertInterval(intervalCollection1, new BigDecimal("7.99"), 2, new BigDecimal("7"),new BigDecimal("13.99"));
        assertInterval(intervalCollection1, new BigDecimal("8"), 2, new BigDecimal("7"),new BigDecimal("13.99"));
        assertInterval(intervalCollection1, new BigDecimal("17"), 2, new BigDecimal("14"),new BigDecimal("20"));
        assertIntervalNull(intervalCollection1, new BigDecimal("-1"), 2);
        assertIntervalNull(intervalCollection1, new BigDecimal("20.01"), 2);
        
        assertInterval(intervalCollection2, new BigDecimal("5"), 2, new BigDecimal("0"),new BigDecimal("9.99"));
        assertInterval(intervalCollection2, new BigDecimal("6.97"), 2, new BigDecimal("0"),new BigDecimal("9.99"));
        assertInterval(intervalCollection2, new BigDecimal("7"), 2, new BigDecimal("0"),new BigDecimal("9.99"));
        assertInterval(intervalCollection2, new BigDecimal("7.99"), 2, new BigDecimal("0"),new BigDecimal("9.99"));
        assertInterval(intervalCollection2, new BigDecimal("8"), 2, new BigDecimal("0"),new BigDecimal("9.99"));
        assertInterval(intervalCollection2, new BigDecimal("17"), 2, new BigDecimal("10"),new BigDecimal("20"));
        assertIntervalNull(intervalCollection2, new BigDecimal("-1"), 2);
        assertIntervalNull(intervalCollection2, new BigDecimal("20.01"), 2);
        
        studentExams = new ArrayList<StudentExam>();
        studentExams.add(new StudentExam(new Student("a","a"), new BigDecimal("5")));
        studentExams.add(new StudentExam(new Student("b","b"), new BigDecimal("3")));
        studentExams.add(new StudentExam(new Student("c","c"), new BigDecimal("4")));
        studentExams.add(new StudentExam(new Student("d","d"), new BigDecimal("9")));
        SortOptions<StudentExam> so = new SortOptions<StudentExam>();
        so.setComparator(new ValueComparator<StudentExam>(new ValueReader<StudentExam>() {
        	@Override
        	public Object read(StudentExam entity, Integer level) {
        		return entity.getAverage();
        	}
		}));
        
        mathematicsBusiness.sort(studentExams,so);
        System.out.println(studentExams);
        
        studentExams = new ArrayList<StudentExam>();
        studentExams.add(new StudentExam(new Student("a","a"), new BigDecimal("5")));
        studentExams.add(new StudentExam(new Student("b","b"), new BigDecimal("3")));
        studentExams.add(new StudentExam(new Student("c","c"), new BigDecimal("4")));
        studentExams.add(new StudentExam(new Student("d","d"), new BigDecimal("9")));
        so = new SortOptions<StudentExam>();
        so.setComparator(new ValueComparator<StudentExam>(new ValueReader<StudentExam>() {
        	@Override
        	public Object read(StudentExam entity, Integer level) {
        		return entity.getAverage();
        	}
		},Boolean.FALSE));
        
        mathematicsBusiness.sort(studentExams,so);
        System.out.println(studentExams);
    }

    @Override protected void finds() {}
    @Override protected void businesses() {}
    @Override protected void create() {}
    @Override protected void delete() {}
    @Override protected void read() {}
    @Override protected void update() {}
    
    /**/
    
    private Average assertAverage(Collection<WeightedValue> weightedValues,Script script,BigDecimal expectedValue){
    	Average average = mathematicsBusiness.average(weightedValues,null, script);
    	Assert.assertEquals(expectedValue,average.getValue());
    	return average;
    }
    
    private void assertInterval(IntervalCollection collection,BigDecimal value,Integer scale,BigDecimal expectedLow,BigDecimal expectedHigh){
    	Interval interval = intervalBusiness.findByCollectionByValue(collection, value, scale);
    	Assert.assertNotNull(interval);
    	Assert.assertEquals(expectedLow,interval.getLow());
    	Assert.assertEquals(expectedHigh,interval.getHigh());
    	Assert.assertTrue(interval.getLow().getValue().compareTo(expectedLow)<=0);
    	Assert.assertTrue(interval.getHigh().getValue().compareTo(expectedHigh)>=0);
    }
    
    private void assertIntervalNull(IntervalCollection collection,BigDecimal value,Integer scale){
    	Interval interval = intervalBusiness.findByCollectionByValue(collection, value, scale);
    	Assert.assertNull(interval);
    }


    
    @AllArgsConstructor @Getter
    private class Student {
    	private String firstName,lastName;
    	@Override
    	public String toString() {
    		return firstName+" "+lastName;
    	}
    }
    
    /*
    @AllArgsConstructor @Getter
    private class StudentMark implements WeightedValue {
    	private Student student;
    	private String subject;
    	private BigDecimal mark;
    	private BigDecimal coefficient;
    	
		@Override
		public BigDecimal getValue() {
			return mark;
		}
		@Override
		public BigDecimal getWeight() {
			return coefficient;
		}
    }*/
    
    @Getter
    private class StudentExam implements Sortable {
    	private Student student;
    	private BigDecimal average;
    	private Rank rank = new Rank();
    	
    	public StudentExam(Student student,BigDecimal average) {
			super();
			this.student = student;
			this.average = average;
		}
		
    	@Override
		public BigDecimal getValue() {
			return average;
		}
		
		@Override
		public String toString() {
			return student+" : "+average+" : "+rank;
		}
    }
    
    
}
