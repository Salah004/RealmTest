package com.salah.realmtest.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.salah.realmtest.R;

import java.util.List;



public abstract class QrCodeViewDialog extends Dialog {
    private Context context;
    private String id;
    private ImageView iv_qr_code;
    public QrCodeViewDialog(Context context , String id) {
        super(context , android.R.style.DeviceDefault_Light_ButtonBar );
        this.context = context;
        this.id = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.dialog_qr_code_view);

        iv_qr_code = findViewById(R.id.iv_qr_code);

        try{
            Bitmap qrCode = getQrCode(id);
            iv_qr_code.setImageBitmap(qrCode);
        }catch (Exception ex){

        }

    }


    private Bitmap getQrCode(String id){
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(id, BarcodeFormat.QR_CODE, 512, 512);
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
    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {
    }
}
