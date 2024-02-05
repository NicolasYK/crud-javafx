package model.entities;

public class Anime {
	
	private Integer AnimeId;
	private String Title;
	private String Genres;
	private String Themes;
	private String Demographics;
	private String Studio;
	public Anime(Integer id, String title, String genres,String themes, 
			String demographics, String studio) {
		AnimeId = id;
		Title = title;
		Genres = genres;
		Themes = themes;
		Demographics = demographics;
		Studio = studio;
	}
	
	public Integer getAnimeId() {
		return AnimeId;
	}
	
	public void setAnimeId(Integer id) {
		AnimeId = id;
	}
	
	public String getTitle() {
		return Title;
	}
	
	public void setTitle(String title) {
		Title = title;
	}
	
	public String getGenres() {
		return Genres;
	}
	
	public void setGenres(String genres) {
		Genres = genres;
	}
	
	public String getDemographics() {
		return Demographics;
	}
	
	public void setDemographics(String demographics) {
		Demographics = demographics;
	}
	
	public String getStudio() {
		return Studio;
	}
	
	public void setStudio(String studio) {
		Studio = studio;
	}
	
	public String getThemes() {
		return Themes;
	}
	
	public void setThemes(String themes) {
		Themes = themes;
	}

}
