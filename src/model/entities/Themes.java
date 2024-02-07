package model.entities;

import java.util.Objects;

public class Themes {
	
	private Integer ThemesId;
	private String Themes;
	
	public Themes(Integer themesId, String themes) {
		ThemesId = themesId;
		Themes = themes;
	}

	public Integer getThemesId() {
		return ThemesId;
	}

	public void setThemesId(Integer themesId) {
		ThemesId = themesId;
	}

	public String getThemes() {
		return Themes;
	}

	public void setThemes(String themes) {
		Themes = themes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ThemesId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Themes other = (Themes) obj;
		return Objects.equals(ThemesId, other.ThemesId);
	}

	@Override
	public String toString() {
		return getThemes();
	}
	
	
	
}
