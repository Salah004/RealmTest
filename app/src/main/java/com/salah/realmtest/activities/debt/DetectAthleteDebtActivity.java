package com.salah.realmtest.activities.debt;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

public class DetectAthleteDebtActivity extends AppCompatActivity implements  OnQRCodeReadListener {

    private boolean TORCH_ON = false;
    static public boolean ALLOW_DETECT = true ;
    private QRCodeReaderView qrCodeReaderView;
    private ImageView btn_torch , btn_menu ;

    private RealmService realmService;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_athlete);
        realmService = new RealmService(Realm.getDefaultInstance());
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionManager permissionManager = PermissionManager.getInstance(DetectAthleteDebtActivity.this);
            permissionManager.checkPermissions(singleton(Manifest.permission.CAMERA), new PermissionManager.PermissionRequestListener() {
                @Override
                public void onPermissionGranted() {
                    Toasty.success(DetectAthleteDebtActivity.this, "Permissions Granted", Toast.LENGTH_SHORT).show();
                    initQRCodeReaderView();
                }

                @Override
                public void onPermissionDenied() {
                    Toasty.error(DetectAthleteDebtActivity.this, "Permissions Denied", Toast.LENGTH_SHORT).show();
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
            Athlete athlete = realmService.getAllAthleteById(text);
           // Toast.makeText(this,text,Toast.LENGTH_LONG).show();
            if (athlete!=null){
                DebtsActivity.athlete = athlete;
                Intent intent = new Intent(DetectAthleteDebtActivity.this,DebtsActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this,"QrCode not valid",Toast.LENGTH_LONG).show();
            }
//
        }
    }


    private void initQRCodeReaderView() {
        qrCodeReaderView = findViewById(R.id.qrdecoderview);
        btn_torch = findViewById(R.id.btn_torch);
        btn_menu = findViewById(R.id.btn_menu);
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

    public void showMenu(View view) {
        PopupMenu popup = new PopupMenu(this, btn_menu );
        popup.getMenuInflater().inflate(R.menu.qr_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                //Toast.makeText(ReadQRCodeActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popup.show();
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