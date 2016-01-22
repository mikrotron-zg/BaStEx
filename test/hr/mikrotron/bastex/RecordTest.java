package hr.mikrotron.bastex;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordTest {

	public static Record testRecord;
	public static String fileName="testdata/pbztest_big.pdf.out";
	static final String LAST_RECORD="*** KRAJ IZVATKA ***";
	static final String FIRST_RECORD="OIB: 02535697732      IZVADAK PO TRANSAKCIJSKOM RAČUNU       ";
	static final int GET_RECORD_INDEX=190;
	static final String GET_RECORD_INDEX_SEARCH_STRING="HR00";
	static final int GET_RECORD_INDEX_RESULT=194;
	static final String NONEXISTING_STRING="This string is not in the record!";
	static final int RECORD_SIZE=325;
	static final int DOUBLE_INDEX=51;
	static final double DOUBLE_VALUE=164.61;
	static final String DOUBLE_SEARCH_STRING="164,61";
	static final double DOUBLE_DELTA=0.001;
	static final String DOUBLE_SEARCH_STRING_WITH_OFFSET="2015302420642";
	static final int DOUBLE_SEARCH_OFFSET=7;
	static final int INT_INDEX=30;
	static final int INT_VALUE=218;
	static final String INT_SEARCH_STRING="218";
	static final String INT_SEARCH_STRING_WITH_OFFSET="Izvadak br.:";
	static final int INT_SEARCH_OFFSET=1;
	static final int DATE_INDEX=32;
	static Date DATE_VALUE;
	static final String DATE_SEARCH_STRING="18.12.2015";
	static final String DATE_SEARCH_STRING_WITH_OFFSET="Datum izvatka:";
	static final int DATE_SEARCH_OFFSET=1;
	static final String FIRST_TRANS="1";
	static final int FIRST_TRANS_INDEX=40;
	static final String SECOND_PAGE_TRANS="9";
	static final int SECOND_PAGE_TRANS_INDEX=164;
	static final String LAST_TRANS="19";
	static final int LAST_TRANS_INDEX=312;
	static final int GAP=9;
	static final int GAP_INDEX=49;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try{
			testRecord = new Record(new String(
					Files.readAllBytes(FileSystems.getDefault().getPath(fileName)), "utf-8"));
			DATE_VALUE=new SimpleDateFormat("yyyy-MM-dd").parse("2015-12-18");
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGetGap(){
		assertEquals("should return -1 for index out of bounds", -1, testRecord.getGap(-1));
		assertEquals("should return gap", GAP, testRecord.getGap(GAP_INDEX));
	}
	
	@Test
	public void testGetRecord() {
		assertEquals("getRecord() fails for first record",
				FIRST_RECORD, testRecord.getRecord(0));
		assertEquals("getRecord() fails for last record", 
				LAST_RECORD, testRecord.getRecord(324));
		assertEquals("getRecord() fails for index out of bounds",
				"",testRecord.getRecord(-1));
	}

	@Test
	public void testGetRecordIndex() {
		assertEquals("getRecordIndex() fails for first line", 
				0, testRecord.getRecordIndex(FIRST_RECORD));
		assertEquals("getRecordindex() fails for last line", 
				324, testRecord.getRecordIndex(LAST_RECORD));
		assertEquals("getRecordIndex() fails for non-existing string", 
				RECORD_SIZE, testRecord.getRecordIndex(NONEXISTING_STRING));
		assertEquals("getRecordIndex(startAt) fails for index 190", 
				GET_RECORD_INDEX_RESULT, testRecord.getRecordIndex(
						GET_RECORD_INDEX_SEARCH_STRING, GET_RECORD_INDEX));
	}

	@Test
	public void testFindTransactionStartIndex(){
		assertEquals("should not find bank statement number as transaction ordinal", 
				-1, testRecord.findTransactionStartIndex(INT_SEARCH_STRING, 0));
		assertEquals("should find first transaction", FIRST_TRANS_INDEX, 
				testRecord.findTransactionStartIndex(FIRST_TRANS,0));
		assertEquals("should find first transaction on second page", SECOND_PAGE_TRANS_INDEX, 
				testRecord.findTransactionStartIndex(SECOND_PAGE_TRANS, 0));
		assertEquals("should find last transaction", LAST_TRANS_INDEX, 
				testRecord.findTransactionStartIndex(LAST_TRANS, 0));
	}
	
	@Test
	public void testAsDoubleInt() {
		try {
			assertEquals("asDouble(int) fails for line " + DOUBLE_INDEX, 
					DOUBLE_VALUE, testRecord.asDouble(DOUBLE_INDEX), DOUBLE_DELTA);
		} catch (IndexOutOfBoundsException | ParseException e) {
			fail("Shouldn't raise exception! " + e.getMessage());
		}
	}

	@Test
	public void testAsDoubleStringInt() {
		try {
			assertEquals("asDouble(String, int) fails for line " + DOUBLE_INDEX, DOUBLE_VALUE,
					testRecord.asDouble(DOUBLE_SEARCH_STRING_WITH_OFFSET, DOUBLE_SEARCH_OFFSET), DOUBLE_DELTA);
		} catch (IndexOutOfBoundsException | ParseException e) {
			fail("Shouldn't raise exception! " + e.getMessage());
		}
	}

	@Test
	public void testAsDoubleString() {
		try {
			assertEquals("asDouble(String, int) fails for line " + DOUBLE_INDEX, DOUBLE_VALUE,
					testRecord.asDouble(DOUBLE_SEARCH_STRING), DOUBLE_DELTA);
		} catch (IndexOutOfBoundsException | ParseException e) {
			fail("Shouldn't raise exception! " + e.getMessage());
		}
	}
	
	@Test(expected = ParseException.class)
	public void testAsDoubleParseException() throws IndexOutOfBoundsException, ParseException{
		testRecord.asDouble(0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testAsDoubleIOBException() throws IndexOutOfBoundsException, ParseException{
		testRecord.asDouble(-1);
	}
	
	@Test
	public void testAsIntInt() {
		try {
			assertEquals("asInt(int) fails for line " + INT_INDEX, INT_VALUE,
					testRecord.asInt(INT_INDEX));
		} catch (IndexOutOfBoundsException | ParseException e) {
			fail("Shouldn't raise exception! " + e.getMessage());
		}
	}

	@Test
	public void testAsIntStringInt() {
		try {
			assertEquals("asInt(String,int) fails for line " + INT_INDEX, INT_VALUE,
					testRecord.asInt(INT_SEARCH_STRING_WITH_OFFSET, INT_SEARCH_OFFSET));
		} catch (IndexOutOfBoundsException | ParseException e) {
			fail("Shouldn't raise exception! " + e.getMessage());
		}
	}

	@Test
	public void testAsIntString() {
		try {
			assertEquals("asInt(String) fails for line " + INT_INDEX, INT_VALUE,
					testRecord.asInt(INT_SEARCH_STRING));
		} catch (IndexOutOfBoundsException | ParseException e) {
			fail("Shouldn't raise exception! " + e.getMessage());
		}
	}

	@Test
	public void testAsDateInt() {
		try {
			assertEquals("asDate(int) fails for line " + DATE_INDEX, DATE_VALUE,
					testRecord.asDate(DATE_INDEX));
		} catch (IndexOutOfBoundsException | ParseException e) {
			fail("Shouldn't raise exception! " + e.getMessage());
		}
	}

	@Test
	public void testAsDateStringInt() {
		try {
			assertEquals("asDate(String) fails for line " + DATE_INDEX, DATE_VALUE,
					testRecord.asDate(DATE_SEARCH_STRING_WITH_OFFSET,DATE_SEARCH_OFFSET));
		} catch (IndexOutOfBoundsException | ParseException e) {
			fail("Shouldn't raise exception! " + e.getMessage());
		}
	}

	@Test
	public void testAsDateString() {
		try {
			assertEquals("asDate(String) fails for line " + DATE_INDEX, DATE_VALUE,
					testRecord.asDate(DATE_SEARCH_STRING));
		} catch (IndexOutOfBoundsException | ParseException e) {
			fail("Shouldn't raise exception! " + e.getMessage());
		}
	}

}