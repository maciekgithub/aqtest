package cdi.custom.scope.cdr.test;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;

import pl.orange.isep.model.event.cdr.CDRBalanceEvent;
import pl.orange.isep.model.event.cdr.CDRBalanceRechargeEvent;
import pl.orange.isep.model.event.cdr.CDREvent;
import pl.orange.isep.model.event.cdr.CDRVoucherBalanceRechargeEvent;

/**
@author mkamin
*/
public class Converter {

	/*
	 * Copyright 2011 Telekomunikacja Polska S.A. All rights reserved. Telekomunikacja Polska S.A. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
	 */
//	package pl.orange.isep.channel.cdr.publisher.aq.util;
//
//	import org.slf4j.Logger;
//	import pl.orange.isep.model.event.cdr.CDRBalanceEvent;
//	import pl.orange.isep.model.event.cdr.CDRBalanceRechargeEvent;
//	import pl.orange.isep.model.event.cdr.CDREvent;
//	import pl.orange.isep.model.event.cdr.CDRVoucherBalanceRechargeEvent;
//
//	import javax.inject.Inject;
//	import java.sql.Timestamp;
//	import java.time.ZoneId;
//	import java.util.Arrays;
//	import java.util.Date;

//	public class CDREventConverter {

		private static final int UNUSED_INT = 0;
		private static final String UNUSED_STRING = "10";

		private static final ZoneId ZONE_ID_GMT = ZoneId.of("GMT");

//		@Inject
//		private Logger logger;

