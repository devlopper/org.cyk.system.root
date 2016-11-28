package org.cyk.system.root.business.api.time;

import java.math.BigDecimal;
import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.time.Attendance;
import org.cyk.system.root.model.time.AttendanceMetricValue;

public interface AttendanceMetricValueBusiness extends TypedBusiness<AttendanceMetricValue> {
    
	Collection<AttendanceMetricValue> findByAttendance(Attendance attendance);
	Collection<AttendanceMetricValue> findByAttendanceByCodes(Attendance attendance,Collection<String> codes);
	Collection<AttendanceMetricValue> findByAttendanceByCodes(Attendance attendance,String...codes);
	
	void setValue(Collection<AttendanceMetricValue> attendanceMetricValues,String code,BigDecimal value);
}
