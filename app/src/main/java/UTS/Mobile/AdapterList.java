package UTS.Mobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder> {
    private final List<ItemList> itemLists;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ItemList item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AdapterList(List<ItemList> itemLists) {
        this.itemLists = itemLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemList item = itemLists.get(position);
        holder.judul.setText(item.getJudul());
        holder.subJudul.setText(item.getSubJudul());
        Glide.with(holder.imageView.getContext()).load(item.getImageUrl()).into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView judul, subJudul;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judul);
            subJudul = itemView.findViewById(R.id.sub_judul);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
