package david.example.com.productlisting;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ProdDisplayActivity extends AppCompatActivity {
    private RecyclerView r_view;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__display);
        r_view = findViewById(R.id.prodDisplayList);
        context = this;
        ArrayList<ProductListing> prodList = this.getIntent().getParcelableArrayListExtra("prod_list");
        setRvadapter(prodList);
    }

    public void setRvadapter (ArrayList<ProductListing> lst) {

        RvAdapter myAdapter = new RvAdapter(this, lst, new RvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductListing item) {
                createProdDetailsActivity(item);
            }
        }) ;
        r_view.setLayoutManager(new LinearLayoutManager(this));
        r_view.setAdapter(myAdapter);

    }

    public void createProdDetailsActivity(final ProductListing product){
        /*new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent newIntent = new Intent(context, ProdDetailsActivity.class);
                newIntent.putExtra("prod_details", product);
                startActivity(newIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, 0 );*/
        Intent newIntent = new Intent(context, ProdDetailsActivity.class);
        newIntent.putExtra("prod_details", product);
        startActivity(newIntent);

    }


}
