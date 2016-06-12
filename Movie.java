import java.util.ArrayList;
import java.util.List;

public class Movie {
	String name = new String();
    double averageRating=0.0;
    List<Double> ratings = new ArrayList<Double>();
	
	Movie(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public String getName() {
		return this.name;
	}
	
	public Double getAvgRating(){
		Double rating = 0.00;
	    if(ratings.size()>0){
		for(Double r : ratings){
	    	rating+=r;
	    }
	    rating = rating/(double)ratings.size();
	    }
	    return rating;
	}
  
	 public void addRating(Double r ){
		 this.ratings.add(r);
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
		Movie other = (Movie) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
