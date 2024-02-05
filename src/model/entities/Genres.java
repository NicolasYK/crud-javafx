package model.entities;

public class Genres {
	
	private Integer GenresId;
	private String genres;
	
	public Genres(Integer GenresId, String genres) {
		this.GenresId = GenresId;
		this.genres = genres;
	}

	public Integer getGenresId() {
		return GenresId;
	}

	public void setGenresId(Integer GenresId) {
		this.GenresId = GenresId;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}
	
	

}