		/**
		 * <p>
		 * Converts input {@code event} to Object array representing CDR object supported by CDR 1 & 2 environments.
		 * </p>
		 *
		 * @param event     {@link pl.orange.isep.model.event.cdr.CDREvent} to convert to CDR object array.
		 * @param version   Oracle database version.
		 * @return  CDR object array.
		 */
		public Object[] createObjectTbl(CDREvent event, OracleVersion version) {

//			logger.info("Converting {} to CDR object array.", event);

			Object[] cdr = new Object[43];

			Date recordDate =
				Date.from(event.getRecordDate().atZone(ZONE_ID_GMT).toInstant());

			switch (version) {
				case ORACLE_10: {
					cdr[0] = UNUSED_INT; // BILLING_ENGINE_ID
					cdr[1] = UNUSED_INT; // SCP_ID
					cdr[2] = event.getSequenceNumber(); // SEQUENCE_NUMBER
					cdr[3] = event.getCdrType(); // CDR_NUMBER
					cdr[4] = new Timestamp(recordDate.getTime()); // RECORD_DATE
					cdr[5] = event.getAccount(); // ACCT_ID
					cdr[6] = UNUSED_INT; // ACCT_REF_ID
					cdr[7] = UNUSED_INT; // ACS_CUST_ID
					cdr[8] = UNUSED_STRING; // VOUCHER
					cdr[9] = (event instanceof CDRVoucherBalanceRechargeEvent) ? ((CDRVoucherBalanceRechargeEvent) event).getVoucherNo() : UNUSED_STRING; // VOUCHER_NUMBER
					cdr[10] = UNUSED_STRING; // CS
					cdr[11] = UNUSED_STRING; // WALLET_TYPE_OR_USSD
					cdr[12] = event.getAccountType(); // ACCOUNT_TYPE
					cdr[13] = UNUSED_STRING; // PI
					cdr[14] = (event instanceof CDRBalanceRechargeEvent) ? ((CDRBalanceRechargeEvent) event).getComponent() : UNUSED_STRING; // COMPONENT
					cdr[15] = UNUSED_STRING; // OLD_ACCT_EXPIRY
					cdr[16] = UNUSED_STRING; // NEW_ACCT_EXPIRY
					cdr[17] = UNUSED_INT; // MAX_CONCURRENT
					cdr[18] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getOldBalanceExpires() : UNUSED_STRING; // OLD_BALANCE_EXPIRIES
					cdr[19] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getNewBalanceExpires() : UNUSED_STRING; // NEW_BALANCE_EXPIRIES
					cdr[20] = UNUSED_STRING; // RELOAD_BONUS
					cdr[21] = UNUSED_INT; // RELOAD_BONUS_AMOUNT
					cdr[22] = UNUSED_INT; // RELOAD_BONUS_LEFT
					cdr[23] = new Timestamp(System.currentTimeMillis()); // LOAD_TIME
					cdr[24] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getBalanceTypes() : UNUSED_STRING; // BALANCE_TYPES
					cdr[25] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getBalances() : UNUSED_STRING; // BALANCES
					cdr[26] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getCosts() : UNUSED_STRING; // COSTS
					cdr[27] = UNUSED_STRING; // RELOAD_BONUS_EXPIRY
					cdr[28] = UNUSED_STRING; // RELOAD_BONUS_EXPIRY_TEST
					cdr[29] = event.getMsisdn(); // CLI
					cdr[30] = UNUSED_STRING; // USER
					cdr[31] = UNUSED_STRING; // RESULT
					cdr[32] = UNUSED_STRING; // NEW_LAST_USE
					cdr[33] = (event instanceof CDRBalanceRechargeEvent) ? ((CDRBalanceRechargeEvent) event).getComponent() : UNUSED_STRING; // REFERENCE
					cdr[34] = event.getMsisdn(); // MSISDN
					cdr[35] = UNUSED_INT; // BONUS_AMOUNT
					cdr[36] = UNUSED_INT; // RECHARGE_AMOUNT
					cdr[37] = UNUSED_STRING; // SMS_MESSAGE
					cdr[38] = UNUSED_INT; // ERROR_CODE
					cdr[39] = UNUSED_INT; // X
					cdr[40] = UNUSED_INT; // Y
					cdr[41] = UNUSED_STRING; // BONUS_DEST
					cdr[42] = (event instanceof CDRBalanceRechargeEvent) ? ((CDRBalanceRechargeEvent) event).getComponent() : UNUSED_STRING; // APPLICATION_DESC
					break;
				}
				default: {
					cdr[0] = event.getAccountType(); // ACCOUNT_TYPE
					cdr[1] = event.getAccount(); // ACCT_ID
					cdr[2] = UNUSED_INT; // ACCT_REF_ID
					cdr[3] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getBalances() : UNUSED_STRING; // BALANCES
					cdr[4] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getBalanceTypes() : UNUSED_STRING; // BALANCE_TYPES
					cdr[5] = UNUSED_INT; // BILLING_ENGINE_ID
					cdr[6] = event.getCdrType(); // CDR_NUMBER
					cdr[7] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getCosts() : UNUSED_STRING; // COSTS
					cdr[8] = new Timestamp(System.currentTimeMillis()); // LOAD_TIME
					cdr[9] = event.getMsisdn(); // MSISDN
					cdr[10] = UNUSED_STRING; // OLD_ACCT_EXPIRY
					cdr[11] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getOldBalanceExpires() : UNUSED_STRING; // OLD_BALANCE_EXPIRIES
					cdr[12] = new Timestamp(recordDate.getTime()); // RECORD_DATE
					cdr[13] = UNUSED_INT; // SCP_ID
					cdr[14] = event.getSequenceNumber(); // SEQUENCE_NUMBER
					cdr[15] = UNUSED_INT; // ERROR_CODE
					cdr[16] = UNUSED_INT; // ACS_CUST_ID
					cdr[17] = (event instanceof CDRBalanceRechargeEvent) ? ((CDRBalanceRechargeEvent) event).getComponent() : UNUSED_STRING; // APPLICATION_DESC
					cdr[18] = (event instanceof CDRBalanceRechargeEvent) ? ((CDRBalanceRechargeEvent) event).getComponent() : UNUSED_STRING; // COMPONENT
					cdr[19] = event.getMsisdn(); // CLI
					cdr[20] = UNUSED_STRING; // USER
					cdr[21] = UNUSED_STRING; // CS
					cdr[22] = UNUSED_INT; // MAX_CONCURRENT
					cdr[23] = UNUSED_STRING; // NEW_ACCT_EXPIRY
					cdr[24] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getNewBalanceExpires() : UNUSED_STRING; //NEW_BALANCE_EXPIRIES
					cdr[25] = UNUSED_STRING; // NEW_LAST_USE
					cdr[26] = UNUSED_STRING; // PI
					cdr[27] = (event instanceof CDRBalanceRechargeEvent) ? ((CDRBalanceRechargeEvent) event).getComponent() : UNUSED_STRING; // REFERENCE
					cdr[28] = UNUSED_STRING; // RELOAD_BONUS
					cdr[29] = UNUSED_INT; // RELOAD_BONUS_AMOUNT
					cdr[30] = UNUSED_STRING; // RELOAD_BONUS_EXPIRY
					cdr[31] = UNUSED_STRING; // RELOAD_BONUS_EXPIRY_TEST
					cdr[32] = UNUSED_INT; // RELOAD_BONUS_LEFT
					cdr[33] = UNUSED_STRING; // RESULT
					cdr[34] = UNUSED_STRING; // VOUCHER
					cdr[35] = (event instanceof CDRVoucherBalanceRechargeEvent) ? ((CDRVoucherBalanceRechargeEvent) event).getVoucherNo() : UNUSED_STRING; // VOUCHER_NUMBER
					cdr[36] = UNUSED_STRING; // WALLET_TYPE_OR_USSD
					cdr[37] = UNUSED_INT; // BONUS_AMOUNT
					cdr[38] = UNUSED_STRING; // BONUS_DEST
					cdr[39] = UNUSED_INT; // RECHARGE_AMOUNT
					cdr[40] = UNUSED_STRING; // SMS_MESSAGE
					cdr[41] = UNUSED_INT; // X
					cdr[42] = UNUSED_INT; // Y
					break;
				}
			}

//			logger.info("{} successfully converted to {}.", event, Arrays.asList(cdr));

			return cdr;

		}

