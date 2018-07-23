package david.example.com.productlisting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BrandRvAdapter extends RecyclerView.Adapter<BrandRvAdapter.BrandViewHolder> {
    public interface OnItemClickListener{
        void onItemClick(BrandListing item);
    }

    private Context mContext;
    private final ArrayList<BrandListing> brand_list;
    private final OnItemClickListener listener;


    public BrandRvAdapter(Context context, ArrayList<BrandListing> b_list, OnItemClickListener _listener){
        mContext = context;
        brand_list = b_list;
        this.listener = _listener;
    }
    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInfl = LayoutInflater.from(mContext);
        view = layoutInfl.inflate(R.layout.cardview_brand_item, parent, false);

        return new BrandViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {

        /*String resName = brand_list.get(position).getLogoURL().substring(0, brand_list.get(position).getLogoURL().lastIndexOf('.'));
        int logoID = mContext.getResources().getIdentifier(resName, "drawable", mContext.getPackageName());
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), logoID);
        holder.txtBrandName.setText(brand_list.get(position).getName());
        holder.brandLogo.setImageBitmap(bmp);*/
        holder.bind(mContext, brand_list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return brand_list.size();
    }

    public static class BrandViewHolder extends RecyclerView.ViewHolder {
        TextView txtBrandName;
        ImageView brandLogo;

        public BrandViewHolder(View itemView){
            super(itemView);
            txtBrandName = (TextView)itemView.findViewById(R.id.brand_item);
            brandLogo = (ImageView)itemView.findViewById(R.id.brand_logo);
        }

        public void bind(final Context context, final BrandListing item, final OnItemClickListener listener){
            String resName = item.getLogoURL().substring(0, item.getLogoURL().lastIndexOf('.'));
            int logoID = context.getResources().getIdentifier(resName, "drawable", context.getPackageName());
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), logoID);
            txtBrandName.setText(item.getName());
            brandLogo.setImageBitmap(bmp);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }

    }
}
