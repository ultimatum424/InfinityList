package com.example.ultim.infinitylist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EndlessScrollListener scrollListener;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList;
    ArrayList<NewsFeedList> newsFeedList;
    SimpleAdapter simpleAdapter;
    AdapterItem adapterItem;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VKSdk.login(this, VKScope.FRIENDS, VKScope.WALL);
        arrayList = new ArrayList<>();
        newsFeedList = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("text", "TEST");
        for (int i = 0; i < 11; i++) {
            //arrayList.add(hashMap);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
            }
            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        LoadVKDate();
        ListView lvItems = (ListView) findViewById(R.id.recycle_view);
        adapterItem = new AdapterItem(this, newsFeedList);
        simpleAdapter = new SimpleAdapter(this, arrayList,
                R.layout.list_v,  new String[]{ "text" }, new int[]{R.id.text_v});
        lvItems.setAdapter(adapterItem);
        lvItems.setOnScrollListener(new EndlessScrollListener() {
            @Override
            protected boolean hasMoreDataToLoad() {
                return true;
            }

            @Override
            protected void loadMoreData(int page) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("text", "TEST");
                for (int i = 0; i < 20; i++) {
                  // arrayList.add(hashMap);
                }
                LoadVKDate();
                arrayList.size();
                newsFeedList.size();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void LoadVKDate(){
        // VKRequest request = VKApi.friends().get(VKParameters.from());
        VKRequest request = new VKRequest("newsfeed.get", VKParameters.from(VKApiConst.FILTERS, "post", VKApiConst.COUNT, 10));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {

                //Do complete stuff
                Gson gson = new Gson();
                NewsFeed newsFeed = gson.fromJson(response.json.toString(), NewsFeed.class);
                for (int i = 0; i < newsFeed.getLength(); i++) {
                    newsFeedList.add(newsFeed.getItem(i));
                }
                //newsFeedList.add(newsFeed);
                List<String> strings = newsFeed.getList();
                for (int i = 0; i < strings.size(); i++) {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("text", strings.get(i));
                    arrayList.add(hashMap);
                }
                simpleAdapter.notifyDataSetChanged();
                adapterItem.notifyDataSetChanged();

            }
            @Override
            public void onError(VKError error) {
                //Do error stuff
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                //I don't really believe in progress
            }
        });

    }
}
