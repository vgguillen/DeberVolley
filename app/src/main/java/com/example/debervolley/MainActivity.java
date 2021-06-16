package com.example.debervolley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    TextView textView;

    RecyclerView recyclerRevista;
    Adapter revistaAdapter;
    List<RevistaModel> revistam;

    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        initUI();
        //stringRequest();




    }

    private void inicializarelements(){
        recyclerRevista = findViewById(R.id.Recycleview_id);
        recyclerRevista.setLayoutManager(new LinearLayoutManager(this));

        jsonArrayRequest();
        revistaAdapter = new Adapter(revistam,this);

        recyclerRevista.setAdapter(revistaAdapter);
    }

    public void btnConsultar(View view){
        textView.setText("");
        EditText txt = findViewById(R.id.txtID);

        url = "https://revistas.uteq.edu.ec/ws/issues.php?j_id="+txt.getText().toString();
        inicializarelements();
    }


    private void initUI(){
    //    textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

    }


    private void stringRequest(){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        queue.add(request);
    }


    private void jsonArrayRequest(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int size = response.length();
                        revistam = new ArrayList<>();
                        for (int i = 0; i < size; i++){
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());

                                String tittle = jsonObject.getString("title");
                                String id = jsonObject.getString("issue_id");
                                String volumen = jsonObject.getString("volume");
                                String numero = jsonObject.getString("number");
                                String anio = jsonObject.getString("year");
                                String doi = jsonObject.getString("doi");
                                String cover = jsonObject.getString("cover");
                                String fecha = jsonObject.getString("date_published");

                                revistam.add(new RevistaModel(id,volumen,numero,anio,fecha,tittle,doi,cover));
                                /*textView.append("Título: "+ tittle +"\n");
                                textView.append("ID: "+ id +"\n");
                                textView.append("Volumen: "+ volumen +"\n");
                                textView.append("Número: "+ numero +"\n");
                                textView.append("Año: "+ anio +"\n");
                                textView.append("DOI: "+ doi +"\n");
                                textView.append("Cover: "+ cover +"\n\n\n");*/
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        queue.add(jsonArrayRequest);
    }
}