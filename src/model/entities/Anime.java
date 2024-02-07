package model.entities;

import java.util.Objects;

public class Anime {
	
	private Integer AnimeId;
	private String Title;
	private Genres Genres;
	private Themes Themes;
	private Demographics Demographics;
	private Studio Studio;
	
	public Anime() {}
	
	public Anime(Integer animeId, String title, Genres genres, Themes themes,
			Demographics demographics, Studio studio) {
		AnimeId = animeId;
		Title = title;
		Genres = genres;
		Themes = themes;
		Demographics = demographics;
		Studio = studio;
	}

	public Integer getAnimeId() {
		return AnimeId;
	}

	public void setAnimeId(Integer animeId) {
		AnimeId = animeId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public Genres getGenres() {
		return Genres;
	}

	public void setGenres(Genres genres) {
		Genres = genres;
	}

	public Themes getThemes() {
		return Themes;
	}

	public void setThemes(Themes themes) {
		Themes = themes;
	}

	public Demographics getDemographics() {
		return Demographics;
	}

	public void setDemographics(Demographics demographics) {
		Demographics = demographics;
	}

	public Studio getStudio() {
		return Studio;
	}

	public void setStudio(Studio studio) {
		Studio = studio;
	}

	@Override
	public int hashCode() {
		return Objects.hash(AnimeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anime other = (Anime) obj;
		return Objects.equals(AnimeId, other.AnimeId);
	}
	
	
	
	
}
