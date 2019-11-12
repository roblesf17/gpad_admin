package firebase.app.dialogflowgpadadmin;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.speech.tts.TextToSpeech;
import android.view.View;

import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.logging.Logger;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , AIListener {

    //crea objeto para la autenticacion
    private FirebaseAuth mAuth;

    //DIALOGFLOW
    private TextToSpeech mTextToSpeech;
    private AIService mAIService;

    //CHAT WEB DIALOGFLOW******************
    String CUSTOM_PACKAGE = "com.android.chrome";
    String websiteURL = "https://www.kommunicate.io/test?appId=16982c94db31e7fad2e95d7e28150f85a&botIds=gbot-kp3f8&assignee=gbot-kp3f8";
    String websiteURLDialogFlorg = "https://dialogflow.cloud.google.com/#/agent/ea164467-ea5d-464b-ae49-e49e9963b6ad/intents";
    CustomTabsClient customTabsClient;
    CustomTabsSession customTabsSession;
    CustomTabsServiceConnection customTabsServiceConnection;
    CustomTabsIntent customTabsIntent;
    /////**********************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mAIService.startListening();

                Snackbar.make(view, "Conectando con DialogFlow", Snackbar.LENGTH_LONG)
                        .setAction("Exito", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);





        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);



        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //dialogflow
        final ai.api.android.AIConfiguration config = new ai.api.android.AIConfiguration("64f86297edff4c259f7cb8f0e3d56610", ai.api.android.AIConfiguration.SupportedLanguages.Spanish, AIConfiguration.RecognitionEngine.System);
        mAIService = AIService.getService(this, config);
        mAIService.setListener( this );
        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });
//


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);

        //CARGARAN LOS NOMBRES TEMPORALES CARGADOS DESDE EL MAIN ACTIVITY
        TextView mtitulo = (TextView) findViewById(R.id.titulo_nav);
        TextView mcorreo = (TextView) findViewById(R.id.correo_nav);

        mtitulo.setText(Login.nombre_Temporal);
        mcorreo.setText(Login.correo_Temporal);
        //
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            startActivity(new Intent(Principal.this , MainActivity.class));
        } else if (id == R.id.nav_gallery) {

            startActivity(new Intent(Principal.this , UPLOAD_main.class));

        } else if (id == R.id.nav_slideshow) {

            startActivity(new Intent(Principal.this , MostrarDocumentos.class));

        } else if (id == R.id.nav_tools) {

            //////
            customTabsServiceConnection = new CustomTabsServiceConnection() {
                @Override
                public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {

                    customTabsClient = client;
                    customTabsClient.warmup(  0L);
                    customTabsSession = customTabsClient.newSession( null );

                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {

                    customTabsClient = null;

                }
            };

            customTabsClient.bindCustomTabsService( this,CUSTOM_PACKAGE,customTabsServiceConnection );
            customTabsIntent = new CustomTabsIntent.Builder( customTabsSession )
                    .setShowTitle( false )
                    .setToolbarColor( Color.WHITE )
                    .build();

            customTabsIntent.launchUrl( this, Uri.parse( websiteURL ) );

            ///////

            /*startActivity(new Intent(Principal.this , CHAT_WEB.class));
            finish();*/

        } else if (id == R.id.nav_share) {

            //////
            customTabsServiceConnection = new CustomTabsServiceConnection() {
                @Override
                public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {

                    customTabsClient = client;
                    customTabsClient.warmup(  0L);
                    customTabsSession = customTabsClient.newSession( null );

                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {

                    customTabsClient = null;

                }
            };

            customTabsClient.bindCustomTabsService( this,CUSTOM_PACKAGE,customTabsServiceConnection );
            customTabsIntent = new CustomTabsIntent.Builder( customTabsSession )
                    .setShowTitle( false )
                    .setToolbarColor( Color.WHITE )
                    .build();

            customTabsIntent.launchUrl( this, Uri.parse( websiteURLDialogFlorg ) );

            ///////

        } else if (id == R.id.nav_send) {

            //mAuth.signOut();
            startActivity(new Intent(Principal.this , Login.class));
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    ///metodos del all listener de dialogflow
    @Override
    public void onResult(AIResponse response) {
        Result result = response.getResult();
        mTextToSpeech.speak(result.getFulfillment().getSpeech(), TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }

    ///
}
