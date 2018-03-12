package com.sawitku.fadapter;

/**
 * Created by Mohammad Iqbal on 9/7/2016.
 * Email : iqbalhood@gmail.com
 * Ini adalah fungsi setting adapter untuk menyiapkan data yang akan ditampilkan di
 * fragment
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sawitku.R;
import com.sawitku.fmodels.Tanya;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class PertanyaanAdapter extends ArrayAdapter<Tanya> {
    ArrayList<Tanya> TanyaList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public PertanyaanAdapter(Context context, int resource, ArrayList<Tanya> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        TanyaList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.tvId             = (TextView)  v.findViewById(R.id.iv_id);
            holder.tvNama           = (TextView)  v.findViewById(R.id.tv_name);
            holder.tvTime           = (TextView)  v.findViewById(R.id.tv_tanggal);
            holder.tvPertanyaan     = (TextView)  v.findViewById(R.id.tv_question);
            holder.tvAlamat         = (TextView)  v.findViewById(R.id.tv_province);
            holder.Foto             = (ImageView)v.findViewById(R.id.iv_movie_poster);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.tvId.setText(TanyaList.get(position).getId());
        holder.tvNama.setText(TanyaList.get(position).getNama());
        holder.tvTime.setText(TanyaList.get(position).getTime());
        holder.tvPertanyaan.setText(TanyaList.get(position).getQuestion());
        holder.tvAlamat.setText(TanyaList.get(position).getAlamat());
        Glide.with(getContext()).load(TanyaList.get(position).getProfil_picture())
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(holder.Foto);



        return v;

    }


    static class ViewHolder {
        public TextView  tvId;
        public TextView  tvNama;
        public TextView  tvTime;
        public TextView  tvPertanyaan;
        public TextView  tvAlamat;
        public ImageView Foto;
    }











}
