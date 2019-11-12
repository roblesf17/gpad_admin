package firebase.app.dialogflowgpadadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UPLOAD_main extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1 ;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;

    private EditText getmEditTextDescription;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;


    //

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;


    private StorageTask mUploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_upload_main );


        mButtonChooseImage = (Button) findViewById( R.id.button_choose_image );
        mButtonUpload = (Button) findViewById( R.id.button_upload );
        mTextViewShowUploads = (TextView) findViewById( R.id.text_view_show_uploads );
        mEditTextFileName = (EditText)findViewById( R.id.edit_text_file_name );
        getmEditTextDescription = (EditText) findViewById( R.id.edit_text_description_upload );
        mImageView = (ImageView)findViewById( R.id.image_view );
        mProgressBar = (ProgressBar)findViewById( R.id.progress_bar );


        //STORAGE
        mStorageRef = FirebaseStorage.getInstance().getReference("Documento_gpad");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Documento_gpad");


        //BUTTOM//
        mButtonChooseImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();

            }
        } );

        //BUTTOM
        mButtonUpload.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mUploadTask != null && mUploadTask.isInProgress())
                {
                    Toast.makeText( UPLOAD_main.this, "Subida en progreso" , Toast.LENGTH_SHORT ).show();
                }

                else {
                    uploadFile();
                }


            }
        } );

        //TEXTVIEW
        mTextViewShowUploads.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                openImagesActivity();

            }
        } );

    }


    private void openFileChooser()
    {
        Intent intent = new Intent( );
        intent.setType( "image/*");
        intent.setAction( Intent.ACTION_GET_CONTENT);
        startActivityForResult( intent , PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(mImageView);

            String url = mImageUri.toString();
            Toast.makeText( UPLOAD_main.this, url , Toast.LENGTH_SHORT ).show();
        }
    }

    //STORARE----------------------------------------------------
    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType( cR.getType( uri ) );
    }

    private void uploadFile()
    {
        if (mImageUri != null)
        {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension( mImageUri ) );

            mUploadTask =  fileReference.putFile( mImageUri).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Handler handler = new Handler( );
                    handler.postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress( 0 );
                        }
                    } , 500);

                    //Uri dowloaduri = taskSnapshot.getMetadata().getD


                    String parte1 = "https://firebasestorage.googleapis.com/v0/b/proyectogpad.appspot.com/o/Documento_gpad%2F";
                    String parte2 =  "?alt=media&token=f5bd30bd-1ca1-4642-b16a-dd2296b4baf0";

                    Toast.makeText( UPLOAD_main.this, "Subida exitosa" , Toast.LENGTH_SHORT ).show();
                    UPLOAD_upload upload = new UPLOAD_upload(mEditTextFileName.getText().toString().trim(),parte1 + taskSnapshot.getMetadata().getReference().getName().toString() + parte2,getmEditTextDescription.getText().toString());




                    Toast.makeText( UPLOAD_main.this, "Subida exitosa" , Toast.LENGTH_SHORT ).show();

                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue( upload );


                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText( UPLOAD_main.this,e.getMessage() , Toast.LENGTH_SHORT ).show();
                }
            } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress( (int) progress );
                }
            } );
        }

        else
        {
            Toast.makeText( UPLOAD_main.this, "archivo no encontrado" , Toast.LENGTH_SHORT ).show();
        }



    }

    private void openImagesActivity()
    {
        /*
        Intent intent = new Intent( this , UPLOAD_ImagesActivity.class );
        startActivity( intent );
        */

    }
}
