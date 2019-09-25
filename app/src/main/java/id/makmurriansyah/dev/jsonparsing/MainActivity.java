package id.makmurriansyah.dev.jsonparsing;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> userList;
    private String url = "https://rantauprapatcoding.000webhostapp.com/datas.json";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.listArea);
        userList = new ArrayList <HashMap<String, String>>();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching data..");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject jsonObj)
            {
                pDialog.dismiss();
                try{
                    JSONArray jsonArray = jsonObj.getJSONArray("user");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject user = jsonArray.getJSONObject(i);
                        String no = user.getString("no");
                        String nama = user.getString("nama");
                        String pemilik = user.getString("pemilik");
                        String jalan = user.getString("jalan");
                        String jenis = user.getString("jenis");
                        JSONObject social = user.getJSONObject("social");
                        String ig = social.getString("instagram");
                        String fb = social.getString("facebook");
                        JSONObject phone = user.getJSONObject("mulai");
                        String tahun = phone.getString("tahun");
                        HashMap<String, String> u = new HashMap();
                        u.put("nama", nama);
                        u.put("pemilik", pemilik);
                        u.put("jalan", jalan);
                        u.put("jenis", jenis);
                        u.put("mulai", tahun);
                        userList.add(u);
                        ListAdapter listAdapter = new SimpleAdapter(MainActivity.this, userList, R.layout.item_listview,
                                new String[]{"nama", "pemilik", "jalan", "jenis", "mulai"}, new int[]{R.id.vNama, R.id.vPemilik, R.id.vJalan, R.id.vJenis, R.id.vTahun});
                        list.setAdapter(listAdapter);
                    }

                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.toString(), 100).show();

                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError p1)
            {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), p1.toString(), 100).show();
            }
        });
        Constant.getInstance(this).addToRequestQueue(req);
    }
}