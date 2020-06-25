package com.hamlet.COVID_19;

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
import java.util.List;


public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleviewHolder> implements Filterable {


    private Context context;
    private ArrayList<ExampleItem> exampleItems;
    private ArrayList<ExampleItem> exampleItemsfull;

    private OnItemClickListner mListner;

    public interface OnItemClickListner{
        void onItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        mListner = listner;
    }

    public ExampleAdapter(Context context, ArrayList<ExampleItem> exampleItems) {
        this.context = context;
        this.exampleItems = exampleItems;
        this.exampleItemsfull = new ArrayList<>(exampleItems);
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<ExampleItem> filterList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterList.addAll(exampleItemsfull);
            }else{
                String pattern = constraint.toString().toLowerCase().trim();

                for(ExampleItem item : exampleItemsfull){
                    if(item.getCountryName().toLowerCase().contains(pattern)){
                        filterList.add(item);
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleItems.clear();
            exampleItems.addAll( (List) results.values );
            notifyDataSetChanged();
        }
    };


    @NonNull
    @Override
    public ExampleviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.example_item,parent,false);
        return new ExampleviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleviewHolder holder, int position) {
        ExampleItem currentItem = exampleItems.get(position);

        String countryName = currentItem.getCountryName();
        int confirmedCases = currentItem.getConfirmedCases();
        int recoverCases = currentItem.getRecoveredCases();
        int deathes = currentItem.getDeathes();

        holder.nameText.setText(countryName);
        holder.deathText.setText("Deathes : " + deathes);
        holder.recoverText.setText("Recovered : " + recoverCases);
        holder.confirmText.setText("Confirmed : " + confirmedCases);

    }

    @Override
    public int getItemCount() {
        return exampleItems.size();
    }

    public class ExampleviewHolder extends RecyclerView.ViewHolder{

        public TextView nameText;
        public TextView confirmText;
        public TextView deathText;
        public TextView recoverText;

        public ExampleviewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            confirmText = itemView.findViewById(R.id.confirmedText);
            recoverText = itemView.findViewById(R.id.recoveredText);
            deathText = itemView.findViewById(R.id.deathText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
