package Other;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by KrishnChinya on 4/22/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MapsResultJson {
    @JsonProperty("driving")
    public driving driving;

    @JsonProperty("bicycling")
    public driving bicycling;

    @JsonProperty("transit")
    public driving transit;

    @JsonProperty("walking")
    public driving walking;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public final static class driving {
        @JsonProperty("legs")
        public List<legs> legs;


        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class legs {
            @JsonProperty("distance")
            public distance distance;

            @JsonProperty("duration")
            public duration duration;
            @JsonProperty("duration_in_traffic")
            public duration_in_traffic duration_in_traffic;
            @JsonProperty("end_location")
            public end_location end_location;
            @JsonProperty("start_location")
            public start_location start_location;


            @JsonProperty("start_address")
            String start_address;
            @JsonProperty("end_address")
            String end_address;


            public static class distance{
                @JsonProperty("text")
                String text;
                @JsonProperty("value")
                String value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public final static class duration {
                @JsonProperty("text")
                String text;
                @JsonProperty("value")
                String value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public final static class duration_in_traffic {
                @JsonProperty("text")
                String text;
                @JsonProperty("value")
                String value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }


            public final static class end_location {
                @JsonProperty("lat")
                String lat;
                @JsonProperty("lng")
                String lng;

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getLng() {
                    return lng;
                }

                public void setLng(String lng) {
                    this.lng = lng;
                }
            }

            public final static class start_location {
                @JsonProperty("lat")
                String lat;
                @JsonProperty("lng")
                String lng;

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getLng() {
                    return lng;
                }

                public void setLng(String lng) {
                    this.lng = lng;
                }
            }


            public String getStart_address() {
                return start_address;
            }

            public void setStart_address(String start_address) {
                this.start_address = start_address;
            }

            public String getEnd_address() {
                return end_address;
            }

            public void setEnd_address(String end_address) {
                this.end_address = end_address;
            }
        }

        @JsonProperty("overview_polyline")
        public overview_polyline overview_polyline;

        public final static class overview_polyline
        {
            @JsonProperty("points")
            ArrayList<ArrayList<String>> points;

            public ArrayList<ArrayList<String>> getPoints() {
                return points;
            }

            public void setPoints(ArrayList<ArrayList<String>> points) {
                this.points = points;
            }
        }

    }

    @JsonProperty("res")
    public ArrayList<res> res;

    public ArrayList<MapsResultJson.res> getRes() {
        return res;
    }

    public void setRes(ArrayList<MapsResultJson.res> res) {
        this.res = res;
    }

    public static final class res{
        @JsonProperty("dest")
        public String dest;
        @JsonProperty("src")
        public String src;
        @JsonProperty("time")
        public String  time;
        @JsonProperty("userid")
        public String userid;

        public String getDest() {
            return dest;
        }

        public void setDest(String dest) {
            this.dest = dest;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }

}

