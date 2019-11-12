package firebase.app.dialogflowgpadadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MostrarDocumentos extends AppCompatActivity {

    LinearLayoutManager mLinearLayoutManager;

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDataBase;
    DatabaseReference mDatabaseReference;

    FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Model> options;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_documentos);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRecyclerView = findViewById(R.id.recyclerView);

        mFirebaseDataBase = FirebaseDatabase.getInstance();

        mDatabaseReference = mFirebaseDataBase.getReference("Documento_gpad");

        showData();
    }





    private void showData() {

        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference,Model.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int i, @NonNull Model model) {


                //Toast.makeText( MostrarDocumentos.this, model.getTitle().toString(), Toast.LENGTH_SHORT ).show();

                holder.setDetails( this,model.getTitulo(),model.getImagen() , model.getDescripcion());



            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext()).inflate( R.layout.row,parent,false );
                ViewHolder viewHolder = new ViewHolder( itemView );
                viewHolder.setOnClickListener( new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    TextView rtitle = (TextView) findViewById( R.id.rTitleTv ) ;
                    String texto = rtitle.getText().toString();

                       //Toast.makeText( MostrarDocumentos.this, ViewHolder.titulo_temporal[position] , Toast.LENGTH_SHORT ).show();

                        Toast.makeText( MostrarDocumentos.this, Integer.toString( position ), Toast.LENGTH_SHORT ).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                        TextView rtitle = (TextView) findViewById( R.id.rTitleTv ); ;
                        String texto = rtitle.getText().toString();

                       // Toast.makeText( MostrarDocumentos.this, texto , Toast.LENGTH_SHORT ).show();

                    }
                } );

                return viewHolder;
            }
        };

        mRecyclerView.setLayoutManager( mLinearLayoutManager );
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter( firebaseRecyclerAdapter );
    }



    protected void onStart()
    {
        super.onStart();

        if (firebaseRecyclerAdapter != null)
        {
            firebaseRecyclerAdapter.startListening();
        }
    }
}
