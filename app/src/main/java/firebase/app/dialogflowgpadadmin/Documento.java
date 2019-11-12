package firebase.app.dialogflowgpadadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Documento extends AppCompatActivity {

    //CREANDO OBJETOS
    EditText tituloP , descripcionP , ImagenP ;
    ListView listV_persona;

    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documento);

        //INDENTIFICAMOS LOS CONTROLES
        tituloP = findViewById(R.id.txt_titulo);
        descripcionP = findViewById(R.id.txt_descripcion);
        ImagenP = findViewById(R.id.txt_imagen);

        //INICIAMOS EL METODO
        inicialistarFirebase();

    }

    //METODO CREADO PARA INICIAR EL FIREBASE
    private void inicialistarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void onclickRegistrarDocumento(View v)
    {
        String titulo = tituloP.getText().toString();
        String descripcion = descripcionP.getText().toString();
        String imagen = ImagenP.getText().toString();

        if (titulo.equals("") || descripcion.equals("") || imagen.equals("") )
        {
            validacion();

        }

        else
        {
            firebase.app.dialogflowgpadadmin.model.Documento p = new  firebase.app.dialogflowgpadadmin.model.Documento();
            p.setUid(UUID.randomUUID().toString());
            p.setTitulo(titulo);
            p.setDescripcion(descripcion);
            p.setImagen(imagen);

            databaseReference.child("Documento").child(p.getUid()).setValue(p);

            Toast.makeText(this ,
                    "Regidtro exitoso " , Toast.LENGTH_SHORT).show();
            limpiar_caja();

        }

    }

    private void validacion()
    {
        String titulo = tituloP.getText().toString();
        String descripcion = descripcionP.getText().toString();
        String imagen = ImagenP.getText().toString();

        if (titulo.equals(""))
        {
            tituloP.setError("Required");
        }
        else if (descripcion.equals(""))
        {
            descripcionP.setError("Required");
        }

        else if (imagen.equals(""))
        {
            ImagenP.setError("Required");
        }
    }

    private void limpiar_caja()
    {
        tituloP.setText("");
        descripcionP.setText("");
        ImagenP.setText("");

    }



}
