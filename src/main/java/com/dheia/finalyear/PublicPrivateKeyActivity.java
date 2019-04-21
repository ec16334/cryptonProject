package com.dheia.finalyear;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class PublicPrivateKeyActivity extends AppCompatActivity {

    private TextView publickey, name;

    QRDatabaseHelper mdatabasehelper;

    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    Button button;

    private ImageView imageView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicprivatekeys);

        publickey = findViewById(R.id.publictextviewDisplay);

        //name = findViewById(R.id.coinNamePublicPrivateKeyView);

        imageView1 = findViewById(R.id.QR_Image1);

        button = findViewById(R.id.publicprivatekeysDeleteButton);


        mdatabasehelper = new QRDatabaseHelper(this);

        String coinName = getIntent().getStringExtra("walletCoinName");
        String coinURL = getIntent().getStringExtra("walletCoinUrl");
        String coinPublicKey = getIntent().getStringExtra("walletCoinPublicKey");
        String coinSymbol = getIntent().getStringExtra("walletCoinSymbol");


        String public1 = "";

        //name.setText(coinName);
        setTitle(coinName);
        publickey.setText("Public Address: "+coinPublicKey);


        if (coinPublicKey.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    coinPublicKey, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                imageView1.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.v("QR CODE GENERATOR", e.toString());
            }
        } else {
            Toast.makeText(this, "No Public Key Found", Toast.LENGTH_SHORT).show();
        }




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor dataSQL = mdatabasehelper.getQRData();
        while (dataSQL.moveToNext()) {
            //check if coin is held already and avoid duplicates on portfolio listview
            if (dataSQL.getString(1).equals(coinName)) {
                mdatabasehelper.deleteQR(Integer.parseInt(dataSQL.getString(0)),dataSQL.getString(1));
                startActivity(new Intent(getApplicationContext(),PublicKeys.class));
                finish();
            }
        }
            }
        });



    }


}
