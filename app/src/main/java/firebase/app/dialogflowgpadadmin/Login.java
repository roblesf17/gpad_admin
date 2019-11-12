package firebase.app.dialogflowgpadadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText mEditTextEmail , mEditTextPassword;
    private Button mButtomlogin;

    private String email = "" ;
    private String password = "" ;

    //VARIABLES TEMPORALES
    public static  String nombre_Temporal;
    public static  String correo_Temporal;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        //identificar los controles
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail_login);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPaswword_login);
        mButtomlogin = (Button) findViewById(R.id.btnLogin);

        mButtomlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = mEditTextEmail.getText().toString();
                password = mEditTextPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty())
                {
                    Iniciando_sesion();
                }

                else
                {
                    Toast.makeText(Login.this ,
                            "Debe completar los campos" , Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void Iniciando_sesion ()
    {
        mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {

                    nombre_Temporal = "Bienvenido Menu Principal";
                    correo_Temporal = email ;

                    startActivity(new Intent(Login.this , Principal.class));
                    finish();
                }

                else
                {
                    Toast.makeText(Login.this ,
                            "Error al iniciar sesion , compruebe los datos" , Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