		/**
		 * <p>
		 * Converts input {@code event} to Object array representing CDR object supported by CDR 1 & 2 environments.
		 * </p>
		 *
		 * @param event     {@link pl.orange.isep.model.event.cdr.CDREvent} to convert to CDR object array.
		 * @param cdrtypeVersion   cdrtype version.
		 * @return  CDR object array.
		 */
		public Object[] createObjectTbl(CDREvent event, int cdrtypeVersion) {

//			logger.info("Converting {} to CDR object array.", event);

			Object[] cdr = new Object[43];

			Date recordDate =
				Date.from(event.getRecordDate().atZone(ZONE_ID_GMT).toInstant());

			switch (cdrtypeVersion) {
				case 1: {
					cdr[0] = UNUSED_INT; // BILLING_ENGINE_ID
					cdr[1] = UNUSED_INT; // SCP_ID
					cdr[2] = event.getSequenceNumber(); // SEQUENCE_NUMBER
					cdr[3] = event.getCdrType(); // CDR_NUMBER
					cdr[4] = new Timestamp(recordDate.getTime()); // RECORD_DATE
					cdr[5] = event.getAccount(); // ACCT_ID
					cdr[6] = UNUSED_INT; // ACCT_REF_ID
					cdr[7] = UNUSED_INT; // ACS_CUST_ID
					cdr[8] = UNUSED_STRING; // VOUCHER
					cdr[9] = (event instanceof CDRVoucherBalanceRechargeEvent) ? ((CDRVoucherBalanceRechargeEvent) event).getVoucherNo() : UNUSED_STRING; // VOUCHER_NUMBER
					cdr[10] = UNUSED_STRING; // CS
					cdr[11] = UNUSED_STRING; // WALLET_TYPE_OR_USSD
					cdr[12] = event.getAccountType(); // ACCOUNT_TYPE
					cdr[13] = UNUSED_STRING; // PI
					cdr[14] = (event instanceof CDRBalanceRechargeEvent) ? ((CDRBalanceRechargeEvent) event).getComponent() : UNUSED_STRING; // COMPONENT
					cdr[15] = UNUSED_STRING; // OLD_ACCT_EXPIRY
					cdr[16] = UNUSED_STRING; // NEW_ACCT_EXPIRY
					cdr[17] = UNUSED_INT; // MAX_CONCURRENT
					cdr[18] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getOldBalanceExpires() : UNUSED_STRING; // OLD_BALANCE_EXPIRIES
					cdr[19] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getNewBalanceExpires() : UNUSED_STRING; // NEW_BALANCE_EXPIRIES
					cdr[20] = UNUSED_STRING; // RELOAD_BONUS
					cdr[21] = UNUSED_INT; // RELOAD_BONUS_AMOUNT
					cdr[22] = UNUSED_INT; // RELOAD_BONUS_LEFT
					cdr[23] = new Timestamp(System.currentTimeMillis()); // LOAD_TIME
					cdr[24] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getBalanceTypes() : UNUSED_STRING; // BALANCE_TYPES
					cdr[25] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getBalances() : UNUSED_STRING; // BALANCES
					cdr[26] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getCosts() : UNUSED_STRING; // COSTS
					cdr[27] = UNUSED_STRING; // RELOAD_BONUS_EXPIRY
					cdr[28] = UNUSED_STRING; // RELOAD_BONUS_EXPIRY_TEST
					cdr[29] = event.getMsisdn(); // CLI
					cdr[30] = UNUSED_STRING; // USER
					cdr[31] = UNUSED_STRING; // RESULT
					cdr[32] = UNUSED_STRING; // NEW_LAST_USE
					cdr[33] = (event instanceof CDRBalanceRechargeEvent) ? ((CDRBalanceRechargeEvent) event).getComponent() : UNUSED_STRING; // REFERENCE
					cdr[34] = event.getMsisdn(); // MSISDN
					cdr[35] = UNUSED_INT; // BONUS_AMOUNT
					cdr[36] = UNUSED_INT; // RECHARGE_AMOUNT
					cdr[37] = UNUSED_STRING; // SMS_MESSAGE
					cdr[38] = UNUSED_INT; // ERROR_CODE
					cdr[39] = UNUSED_INT; // X
					cdr[40] = UNUSED_INT; // Y
					cdr[41] = UNUSED_STRING; // BONUS_DEST
					break;
				}
				case 2: {
					cdr[0] = UNUSED_STRING; // ACCOUNT_TYPE
					cdr[1] = UNUSED_STRING; // ACCT_ID
					cdr[2] = UNUSED_STRING; // ACCT_REF_ID
					cdr[3] = UNUSED_STRING; // BALANCES
					cdr[4] = UNUSED_STRING; // BALANCE_TYPES
					cdr[5] = UNUSED_STRING; // BILLING_ENGINE_ID
					cdr[6] = UNUSED_STRING; // CDR_NUMBER
					cdr[7] = UNUSED_STRING; // COSTS
//					cdr[7] = UNUSED_STRING; // ACCOUNT_TYPE
					
					Object string_date="2016-07-26 23:23:23";
					//					cdr[8] = new Timestamp(System.currentTimeMillis()); // LOAD_TIME
					cdr[8] = string_date; // LOAD_TIME
					
					cdr[9] = UNUSED_STRING; // MSISDN
					cdr[10] = UNUSED_STRING; // OLD_ACCT_EXPIRY
//					cdr[11] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getOldBalanceExpires() : UNUSED_STRING; // OLD_BALANCE_EXPIRIES
					cdr[11] = string_date; // OLD_BALANCE_EXPIRIES
//					cdr[12] = new Timestamp(recordDate.getTime()); // RECORD_DATE
					cdr[12] = string_date; // RECORD_DATE
					cdr[13] = UNUSED_STRING; // SCP_ID
					cdr[14] = UNUSED_STRING; // SEQUENCE_NUMBER
					cdr[15] = UNUSED_STRING; // ERROR_CODE
					cdr[16] = UNUSED_STRING; // ACS_CUST_ID
					cdr[17] = UNUSED_STRING; // APPLICATION_DESC
					cdr[18] = UNUSED_STRING; // COMPONENT
					cdr[19] = UNUSED_STRING; // CLI
					cdr[20] = UNUSED_STRING; // USER
					cdr[21] = UNUSED_STRING; // CS
					cdr[22] = UNUSED_STRING; // MAX_CONCURRENT
					cdr[23] = UNUSED_STRING; // NEW_ACCT_EXPIRY
//					cdr[24] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getNewBalanceExpires() : UNUSED_STRING; //NEW_BALANCE_EXPIRIES
					cdr[24] = string_date; //NEW_BALANCE_EXPIRIES
					cdr[25] = UNUSED_STRING; // NEW_LAST_USE
					cdr[26] = UNUSED_STRING; // PI
					cdr[27] = UNUSED_STRING; // REFERENCE
					cdr[28] = UNUSED_STRING; // RELOAD_BONUS
					cdr[29] = UNUSED_STRING; // RELOAD_BONUS_AMOUNT
					cdr[30] = UNUSED_STRING; // RELOAD_BONUS_EXPIRY
					cdr[31] = UNUSED_STRING; // RELOAD_BONUS_EXPIRY_TEST
					cdr[32] = UNUSED_STRING; // RELOAD_BONUS_LEFT
					cdr[33] = UNUSED_STRING; // RESULT
					cdr[34] = UNUSED_STRING; // VOUCHER
					cdr[35] = UNUSED_STRING; // VOUCHER_NUMBER
					cdr[36] = UNUSED_STRING; // WALLET_TYPE_OR_USSD
					cdr[37] = UNUSED_STRING; // BONUS_AMOUNT
					cdr[38] = UNUSED_STRING; // BONUS_DEST
					cdr[39] = UNUSED_STRING; // RECHARGE_AMOUNT
					cdr[40] = UNUSED_STRING; // SMS_MESSAGE
					cdr[41] = UNUSED_STRING; // X
					cdr[42] = UNUSED_STRING; // Y
					break;
				}
				default: {
					cdr[0] = event.getAccountType(); // ACCOUNT_TYPE
					cdr[1] = event.getAccount(); // ACCT_ID
					cdr[2] = UNUSED_INT; // ACCT_REF_ID
					cdr[3] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getBalances() : UNUSED_STRING; // BALANCES
					cdr[4] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getBalanceTypes() : UNUSED_STRING; // BALANCE_TYPES
					cdr[5] = UNUSED_INT; // BILLING_ENGINE_ID
					cdr[6] = event.getCdrType(); // CDR_NUMBER
					cdr[7] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getCosts() : UNUSED_STRING; // COSTS
					cdr[8] = new Timestamp(System.currentTimeMillis()); // LOAD_TIME
					cdr[9] = event.getMsisdn(); // MSISDN
					cdr[10] = UNUSED_STRING; // OLD_ACCT_EXPIRY
					cdr[11] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getOldBalanceExpires() : UNUSED_STRING; // OLD_BALANCE_EXPIRIES
					cdr[12] = new Timestamp(recordDate.getTime()); // RECORD_DATE
					cdr[13] = UNUSED_INT; // SCP_ID
					cdr[14] = event.getSequenceNumber(); // SEQUENCE_NUMBER
					cdr[15] = UNUSED_INT; // ERROR_CODE
					cdr[16] = UNUSED_INT; // ACS_CUST_ID
					cdr[17] = (event instanceof CDRBalanceRechargeEvent) ? ((CDRBalanceRechargeEvent) event).getComponent() : UNUSED_STRING; // APPLICATION_DESC
					cdr[18] = (event instanceof CDRBalanceRechargeEvent) ? ((CDRBalanceRechargeEvent) event).getComponent() : UNUSED_STRING; // COMPONENT
					cdr[19] = event.getMsisdn(); // CLI
					cdr[20] = UNUSED_STRING; // USER
					cdr[21] = UNUSED_STRING; // CS
					cdr[22] = UNUSED_INT; // MAX_CONCURRENT
					cdr[23] = UNUSED_STRING; // NEW_ACCT_EXPIRY
					cdr[24] = (event instanceof CDRBalanceEvent) ? ((CDRBalanceEvent) event).getNewBalanceExpires() : UNUSED_STRING; //NEW_BALANCE_EXPIRIES
					cdr[25] = UNUSED_STRING; // NEW_LAST_USE
					cdr[26] = UNUSED_STRING; // PI
					cdr[27] = (event instanceof CDRBalanceRechargeEvent) ? ((CDRBalanceRechargeEvent) event).getComponent() : UNUSED_STRING; // REFERENCE
					cdr[28] = UNUSED_STRING; // RELOAD_BONUS
					cdr[29] = UNUSED_INT; // RELOAD_BONUS_AMOUNT
					cdr[30] = UNUSED_STRING; // RELOAD_BONUS_EXPIRY
					cdr[31] = UNUSED_STRING; // RELOAD_BONUS_EXPIRY_TEST
					cdr[32] = UNUSED_INT; // RELOAD_BONUS_LEFT
					cdr[33] = UNUSED_STRING; // RESULT
					cdr[34] = UNUSED_STRING; // VOUCHER
					cdr[35] = (event instanceof CDRVoucherBalanceRechargeEvent) ? ((CDRVoucherBalanceRechargeEvent) event).getVoucherNo() : UNUSED_STRING; // VOUCHER_NUMBER
					cdr[36] = UNUSED_STRING; // WALLET_TYPE_OR_USSD
					cdr[37] = UNUSED_INT; // BONUS_AMOUNT
					cdr[38] = UNUSED_STRING; // BONUS_DEST
					cdr[39] = UNUSED_INT; // RECHARGE_AMOUNT
					cdr[40] = UNUSED_STRING; // SMS_MESSAGE
					cdr[41] = UNUSED_INT; // X
					cdr[42] = UNUSED_INT; // Y
					break;
				}
			}

//			logger.info("{} successfully converted to {}.", event, Arrays.asList(cdr));

			return cdr;

		}
		
	}

	
	
	
	
