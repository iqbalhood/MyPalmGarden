package com.sawitku;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.sawitku.activitydrawer.DownloadFilesActivity;
import com.sawitku.activitydrawer.SearchActivity;
import com.sawitku.fadapter.PertanyaanAdapter;
import com.sawitku.fmodels.Pertanyaan;
import com.sawitku.fmodels.Tanya;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class HomeActivity extends AppCompatActivity
        implements SwipyRefreshLayout.OnRefreshListener, NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;

    //Getting reference to Firebase Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;

    ArrayList<Tanya> pullList;
    ArrayList<Tanya> pullGet;
    List<String> pullID = new ArrayList<String>();

    long order = 0;

    PertanyaanAdapter adapter;

    SwipyRefreshLayout mSwipyRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

         mSwipyRefreshLayout = (SwipyRefreshLayout)findViewById(R.id.swipyrefreshlayout);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FirebaseUser user = mAuth.getCurrentUser();

        DatabaseReference qry = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid()).getRef();


        qry.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View hView =  navigationView.getHeaderView(0);

                TextView nav_user  = (TextView)hView.findViewById(R.id.textViewNama);
                TextView nav_email  = (TextView)hView.findViewById(R.id.textViewEmail);
                ImageView nav_photo = (ImageView)hView.findViewById(R.id.imageViewGambar);



                if(snapshot.child("nama").exists()){
                    String nama =  snapshot.child("nama").getValue().toString();
                    nav_user.setText(nama);

                }

                if(snapshot.child("foto").exists()){
                    String foto =  snapshot.child("foto").getValue().toString();
                    Glide.with(getApplicationContext()).load(foto)
                            .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                            .into(nav_photo);

                }

                if(snapshot.child("namaDisplay").exists()){
                    String nama =  snapshot.child("namaDisplay").getValue().toString();
                    nav_email.setText("("+nama+")");

                }else{
                    nav_email.setVisibility(View.GONE);
                }




            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Log.e("Chat", "The read failed: " + error.getText());
            }
        });
        
        // List Pertanyaan


        pullList = new ArrayList<Tanya>();
        pullGet = new ArrayList<Tanya>();
        adapter = new PertanyaanAdapter(HomeActivity.this, R.layout.list_pertanyaan_sawit_line, pullList);
        mainListView = (ListView) findViewById( R.id.mainListView);

        DatabaseReference qryy = FirebaseDatabase.getInstance().getReference().child("qna").child("1").child("question").getRef();

        Query queryRef = qryy.orderByChild("order").limitToFirst(6);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {



                // Find the ListView resource.

                int i = 0;

                for (DataSnapshot singleSnapshot : snapshot.getChildren()){
                    Tanya member = new Tanya();
                    String nama = (String) singleSnapshot.child("nama").getValue();
                    String id = singleSnapshot.child("id").getValue().toString();
                    String pertanyaan = singleSnapshot.child("question").getValue().toString();
                    String provinsi = singleSnapshot.child("provinsi").getValue().toString();
                    String foto = singleSnapshot.child("profil_picture").getValue().toString();

                    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    String tanggal =  sfd.format(new Date(Long.parseLong(id)));


                    member.setId(id);
                    member.setNama(nama);
                    member.setQuestion(pertanyaan);
                    member.setAlamat(provinsi);
                    member.setTime(tanggal);
                    member.setProfil_picture(foto);

                    pullID.add(id);
                    pullList.add(member);

                    order = Long.parseLong(id) * -1;
                    System.out.println("Latest ID " + order);

                }


                mainListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Log.e("Chat", "The read failed: " + error.getText());
            }
        });






        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent question = new Intent(HomeActivity.this, CreateQuestion.class);
                startActivity(question);


            }
        });


        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                System.out.println( "Refresh triggered at ACTIVITY BLOCK "
                        + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));

                LoadMore();
            }
        });


        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub

                String  idMember = String.valueOf(pullList.get(position).getId());
                Intent k = new Intent(HomeActivity.this, ListJawaban.class);
                k.putExtra("id", "1");
                k.putExtra("idQuestion", idMember);
                k.putExtra("question", String.valueOf(pullList.get(position).getQuestion()));
                startActivity(k);

            }
        });










    }


    public void LoadMore(){

        pullGet.clear();
        DatabaseReference qry = FirebaseDatabase.getInstance().getReference().child("qna").child("1").child("question").getRef();

        Query queryRef = qry.orderByChild("order").startAt((order+1)).limitToFirst(6);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot singleSnapshot : snapshot.getChildren()){
                    Tanya member = new Tanya();
                    String nama = (String) singleSnapshot.child("nama").getValue();
                    String id = singleSnapshot.child("id").getValue().toString();
                    String pertanyaan = singleSnapshot.child("question").getValue().toString();
                    String provinsi = singleSnapshot.child("provinsi").getValue().toString();
                    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    String tanggal =  sfd.format(new Date(Long.parseLong(id)));
                    String foto = singleSnapshot.child("profil_picture").getValue().toString();

                    member.setId(id);
                    member.setNama(nama);
                    member.setQuestion(pertanyaan);
                    member.setAlamat(provinsi);
                    member.setTime(tanggal);
                    member.setProfil_picture(foto);


                    pullGet.add(member);

                    order = Long.parseLong(id) * -1;
                    System.out.println("Latest ID PULL " + order);

                }
                pullList.addAll(pullGet);
                mainListView.setAdapter(adapter);
                mSwipyRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Log.e("Chat", "The read failed: " + error.getText());
            }
        });

    }








    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navigation_map) {
            Intent pindah = new Intent(HomeActivity.this, PeopleMap.class);
            startActivity(pindah);
            // Handle the camera action
        } else if (id == R.id.navigation_search) {
            Intent pindah = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(pindah);

        } else if (id == R.id.navigation_download) {
            Intent pindah = new Intent(HomeActivity.this, DownloadFilesActivity.class);
            startActivity(pindah);

        } else if (id == R.id.navigation_profile) {

            Intent pindah = new Intent(HomeActivity.this, EditProfileActivity.class);
            startActivity(pindah);

        }  else if (id == R.id.nav_send) {
            signOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void signOut() {
        mAuth.signOut();
        Intent pindah = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(pindah);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        System.out.println( "Refresh triggered at "
                + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));
         mSwipyRefreshLayout.setRefreshing(false);

    }
}
