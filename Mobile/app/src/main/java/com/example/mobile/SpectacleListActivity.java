package com.example.mobile;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class SpectacleListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SpectacleAdapter adapter;
    private List<Spectacle> spectacleList;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectacle_list);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spectacleList = new ArrayList<>();
        adapter = new SpectacleAdapter(spectacleList, this);
        recyclerView.setAdapter(adapter);

        // Initial fetch (affiche tout)
        fetchSpectaclesGlobal("");

        // Recherche en temps réel
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchSpectaclesGlobal(newText);
                return true;
            }
        });
    }

    private void fetchSpectaclesGlobal(String query) {
        String url = "http://10.0.2.2:8091/api/spectacles/search-global?query=" + query;

        Log.d("DEBUG_URL", "URL utilisée : " + url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        spectacleList.clear();

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);

                            String lieuNom = obj.optString("nomLieu", "Lieu non spécifié");

                            Spectacle s = new Spectacle(
                                    obj.getLong("id"),
                                    obj.getString("titre"),
                                    obj.getString("date"),
                                    obj.getString("heureDebut"),
                                    obj.getString("duree"),
                                    obj.getInt("nbSpectateurs"),
                                    lieuNom
                            );

                            spectacleList.add(s);
                        }

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Toast.makeText(this, "Erreur parsing: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Erreur réseau: " + error.getMessage(), Toast.LENGTH_LONG).show();
                });

        Volley.newRequestQueue(this).add(request);
    }
}
