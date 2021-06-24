package edu.aha.agualimpiafinal.providers;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

import edu.aha.agualimpiafinal.utils.CompressorBitmapImage;

public class ImageProvider {

    StorageReference mStorage;
    FirebaseStorage mFirebaseStorage;

    public ImageProvider()
    {
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorage = mFirebaseStorage.getReference();
    }

    public UploadTask save (Context context, File file)
    {
        byte[] imageByte = CompressorBitmapImage.getImage(context, file.getPath(),500,500); //
        StorageReference storage = mStorage.child(new Date() + ".jpg");
        mStorage = storage;
        UploadTask task = storage.putBytes(imageByte);
        return task;
    }

}
