package com.example.ontimetourismrecommender;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class recommendation_explore_Adaptar  extends RecyclerView.Adapter<recommendation_explore_Adaptar.TopPlacesViewHolder> {

    Context context;
    List<siteData> Site;

    public recommendation_explore_Adaptar(Context context, List<siteData> Site) {
        this.context = context;
        this.Site = Site;
    }

    @NonNull
    @Override
    public TopPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recommendation_column, parent, false);

        // here we create a recyclerview row item layout file
        return new TopPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPlacesViewHolder holder, int position) {
siteData site=Site.get(position);

        holder.siteinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sheardpref.getInstance(context).SetSiteInfoId(site.getID());
                Intent intent=new Intent(context, site_ifo.class);
                context.startActivity(intent);

            }
        });
     holder.Map.setOnClickListener(new View.OnClickListener(){

         @Override
         public void onClick(View v) {

                 Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(site.getMap()));
                 context.startActivity(i);


         }
     });
        holder.placeName.setText(site.getName());
       Picasso.get().load(site.getImage()).into(holder.placeImage);

    }

    @Override
    public int getItemCount() {
        return Site.size();
    }

    public static final class TopPlacesViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImage, siteinfo;
        TextView placeName, Map;

        public TopPlacesViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.recomdationSite_image);
            placeName = itemView.findViewById(R.id.RecommenationSiteName);
            Map = itemView.findViewById(R.id.themap);
             siteinfo=itemView.findViewById(R.id.GoToSiteInf);


        }
    }
}
