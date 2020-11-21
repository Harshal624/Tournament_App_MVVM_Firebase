package ace.infosolutions.tournyapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ace.infosolutions.tournyapp.model.MyItems;
import ace.infosolutions.tournyapp.databinding.ItemListBinding;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
   private ItemListBinding binding;
   private ArrayList<MyItems> arrayList;

    public RecyclerAdapter(ArrayList<MyItems> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        holder.title.setText(arrayList.get(pos).getTitle());
        holder.subtitle.setText(arrayList.get(pos).getSubtitle());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView subtitle;
        private ImageView photo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = binding.title;
            photo = binding.imageview;
            subtitle = binding.subtitle;
        }
    }

}
