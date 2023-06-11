package com.example.finalmobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalmobile.R;
import com.example.finalmobile.models.Cast;
import com.example.finalmobile.networks.Const;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {
    private final List<Cast> casts;

    public CastAdapter(List<Cast> casts){
        this.casts = casts;
        System.out.println(casts.size());
    }
    @NonNull
    @NotNull
    @Override
    public CastAdapter.CastViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_cast_recycler, parent, false);
        return new CastAdapter.CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CastAdapter.CastViewHolder holder, int position) {
        holder.tvCastName.setText(casts.get(position).getName());
        holder.tvCastCharacter.setText(casts.get(position).getCharacter());
        Glide.with(holder.itemView.getContext()).load(Const.IMG_URL_200 + casts.get(position).getProfile_path()).into(holder.ivCastPhoto);

    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCastPhoto;
        TextView tvCastName;
        TextView tvCastCharacter;
        public CastViewHolder(View itemView) {
            super(itemView);
            ivCastPhoto = itemView.findViewById(R.id.iv_cast);
            tvCastName = itemView.findViewById(R.id.tv_cast_name);
            tvCastCharacter = itemView.findViewById(R.id.tv_cast_character);
        }
    }
}
