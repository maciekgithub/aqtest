package cdi.custom.scope.cdr.test;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.persistence.Query;
//
//import org.eclipse.persistence.sessions.Session;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.aq.AQAgent;
import oracle.jdbc.aq.AQEnqueueOptions;
import oracle.jdbc.aq.AQFactory;
import oracle.jdbc.aq.AQMessage;
import oracle.jdbc.aq.AQMessageProperties;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import pl.orange.isep.model.event.cdr.CDRVoucherBalanceRechargeEvent;

/**
@author mkamin
*/
public class CdrTest {
	

	public static void main(String[] args) throws SQLException, MalformedObjectNameException, AttributeNotFoundException, InstanceNotFoundException,
		MBeanException, ReflectionException, InvalidAttributeValueException, FileNotFoundException {

		System.out.println("+++++++++++++++++++++++ Begin +++++++++++++++++++++++");

		Converter c = new Converter();

		//		sample CDREvent
		CDRVoucherBalanceRechargeEvent e = new CDRVoucherBalanceRechargeEvent();

		e.setRecordDate(LocalDateTime.now());
		e.setVoucherNo("123412341234");
		e.setComponent("TEST");
		e.setOldBalanceExpires("2015-06-06 12:11:11");
		e.setNewBalanceExpires("2015-06-06 12:11:11");
		e.setBalances("XX");
		e.setCosts("234");
		e.setAccount(1);

		Connection connection = OracleJDBC.OracleJDBC.getConnection(OracleJDBC.ARIEL);

		//		System.setProperty("-Doracle.jdbc.Trace", "true");
		//		System.setProperty("-Doracle.jdbc.level", "ALL");

		// enable logging programatically

		//		javax.management.MBeanServer mbs = java.lang.management.ManagementFactory.getPlatformMBeanServer();
		//		String loader = Thread.currentThread().getContextClassLoader().toString().replaceAll("[,=:\"]+", "");
		//		javax.management.ObjectName name = new javax.management.ObjectName("com.oracle.jdbc:type=diagnosability,name=" + loader);
		//		mbs.setAttribute(name, new javax.management.Attribute("LoggingEnabled", true));

		//		System.out.println("LoggingEnabled = " + mbs.getAttribute(name, "LoggingEnabled"));

		Object[] obj = c.createObjectTbl(e, 2);

		Object[] o = new Object[43];
		for (Object object : o) {
			System.out.println("entry " + object);
		}
		enqueue(obj, connection);
	}

	@SuppressWarnings("deprecation")
	private static void enqueue(Object[] cdr, Connection c) throws SQLException {

		try (OracleConnection connection = c.unwrap(OracleConnection.class)) {

			// Specify an agent as the sender:
			AQAgent sender =
				AQFactory.createAQAgent();
			sender.setName("ISEP_CHANNEL_CDR");
			// Protocol-specific address of the recipient. If the protocol is 0 (default), the address is of the form [schema.]queue[@dblink].
			sender.setAddress("isep-channel-cdr@isep");

			AQAgent[] recipients =
				new AQAgent[1];
			for (int i = 0; i < 1; i++) {
				recipients[i] = AQFactory.createAQAgent();
				recipients[i].setName("ARAMIS");
			}

			//			TypeDescriptor typeDescriptor =
			//				TypeDescriptor.getTypeDescriptor("CDRS.CDR_TYPE", connection);

			StructDescriptor payloadDescriptor =
				StructDescriptor.createDescriptor("CDRS.CDR_TYPE", connection);

			//			System.out.println("typeDescriptor getName" + typeDescriptor.getName());
			//			System.out.println("typeDescriptor getSchemaName" + typeDescriptor.getSchemaName());
			//			System.out.println("typeDescriptor getTypeCodeName" + typeDescriptor.getTypeCodeName());
			//			System.out.println("typeDescriptor getTypeName" + typeDescriptor.getTypeName());

			for (int i = 0; i < cdr.length; i++) {
				System.out.println("CDR to be enqueued " + i + " " + cdr[i]);
			}

			STRUCT messagePayload =
				new STRUCT(payloadDescriptor, null, connection);
			//			messagePayload.setObjArray(cdr);
			messagePayload.setObjArray(cdr);
			/*
			 * Correlation - specifies the identification supplied by the producer for a message at enqueuing.
			 *
			 * Exception queue - specifies the name of the queue to which the message is moved to if it cannot be processed
			 * successfully.
			 */
			AQMessageProperties messageProperties =
				AQFactory.createAQMessageProperties();
			messageProperties.setSender(sender);
			messageProperties.setRecipientList(recipients);
			messageProperties.setCorrelation("CDRS");
			messageProperties.setExceptionQueue("EXCEPTION_QUEUE");

			// Set all created props to message
			AQMessage message =
				AQFactory.createAQMessage(messageProperties);
			message.setPayload(messagePayload);

			// Retrieve the message id after enqueue:
			AQEnqueueOptions enqueueOptions = new AQEnqueueOptions();
			enqueueOptions.setRetrieveMessageId(true);
			System.out.println("enqueueing...");
			connection.enqueue("BONUS_QUEUE", enqueueOptions, message);

		}
	}
}
