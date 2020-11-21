package ace.infosolutions.tournyapp.model;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class MyItems {
    private String title;
    private String subtitle;

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }


    public MyItems(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    @NonNull
    @Override
    public String toString() {
        return "Title: "+title + "\n"+
                "Subtitle: "+subtitle + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyItems myItems = (MyItems) o;
        return title.equals(myItems.title) &&
                subtitle.equals(myItems.subtitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, subtitle);
    }

    public static final DiffUtil.ItemCallback<MyItems> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MyItems>() {
                @Override
                public boolean areItemsTheSame(@NonNull MyItems oldItem, @NonNull MyItems newItem) {
                    return oldItem.title.equals(newItem.title);
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull MyItems oldItem, @NonNull MyItems newItem) {
                    return oldItem.equals(newItem);
                }
            };
}
