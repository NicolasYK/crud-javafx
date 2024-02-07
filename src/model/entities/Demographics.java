package model.entities;

import java.util.Objects;

public class Demographics {
	
	private Integer DemographicId;
	private String Demographic;
	
	public Demographics(Integer demographicId, String demographic) {
		DemographicId = demographicId;
		Demographic = demographic;
	}
	
	public Integer getDemographicId() {
		return DemographicId;
	}
	
	public void setDemographicId(Integer demographicId) {
		DemographicId = demographicId;
	}
	
	public String getDemographic() {
		return Demographic;
	}
	
	public void setDemographic(String demographic) {
		Demographic = demographic;
	}

	@Override
	public int hashCode() {
		return Objects.hash(DemographicId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Demographics other = (Demographics) obj;
		return Objects.equals(DemographicId, other.DemographicId);
	}

	@Override
	public String toString() {
		return getDemographic();
	}

	
	
}
