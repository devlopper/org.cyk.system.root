package org.cyk.system.root.service.impl.integration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.cyk.system.root.business.api.mathematics.MathematicsBusiness;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.mathematics.Average;
import org.cyk.system.root.model.mathematics.Rank;
import org.cyk.system.root.model.mathematics.Rankable;
import org.cyk.system.root.model.mathematics.Weightable;
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
    private Script averageScript; 
    
    private Student student1,student2,student3,student4;
    
    @Override
    protected void populate() {
    	create(averageScript = script(MathematicsBusiness.AVERAGE+" = Math.round("+MathematicsBusiness.DIVIDEND+" / "+MathematicsBusiness.DIVISOR+")"
    			+ ".toFixed(2)",MathematicsBusiness.AVERAGE));
    	
    	student1 = new Student("N1", "LN1");
    	student2 = new Student("N2", "LN2");
    	student3 = new Student("N3", "LN3");
    	student4 = new Student("N4", "LN4");
    }
    
    private Script script(String text,String...variables){
        Script script = new Script();
        script.setFile(new File());
        script.getFile().setBytes(text.getBytes());
        if(variables!=null)
        	for(String variable : variables)
        		script.getVariables().add(variable);
        
        return script;
    }

    
    @Override
    protected void _execute_() {
        super._execute_();
        Average average = new Average();
        average.getWeightables().add(new MyWeightable(new BigDecimal("1"), new BigDecimal("1")));
        average.getWeightables().add(new MyWeightable(new BigDecimal("2"), new BigDecimal("1")));
        average.getWeightables().add(new MyWeightable(new BigDecimal("3"), new BigDecimal("1")));
        
        assertAverage(average, null, new BigDecimal("2.00"));
        
        assertAverage(average, averageScript, new BigDecimal("2.00"));
        
        List<StudentExam> studentExams = new ArrayList<StudentExam>();
        
        average = new Average();
        average.getWeightables().add(new StudentMark(student1, "Math", new BigDecimal("10"), new BigDecimal("1")));
        average.getWeightables().add(new StudentMark(student1, "Math", new BigDecimal("26"), new BigDecimal("2")));
        assertAverage(average, null, new BigDecimal("12.00"));
        studentExams.add(new StudentExam(student1, average.getValue()));
        
        average = new Average();
        average.getWeightables().add(new StudentMark(student2, "Math", new BigDecimal("16"), new BigDecimal("1")));
        average.getWeightables().add(new StudentMark(student2, "Math", new BigDecimal("18"), new BigDecimal("2")));
        assertAverage(average, null, new BigDecimal("11.33"));
        studentExams.add(new StudentExam(student2, average.getValue()));
        
        average = new Average();
        average.getWeightables().add(new StudentMark(student3, "Math", new BigDecimal("20"), new BigDecimal("1")));
        average.getWeightables().add(new StudentMark(student3, "Math", new BigDecimal("19"), new BigDecimal("2")));
        assertAverage(average, null, new BigDecimal("13.00"));
        studentExams.add(new StudentExam(student3, average.getValue()));
        
        average = new Average();
        average.getWeightables().add(new StudentMark(student4, "Math", new BigDecimal("16"), new BigDecimal("1")));
        average.getWeightables().add(new StudentMark(student4, "Math", new BigDecimal("20"), new BigDecimal("2")));
        assertAverage(average, null, new BigDecimal("12.00"));
        studentExams.add(new StudentExam(student4, average.getValue()));
        
        System.out.println(studentExams);
        mathematicsBusiness.rank(StudentExam.class, MathematicsBusiness.RankType.SEQUENCE, studentExams, new StudentExamComparator(), null);
        System.out.println(studentExams);
        mathematicsBusiness.rank(StudentExam.class, MathematicsBusiness.RankType.EXAEQUO, studentExams, new StudentExamComparator(), null);
        System.out.println(studentExams);
    }

    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
        
    }

    @Override
    protected void create() {
        
    }

    @Override
    protected void delete() {
        
    }

    

    @Override
    protected void read() {
        
    }

    @Override
    protected void update() {
        
    }
    
    /**/
    
    private void assertAverage(Average average,Script script,BigDecimal expectedValue){
    	Assert.assertEquals(expectedValue,mathematicsBusiness.average(average,null, script).getValue());
    }

    @AllArgsConstructor @Getter
    private class MyWeightable implements Weightable {

    	private BigDecimal value;
    	private BigDecimal weight;
    	
    }
    
    @AllArgsConstructor @Getter
    private class Student {
    	private String firstName,lastName;
    	@Override
    	public String toString() {
    		return firstName+" "+lastName;
    	}
    }
    
    @AllArgsConstructor @Getter
    private class StudentMark implements Weightable {
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
    }
    
    @Getter
    private class StudentExam implements Weightable,Rankable {
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
		public BigDecimal getWeight() {
			return new BigDecimal("1");
		}
		
		@Override
		public String toString() {
			return student+" : "+average+" : "+rank;
		}
    }
    
    private class StudentExamComparator implements Comparator<StudentExam> {

		@Override
		public int compare(StudentExam o1, StudentExam o2) {
			return o1.average.compareTo(o2.average);
		}
    	
    }
}
