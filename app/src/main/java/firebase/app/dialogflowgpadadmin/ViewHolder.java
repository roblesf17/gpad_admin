package firebase.app.dialogflowgpadadmin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.core.Context;
import com.squareup.picasso.Picasso;


public class ViewHolder extends RecyclerView.ViewHolder
{

    View mView;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mClickListener.onItemClick(view,getAdapterPosition());


            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                mClickListener.onItemLongClick(view,getAdapterPosition());

                return true;


            }
        });

    }


    public static String titulo_temporal[] = new String[99];
    public static int i = 0 ;

    public void setDetails(FirebaseRecyclerAdapter<Model, ViewHolder> ctx , String title , String image , String Descripcion)
    {
       TextView mTitle = mView.findViewById(R.id.rTitleTv);
        TextView mDescription = mView.findViewById(R.id.rDescription);
       ImageView mImage = mView.findViewById(R.id.rImage);


       mTitle .setText(title);
        mDescription.setText( Descripcion );
       Picasso.get().load(image).into(mImage);

        titulo_temporal[i] =  title ;
        i++;
    }

    private ViewHolder.ClickListener mClickListener;
    public interface ClickListener
    {
        void onItemClick (View view , int position);
        void onItemLongClick (View view , int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener)
    {
        mClickListener = clickListener;
    }

}
