# Driver License Barcode Decoder (Java)

Java library to decode barcode string from driver's license that follows the [American Association of Motor Vehicle Administrators(aamva)](http://www.aamva.org/) standard.

## Requirements

- JRE 1.6+

## How to use

		DriverLicense dl = new DriverLicense(barCode);
		String dl_number = license.getDriverLicenseNumber();
		String dl_fname = license.getFirstName();
		String dl_lname = license.getLastName();
		//etc...

## Test

- Tested on NJ, NY, PA licenses
- Should work with most states

## Keywords

- java, android, dln, dmv, driver's license, decoder, barcode, scanner

## References

- [DL/ID Card Design Standard](http://www.aamva.org/WorkArea/DownloadAsset.aspx?id=4435)
