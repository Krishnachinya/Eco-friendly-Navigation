package com.krishnchinya.hackdfw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.widget.Filter;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Other.MapsResultJson;
import Other.RetrofitObjectAPI;
import Other.YelpResultJson;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainMenu extends Activity implements AdapterView.OnItemClickListener, OnMapReadyCallback{
    TextView textView;
    GlobalVars globalVars;
    AutoCompleteTextView from,to;
    Button retrive;
    Retrofit retrofit;
    GoogleMap mMap;
    ArrayList<String> priority = new ArrayList<>();
    ArrayList<String> emission = new ArrayList<>();
    ArrayList<String> calorie = new ArrayList<>();

    ArrayList<String> rides = new ArrayList<>();

    private RecyclerView modeRecyclerView,RideRecyclerView;



    private static final String LOG_TAG = "ExampleApp";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    //------------ make your specific key ------------
    private static final String API_KEY = "AIzaSyAgGPjJkn2YgzkAAngqD1ghfTqv9pu2KEs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        globalVars = (GlobalVars) getApplicationContext();

        //textView = (TextView) findViewById(R.id.welcome);
        //textView.setText("Hello "+globalVars.getUsername());

        modeRecyclerView = (RecyclerView) findViewById(R.id.modes);
        RideRecyclerView = (RecyclerView) findViewById(R.id.rides);

        modeRecyclerView.setHasFixedSize(true);
        RideRecyclerView.setHasFixedSize(true);

        // mLayoutManager=new LinearLayoutManager();
        modeRecyclerView.setLayoutManager(new LinearLayoutManager(MainMenu.this, LinearLayout.VERTICAL,false));
        RideRecyclerView.setLayoutManager(new LinearLayoutManager(MainMenu.this, LinearLayout.VERTICAL,false));


        retrive = (Button) findViewById(R.id.Retrive);

        retrofit = new Retrofit.Builder().
                baseUrl("http://ec2-54-202-171-166.us-west-2.compute.amazonaws.com:5001/hackdfw/location/").
                addConverterFactory(JacksonConverterFactory.create())
                .client(getRequestHeader())
                .build();



        from = (AutoCompleteTextView) findViewById(R.id.From);

        from.setAdapter(new GooglePlacesAutocompleteAdapter(this,R.layout.list_item));
        from.setOnItemClickListener(this);

        to = (AutoCompleteTextView) findViewById(R.id.To);

        to.setAdapter(new GooglePlacesAutocompleteAdapter(this,R.layout.list_item));
        to.setOnItemClickListener(this);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);



        retrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String From = from.getText().toString();
            String To = to.getText().toString();

                RetrofitObjectAPI object = retrofit.create(RetrofitObjectAPI.class);
                Call<MapsResultJson> call = object.getFromTo(From,To);

                call.enqueue(new Callback<MapsResultJson>() {
                    @Override
                    public void onResponse(Call<MapsResultJson> call, Response<MapsResultJson> response) {

                        MapsResultJson mapsResultJson = response.body();

                        drawPolylines(mapsResultJson.walking,Color.BLUE,"Walking",mapsResultJson);
                        drawPolylines(mapsResultJson.bicycling,Color.GREEN,"Cycling",mapsResultJson);
                        drawPolylines(mapsResultJson.transit,Color.YELLOW,"Transit",mapsResultJson);
                        drawPolylines(mapsResultJson.driving,Color.RED,"Driving",mapsResultJson);

                        myModeAdapter myModeAdapter = new myModeAdapter(priority,emission,calorie);
                        modeRecyclerView.setAdapter(myModeAdapter);

                        myRideAdapter myRideAdapter = new myRideAdapter(rides);
                        RideRecyclerView.setAdapter(myRideAdapter);

                    }

                    @Override
                    public void onFailure(Call<MapsResultJson> call, Throwable t) {
                        int a = 10;
                    }
                });

            }

        });

        mapFragment.getMapAsync(this);
    }
