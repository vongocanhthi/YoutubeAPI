package com.anhthi.youtubeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anhthi.youtubeapi.adapter.VideoAdapter;
import com.anhthi.youtubeapi.model.Video;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends YouTubeBaseActivity {
    public static String API_KEY = "AIzaSyCV2y7kieJ1oyg5K6eOmgFZ343MbHtx-Zo";
    String ID_PLAYLIST = "PL3eHC8yQlnBRMT_bRlMcW84h7YKeXsLdu";
    String urlGetJson = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + ID_PLAYLIST + "&key=" + API_KEY;
    ListView lvVideo;
    ArrayList<Video> videoArrayList;
    VideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getJsonYoutube(urlGetJson);
        eventItemVideo();
    }

    private void eventItemVideo() {
        lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, PlayVideoActivity.class);
                intent.putExtra("videoId", videoArrayList.get(position).getVideoId());
                startActivity(intent);
            }
        });
    }

    private void init() {
        lvVideo = findViewById(R.id.lvVideo);
        videoArrayList = new ArrayList<>();
        adapter = new VideoAdapter(MainActivity.this, videoArrayList);
        lvVideo.setAdapter(adapter);
    }

    private void getJsonYoutube(String url) {
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int i =0;
                            String title = "", url = "", videoId = "";
                            JSONArray jsonItems = response.getJSONArray("items");
                            for ( ; i < jsonItems.length();i++){
                                JSONObject jsonItem = jsonItems.getJSONObject(i);
                                JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                                title = jsonSnippet.getString("title");
                                JSONObject jsonThumbnails = jsonSnippet.getJSONObject("thumbnails");
                                JSONObject jsonMedium = jsonThumbnails.getJSONObject("medium");
                                url = jsonMedium.getString("url");
                                JSONObject jsonResourceId = jsonSnippet.getJSONObject("resourceId");
                                videoId = jsonResourceId.getString("videoId");

                                videoArrayList.add(new Video(title, url, videoId));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

}
