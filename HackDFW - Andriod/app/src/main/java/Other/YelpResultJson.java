package Other;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KrishnChinya on 4/22/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class YelpResultJson {
    @JsonProperty("result")
    public List<result> result;


    public final static class result{

        @JsonProperty("location")
        public ArrayList<String> location;

        public ArrayList<String> getLocation() {
            return location;
        }

        public void setLocation(ArrayList<String> location) {
            this.location = location;
        }

        @JsonProperty("name")
        public String name;


        @JsonProperty("rating")
        public String  rating;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }


    }





}
