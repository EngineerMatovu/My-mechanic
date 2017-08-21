package com.example.android.io2014.fire;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.io2014.Model;
import com.example.android.io2014.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by basasa-pc on 8/9/2017.
 */

public class Garage_Adapter extends RecyclerView.Adapter<Garage_Adapter.MyViewHolder>{
    private Context mContext;
    private List<Model> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name, address;
//        public ImageView thumbnail;
        List<Model> productList=new ArrayList<>();
        Context mContext;
        public MyViewHolder(View view, Context mContext,List<Model> productList) {
            super(view);
            this.productList=productList;
            this.mContext=mContext;

            view.setOnClickListener(this);
            name= (TextView) itemView.findViewById(R.id.garagename);
            address= (TextView) itemView.findViewById(R.id.address);

//            thumbnail= (ImageView) itemView.findViewById(R.id.thumbnail);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            Model product =this.productList.get(position);
//            Intent intent=new Intent(mContext, DetailsActivity.class);
//
//            intent.putExtra("image_id",product.getThumbnail());
//            intent.putExtra("category",product.getCategory());
//            intent.putExtra("price",product.getPrice());
//            intent.putExtra("name",product.getName());
//            intent.putExtra("contact",product.getContact());
//            intent.putExtra("rMessage",product.getLocation());
//            intent.putExtra("description",product.getDescription());

//            this.mContext.startActivity(intent);
        }
    }

    public Garage_Adapter(Context mContext, List<Model> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.garage_row, parent, false);
        return new MyViewHolder(itemView,mContext,productList);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        MainActivity add_garage=new MainActivity();
        Model pd = productList.get(position);
        holder.name.setText(pd.getGaragename());
        Double distance= distanceFrom(Double.parseDouble(pd.getLatitude()),Double.parseDouble(pd.getLogitude()));
        holder.address.setText(distance.toString()+" "+"km");
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public double distanceFrom( double lat2, double lng2) {
        double ld=-0.329202;
        double lgf=32.570987;
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2-ld);
        double dLng = Math.toRadians(lng2-lgf);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(ld)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;
        double meterConversion = 1609;

        return Double.parseDouble(String.format("%.2f", new Double(dist * meterConversion).floatValue()));  // this will return distance

    }


}
