package org.cyk.system.root.persistence.impl.time;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.model.time.Attendance;
import org.cyk.system.root.model.time.AttendanceMetricValue;
import org.cyk.system.root.persistence.api.time.AttendanceMetricValueDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;

public class AttendanceMetricValueDaoImpl extends AbstractTypedDao<AttendanceMetricValue> implements AttendanceMetricValueDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByAttendance,readByAttendanceByCodes;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByAttendance, _select().where(AttendanceMetricValue.FIELD_ATTENDANCE));
		registerNamedQuery(readByAttendanceByCodes, _select().where(AttendanceMetricValue.FIELD_ATTENDANCE).and()
				.whereString("r.globalIdentifier.code IN :identifiers"));
	}
	
	@Override
	public Collection<AttendanceMetricValue> readByAttendance(Attendance attendance) {
		return namedQuery(readByAttendance).parameter(AttendanceMetricValue.FIELD_ATTENDANCE, attendance).resultMany();
	}

	@Override
	public Collection<AttendanceMetricValue> readByAttendanceByCodes(Attendance attendance, Collection<String> codes) {
		return namedQuery(readByAttendanceByCodes).parameter(AttendanceMetricValue.FIELD_ATTENDANCE, attendance)
				.parameter(QueryStringBuilder.VAR_IDENTIFIERS, codes).resultMany();
	}

	@Override
	public Collection<AttendanceMetricValue> readByAttendanceByCodes(Attendance attendance, String... codes) {
		return readByAttendanceByCodes(attendance, Arrays.asList(codes));
	}

}
 