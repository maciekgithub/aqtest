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
