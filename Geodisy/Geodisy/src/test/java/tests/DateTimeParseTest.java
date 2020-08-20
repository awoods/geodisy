package tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static Dataverse.DataverseJSONFieldClasses.Fields.CitationSimpleJSONFields.Date.checkDateString;



public class DateTimeParseTest{
    String dateOnly = "2015-07-13";
    String dateTime = "2014-07-13T11:02:21Z";
    String dateBC = "-0002-01-02";
    String dateTimeBC = "-1000-11-20T03:41:11Z";
    String yearOnly = "1020";
    String yearMonth = "3021-12";
    String yearOnlyBC = "-0509";
    String badDate = "Product Date";


    @Test
    public void testDateTimeParsing(){

        assertEquals(checkDateString(dateOnly).toString(), "2015-07-13");
        assertEquals(checkDateString(dateTime).toString(), "2014-07-13T11:02:21Z");
        assertEquals(checkDateString(dateBC).toString(), "-0002-01-02");
        assertEquals(checkDateString(dateTimeBC).toString(), "-1000-11-20T03:41:11Z");
        assertEquals(checkDateString(yearOnly).toString(), "1020");
        assertEquals(checkDateString(yearOnlyBC).toString(), "-509");
        assertEquals(checkDateString(yearMonth).toString(), "3021-12");
        assertEquals(checkDateString(badDate).toString(), "9999");

    }
}
