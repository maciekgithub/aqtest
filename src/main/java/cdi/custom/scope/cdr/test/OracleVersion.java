package cdi.custom.scope.cdr.test;


/**
@author mkamin
*/

public enum OracleVersion {

	ORACLE_10("10"),
	ORACLE_11("11"),
	ORACLE_12("12");

	private String value;

	private OracleVersion(String value) {
		this.value = value;
	}

	public static OracleVersion fromValue(String value) {
		for (OracleVersion status : values()) {
			if (status.value.equals(value)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Unknown Oracle version string: " + value);
	}

}