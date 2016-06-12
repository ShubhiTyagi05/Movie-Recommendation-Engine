import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class Reviewer {
	String name=null;
	Map<Movie, Double> ratings = new HashMap<Movie, Double>();
	Multimap<Double, Reviewer> similarity = ArrayListMultimap.create();
	Multimap<Double, Movie> movieSimilarity = ArrayListMultimap.create();
	
	Reviewer() {
		name = new String();
	}

	Reviewer(String name) {
		this();
		this.name = name;
	}

	public Map<Movie, Double> getRatings() {
		return ratings;
	}

	public void addRating(Movie movie, Double rating) {
		this.ratings.put(movie, rating);
	}

	
	public Multimap<Double, Reviewer> getSimilarity() {
		return similarity;
	}

	public void addSimilarity(Reviewer reviewer, Double similarity) {
		this.similarity.put(similarity, reviewer);
	}

	public Multimap<Double, Movie> getMovieSimilarity() {
		return this.movieSimilarity;
	}

	public void addMovieSimilarity(Movie movie, Double rating) {
		this.movieSimilarity.put(rating, movie);
	}
	public String getName() {
		return this.name;
	}

	public String toString() {
		StringBuffer text = new StringBuffer();
		text.append("\n-----------------------\n");
		text.append("Name : " + this.getName() + "\n");
		for (Movie movie : ratings.keySet()) {
			text.append(movie.toString() + "  :  " + this.ratings.get(movie) + "\n");
		}
		text.append("-----------xxx----------");
		return text.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reviewer other = (Reviewer) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
