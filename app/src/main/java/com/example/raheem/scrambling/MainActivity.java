package com.example.raheem.scrambling;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MyTAG";
    private final static int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1234;

    Bitmap bitmap;
    Button btnImage;
    ImageView imgView;
    private static final int PICK_IMAGE = 100;
    Uri imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        imgView = findViewById(R.id.imageView);
        btnImage = findViewById(R.id.bt);


        //التشفير
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openGallary();
            }
        });
    }

    public void btUnScramblingClick(View v) {

        openGallary();

    }




    public void openGallary() {
        Intent Gallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Gallary, PICK_IMAGE);
    }
    public void BtnClick(View v) {
//        saveTempBitmap(bitmap);
        try {
            saveImage(bitmap);

            Toast.makeText(MainActivity.this,
                    "تم حفظ الصوره بنجاح", Toast.LENGTH_LONG).show();
        }catch (Exception ex){


            Toast.makeText(MainActivity.this,
                    "خطأ في حفظ الصوره", Toast.LENGTH_LONG).show();
        }

    }

//    public void saveTempBitmap(Bitmap bitmap) {
//        if (isExternalStorageWritable()) {
//            saveImage(bitmap);
//        } else {
//            //prompt the user or do something
//        }
//    }

    private void saveImage(Bitmap bitmap) {
        String fileName = "Image_" + (System.currentTimeMillis() / 1000) + ".png";
        checkWritePermissionGranted();
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, fileName, "scrambled image");
        Log.d(TAG, "saveImage: " + imageURI);
        Log.d(TAG, "saveImage: " + getRealPathFromUri(imageURI));
        deleteImage(getRealPathFromUri(imageURI));
    }
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void deleteImage(String imagePath) {
        File file = new File(imagePath);
        if (file.exists()) {
            boolean is = file.delete();
            Log.d(TAG, is? "image has been deleted" : "image not deleted");
            if (is)
                callBroadCast();
        }
    }

    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) { }
            });
        } else {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }

    public void checkWritePermissionGranted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    /* Checks if external storage is available for read and write */
//    public boolean isExternalStorageWritable() {
//        String state = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            return true;
//        }
//        return false;
//    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageURI = data.getData();
            Log.d(TAG, "onActivityResult: " + imageURI);
            try {
                InputStream imageSt = getContentResolver().openInputStream(imageURI);
                bitmap = BitmapFactory.decodeStream(imageSt);
                bitmap = scrambling(bitmap);
                imgView.setImageBitmap(bitmap);
            } catch (FileNotFoundException ignored) {
            }
        }
    }

    private Bitmap scrambling(Bitmap bitmap) {

        int z = bitmap.getWidth() * bitmap.getHeight();
        int[] bitmapArray = new int[z];
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        bitmap.getPixels(bitmapArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        int w = bitmap.getHeight() / 100, w2 = bitmap.getWidth() / 100;
        if (w < 7 || w2 <10 ) {
            w = 7;
            w2 = 7;
        }
        int hold;
        boolean s = true;
        int h1 = bitmapArray.length / 2;
        boolean b = true;
        for (int r = 0; r < bitmap.getWidth() / 2; r++) {
            if (r % w == 0)
                if (b)
                    b = false;
                else b = true;

            if (b)
                for (int i = r; i < bitmapArray.length; i += bitmap.getWidth()) {
                    hold = bitmapArray[i];
                    bitmapArray[i] = bitmapArray[i + (bitmap.getWidth() / 2)];
                    bitmapArray[i + (bitmap.getWidth() / 2)] = hold;
                }
        }
        int q = 0;
        for (int i = 0; i < bitmapArray.length / 2; i += bitmap.getWidth()) {
            if (q % w2 == 0)
                if (s)
                    s = false;
                else s = true;

            if (s)
                for (int j = i; j < i + bitmap.getWidth(); j++) {
                    hold = bitmapArray[j];
                    bitmapArray[j] = bitmapArray[h1 + j];
                    bitmapArray[h1 + j] = hold;
                }
            q++;
        }
        bitmap = Bitmap.createBitmap(bitmapArray, 0, bitmap.getWidth(), bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        return bitmap;

    }

}