package nations;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Country {

	private int countryId;
	private String name;
	private BigDecimal area;
	private LocalDate nationalDay;
	private String countryCode2;
	private String countryCode3;
	private int regionId;
	
	
	public Country(int countryId, String name, BigDecimal area, LocalDate nationalDay, String countryCode2,
			String countryCode3, int regionId) {
		super();
		this.countryId = countryId;
		this.name = name;
		this.area = area;
		this.nationalDay = nationalDay;
		this.countryCode2 = countryCode2;
		this.countryCode3 = countryCode3;
		this.regionId = regionId;
	}


	public int getCountryId() {
		return countryId;
	}


	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public BigDecimal getArea() {
		return area;
	}


	public void setArea(BigDecimal area) {
		this.area = area;
	}


	public LocalDate getNationalDay() {
		return nationalDay;
	}


	public void setNationalDay(LocalDate nationalDay) {
		this.nationalDay = nationalDay;
	}


	public String getCountryCode2() {
		return countryCode2;
	}


	public void setCountryCode2(String countryCode2) {
		this.countryCode2 = countryCode2;
	}


	public String getCountryCode3() {
		return countryCode3;
	}


	public void setCountryCode3(String countryCode3) {
		this.countryCode3 = countryCode3;
	}


	public int getRegionId() {
		return regionId;
	}


	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	
	
}
