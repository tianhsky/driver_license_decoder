package com.util.aamva.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.util.aamva.DriverLicense;

public class DriverLicensePATest {

	static String barCode = "";
	static String barCodeWithNo = "";
	static DriverLicense license = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String file_path = "res/txt/pa";
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
		assertEquals("State should be PA", "PA", license.getState());
	}

	@Test
	public void testHeight() {
		assertEquals("Height should be 506", 506, (int) license.getHeight());
	}

	@Test
	public void testSex() {
		assertEquals("Sex should be M", "M", license.getSex());
	}

	@Test
	public void testIssuedDate() {
		assertEquals("IssuedDate should have year 2010", 2010, license
				.getLicenseIssuedDate().get(Calendar.YEAR));
		assertEquals("IssuedDate should have month 12", 12 - 1, license
				.getLicenseIssuedDate().get(Calendar.MONTH));
		assertEquals("IssuedDate should have date 04", 4, license
				.getLicenseIssuedDate().get(Calendar.DATE));
	}

	@Test
	public void testExpDate() {
		assertEquals("EXP should have year 2015", 2015, license
				.getLicenseExpirationDate().get(Calendar.YEAR));
		assertEquals("EXP should have month 1", 1 - 1, license
				.getLicenseExpirationDate().get(Calendar.MONTH));
		assertEquals("EXP should have date 18", 18, license
				.getLicenseExpirationDate().get(Calendar.DATE));
	}

	@Test
	public void testDOB() {
		assertEquals("DOB should have year 1960", 1960,
				license.getDOB().get(Calendar.YEAR));
		assertEquals("DOB should have month 2", 2 - 1,
				license.getDOB().get(Calendar.MONTH));
		assertEquals("DOB should have date 17", 17,
				license.getDOB().get(Calendar.DATE));
	}
	
	@Test
	public void testHasExpired() {
		assertEquals("License should have not expired", false,
				license.hasExpired());
	}

	@Test
	public void testDriverLicenseNumber() {
		assertEquals("LicenseNumber should be 16117181", "16117181",
				license.getDriverLicenseNumber());
	}

	@Test
	public void testJSON() {
		System.out.println("json=");
		System.out.println(license.toJson());
	}
}
