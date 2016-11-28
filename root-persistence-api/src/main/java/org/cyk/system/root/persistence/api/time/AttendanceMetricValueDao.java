package org.cyk.system.root.persistence.api.time;

import java.util.Collection;

import org.cyk.system.root.model.time.Attendance;
import org.cyk.system.root.model.time.AttendanceMetricValue;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AttendanceMetricValueDao extends TypedDao<AttendanceMetricValue> {

	Collection<AttendanceMetricValue> readByAttendance(Attendance attendance);
	Collection<AttendanceMetricValue> readByAttendanceByCodes(Attendance attendance,Collection<String> codes);
	Collection<AttendanceMetricValue> readByAttendanceByCodes(Attendance attendance,String...codes);
	
	
	
}
