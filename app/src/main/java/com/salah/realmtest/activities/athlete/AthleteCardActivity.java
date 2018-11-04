package com.salah.realmtest.activities.athlete;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.salah.realmtest.Informations;
import com.salah.realmtest.R;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.services.RealmService;

import java.io.ByteArrayOutputStream;
import java.io.File;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;


public class AthleteCardActivity extends AppCompatActivity {

    public static Athlete athlete;
    private ImageView iv_qr_code , iv_athlete;
    TextView tv_first_name , tv_last_name ;
    private Bitmap ic_qr_code , ic_card;
    private FrameLayout fl_card;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_athlete);
        realmService = new RealmService(Realm.getDefaultInstance());

        iv_qr_code = findViewById(R.id.iv_qr_code);
        //iv_athlete = findViewById(R.id.iv_athlete);
        tv_first_name = findViewById(R.id.tv_first_name);
        tv_last_name = findViewById(R.id.tv_last_name);
        fl_card = findViewById(R.id.fl_card);

        tv_last_name.setText("Prénom : "+athlete.getLastName());
        tv_first_name.setText("Nom : "+athlete.getFirstName());

        try{
            ic_qr_code = getQrCode(athlete.getId());
            iv_qr_code.setImageBitmap(ic_qr_code);
        }catch (Exception ex){

        }

        try {
            File imgFile = new File(athlete.getPick());
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                iv_athlete.setImageBitmap(myBitmap);
            }
        }catch (Exception e){

        }

        Informations.setTextFont(this,tv_first_name,Informations.MYRIAD_BOLD);
        Informations.setTextFont(this,tv_last_name,Informations.MYRIAD_BOLD);

    }


    private Bitmap getQrCode(String id){
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(id, BarcodeFormat.QR_CODE,512,512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;
            //((ImageView) findViewById(R.id.iv_athlete)).setImageBitmap(bmp);
        } catch (WriterException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public void onShare(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_STREAM, getImageUri(AthleteCardActivity.this, getBitmapFromView(fl_card)));
        try {
            startActivity(Intent.createChooser(i, athlete.getFirstName()));
        } catch (android.content.ActivityNotFoundException ex) {

            ex.printStackTrace();
        }

    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),      view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    public void onPrint(View view) {
    }

    public void onSave(View view) {
        try {
            Uri uri = getImageUri(AthleteCardActivity.this, getBitmapFromView(fl_card));
            Toasty.success(AthleteCardActivity.this,"Card created successfully", Toast.LENGTH_LONG);
        }catch (Exception e){
            Toasty.error(AthleteCardActivity.this,"Failed to create card", Toast.LENGTH_LONG);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
