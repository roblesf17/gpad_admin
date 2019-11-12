package firebase.app.dialogflowgpadadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText mEditTextNombre , mEditTextEmail , mEditTextPassword;
    private Button mButtomRegistrar;

    //VARIABLES TEMPORALES
    public static  String nombre_Temporal;
    public static  String correo_Temporal;



    //VARIABLES DE DATOS A REGISTRAR
    private String nombre = "" ;
    private String email = "" ;
    private String password = "" ;

    //crear el objeto firebase
    FirebaseAuth mAuth;
    //crear objeto de databereference
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INSTANCIAMOS EL FIREBASE
        mAuth = FirebaseAuth.getInstance();
        //hace refefencia al nodo principal de la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //identificar los controles
        mEditTextNombre = (EditText) findViewById(R.id.editTextNombre);
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPaswword);
        mButtomRegistrar = (Button) findViewById(R.id.btnRegistrar);


        //AL PRESIONAR EL BOTON REGISTRAR
        mButtomRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre = mEditTextNombre.getText().toString();
                email = mEditTextEmail.getText().toString();
                password = mEditTextPassword.getText().toString();


                if(!nombre.isEmpty() && !email.isEmpty() && !email.isEmpty())
                {
                    if (password.length() >= 6)
                    {
                        registrar_usuario();
                    }

                    else
                    {
                        Toast.makeText(MainActivity.this ,
                                "La contraseña debe ser al menos de 6 caracteres" , Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this ,
                            "Debe completar los campos" , Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private  void registrar_usuario()
    {
        mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    //se creara el mapa de valores
                    Map<String , Object> map = new HashMap<>();

                    //campos que tendra la BD  de la tabla USUARIOS
                    map.put("Nombre" , nombre);
                    map.put("Correo" , email);
                    map.put("Contraseña" , password);


                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2)
                        {
                            //SI LOS DATOS FUERON CORRECTOS Y EXITOSOS
                            if(task2.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this ,
                                        "Guardando ..." , Toast.LENGTH_SHORT).show();

                                Toast.makeText(MainActivity.this ,
                                        "Usuario Registrado" , Toast.LENGTH_SHORT).show();



                                mEditTextEmail.setText("");
                                mEditTextNombre.setText("");
                                mEditTextPassword.setText("");

                                //nombre_Temporal = nombre ;
                                //correo_Temporal = email ;

                                Intent intent = new Intent(MainActivity.this, Principal.class);
                                startActivity(intent);

                            }

                            else
                            {
                                Toast.makeText(MainActivity.this ,
                                        "Registro de usuario fallido ..." , Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

                else
                {
                    Toast.makeText(MainActivity.this ,
                            "No se pudo registrar este usuario" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
