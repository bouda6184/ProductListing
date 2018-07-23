package david.example.com.productlisting;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class ProdDetailsActivity extends AppCompatActivity {
    private Button btnSpecs;
    private Button btnManual;
    private Button btnWebsite;
    private TextView txtProdName;
    private TextView txtPrice;
    private TextView txtBrand;
    private TextView txtSpecial;
    private ImageView imgMachine;
    private Context cont;
    ProductListing prodListing;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_details);
        cont = this;
        prodListing = this.getIntent().getParcelableExtra("prod_details");

        btnSpecs = (Button)findViewById(R.id.btnSpecs);
        btnManual = (Button)findViewById(R.id.btnManual);
        btnWebsite = (Button)findViewById(R.id.btnWebsite);
        txtProdName = (TextView)findViewById(R.id.txtProdName);
        txtPrice = (TextView)findViewById(R.id.txtProdPrice);
        txtBrand = (TextView)findViewById(R.id.txtProdBrand);
        txtSpecial = (TextView)findViewById(R.id.txtSpecial);
        imgMachine = (ImageView)findViewById(R.id.imgProd);

        initComponents(prodListing);
    }

    private void initComponents(ProductListing prod){
        txtProdName.setText(prod.getName());
        txtPrice.setText(String.format("%.2f",prod.getPrice()));
        txtBrand.setText(prod.getBrand());
        byte imgData[] = DBHandler.getBlobByProductName(prod.getName());
        Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
        imgMachine.setImageBitmap(bmp);
        setButtonsOnClickEvent();
    }

    private void setButtonsOnClickEvent(){
        btnSpecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermissionAndLoadFile(prodListing.getSpecURL());
            }
        });
        btnManual.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                askPermissionAndLoadFile(prodListing.getPdfManualTitle());
            }
        });
        btnWebsite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                loadWebViewer(prodListing.getWebsiteURL());
            }
        });
    }

    private void loadWebViewer(String url){
        Intent newIntent = new Intent(getApplicationContext(), WebViewer.class);
        newIntent.putExtra("url", url);
        startActivity(newIntent);
    }

    private void askPermissionAndLoadFile(String url) {
        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (canRead) {

            try{
                File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File files[] = file.listFiles();
                Uri uri = null;
                int nb = 0;
                while(nb < files.length){
                    if(files[nb].getName().equals(url)){
                        uri = Uri.fromFile(files[nb]);
                        break;
                    }
                    nb++;
                }
                if(nb != files.length){
                    Intent newIntent = new Intent(getApplicationContext(), PDF_Viewer.class);
                    newIntent.putExtra("filepath", uri.toString());
                    startActivity(newIntent);

                } else {
                    if(url.startsWith("http")){
                        loadWebViewer(url);
                    } else {
                        Toast.makeText(getApplicationContext(), "Impossible d'afficher ce document...fichier introuvable...", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception ex){
                Toast.makeText(getApplicationContext(), "Désolé...Erreur de lecture de fichiers, impossible de visionner le document pour l'instant...", Toast.LENGTH_LONG).show();
            }


        }
    }
    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);


            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_ID_READ_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "La permission de l'usager doit être accordée pour visionner ce document!", Toast.LENGTH_SHORT).show();
        }
    }

}
