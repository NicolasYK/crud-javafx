package model.entities;

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
	
	
	
}
