package org.cyk.system.root.business.impl.time;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.time.AttendanceMetricValueBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.time.Attendance;
import org.cyk.system.root.model.time.AttendanceMetricValue;
import org.cyk.system.root.persistence.api.time.AttendanceMetricValueDao;

public class AttendanceMetricValueBusinessImpl extends AbstractTypedBusinessService<AttendanceMetricValue, AttendanceMetricValueDao> implements AttendanceMetricValueBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public AttendanceMetricValueBusinessImpl(AttendanceMetricValueDao dao) {
		super(dao); 
	}

	@Override
	public Collection<AttendanceMetricValue> findByAttendance(Attendance attendance) {
		return dao.readByAttendance(attendance);
	}

	@Override
	public Collection<AttendanceMetricValue> findByAttendanceByCodes(Attendance attendance, Collection<String> codes) {
		return dao.readByAttendanceByCodes(attendance, codes);
	}

	@Override
	public Collection<AttendanceMetricValue> findByAttendanceByCodes(Attendance attendance, String... codes) {
		return dao.readByAttendanceByCodes(attendance, Arrays.asList(codes));
	}

	@Override
	public void setValue(Collection<AttendanceMetricValue> attendanceMetricValues, String code, BigDecimal value) {
		for(AttendanceMetricValue attendanceMetricValue : attendanceMetricValues)
			if(attendanceMetricValue.getCode().equals(code))
				attendanceMetricValue.getMetricValue().getNumberValue().setUser(value);
	}

	
}
