package com.salah.realmtest.activities.athlete;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;
import com.intentfilter.androidpermissions.PermissionManager;
import com.salah.realmtest.R;
import com.salah.realmtest.models.Athlete;
import com.salah.realmtest.services.RealmService;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;

import static java.util.Collections.singleton;

public class DetectAthleteActivity extends AppCompatActivity implements  OnQRCodeReadListener {

    private boolean TORCH_ON = false;
    static public boolean ALLOW_DETECT = true ;
    private QRCodeReaderView qrCodeReaderView;
    private ImageView btn_torch  ;

    private RealmService realmService;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_athlete);
        realmService = new RealmService(Realm.getDefaultInstance());
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionManager permissionManager = PermissionManager.getInstance(DetectAthleteActivity.this);
            permissionManager.checkPermissions(singleton(Manifest.permission.CAMERA), new PermissionManager.PermissionRequestListener() {
                @Override
                public void onPermissionGranted() {
                    Toasty.success(DetectAthleteActivity.this, "Permissions Granted", Toast.LENGTH_SHORT).show();
                    initQRCodeReaderView();
                }

                @Override
                public void onPermissionDenied() {
                    Toasty.error(DetectAthleteActivity.this, "Permissions Denied", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            initQRCodeReaderView();
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
            Athlete athlete = realmService.getAthleteById(text);
            AthleteActivity.athlete = athlete;
            Intent intent = new Intent(DetectAthleteActivity.this,AthleteActivity.class);
            startActivity(intent);
            // Toast.makeText(this,text,Toast.LENGTH_LONG).show();
            if (athlete!=null){

            }else {
                Toasty.error(this,"QrCode not valid",Toast.LENGTH_LONG).show();
            }
//
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmService.closeRealm();
    }

    public void onBack(View view) {
        onBackPressed();
    }
}
