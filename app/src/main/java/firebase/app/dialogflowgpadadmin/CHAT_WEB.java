package firebase.app.dialogflowgpadadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;

import android.content.ComponentName;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

public class CHAT_WEB extends AppCompatActivity {

    String CUSTOM_PACKAGE = "com.android.chrome";
    String websiteURL = "https://www.kommunicate.io/test?appId=16982c94db31e7fad2e95d7e28150f85a&botIds=gbot-kp3f8&assignee=gbot-kp3f8";

    CustomTabsClient customTabsClient;
    CustomTabsSession customTabsSession;
    CustomTabsServiceConnection customTabsServiceConnection;
    CustomTabsIntent customTabsIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chat__web );


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

    }
}
