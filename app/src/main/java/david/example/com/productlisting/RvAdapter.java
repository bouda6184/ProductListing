package david.example.com.productlisting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder>{
    public interface OnItemClickListener{
        void onItemClick(ProductListing item);
    }

    RequestOptions options ;
    private Context mContext ;
    private ArrayList<ProductListing> mData ;
    private final OnItemClickListener listener;
    private DBHandler dbHandler;


    public RvAdapter(Context mContext, ArrayList<ProductListing> lst, OnItemClickListener _listener) {
        this.mContext = mContext;
        this.mData = lst;
        this.listener = _listener;
        this.dbHandler = new DBHandler(this.mContext);
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading_shape)
                .error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.product_item,parent,false);
        // click listener here
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        /*String resName = mData.get(position).getImageURL().substring(0, mData.get(position).getImageURL().lastIndexOf('.'));
        int imgID = mContext.getResources().getIdentifier(resName, "drawable", mContext.getPackageName());
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), imgID);
        holder.prod_name.setText(mData.get(position).getName());
        holder.brand_name.setText(mData.get(position).getBrand());
        holder.prod_price.setText(mData.get(position).getPrice().toString());
        holder.prod_type.setText(mData.get(position).getDescription());

        holder.prod_img.setImageBitmap(bmp);
        // load image from the internet using Glide
        //Glide.with(mContext).load(mData.get(position).getImageURL()).apply(options).into(holder.prod_img);
        */
        holder.bind(mContext, mData.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView prod_name, brand_name, prod_price, prod_type;
        ImageView prod_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            prod_name = itemView.findViewById(R.id.prodName);
            brand_name = itemView.findViewById(R.id.brandName);
            prod_price = itemView.findViewById(R.id.prodPrice);
            prod_type = itemView.findViewById(R.id.machType);
            prod_img = itemView.findViewById(R.id.prodImage);
        }

        public void bind(final Context context, final ProductListing prod, final OnItemClickListener _listener){
            byte[] imgBytes = DBHandler.getBlobByProductName(prod.getName());
            Bitmap bmp = BitmapFactory.decodeByteArray(imgBytes, 0 , imgBytes.length);
            prod_name.setText(prod.getName());
            brand_name.setText(prod.getBrand());
            prod_price.setText(String.format("%.2f", prod.getPrice()));
            prod_type.setText(prod.getDescription());

            prod_img.setImageBitmap(bmp);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    _listener.onItemClick(prod);
                }
            });
        }
    }
}
