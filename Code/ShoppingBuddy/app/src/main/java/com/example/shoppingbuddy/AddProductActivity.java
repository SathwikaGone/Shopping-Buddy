package com.example.shoppingbuddy;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Spinner spinner;
    private static final String[] paths = {"Men's clothing", "Women's clothing", "Kid's clothing",
            "Men's Accessories", "Women's Accessories", "Cell Phones and Accessories",
            "Tv and Computers", "Men's Footwear", "Women's Footwear"};
    private FirebaseFirestore db;
    private StorageReference storage, storg;
    private CollectionReference itemCollection;
    private DocumentReference itemDoc;
    private static final int PICK_IMG_REQUEST = 1;
    Button uploadbtn;
    private Uri imageURI;
    private Long imageName;
    private String imageURL;
    ImageView prodimage;
    String id,name,description, category;
    Double cost;
    EditText productid,productname,productdes,productcost;
    Spinner productcate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         productid=findViewById(R.id.editText6);
         productname=findViewById(R.id.editText9);
         productdes=findViewById(R.id.editText12);
         productcate=findViewById(R.id.spinner);
         productcost=findViewById(R.id.editText13);
        prodimage=findViewById(R.id.imageView3);
        uploadbtn=findViewById(R.id.button13);
        Button addprod=findViewById(R.id.button12);
        Button cancel=findViewById(R.id.button14);


        storage = FirebaseStorage.getInstance().getReference("productimages");
        storg = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        itemCollection = db.collection("products");

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        addprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=productid.getText().toString();
                name=productname.getText().toString();
                description=productdes.getText().toString();
                category=productcate.getSelectedItem().toString();
                cost=Double.parseDouble(productcost.getText().toString());

                if(id.equals("")||name.equals("")||description.equals("") || category.equals("") || imageURL.equals("") ||   cost<=0){
                    Toast.makeText(AddProductActivity.this, "All the field needs to be filled ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String, Object> addproduct = new HashMap<>();
                    addproduct.put("itemId", id);
                    addproduct.put("itemName", name);
                    addproduct.put("itemDetails", description);
                    addproduct.put("category", category);
                    addproduct.put("cost", cost);
                    addproduct.put("imageURL", imageURL);

                    itemCollection.document().set(addproduct);
                    Toast.makeText(AddProductActivity.this, "Item added to the list", Toast.LENGTH_SHORT).show();

                    finish();


                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AddProductActivity.this,AdminHomeActivity.class);
                startActivity(in);
            }
        });

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddProductActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMG_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMG_REQUEST && resultCode == RESULT_OK
                && data !=null && data.getData()!=null){
            imageURI = data.getData();
            Log.d("clck", "inside onactivity");
            uploadImage();
        }

    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadImage() {
        Log.d("clck", "inside uploadimage");
        if (imageURI != null) {
            imageName = System.currentTimeMillis();
            final StorageReference fileRef = storage.child(imageName + "." + getFileExtension(imageURI));
            fileRef.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageURL = uri.toString();
                            Log.d("clck", "image inserted");
                            Picasso.get().load(imageURL).into(prodimage);
                            Log.d("clck", "image displayed");
                        }
                    });
                }
            });

        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.History){
            Intent in=new Intent(this,UserHistoryActivity.class);
            startActivity(in);
        }
        else if (id == R.id.Logout) {
            // Handle the accessories action
            Intent in=new Intent(this,MainActivity.class);
            startActivity(in);
        }
        else if(id==R.id.addedprod){
            Intent in=new Intent(this,AdminHistoryAprodActivity.class);
            startActivity(in);
        }
        else if(id==R.id.deletedprod){
            Intent in=new Intent(this,AdminHistoryDProdActivity.class);
            startActivity(in);
        }
        else if (id==R.id.addedpromo){
            Intent in=new Intent(this,AdminHistoryAPromoActivity.class);
            startActivity(in);
        }
        else if (id==R.id.deletedpromo){
            Intent in=new Intent(this,AdminHistoryDPromoActivity.class);
            startActivity(in);
        }
        else if (id==R.id.Inventory){
            Intent in=new Intent(this,AdminInventory.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
