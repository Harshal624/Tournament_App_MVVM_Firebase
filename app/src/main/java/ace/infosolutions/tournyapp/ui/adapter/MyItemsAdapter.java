package ace.infosolutions.tournyapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ace.infosolutions.tournyapp.model.MyItems;
import ace.infosolutions.tournyapp.ui.callbacks.MyItemClickListener;
import ace.infosolutions.tournyapp.databinding.ItemListBinding;

public class MyItemsAdapter extends ListAdapter<MyItems, MyItemsAdapter.ViewHolder> {
    private ItemListBinding binding;
    private MyItemClickListener listener;


    public MyItemsAdapter(@NonNull DiffUtil.ItemCallback<MyItems> diffCallback,MyItemClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyItems user = getItem(position);
        holder.bind(user);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 11/18/2020 Handle onclicklistener
                    listener.onClick(getAdapterPosition());
                }
            });
        }

        public void bind(MyItems user) {
            binding.title.setText(user.getTitle());
            binding.subtitle.setText(user.getSubtitle());
        }
    }

}
