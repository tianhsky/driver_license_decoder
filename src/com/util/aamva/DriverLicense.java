package com.util.aamva;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import com.google.gson.Gson;

public class DriverLicense {

	private String dataString;
	private HashMap<String, String> dataHash;
	private Decoder decoder;

	public DriverLicense(String barCode) {
		dataString = barCode;
		decoder = new Decoder(barCode);
		dataHash = decoder.getSubFile();
	}

	public Decoder getDecoder() {
		return decoder;
	}

	/**
	 * Get extracted first name or parse from name string.
	 * 
	 * @return
	 * 
	 *         Examples:
	 * 
	 *         Name: "TianYu Huang", firstName: "TianYu";
	 * 
	 *         Name: "Tian Yu Huang", firstName: "Tian";
	 * 
	 *         Name: "Tian Yu Z Huang", firstName: "Tian Yu Z";
	 * 
	 */
	public String getFirstName() {
		String firstName = dataHash.get("FirstName");
		if (firstName != null && !firstName.isEmpty()) {
			firstName = firstName.trim();
		} else {
			String name = dataHash.get("Name");
			if (name != null && !name.isEmpty()) {
				String[] nameTokens = name.split(" ");
				if (nameTokens.length <= 3) {
					firstName = nameTokens[0].trim();
				} else {
					for (int i = 1; i < nameTokens.length; i++) {
						firstName += nameTokens[i].trim();
						if (i < nameTokens.length - 1) {
							firstName += " ";
						}
					}
				}
			} else {
				firstName = "";
			}
		}
		return firstName;
	}

	/**
	 * Get extracted last name or parse from name string.
	 * 
	 * @return
	 * 
	 *         Examples:
	 * 
	 *         Name: "TianYu Huang", lastName: "Huang";
	 * 
	 *         Name: "Tian Yu Huang", lastName: "Huang";
	 * 
	 *         Name: "Tian Yu Z Huang", lastName: "Huang";
	 * 
	 */
	public String getLastName() {
		String lastName = dataHash.get("LastName");
		if (lastName != null && !lastName.isEmpty()) {
			lastName = lastName.trim();
		} else {
			String name = dataHash.get("Name");
			if (name != null && !name.isEmpty()) {
				String[] nameTokens = name.split(" ");
				if (nameTokens.length == 1) {
					lastName = "";
				} else {
					lastName = nameTokens[nameTokens.length - 1].trim();
				}
			} else {
				lastName = "";
			}
		}
		return lastName;
	}

	/**
	 * Get extracted middle name or parse from name string.
	 * 
	 * @return
	 * 
	 *         Examples:
	 * 
	 *         Name: "TianYu Huang", middleName: "";
	 * 
	 *         Name: "Tian Yu Huang", middleName: "Yu";
	 * 
	 *         Name: "Tian Yu Z Huang", middleName: "";
	 * 
	 */
	public String getMiddleName() {
		String middleName = dataHash.get("MiddleName");
		if (middleName != null && !middleName.isEmpty()) {
			middleName = middleName.trim();
		} else {
			String name = dataHash.get("Name");
			if (name != null && !name.isEmpty()) {
				String[] nameTokens = name.split(" ");
				if (nameTokens.length == 3) {
					middleName = nameTokens[1].trim();
				} else {
					middleName = "";
				}
			} else {
				middleName = "";
			}
		}
		return middleName;
	}

	/**
	 * Get extracted state
	 * 
	 * @return 2-Letter state abbreviations
	 */
	public String getState() {
		String state = dataHash.get("State");
		if (state != null && !state.isEmpty()) {
			state = state.trim().toUpperCase();
		} else {
			state = "";
		}
		return state;
	}

	/**
	 * Get extracted address
	 * 
	 * @return Address
	 */
	public String getAddress() {
		String address = dataHash.get("Address");
		if (address != null && !address.isEmpty()) {
			address = address.trim();
		} else {
			address = "";
		}
		return address;
	}

	/**
	 * Get extracted city
	 * 
	 * @return City
	 */
	public String getCity() {
		String city = dataHash.get("City");
		if (city != null && !city.isEmpty()) {
			city = city.trim();
		} else {
			city = "";
		}
		return city;
	}

	/**
	 * Get extracted ZIP code
	 * 
	 * @return ZIP code
	 */
	public String getZipCode() {
		String zipCode = dataHash.get("ZipCode");
		if (zipCode != null && !zipCode.isEmpty()) {
			zipCode = zipCode.trim();
		} else {
			zipCode = "";
		}
		return zipCode;
	}

	/**
	 * Get extracted country
	 * 
	 * @return Country
	 */
	public String getCountry() {
		String country = dataHash.get("Country");
		if (country != null && !country.isEmpty()) {
			country = country.trim().toUpperCase();
		} else {
			country = "";
		}
		return country;
	}

