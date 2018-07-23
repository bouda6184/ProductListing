package david.example.com.productlisting;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

import android.view.View;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DBHandler dbHandler;
    private Context cont;
    private CardView cdvMachSemiAuto = null;
    private CardView cdvMachSuperAuto = null;
    private CardView cdvMoulin = null;
    private CardView cdvMarque = null;
    private CardView cdvOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHandler = new DBHandler(this);
        cont = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCardVwFunctions();
        try {
            dbHandler.updateDataBase();
        } catch(IOException ex) {
            throw new Error("impossible de mettre à jour la base de données...");
        }
    }

    private void initCardVwFunctions(){
        cdvMachSemiAuto = findViewById(R.id.categSemiAuto);
        cdvMachSuperAuto = findViewById(R.id.categSuperAuto);
        cdvMoulin = findViewById(R.id.categMoulins);
        cdvMarque = findViewById(R.id.categMarques);
        cdvOption = findViewById(R.id.categOptions);

        cdvMachSemiAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createProductListActivity(dbHandler.getSemiAutoMachines());
            }
        });

        cdvMachSuperAuto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                createProductListActivity(dbHandler.getSuperAutoMachines());
            }
        });

        cdvMoulin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                createProductListActivity(dbHandler.getGrinders());
            }
        });

        cdvMarque.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                createBrandListActivity(dbHandler.getAllBrands());
            }
        });

        cdvOption.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });

    }

    public void createProductListActivity(final ArrayList<ProductListing> prodListings){
        /*new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent newIntent = new Intent(cont, ProdDisplayActivity.class);
                newIntent.putParcelableArrayListExtra("prod_list", prodListings);
                startActivity(newIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, 0 );*/
        Intent newIntent = new Intent(cont, ProdDisplayActivity.class);
        newIntent.putParcelableArrayListExtra("prod_list", prodListings);
        startActivity(newIntent);
    }

    public void createBrandListActivity(final ArrayList<BrandListing> brandListings){
        /*new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent newIntent = new Intent(cont, BrandDisplayActivity.class);
                newIntent.putParcelableArrayListExtra("brand_list", brandListings);
                startActivity(newIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, 0 );*/
        Intent newIntent = new Intent(cont, BrandDisplayActivity.class);
        newIntent.putParcelableArrayListExtra("brand_list", brandListings);
        startActivity(newIntent);
    }

}
