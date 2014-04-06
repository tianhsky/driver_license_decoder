package com.util.aamva;

import java.util.HashMap;

public class Decoder {
	static final char PDF_LINEFEED = '\n';
	static final char PDF_RECORDSEP = (char) 30;
	static final char PDF_SEGTERM = '\n';
	static final String PDF_FILETYPE = "ANSI ";
	static final HashMap<String, String> fields;
	static {
		fields = new HashMap<String, String>();

		fields.put("DAA", "Name");
		fields.put("DLDAA", "Name");
		fields.put("DAB", "LastName");
		fields.put("DCS", "LastName");
		fields.put("DAC", "FirstName");
		fields.put("DCT", "FirstName");
		fields.put("DAD", "MiddleName");

		fields.put("DBC", "Sex");
		fields.put("DAU", "Height");
		fields.put("DAY", "EyeColor");

		fields.put("DAG", "Address");
		fields.put("DAI", "City");
		fields.put("DAN", "City");
		fields.put("DAJ", "State");
		fields.put("DAO", "State");
		fields.put("DAK", "ZipCode");
		fields.put("DAP", "Zipcode");
		fields.put("DCG", "Country");

		fields.put("DBB", "DOB");
		fields.put("DAQ", "DriverLicenseNumber");
		fields.put("DBD", "LicenseIssuedDate");
		fields.put("DBA", "LicenseExpirationDate");
	}

	protected HashMap headers;
	protected HashMap<String, String> subfile;

	public Decoder(String data) {
		headers = decodeHeaders(data);
		subfile = decodeSubFile(data);
	}

	public HashMap getHeaders() {
		return headers;
	}

	public HashMap<String, String> getSubFile() {
		return subfile;
	}

	public String getFileType() {
		String result = (String) getHeaders().get("FileType");
		if (result != null && !result.isEmpty()) {
			result = result.trim().toUpperCase();
		} else {
			result = "";
		}
		return result;
	}

	public int getIdentificationNumber() {
		return Integer.parseInt(getHeaders().get("IdentificationNumber").toString());
	}

	public int getVersionNumber() {
		return Integer.parseInt(getHeaders().get("VersionNumber").toString());
	}

	public int getJurisdictionVerstion() {
		return Integer.parseInt(getHeaders().get("JurisdictionVerstion").toString());
	}

	public String getSubfileType() {
		String result = (String) getHeaders().get("SubfileType");
		if (result != null && !result.isEmpty()) {
			result = result.trim();
		} else {
			result = "";
		}
		return result;
	}

	public int getSubfileOffset() {
		return Integer.parseInt(getHeaders().get("SubfileOffset").toString());
	}

	public int getSubfileLength() {
		return Integer.parseInt(getHeaders().get("SubfileLength").toString());
	}

	protected HashMap<String, String> decodeHeaders(String data) {
		HashMap hm = new HashMap();

		// declare
		char complianceIndicator, dataElementSeparator, recordSeparator, segmentTerminator;
		String fileType, entries, subfileType;
		int versionNumber, issuerIdentificationNumber, jurisdictionVerstion, offset, length;

		// extract headers
		complianceIndicator = data.charAt(0);
		dataElementSeparator = data.charAt(1);
		recordSeparator = data.charAt(2);
		segmentTerminator = data.charAt(3);
		fileType = data.substring(4, 9);
		hm.put("FileType", fileType);
		issuerIdentificationNumber = Integer.parseInt(data.substring(9, 15));
		hm.put("IdentificationNumber", issuerIdentificationNumber);
		versionNumber = Integer.parseInt(data.substring(15, 17));
		hm.put("VersionNumber", versionNumber);

		if (versionNumber <= 1) {
			entries = data.substring(17, 19);
			subfileType = data.substring(19, 21);
			offset = Integer.parseInt(data.substring(21, 25));
			length = Integer.parseInt(data.substring(25, 29));
		} else {
			jurisdictionVerstion = Integer.parseInt(data.substring(17, 19));
			hm.put("JurisdictionVerstion", jurisdictionVerstion);
			entries = data.substring(19, 21);
			subfileType = data.substring(21, 23);
			hm.put("SubfileType", subfileType);
			offset = Integer.parseInt(data.substring(23, 27));
			length = Integer.parseInt(data.substring(27, 31));
		}

		if (fileType.equals("ANSI ")) {
			offset += 2;
		}

		hm.put("SubfileOffset", offset);
		hm.put("SubfileLength", length);

		return hm;
	}

	protected HashMap<String, String> decodeSubFile(String data) {
		int offset = getSubfileOffset();
		int length = getSubfileLength();

		// subfile
		String subfile = data.substring(offset, offset + length);

		// store in name value pair
		String[] lines = subfile.split("\n");
		HashMap<String, String> hm = new HashMap<String, String>();
		for (String l : lines) {
			if (l.length() > 3) {
				String key = l.substring(0, 3);
				String value = l.substring(3);
				if (fields.get(key) != null) {
					hm.put(fields.get(key), value);
				}
			}
		}
		return hm;
	}

}
