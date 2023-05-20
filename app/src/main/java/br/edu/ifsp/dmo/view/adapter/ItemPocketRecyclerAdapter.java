package br.edu.ifsp.dmo.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import br.edu.ifsp.dmo.R;
import br.edu.ifsp.dmo.model.entities.Tarefa;
import br.edu.ifsp.dmo.mvp.MainMVP;
import br.edu.ifsp.dmo.view.RecyclerViewItemClickListener;

public class ItemPocketRecyclerAdapter extends RecyclerView.Adapter<ItemPocketRecyclerAdapter.ViewHolder>{
    private Context context;
    private MainMVP.Presenter presenter;
    private List<Tarefa> data;
    private static RecyclerViewItemClickListener clickListener;

    public ItemPocketRecyclerAdapter(Context context, List<Tarefa> data, MainMVP.Presenter presenter){
        this.context = context;
        this.presenter = presenter;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tarefa tarefa = data.get(position);
        holder.tituloTextView.setText(tarefa.getTitulo());
        holder.descricaoTextView.setText(tarefa.getDescricao());
        if(tarefa.isFavorite()){
            holder.favoriteImageView.setColorFilter(context.getColor(R.color.RED));
        }else{
            holder.favoriteImageView.setColorFilter(context.getColor(R.color.black));
        }
        holder.favoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartClick(tarefa);
            }
        });
        holder.removeImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                removeClick(tarefa);
            }
        });
        holder.editImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editClick(tarefa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(RecyclerViewItemClickListener listener){
        clickListener = listener;
    }

    private void heartClick(Tarefa tarefa){
        presenter.favoriteTask(tarefa);
        notifyDataSetChanged();
    }

    private void removeClick(Tarefa tarefa){
        presenter.removeTask(tarefa);
        notifyDataSetChanged();
    }

    private void editClick(Tarefa tarefa){
        presenter.editTask(tarefa);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tituloTextView;
        public TextView descricaoTextView;
        public ImageView favoriteImageView;
        public ImageView removeImageView;
        public ImageView editImageView;

        public ViewHolder(View itemView){
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.text_title_listitem);
            descricaoTextView = itemView.findViewById(R.id.text_url_listitem);
            favoriteImageView = itemView.findViewById(R.id.image_favorite_listitem);
            removeImageView = itemView.findViewById(R.id.image_remove_listitem);
            editImageView = itemView.findViewById(R.id.image_edit_listitem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null){
                clickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
