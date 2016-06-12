import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class Recommender {

	Map<String, Reviewer> reviewerMap = new HashMap<String, Reviewer>();
	Map<String, Reviewer> testReviewerMap = new HashMap<String, Reviewer>();

	Recommender() {
	}

	public void evaluateSimilarReviewers(Reviewer testReviewer) {
		for (Reviewer reviewer : reviewerMap.values()) {
			List<Double> x = new ArrayList<Double>();
			List<Double> y = new ArrayList<Double>();
			for (Movie movie : testReviewer.getRatings().keySet()) {
				if (reviewer.getRatings().keySet().contains(movie)) {
					x.add(testReviewer.getRatings().get(movie));
					y.add(reviewer.getRatings().get(movie));
				}
			}
			PearsonCorelation pc = new PearsonCorelation(x, y);
			testReviewer.addSimilarity(reviewer, pc.getCorelation());
		}
	}

	public void loadReviewers(String filePath, List<Movie> movies, boolean testReviewer) throws IOException {
		List<String> lines = FileUtils.readLines(new File(filePath), "UTF-8");
		for (String line : lines) {
			List<String> reviewerNratings = Splitter.on("{")
					.splitToList(line.replace("\u2019", "").replace("'", "").replaceAll("}", ""));
			String reviewerName = reviewerNratings.get(0).substring(0, reviewerNratings.get(0).length() - 2);
			Reviewer reviewer = new Reviewer(reviewerName);
			System.out.println("Reviewer : " + reviewer.getName());
			List<String> ratings = Splitter.on("|").splitToList(reviewerNratings.get(1));
			for (String rating : ratings) {
				boolean movieAdded = false;
				List<String> movieRating = Splitter.on(":").splitToList(rating);
				String movieName = movieRating.get(0).toLowerCase().trim();
				String ratingValue = movieRating.get(1).replaceAll(",", "").replaceAll("\u002C", "");
				for (Movie movie : movies) {
					if (movie.getName().equals(movieName)) {
						reviewer.addRating(movie, Double.valueOf(ratingValue));
						movie.addRating(Double.valueOf(ratingValue));
						movieAdded = true;
						break;
					}
				}
				if (testReviewer == true && movieAdded == false) {
					Movie movie = new Movie(movieName);
					reviewer.addRating(movie, Double.valueOf(ratingValue));
				}
			}
			if (testReviewer == false) {
				reviewerMap.put(reviewer.getName(), reviewer);
			} else {
				testReviewerMap.put(reviewer.getName(), reviewer);
			}
		}
	}

	public Map<String, Movie> loadMovies(String filePath) throws IOException {
		//List<Movie> movies = new ArrayList<Movie>();
		Map<String,Movie> movies = new HashMap<String,Movie>();
		List<String> lines = FileUtils.readLines(new File(filePath));
		Set<String> movieNames = new HashSet<String>();
		for (String line : lines) {
			List<String> reviewer = Splitter.on("{").trimResults().omitEmptyStrings()
					.splitToList(line.replace("'", "").replace("\u2019", "").toLowerCase());
			List<String> ratings = Splitter.on("|").trimResults().omitEmptyStrings().splitToList(reviewer.get(1));
			for (String r : ratings) {
				String name = Splitter.on(":").trimResults().omitEmptyStrings().splitToList(r).get(0);
				if (!(movieNames.contains(name.trim()))) {
					movieNames.add(name);
					Movie movie = new Movie(name);
					//movies.add(movie);
					movies.put(name,movie);
				}
			}
		}
		return movies;
	}

	public void evaluateMovies(Reviewer testReviewer, List<Movie> movies) {
		Set<Movie> testRevMovies = testReviewer.getRatings().keySet();
		for (Movie movie : movies) {
			if (!(testRevMovies.contains(movie))) {
				testReviewer.addMovieSimilarity(movie, getRating(testReviewer, movie));
			}
		}
	}

	private Double getRating(Reviewer testReviewer, Movie movie) {
		double rating = 0.00;
		double totalSim = 0.00;
		double movieRating = 0.00;
		for (Reviewer r : testReviewer.getSimilarity().values()) {
			double reviewerSim = getReviewerRating(testReviewer, r);
			
			double reviewerRating = 0.00;
			if (r.getRatings().keySet().contains(movie)) {
				totalSim += reviewerSim;
				reviewerRating+=r.getRatings().get(movie);
			}
			movieRating += (reviewerRating * reviewerSim);
		}
		if (totalSim != 0.0) {
			rating = movieRating / totalSim;
		} else {
			System.out.println("Similarity Total = 0.0");
		}
		return rating;
	}

	private double getReviewerRating(Reviewer testReviewer, Reviewer reviewer) {
		double sim = 0.00;
		for (double d : testReviewer.getSimilarity().keySet()) {
			for (Reviewer r : testReviewer.getSimilarity().get(d)) {
				if (r.equals(reviewer)) {
					sim = d;
					break;
				}
			}
		}
		return sim;
	}

	public Map getReviewerMap() {
		return this.reviewerMap;
	}

	public Map getTestReviewerMap() {
		return this.testReviewerMap;
	}
}
