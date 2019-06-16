package com.example.admin.dormmanagingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.dormmanagingapp.Model.Resident;
import com.example.admin.dormmanagingapp.R;

import java.util.List;

public class GalleryAdapterWithCache extends ArrayAdapter<Resident> {

    private Context context;
    List<Resident> residentList;

    public GalleryAdapterWithCache(Context context, List<Resident> residentList) {
        super(context, R.layout.grid_item, residentList);
        this.context = context;
        this.residentList = residentList;
    }
    /*public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);

        ProductViewHolder holder;

        if (convertView == null) {
            convertView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            convertView = vi.inflate(R.layout.grid_item, parent, false);

            //
            holder = new ProductViewHolder();
            holder.img = (ImageView)convertView.findViewById(R.id.image);
            holder.title = (TextView)convertView.findViewById(R.id.title);

            //
            convertView.setTag(holder);
        }
        else{
            holder = (ProductViewHolder) convertView.getTag();
        }


        //
        holder.populate(product, ((MyActivityGrid)mContext).isLvBusy());

        //
        return convertView;
    }

    static class ProductViewHolder {
        public ImageView img;
        public TextView title;

        void populate(Product p) {
            title.setText(p.title);

            //
            ImageDownloader imageDownloader = new ImageDownloader();
            imageDownloader.download(p.img_url, img);
        }

        void populate(Product p, boolean isBusy) {
            title.setText(p.title);

            if (!isBusy){
                // download from internet
                ImageDownloader imageDownloader = new ImageDownloader();
                imageDownloader.download(p.img_url, img);
            }
            else{
                // set default image
                img.setImageResource(R.drawable.spinner);
            }
        }
    }*/
}
