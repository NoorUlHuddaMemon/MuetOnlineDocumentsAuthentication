package com.example.abdulmustafamemon.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_SHORT;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private SliderLayout mDemoSlider;

    Context context;
    View view;
    EditText enter_id;
    Button search_by_id;
    ImageButton submit;
    LinearLayout linear_layout;
    IntentIntegrator scan;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    String GetEditText;

    private String key1;


    private String key;
    private String PATH;
    private  String fullname;
    private String rollNo;
    private String fatherName;
    private String dept;
    private String enrollment;
    private String doa;
    private String dod;
    private String position;
    private String cgpa;
    private String dop;
    private String doe;
    private String docType;
    private String posName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PreferenceManager.setDefaultValues(this,R.xml.settings,false);
        final SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);


        final Boolean beep= sharedPreferences.getBoolean("beep",true);
        Boolean frontCamera= sharedPreferences.getBoolean("frontCamera",false);
        final int camId;
        if(frontCamera==false)
            camId=0;
        else
            camId=1;
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);


        //initiate scan with our custom scan activity

        scan=new IntentIntegrator(Home.this);
        scan.setCaptureActivity(ScannerActivity.class);
        scan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        scan.setBeepEnabled(beep).setCameraId(camId);



        Button buttonBarCodeScan = findViewById(R.id.q_r_code_scanner);
        buttonBarCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan.initiateScan();




            }
        });




        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        mDemoSlider = (SliderLayout)  findViewById(R.id.slider);
        enter_id = (EditText)  findViewById(R.id.enter_id);

        search_by_id = (Button)  findViewById(R.id.search_by_id);


        linear_layout = (LinearLayout)  findViewById(R.id.linear_layout);

        search_by_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                search_by_id.setVisibility(View.GONE);

                linear_layout.setVisibility(View.VISIBLE);

            }
        });
        submit =(ImageButton)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = enter_id.getText().toString().trim();
                if(key.isEmpty())
                {enter_id.setError("Enter the key!");
                    //Toast.makeText(getApplicationContext(),"Empty field",Toast.LENGTH_SHORT).show();
                }
                else if(key.length()!=15)
                {
                    enter_id.setError("Key should have 15 characters!");
                    //Toast.makeText(getApplicationContext(),"Verification key must be 15 characters",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    getData(key);
                }

            }
        });


        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put(" Verify Your Documents... ",R.drawable.muet);
        file_maps.put(" We  help you  verify your Degree.",R.drawable.grad1);
        file_maps.put(" We  help you  verify your Pass Certificate.",R.drawable.docs);
        file_maps.put(" We  help you  verify your Transcript. ", R.drawable.slider3);



        for(
                String name :file_maps.keySet())

        {

            TextSliderView textSliderView = new TextSliderView(Home.this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
//                    .setOnSliderClickListener(getContext());

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                   .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);

        }
       mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);

    }

    private void getData(String key) {
        key = "2"+key;
        PATH = "http://forbitech.com/MobileApp/index.php?key="+key;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PATH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");

                    if (success == -1) {
                       // Toast.makeText(getApplicationContext(), "This key does not exist!", Toast.LENGTH_SHORT).show();
                        Intent re = new Intent(Home.this, Result.class);
                        startActivity(re);


                    } else if (success == 0) {
                       // Toast.makeText(getApplicationContext(), "This key has been expired!", Toast.LENGTH_SHORT).show();
                        Intent re = new Intent(Home.this, ExpiredKey.class);
                        startActivity(re);
                    }
                    else if (success == 1) {
                        JSONArray pinfo = jsonObject.getJSONArray("pinfo");
                        JSONObject p = pinfo.getJSONObject(0);
                        JSONArray dinfo = jsonObject.getJSONArray("dinfo");

                        JSONObject d = dinfo.getJSONObject(0);
                        fullname = p.getString("fullname");
                        rollNo = p.getString("rollNo");
                        fatherName = p.getString("fatherName");
                        dept = p.getString("name");
                        enrollment = p.getString("enrollmentNo");
                        doa = p.getString("doa");

                        dod = d.getString("dod");
                        position = d.getString("position");
                        dop = d.getString("dop");
                        cgpa = d.getString("cgpa");
                        doe  =d.getString("doe");
                        docType = jsonObject.getString("docType");

if(Integer.parseInt(position)==0)
{
    position = "None";
}
else if(Integer.parseInt(position)==1)
{
    position = "First";
}
else if(Integer.parseInt(position)==2)
{
    position = "Second";
}
else if(Integer.parseInt(position)==3)
{position = "Third";}



if (docType.equals("D")) {

    alertDegree();
    /*Intent intent = new Intent(Home.this, Degree.class);
    intent.putExtra("fname", fullname);
    intent.putExtra("fatName", fatherName);
    intent.putExtra("rollno", rollNo);
    intent.putExtra("dept", dept);
    intent.putExtra("dod",dod);
    intent.putExtra("cgpa",cgpa);
    intent.putExtra("doe",doe);

    startActivity(intent);*/
    }


    if (docType.equals("T")) {
        alertTranscript();
    /*Intent intent = new Intent(Home.this, Transcript.class);
    intent.putExtra("fname", fullname);
    intent.putExtra("fatName", fatherName);
    intent.putExtra("rollno", rollNo);
    intent.putExtra("dept", dept);
    intent.putExtra("enroll", enrollment);
    intent.putExtra("doa", doa);

    intent.putExtra("position", posName);
    intent.putExtra("dop", dop);
    intent.putExtra("cgpa",cgpa);

    startActivity(intent);*/
                        }
                        if (docType.equals("P")) {
                            alertPassCertificate();
                            /*Intent intent = new Intent(Home.this, PassCertificate.class);
                            intent.putExtra("fname", fullname);
                            intent.putExtra("fatName", fatherName);
                            intent.putExtra("rollno", rollNo);
                            intent.putExtra("dept", dept);

                            intent.putExtra("position", posName);

                            intent.putExtra("cgpa",cgpa);
                            intent.putExtra("doe",doe);
                           startActivity(intent);*/

                        }

                    }

                } catch(JSONException e){
                    e.printStackTrace();
                }
            }


        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQue = Volley.newRequestQueue(this);
        requestQue.add(stringRequest);
    }

    @SuppressLint("ResourceType")
    public void alertDegree() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.degree, null);
        alert.setCustomTitle(view);


               alert .setMessage("\nFull Name:" +fullname+
                        "\n\nFather Name:" +fatherName+
                        "\n\nRoll No:"+rollNo+"\n\nCGPA:"+cgpa+"\n\nDate of Examination:"+doe+"\n\nDate of Decleration:"+dod+
                "\n\nDepartment:"+dept)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }
    public void alertTranscript() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.transcript, null);
        alert.setCustomTitle(view);


        alert .setMessage("\nFull Name:" +fullname+
                        "\n\nFather Name:" +fatherName+
                        "\n\nRoll No:"+rollNo+"\n\nCGPA:"+cgpa+"\n\nPosition:"+position+"\n\nDate of Admission:"+doa+"\n\nDate of Passing:"+dop+
                        "\n\nDepartment:"+dept+"\n\nEnrollment No:"+enrollment)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }
    public void alertPassCertificate() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.pass_certificate, null);
        alert.setCustomTitle(view);


        alert .setMessage("\nFull Name:" +fullname+
                        "\n\nFather Name:" +fatherName+
                        "\n\nRoll No:"+rollNo+"\n\nCGPA:"+cgpa+"\n\nFinal Exam Held::"+doe+
                        "\n\nDepartment:"+dept+"\nPosition:"+position)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }




    private void openPlayStorePage() {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
    private void openFeedback() {
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(new Uri.Builder().scheme("mailto").build());
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"muetdocsv.customer@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "[MuetOnlineDocumentsAuthentication] Feedback");
        email.putExtra(Intent.EXTRA_TEXT, "\nMy device info: \n" + DeviceInfo.getDeviceInfo()
                + "\nApp version: " + BuildConfig.VERSION_NAME
                + "\nFeedback:" + "\n");
        try {
            startActivity(Intent.createChooser(email, "Send feedback"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, R.string.about_no_email, Toast.LENGTH_SHORT).show();
        }
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

                super.onBackPressed();
                return;
            }
                    // ignoring this exception
                }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.home:
                Intent h= new Intent(Home.this,Home.class);
                startActivity(h);
                break;

            case R.id.about:
                Intent g= new Intent(Home.this,About.class);
                startActivity(g);
                break;

            case R.id.rate_us:
                openPlayStorePage();
                break;
            case R.id.terms_and_conditions:
                Intent t= new Intent(Home.this,TermsAndConditions.class);
                startActivity(t);
                break;
            case R.id.action_settings:
                Intent w= new Intent(Home.this,Settings.class);
                startActivity(w);

                break;
            case R.id.action_feedback:
                openFeedback();
                break;
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult key = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (key.getContents() != null) {
            if (key.getContents() == null) {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //show dialogue with result
                getQRData(key.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }







    }
    private void getQRData(String key) {
        key = "2"+key;
        PATH = "http://forbitech.com/MobileApp/index.php?key="+key;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PATH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");

                    if (success == -1) {
                       // Toast.makeText(getApplicationContext(), "This QRCode does not exist!", Toast.LENGTH_SHORT).show();
                        Intent re = new Intent(Home.this, Result.class);
                        startActivity(re);



                    } else if (success == 0) {
                       // Toast.makeText(getApplicationContext(), "This QRCode has been expired!", Toast.LENGTH_SHORT).show();
                        Intent re = new Intent(Home.this, ExpiredQRCode.class);
                        startActivity(re);

                    }
                    else if (success == 1) {
                        JSONArray pinfo = jsonObject.getJSONArray("pinfo");
                        JSONObject p = pinfo.getJSONObject(0);
                        JSONArray dinfo = jsonObject.getJSONArray("dinfo");

                        JSONObject d = dinfo.getJSONObject(0);
                        fullname = p.getString("fullname");
                        rollNo = p.getString("rollNo");
                        fatherName = p.getString("fatherName");
                        dept = p.getString("name");
                        enrollment = p.getString("enrollmentNo");
                        doa = p.getString("doa");

                        dod = d.getString("dod");
                        position = d.getString("position");
                        dop = d.getString("dop");
                        cgpa = d.getString("cgpa");
                        doe  =d.getString("doe");
                        docType = jsonObject.getString("docType");

if(Integer.parseInt(position)==0)
{
    position = "None";
}
else if(Integer.parseInt(position)==1)
{
    position = "First";
}
else if(Integer.parseInt(position)==2)
{
    position = "Second";
}
else if(Integer.parseInt(position)==3)
{position = "Third";}



                        if (docType.equals("D")) {
                                alertDegree();
                            /*Intent intent = new Intent(Home.this, Degree.class);
                            intent.putExtra("fname", fullname);
                            intent.putExtra("fatName", fatherName);
                            intent.putExtra("rollno", rollNo);
                            intent.putExtra("dept", dept);
                            intent.putExtra("dod",dod);
                            intent.putExtra("cgpa",cgpa);
                            intent.putExtra("doe",doe);

                            startActivity(intent);*/
                        }

                        if (docType.equals("T")) {
    alertTranscript();
                            /*Intent intent = new Intent(Home.this, Transcript.class);
                            intent.putExtra("fname", fullname);
                            intent.putExtra("fatName", fatherName);
                            intent.putExtra("rollno", rollNo);
                            intent.putExtra("dept", dept);
                            intent.putExtra("enroll", enrollment);
                            intent.putExtra("doa", doa);

                            intent.putExtra("position", posName);
                            intent.putExtra("dop", dop);
                            intent.putExtra("cgpa",cgpa);

                            startActivity(intent);*/
                        }
                        if (docType.equals("P")) {
    alertPassCertificate();
                           /* Intent intent = new Intent(Home.this, PassCertificate.class);
                            intent.putExtra("fname", fullname);
                            intent.putExtra("fatName", fatherName);
                            intent.putExtra("rollno", rollNo);
                            intent.putExtra("dept", dept);

                            intent.putExtra("position", posName);

                            intent.putExtra("cgpa",cgpa);
                            intent.putExtra("doe",doe);
                            startActivity(intent);*/

                        }

                    }

                } catch(JSONException e){
                    e.printStackTrace();
                }
            }


        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQue = Volley.newRequestQueue(this);
        requestQue.add(stringRequest);

    }


    //method to construct dialogue with scan results


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals("beep")){
            scan.setBeepEnabled(sharedPreferences.getBoolean(key,true));

        }
        if(key.equals("frontCamera")){
            int camId;
            if(sharedPreferences.getBoolean(key,false)==false)
                camId=0;
            else
                camId=1;
            scan.setCameraId(camId);
        }

    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

}
