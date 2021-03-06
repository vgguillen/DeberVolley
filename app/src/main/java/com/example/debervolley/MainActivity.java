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
    TextView txtID;
    RecyclerView recyclerRevista;
    Adapter revistaAdapter;
    List<RevistaModel> revistam;
    String url = "https://revistas.uteq.edu.ec/ws/issues.php?j_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        //initUI();
        //stringRequest();





    }



    public void btnConsultar(View view){

        txtID = findViewById(R.id.txtID);

        recyclerRevista = findViewById(R.id.Recycleview_id);
        recyclerRevista.setLayoutManager(new LinearLayoutManager(this));

        jsonArrayRequest(txtID.getText().toString());



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


    private void jsonArrayRequest(String ID){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url + ID,
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
                                /*textView.append("T??tulo: "+ tittle +"\n");
                                textView.append("ID: "+ id +"\n");
                                textView.append("Volumen: "+ volumen +"\n");
                                textView.append("N??mero: "+ numero +"\n");
                                textView.append("A??o: "+ anio +"\n");
                                textView.append("DOI: "+ doi +"\n");
                                textView.append("Cover: "+ cover +"\n\n\n");*/
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        revistaAdapter = new Adapter(revistam,getApplicationContext());

                        recyclerRevista.setAdapter(revistaAdapter);
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