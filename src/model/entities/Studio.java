package model.entities;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(StudioId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Studio other = (Studio) obj;
		return Objects.equals(StudioId, other.StudioId);
	}

	@Override
	public String toString() {
		return getStudio();
	}
	
	

}
