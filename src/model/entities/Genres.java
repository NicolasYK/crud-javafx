package model.entities;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(GenresId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genres other = (Genres) obj;
		return Objects.equals(GenresId, other.GenresId);
	}

	@Override
	public String toString() {
		return getGenres();
	}
	
	

}
