package david.example.com.productlisting;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

public class BrandDisplayActivity extends AppCompatActivity {
    private RecyclerView r_view;
    private DBHandler dbHandler;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_display);
        dbHandler = new DBHandler(this);
        r_view = findViewById(R.id.brandDisplayList);
        context = this;
        ArrayList<BrandListing> brandList = this.getIntent().getParcelableArrayListExtra("brand_list");
        setBrandRvadapter(brandList);
    }

    public void setBrandRvadapter (ArrayList<BrandListing> lst) {
        BrandRvAdapter myAdapter = new BrandRvAdapter(this, lst, new BrandRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BrandListing item) {
                createProductListActivity(dbHandler.getProductsByBrand(item.getName()));
            }
        });
        r_view.setLayoutManager(new GridLayoutManager(this, 2));
        r_view.setAdapter(myAdapter);

    }

    public void createProductListActivity(final ArrayList<ProductListing> prodListings){
        /*new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent newIntent = new Intent(context, ProdDisplayActivity.class);
                newIntent.putParcelableArrayListExtra("prod_list", prodListings);
                startActivity(newIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, 0 );*/
        Intent newIntent = new Intent(context, ProdDisplayActivity.class);
        newIntent.putParcelableArrayListExtra("prod_list", prodListings);
        startActivity(newIntent);
    }
}