//
//    i*355 = grams of CO2 emission reduced
//    i*42 = cal  per mile cycled
//    i*8 = cal per mile walked

    public void drawPolylines(MapsResultJson.driving mapsResultJson,int color,String mode,MapsResultJson mapsResultJson1) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (mapsResultJson!=null && mapsResultJson.legs.size() != 0) {
            priority.add(mode);

            emission.add(""+(Integer.valueOf(mapsResultJson.legs.get(0).distance.getValue())/1600)*355);

            if(mode == "Cycling")
            {
                calorie.add(""+(Integer.valueOf(mapsResultJson.legs.get(0).distance.getValue())/1600)*42);

            }else if(mode == "Walking")
            {
                calorie.add(""+(Integer.valueOf(mapsResultJson.legs.get(0).distance.getValue())/1600)*84);
            } else if(mode == "Transit"){
                calorie.add(""+(Integer.valueOf(mapsResultJson.legs.get(0).distance.getValue())/1600)*12);
            } else {
                calorie.add("0");
                emission.add(3,"0");
            }
            for (int i = 0; i < mapsResultJson.overview_polyline.getPoints().size(); i++) {
                for (int j = mapsResultJson.overview_polyline.getPoints().get(i).size() - 1;
                     j >= 0; j--) {
                    arrayList.add(mapsResultJson.overview_polyline.getPoints().get(i).get(j));
                }
            }
            overlay(arrayList, color);
        }

        if(mapsResultJson1.res!=null && mapsResultJson1.res.size()!=0)
        {
            for(int i=0;i<mapsResultJson1.res.size();i++)
            {
                rides.add(mapsResultJson1.res.get(i).dest);
                rides.add(mapsResultJson1.res.get(i).src);
                rides.add(mapsResultJson1.res.get(i).time);
            }
        }


    }



    public OkHttpClient getRequestHeader() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        return okHttpClient;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


    public void overlay(ArrayList<String> arrayList,int color)
    {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        RetrofitObjectAPI object = retrofit.create(RetrofitObjectAPI.class);



        // Polylines are useful for marking paths and routes on the map.
        if(arrayList.size() != 0) {
            PolylineOptions polylineOptions = new PolylineOptions().geodesic(true);
            for (int i = 0; i < arrayList.size(); i = i + 2) {
                latLngs.add(new LatLng(Double.valueOf(arrayList.get(i)), Double.valueOf(arrayList.get(i + 1))));
                if(i==0 | i == arrayList.size()-2) {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.valueOf(arrayList.get(i)),
                                    Double.valueOf(arrayList.get(i + 1)))));
                    if(i==0){
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(Double.valueOf(arrayList.get(i)),
                                    Double.valueOf(arrayList.get(i + 1))), 15));
                }
                }

            }
            polylineOptions.addAll(latLngs);
            polylineOptions.width(10+(color/1000000000));
            polylineOptions.color(color);
            polylineOptions.visible(true);


            Polyline polyline = mMap.addPolyline(polylineOptions);

            retrofit = new Retrofit.Builder().
                    baseUrl("http://ec2-54-202-171-166.us-west-2.compute.amazonaws.com:5001/hackdfw/restaurants/").
                    addConverterFactory(JacksonConverterFactory.create())
                    .client(getRequestHeader())
                    .build();

            if(color == Color.RED) {

                RetrofitObjectAPI object1 = retrofit.create(RetrofitObjectAPI.class);
                Call<YelpResultJson> call1 = object1.getYelp(arrayList.get(0), arrayList.get(1));


                call1.enqueue(new Callback<YelpResultJson>() {
                    @Override
                    public void onResponse(Call<YelpResultJson> call, Response<YelpResultJson> response) {
                        ArrayList<String> arrayList = new ArrayList<>();
                        YelpResultJson yelpResultJson = response.body();
                        for (int i = 0; i < yelpResultJson.result.size(); i++) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.valueOf(yelpResultJson.result.get(i).
                                            location.get(0)), Double.valueOf(yelpResultJson.result.get(i).location.get(1))))
                                    .title(yelpResultJson.result.get(i).name)
                                    .snippet(yelpResultJson.result.get(i).rating));
                        }

                    }

                    @Override
                    public void onFailure(Call<YelpResultJson> call, Throwable t) {
                        int a = 30;
                    }
                });

            }
        }
    }



    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }



    public static ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            System.out.println("URL: "+url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

}

class myModeAdapter extends RecyclerView.Adapter<modesViewHolder>
{
    ArrayList<String> priority;
    ArrayList<String> emission;
    ArrayList<String> calories;
    public myModeAdapter(ArrayList<String> priority, ArrayList<String> emission, ArrayList<String> calories) {

        this.priority = priority;
        this.emission = emission;
        this.calories = calories;
    }

    @Override
    public modesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.modecardview,parent,false);

        modesViewHolder vh =new modesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(modesViewHolder holder, int position) {
        holder.textView.setText(priority.get(position)+ " CO2 -"+ emission.get(position)+" Cal -"+calories.get(position) );
        if(position == 0)
        {
            holder.textView.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return priority.size();
    }
}

class modesViewHolder extends  RecyclerView.ViewHolder{
    TextView textView,modetype2;
    public modesViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.modetype1);
        //modetype2 = (TextView) itemView.findViewById(R.id.modetype2);
    }
}


class myRideAdapter extends RecyclerView.Adapter<rideViewHolder>
{
    ArrayList<String> Rides;

    public myRideAdapter(ArrayList<String> Rides) {

        this.Rides = Rides;
    }

    @Override
    public rideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ridecardview,parent,false);

        rideViewHolder vh =new rideViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(rideViewHolder holder, int position) {
        holder.textView.setText("From:"+Rides.get(position)+" To:"+Rides.get(position+1)+"Time:"+Rides.get(position));
    }

    @Override
    public int getItemCount() {
        return Rides.size()/3;
    }
}

class rideViewHolder extends  RecyclerView.ViewHolder{
    TextView textView,modetype2;
    public rideViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.modetype1);
        //modetype2 = (TextView) itemView.findViewById(R.id.modetype2);
    }
}