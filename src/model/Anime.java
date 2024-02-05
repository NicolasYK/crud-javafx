package model;

public class Anime {
	
	private Integer Id;
	private String Title;
	private String Genres;
	private String Demographics;
	private String Studio;
	public Anime(Integer id, String title, String genres, 
			String demographics, String studio) {
		Id = id;
		Title = title;
		Genres = genres;
		Demographics = demographics;
		Studio = studio;
	}
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
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
	
	
	

}
