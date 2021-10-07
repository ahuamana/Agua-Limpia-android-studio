package edu.aha.agualimpiafinal.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.ActivityButterflyChallengeBinding;
import edu.aha.agualimpiafinal.models.MoldeSustantivo;
import edu.aha.agualimpiafinal.providers.ImageProvider;
import edu.aha.agualimpiafinal.providers.InsectosProvider;

public class ButterflyChallengeActivity extends AppCompatActivity {

    private ActivityButterflyChallengeBinding binding;

    Options mOptions;
    ArrayList<String> mReturnValues = new ArrayList<>();
    File mImageFile;
    File mImageFile2;
    File mImageFile3;

    String id_photo_alas, id_photo_cabeza, id_photo_abdomen;

    Context mContext;
    ProgressDialog mDialog;

    ImageProvider mImageProvider;
    MoldeSustantivo sustantivo;
    InsectosProvider mInsectosProvider;

    String email, firstname, lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityButterflyChallengeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //All code here

        mImageProvider=new ImageProvider();
        mInsectosProvider = new InsectosProvider();

        setOnClickListeners();

        cargarPreferencias();
        
        getUserInfoAll();

        goBackActivity();

    }

    private void goBackActivity() {
        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getUserInfoAll() {

        getUserInfo(email, "cabeza mariposa", binding.circleImageViewPhoto, binding.textViewImagenNosubida, binding.btnregistrar);
        getUserInfo(email, "alas mariposa", binding.circleImageViewPhotoAlas, binding.textViewImagenNosubidaAlas, binding.btnregistrarAlas);
        getUserInfo(email, "adbomen mariposa", binding.circleImageViewPhotoAbdomen, binding.textViewImagenNosubidaAbdomen, binding.btnregistrarAbdomen);
    }

    private void getUserInfo(String emailReceiver, String nameSustantivo, final CircleImageView circleImageView, TextView textView, Button btnregistrar) {

        //Log.e("TASK", "email" + email);
        mInsectosProvider.search(emailReceiver,nameSustantivo).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {

                    if(task.getResult().size() > 0)
                    {
                        String id_photo = task.getResult().getDocuments().get(0).get("id").toString();

                        //Log.e("TASK", "URL DOCUMENTO 0:"+ task.getResult().getDocuments().get(0).get("url"));
                        if(nameSustantivo.equals("cabeza mariposa"))
                        {
                            id_photo_cabeza = id_photo;
                        }else
                        {
                            if(nameSustantivo.equals("alas mariposa"))
                            {
                                id_photo_alas = id_photo;
                            }else
                            {
                                id_photo_abdomen = id_photo;
                            }
                        }

                        String url = task.getResult().getDocuments().get(0).get("url").toString();
                        circleImageView.setBorderColor(0);//eliminar border color del XML para que se vea mas agradable
                        circleImageView.setBorderWidth(0);//eliminar ancho de border del XML para que se vea mas agradable
                        btnregistrar.setText("ACTUALIZAR");
                        textView.setVisibility(View.GONE);

                        //Set image from db
                        Glide.with(ButterflyChallengeActivity.this)
                                .load(url)
                                .placeholder(R.drawable.loading_icon)
                                .into(circleImageView);
                    }
                }

            }
        });

    }

    private void cargarPreferencias() {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        firstname= preferences.getString("spfirstname","");
        //middlename= preferences.getString("spmiddlename","");
        lastname= preferences.getString("splastname","");
        email= preferences.getString("spEmail","");

    }

    private void setOnClickListeners() {

        binding.fabSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCamera(100);
            }
        });

        binding.fabSelectImageAlas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera(110);
            }
        });

        binding.fabSelectImageAbdomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera(120);
            }
        });

        binding.btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.btnregistrar.getText().equals("ACTUALIZAR"))
                {
                    Log.e("ACTUALIZAR","TIENES QUE ACTUALIZAR CABEZA");
                    updatePhoto(mImageFile, binding.textViewImagenNosubida, id_photo_cabeza);

                }else
                {
                    setSustantivoData();
                    sustantivo.setName("cabeza mariposa");

                    registrarData(mImageFile, binding.textViewImagenNosubida);
                }

            }
        });

        binding.btnregistrarAlas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.btnregistrar.getText().equals("ACTUALIZAR"))
                {
                    Log.e("ACTUALIZAR","TIENES QUE ACTUALIZAR ALAS");
                    updatePhoto(mImageFile2, binding.textViewImagenNosubidaAlas, id_photo_alas);

                }else
                {

                    setSustantivoData();
                    sustantivo.setName("alas mariposa");

                    registrarData(mImageFile2, binding.textViewImagenNosubidaAlas);
                }
            }
        });

        binding.btnregistrarAbdomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.btnregistrar.getText().equals("ACTUALIZAR"))
                {
                    Log.e("ACTUALIZAR","TIENES QUE ACTUALIZAR ADBOMEN");
                    updatePhoto(mImageFile3, binding.textViewImagenNosubidaAbdomen, id_photo_abdomen);

                }else
                {

                    setSustantivoData();
                    sustantivo.setName("adbomen mariposa");

                    registrarData(mImageFile3, binding.textViewImagenNosubidaAbdomen);
                }

            }
        });

    }

    private void updatePhoto(final File mImageFileReciever, MaterialTextView textViewImagenNosubida, String idphoto) {

        mDialog = new ProgressDialog(ButterflyChallengeActivity.this);
        mDialog.setTitle("Espere un momento");
        mDialog.setMessage("Guardando Información");

        if(idphoto != null)
        {
            Log.e("idphoto", ""+idphoto);

            if(mImageFileReciever != null)
            {
                if(!mImageFileReciever.equals(""))
                {
                    mDialog.show();

                    mImageProvider.save(ButterflyChallengeActivity.this, mImageFileReciever).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if(task.isSuccessful())
                            {
                                mImageProvider.getDownloadUri().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri)
                                    {

                                        String url = uri.toString();
                                        Log.e("URL","url: "+ url);

                                        //sustantivo.setUrl(url);

                                        updatePhotoFirebase(url, textViewImagenNosubida, idphoto); //ACtualiza la informacion en firestorage

                                    }
                                });
                            }else {
                                mDialog.dismiss();
                                Toast.makeText(ButterflyChallengeActivity.this, "No se pudo almacenar la imagen", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            mDialog.dismiss();
                            Log.e("TAG","ERROR" + e.getMessage());

                        }
                    });

                }
            }

        }



    }

    private void updatePhotoFirebase(String url, MaterialTextView textView, String idphoto) {

        Log.e("url","url reciever: "+url);

        mInsectosProvider.update(idphoto, url).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    textView.setVisibility(View.GONE);
                    Toast.makeText(ButterflyChallengeActivity.this, "Foto actualizado Correctamente", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();

                    //goToNextActivity();

                }else {
                    mDialog.dismiss();
                    Toast.makeText(mContext, "No se pudieron almacenar los datos", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "Error al al crear la tarea", Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });

    }

    private void setSustantivoData ()
    {
        String documento =  mInsectosProvider.createDocument().getId();
        sustantivo = new MoldeSustantivo();

        sustantivo.setId(documento);
        sustantivo.setAuthor_email(email);
        sustantivo.setAuthor_name(firstname);
        sustantivo.setAuthor_lastname(lastname);
        sustantivo.setTimestamp(System.currentTimeMillis()/1000);
        sustantivo.setTipo("Insecto");

    }

    private void registrarData(final File mImageFileReciever, TextView textView) {

        mDialog = new ProgressDialog(ButterflyChallengeActivity.this);
        mDialog.setTitle("Espere un momento");
        mDialog.setMessage("Guardando Información");

        if(mImageFileReciever != null)
        {
            if(!mImageFileReciever.equals(""))
            {
                mDialog.show();

                mImageProvider.save(ButterflyChallengeActivity.this, mImageFileReciever).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful())
                        {
                            mImageProvider.getDownloadUri().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri)
                                {

                                    String url = uri.toString();
                                    Log.e("URL","url: "+ url);

                                    sustantivo.setUrl(url);

                                    SaveOnFirebase(url , sustantivo, textView); //ACtualiza la informacion en firestorage

                                }
                            });
                        }else {
                            mDialog.dismiss();
                            Toast.makeText(ButterflyChallengeActivity.this, "No se pudo almacenar la imagen", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        mDialog.dismiss();
                        Log.e("TAG","ERROR" + e.getMessage());

                    }
                });

            }
        }

    }

    private void SaveOnFirebase(String url, MoldeSustantivo sus, TextView textView) {

        Log.e("url","url reciever: "+url);

        mInsectosProvider.create(sus).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    textView.setVisibility(View.GONE);
                    Toast.makeText(ButterflyChallengeActivity.this, "Datos registrados correctamente", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();

                    goToNextActivity();

                }else {
                    mDialog.dismiss();
                    Toast.makeText(mContext, "No se pudieron almacenar los datos", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "Error al al crear la tarea", Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });

    }

    private void goToNextActivity() {

        //Intent i = new Intent(mContext, )
        Intent i = new Intent(ButterflyChallengeActivity.this, ResultadoCapturaImageActivity.class);
        i.putExtra("mensaje","");
        startActivity(i);
    }

    private void openCamera(int requescode) {

        //ImagePicker
        mOptions = Options.init()
                .setRequestCode(requescode)                                           //Request code for activity results
                .setCount(1)                                                   //Number of images to restict selection count
                .setFrontfacing(false)                                         //Front Facing camera on start                             //Pre selected Image Urls
                .setSpanCount(4)                                               //Span count for gallery min 1 & max 5
                .setMode(Options.Mode.Picture)                                     //Option to select only pictures or videos or both
                .setVideoDurationLimitinSeconds(30)                            //Duration for video recording
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                .setPath("/pix/images");


        Pix.start(ButterflyChallengeActivity.this, mOptions);

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition

        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(ButterflyChallengeActivity.this, mOptions);
                } else {
                    Toast.makeText(ButterflyChallengeActivity.this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED)
        {
            if (data != null)
            {
                if (resultCode == Activity.RESULT_OK && requestCode == 100)
                {
                    Log.e("DATA INGRESASTE: ", "RequestCode: " + requestCode + " & resultacode: "+resultCode);
                    mReturnValues = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                    mImageFile = new File(mReturnValues.get(0)); // Guardar en File la imagen recibida si el usuario selecciono una imagen
                    binding.circleImageViewPhoto.setBorderColor(0);//eliminar border color del XML para que se vea mas agradable
                    binding.circleImageViewPhoto.setBorderWidth(0);//eliminar ancho de border del XML para que se vea mas agradable
                    binding.circleImageViewPhoto.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath())); //Asignar la imagen al id del xml
                    Log.e("IMAGE PATH",""+ mReturnValues.get(0));
                    Log.e("IMAGE ABS PATH",""+ mImageFile.getAbsolutePath());

                }
                else
                {
                    if(resultCode == Activity.RESULT_OK && requestCode == 110)
                    {
                        //code here
                        Log.e("DATA INGRESASTE: ", "RequestCode: " + requestCode + " & resultacode: "+resultCode);
                        mReturnValues = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                        mImageFile2 = new File(mReturnValues.get(0)); // Guardar en File la imagen recibida si el usuario selecciono una imagen
                        binding.circleImageViewPhotoAlas.setBorderColor(0);//eliminar border color del XML para que se vea mas agradable
                        binding.circleImageViewPhotoAlas.setBorderWidth(0);//eliminar ancho de border del XML para que se vea mas agradable
                        binding.circleImageViewPhotoAlas.setImageBitmap(BitmapFactory.decodeFile(mImageFile2.getAbsolutePath())); //Asignar la imagen al id del xml
                        Log.e("IMAGE PATH",""+ mReturnValues.get(0));
                        Log.e("IMAGE ABS PATH",""+ mImageFile2.getAbsolutePath());


                    }else
                    {
                        if(resultCode == Activity.RESULT_OK && requestCode == 120)
                        {
                            //code here
                            Log.e("DATA INGRESASTE: ", "RequestCode: " + requestCode + " & resultacode: "+resultCode);
                            mReturnValues = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                            mImageFile3 = new File(mReturnValues.get(0)); // Guardar en File la imagen recibida si el usuario selecciono una imagen
                            binding.circleImageViewPhotoAbdomen.setBorderColor(0);//eliminar border color del XML para que se vea mas agradable
                            binding.circleImageViewPhotoAbdomen.setBorderWidth(0);//eliminar ancho de border del XML para que se vea mas agradable
                            binding.circleImageViewPhotoAbdomen.setImageBitmap(BitmapFactory.decodeFile(mImageFile3.getAbsolutePath())); //Asignar la imagen al id del xml
                            Log.e("IMAGE PATH",""+ mReturnValues.get(0));
                            Log.e("IMAGE ABS PATH",""+ mImageFile3.getAbsolutePath());


                        }else
                        {
                            Toast.makeText(ButterflyChallengeActivity.this, "error al seleccionar la foto", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }

        }
        else
        {
            Toast.makeText(ButterflyChallengeActivity.this, "operacion Cancelado!", Toast.LENGTH_SHORT).show();
        }


    }

}