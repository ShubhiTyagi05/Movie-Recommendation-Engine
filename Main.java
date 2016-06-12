import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Multimap;

public class Main {
	public static void main(String[] args) throws IOException {
		Recommender r = new Recommender();
		Map<String, Movie> movies = r.loadMovies("/Users/shubhityagi/Documents/RecommenderData.txt");
		List<Movie> movieList = new ArrayList<Movie>();
		for(Movie m : movies.values()){
			movieList.add(m);
		}
		r.loadReviewers("/Users/shubhityagi/Documents/RecommenderData.txt", movieList, false); // loadingtrainingreviewers
		System.out.println("Movies : " + movies.toString());
		r.loadReviewers("/Users/shubhityagi/Documents/testReviewer.txt", movieList, true); // loadingtestreviewer
		/*
		 * for(Object s : r.getReviewerMap().values() ){ s = (Reviewer)s;
		 * System.out.println(s.toString()); }
		 */
		Set<String> testReviewerSet = r.getTestReviewerMap().keySet();
		for (String name : testReviewerSet) {
			Reviewer t = (Reviewer) r.getTestReviewerMap().get(name);
			System.out.println("Finding Recommendations for : " + t.toString());
			
			//*****************************FINDING USER SIMILARITY*****************************************//
			r.evaluateSimilarReviewers(t);// Finding recommendations for reviewer t
			Multimap<Double, Reviewer> simMap = t.getSimilarity();
			List<Double> ratings = new ArrayList<Double>();
			ratings.addAll(simMap.keySet());
			Collections.sort(ratings);
			Collections.reverse(ratings);
			for (double rating : ratings) {
				System.out.println(simMap.get(rating).toString() + "    Rating : " + rating);
			}
			//**********************************FINDING MOVIE SIMILARITY*********************************//
			r.evaluateMovies(t,movieList);
			Multimap<Double, Movie> movieRatingMap = t.getMovieSimilarity();
			List<Double> movieRatings = new ArrayList<Double>();
			movieRatings.addAll(movieRatingMap.keySet());
			Collections.sort(movieRatings);
			Collections.reverse(movieRatings);
			for (double rating : movieRatings) {
				System.out.println(movieRatingMap.get(rating) + "    Rating : " + rating);
			}
		}
	}
}