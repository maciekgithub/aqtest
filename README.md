Klasa **CdrTest** zawiera metode main która wrzuca CDRa do 
```
84 Connection connection = OracleJDBC.OracleJDBC.getConnection(OracleJDBC.ARIEL);
```

CDR jest tworzony sztucznie w ten sposób:
```
73		CDRVoucherBalanceRechargeEvent e = new CDRVoucherBalanceRechargeEvent();
74
75		e.setRecordDate(LocalDateTime.now());
76		e.setVoucherNo("123412341234");
77		e.setComponent("TEST");
78		e.setOldBalanceExpires("2015-06-06 12:11:11");
79		e.setNewBalanceExpires("2015-06-06 12:11:11");
80		e.setBalances("XX");
81		e.setCosts("234");
82		e.setAccount(1);
```

Trzeba rozwiązać sobie zależnośći w **pom.xml** - dodając ojdbc6_g.jar w celu śledzenia logów sterownika jdbc.

**Ustawienie logowania OJDBC programistycznie:**
```
		javax.management.MBeanServer mbs = java.lang.management.ManagementFactory.getPlatformMBeanServer();
		String loader = Thread.currentThread().getContextClassLoader().toString().replaceAll("[,=:\"]+", "");
		javax.management.ObjectName name = new javax.management.ObjectName("com.oracle.jdbc:type=diagnosability,name=" + loader);
		mbs.setAttribute(name, new javax.management.Attribute("LoggingEnabled", true));
		System.out.println("LoggingEnabled = " + mbs.getAttribute(name, "LoggingEnabled"));
```

**Ustawienie logowania OJDBC konfiguracyjnie:**

uruchamiałem z wiersza polecen bo z eclipsa mi nie dziłało:
```
java -Djava.util.logging.config.file=${PATH_TO_CFG_FILE}/myConfig.properties -Doracle.jdbc.Trace=true -cp .:${PATH_TO_M2_REPO}/pl/orange/isep/isep-model/0.1-SNAPSHOT/isep-model-0.1-SNAPSHOT.jar:/${PATH_TO_M2_REPO}/isep-forked/isep/isep-api/target/isep-api-0.1-SNAPSHOT.jar:${PATH_TO_M2_REPO}/oracle/ojdbc/ojdbc_g/1.0/ojdbc_g-1.0.jar cdi.custom.scope.cdr.test.CdrTest
```

```
vim myConfig.properties
.level=SEVERE
oracle.jdbc.level=SEVERE
oracle.jdbc.aq.level=ALL
oracle.jdbc.handlers=java.util.logging.FileHandler
java.util.logging.FileHandler.level=FINE
java.util.logging.FileHandler.pattern=jdbc.log -->output file 
java.util.logging.FileHandler.count=1
java.util.logging.FileHandler.formatter=java.util.logging.SimpleFormatter
```
