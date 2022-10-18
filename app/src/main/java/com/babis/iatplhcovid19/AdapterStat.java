package com.babis.iatplhcovid19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterStat extends RecyclerView.Adapter<AdapterStat.HolderStat> implements Filterable {

    private Context context;
    public ArrayList<ModelStat> statArrayList, filterList;
    private FilterStat filter;

    public AdapterStat(Context context, ArrayList<ModelStat> statArrayList) {
        this.context = context;
        this.statArrayList = statArrayList;
        this.filterList = statArrayList;
    }

    @NonNull
    @Override
    public HolderStat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_stat, parent, false);

        return new HolderStat(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderStat holder, int position) {
        //get data
        ModelStat modelStat = statArrayList.get(position);

        String country = modelStat.getCountry();
        String cases = modelStat.getCases();
        String recovered = modelStat.getRecovered();
        String critical = modelStat.getCritical();
        String active = modelStat.getActive();
        String todayCases = modelStat.getTodayCases();
        String todayDeaths = modelStat.getTodayDeaths();
        String deaths = modelStat.getDeaths();

        //ste data
        holder.tvCountry.setText(country);
        holder.tvCases.setText(cases);
        holder.tvRecovered.setText(recovered);
        holder.tvCritical.setText(critical);
        holder.tvActive.setText(active);
        holder.tvTodayCases.setText(todayCases);
        holder.tvTodayDeaths.setText(todayDeaths);
        holder.tvTotalDeaths.setText(deaths);


    }

    @Override
    public int getItemCount() {
        return statArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new FilterStat(this, filterList);
        }
        return filter;
    }

    class HolderStat extends RecyclerView.ViewHolder{

        TextView tvCountry,tvCases,tvRecovered,tvCritical,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths;

        public HolderStat(@NonNull View itemView) {
            super(itemView);

            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvCases = itemView.findViewById(R.id.tvCases);
            tvRecovered = itemView.findViewById(R.id.tvRecovered);
            tvCritical = itemView.findViewById(R.id.tvCritical);
            tvActive = itemView.findViewById(R.id.tvActive);
            tvTodayCases = itemView.findViewById(R.id.tvTodayCases);
            tvTotalDeaths = itemView.findViewById(R.id.tvTotalDeaths);
            tvTodayDeaths = itemView.findViewById(R.id.tvTodayDeaths);

        }
    }

}
