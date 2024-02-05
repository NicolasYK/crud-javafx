package model.entities;

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
	
}