	/**
	 * Get extracted eye color
	 * 
	 * @return Eye color
	 */
	public String getEyeColor() {
		String eyeColor = dataHash.get("EyeColor");
		if (eyeColor != null && !eyeColor.isEmpty()) {
			eyeColor = eyeColor.trim();
		} else {
			eyeColor = "";
		}
		return eyeColor;
	}

	/**
	 * Get extracted driver's license number
	 * 
	 * @return Driver's license number
	 */
	public String getDriverLicenseNumber() {
		String licenseNumber = dataHash.get("DriverLicenseNumber");
		if (licenseNumber != null && !licenseNumber.isEmpty()) {
			licenseNumber = licenseNumber.trim().replaceAll("[.]", "");
		} else {
			licenseNumber = "";
		}
		return licenseNumber;
	}

	/**
	 * Get sex
	 * 
	 * @return Sex
	 */
	public String getSex() {
		String sex = dataHash.get("Sex");
		if (sex != null && !sex.isEmpty()) {
			sex = sex.trim();
			if (sex.equals("1")) {
				sex = "M";
			} else if (sex.equals("2")) {
				sex = "F";
			} else {
				sex = sex.toUpperCase();
			}
		} else {
			sex = "";
		}
		return sex;
	}

	/**
	 * Get parsed DOB
	 * 
	 * @return DOB
	 */
	public Calendar getDOB() {
		Calendar calendar = null;
		String dob = dataHash.get("DOB");
		if (dob != null && !dob.isEmpty()) {
			calendar = parseDate(dob);
		} else {
			// Not found
		}
		return calendar;
	}

	/**
	 * Get parsed LicenseIssuedDate
	 * 
	 * @return LicenseIssuedDate
	 */
	public Calendar getLicenseIssuedDate() {
		Calendar calendar = null;
		String licenseIssuedDate = dataHash.get("LicenseIssuedDate");
		if (licenseIssuedDate != null && !licenseIssuedDate.isEmpty()) {
			calendar = parseDate(licenseIssuedDate);
		} else {
			// Not found
		}
		return calendar;
	}

	/**
	 * Get parsed LicenseExpirationDate
	 * 
	 * @return LicenseExpirationDate
	 */
	public Calendar getLicenseExpirationDate() {
		Calendar calendar = null;
		String licenseExpirationDate = dataHash.get("LicenseExpirationDate");
		if (licenseExpirationDate != null && !licenseExpirationDate.isEmpty()) {
			calendar = parseDate(licenseExpirationDate);
		} else {
			// Not found
		}
		return calendar;
	}

	/**
	 * Get parsed Height
	 * 
	 * @return Height
	 */
	public float getHeight() {
		String height = dataHash.get("Height");
		if (height != null && !height.isEmpty()) {
			height = height.trim().replaceAll("[\\D]", "");
		} else {
			height = "";
		}
		return Float.parseFloat(height);
	}
	
	public boolean hasExpired(){
		return getLicenseExpirationDate().compareTo(Calendar.getInstance()) < 0;
	}

	/**
	 * Get current object representation in JSON string
	 * 
	 * @return Serialized string in JSON
	 */
	public String toJson() {
		String json = "";
		Gson gson = new Gson();

		HashMap jsonHash = new HashMap();
		jsonHash.put("first_name", getFirstName());
		jsonHash.put("last_name", getLastName());
		jsonHash.put("address", getAddress());
		jsonHash.put("city", getCity());
		jsonHash.put("state", getState());
		jsonHash.put("zipcode", getZipCode());
		jsonHash.put("driver_license_number", getDriverLicenseNumber());
		jsonHash.put("eye_color", getEyeColor());
		jsonHash.put("height", getHeight());
		jsonHash.put("sex", getSex());

		jsonHash.put("dob", formatDate(getDOB()));
		jsonHash.put("license_issued_date", formatDate(getLicenseIssuedDate()));
		jsonHash.put("license_expiration_date",
				formatDate(getLicenseExpirationDate()));

		json = gson.toJson(jsonHash);
		return json;
	}

	// -----------------------------------------------------------------------//

	protected Calendar parseDate(String date) {
		String format = "ISO";
		int potentialYear = Integer.parseInt(date.substring(0, 4));
		if (potentialYear > 1300) {
			format = "Other";
		}

		// Parse calendar based on format
		int year, month, day;
		Calendar calendar = Calendar.getInstance();
		if (format.equals("ISO")) {
			year = Integer.parseInt(date.substring(4, 8));
			month = Integer.parseInt(date.substring(0, 2));
			day = Integer.parseInt(date.substring(2, 4));
		} else {
			year = Integer.parseInt(date.substring(0, 4));
			month = Integer.parseInt(date.substring(4, 6));
			day = Integer.parseInt(date.substring(6, 8));
		}
		calendar.set(year, month - 1, day, 0, 0, 0);
		return calendar;
	}

	protected String formatDate(Calendar date) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		result = sdf.format(date.getTime());
		return result;
	}
}
