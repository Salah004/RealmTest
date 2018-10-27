package com.salah.realmtest.activities.session;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;
import com.salah.realmtest.R;

import java.io.IOException;

public class DetectAthleteSessionActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, OnQRCodeReadListener {

    private static final int MY_PERMISSION_REQUEST_CAMERA = 0;
    private boolean TORCH_ON = false;
    static public boolean ALLOW_DETECT = true ;
    private QRCodeReaderView qrCodeReaderView;
    private ImageView btn_torch  ;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_athlete);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            initQRCodeReaderView();
        } else {
            requestCameraPermission();
        }
    }

    @Override protected void onResume() {
        super.onResume();
        ALLOW_DETECT = true;
        if (qrCodeReaderView != null) {
            qrCodeReaderView.startCamera();
        }
    }

    @Override protected void onPause() {
        super.onPause();
        if (qrCodeReaderView != null) {
            qrCodeReaderView.stopCamera();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSION_REQUEST_CAMERA) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Camera permission was granted.", Toast.LENGTH_SHORT).show();
            initQRCodeReaderView();
        } else {
            Toast.makeText(this, "Camera permission request was denied.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override public void onQRCodeRead(String text, PointF[] points) {
        if( ALLOW_DETECT ){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
            ALLOW_DETECT = false;
            try {
                AssetFileDescriptor afd = getAssets().openFd("son.mp3");
                MediaPlayer mp = new MediaPlayer();
                mp.setDataSource( afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength() );
                mp.prepare();
                mp.start();
            }catch ( IOException ex ){}
            if ( TORCH_ON ){
                qrCodeReaderView.setTorchEnabled(false);
                btn_torch.setImageResource(R.drawable.ic_flash_on_24dp);
            }
            TORCH_ON = !TORCH_ON;
            Toast.makeText(this,text,Toast.LENGTH_LONG).show();
//            InfoActivity.info = text;
//            Intent intent = new Intent(DecoderActivity.this,InfoActivity.class);
//            startActivity(intent);
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
        } else {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.CAMERA
            }, MY_PERMISSION_REQUEST_CAMERA);
        }
    }

    private void initQRCodeReaderView() {
        qrCodeReaderView = findViewById(R.id.qrdecoderview);
        btn_torch = findViewById(R.id.btn_torch);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setQRDecodingEnabled(true);
        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setBackCamera();
        qrCodeReaderView.startCamera();
    }

    public void torchStatChanged(View view){
        if ( TORCH_ON ){
            qrCodeReaderView.setTorchEnabled(false);
            btn_torch.setImageResource(R.drawable.ic_flash_on_24dp);
        }else {
            qrCodeReaderView.setTorchEnabled(true);
            btn_torch.setImageResource(R.drawable.ic_flash_off_24dp);
        }
        TORCH_ON = !TORCH_ON;
    }


    public void onBack(View view) {
        onBackPressed();
    }
}
