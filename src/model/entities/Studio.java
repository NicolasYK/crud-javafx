package model.entities;

public class Studio {
	
	private Integer StudioId;
	private String Studio;
	
	public Studio(Integer studioId, String studio) {
		StudioId = studioId;
		Studio = studio;
	}

	public Integer getStudioId() {
		return StudioId;
	}

	public void setStudioId(Integer studioId) {
		StudioId = studioId;
	}

	public String getStudio() {
		return Studio;
	}

	public void setStudio(String studio) {
		Studio = studio;
	}
	
	

}
