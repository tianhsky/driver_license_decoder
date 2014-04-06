package com.util.aamva.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.util.aamva.DriverLicense;

public class DriverLicenseNJTest {

	static String barCode = "";
	static String barCodeWithNo = "";
	static DriverLicense license = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String file_path = "res/txt/nj";
		BufferedReader br = null;
		int cursor = -1;
		br = new BufferedReader(new FileReader(file_path));
		int i = 0;
		while ((cursor = br.read()) != -1) {
			barCodeWithNo += "(" + i + ")" + (char) cursor;
			barCode += (char) cursor;
			i++;
		}
		license = new DriverLicense(barCode);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testState() {
		assertEquals("State should be NJ", "NJ", license.getState());
	}

	@Test
	public void testHeight() {
		assertEquals("Height should be 67", 67, (int) license.getHeight());
	}

	@Test
	public void testSex() {
		assertEquals("Sex should be M", "M", license.getSex());
	}

	@Test
	public void testIssuedDate() {
		assertEquals("IssuedDate should have year 2010", 2012, license
				.getLicenseIssuedDate().get(Calendar.YEAR));
		assertEquals("IssuedDate should have month 7", 5 - 1, license
				.getLicenseIssuedDate().get(Calendar.MONTH));
		assertEquals("IssuedDate should have date 23", 24, license
				.getLicenseIssuedDate().get(Calendar.DATE));
	}

	@Test
	public void testExpDate() {
		assertEquals("EXP should have year 2013", 2013, license
				.getLicenseExpirationDate().get(Calendar.YEAR));
		assertEquals("EXP should have month 5", 5 - 1, license
				.getLicenseExpirationDate().get(Calendar.MONTH));
		assertEquals("EXP should have date 1", 1, license
				.getLicenseExpirationDate().get(Calendar.DATE));
	}

	@Test
	public void testDOB() {
		assertEquals("DOB should have year 1962", 1962,
				license.getDOB().get(Calendar.YEAR));
		assertEquals("DOB should have month 10", 10 - 1,
				license.getDOB().get(Calendar.MONTH));
		assertEquals("DOB should have date 26", 26,
				license.getDOB().get(Calendar.DATE));
	}

	@Test
	public void testHasExpired() {
		assertEquals("License should have expired", true,
				license.hasExpired());
	}

	@Test
	public void testDriverLicenseNumber() {
		assertEquals("LicenseNumber should be D46220533109611",
				"D46220533109611", license.getDriverLicenseNumber());
	}

	@Test
	public void testJSON() {
		System.out.println("json=");
		System.out.println(license.toJson());
	}
}
