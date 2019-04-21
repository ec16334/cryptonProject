package com.dheia.finalyear;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Vibrator;
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

public class SendingAddress extends AppCompatActivity {

    TextView message, paymentdestinationTextView,transactionidTextView;
    Button qrGenerator, complete;
    String paymentdestination = "";
    String sendingCurrency = "";
    String recievingCurrency = "";
    String sendingAmount = "";
    String recievingAmount = "";
    String transactionidReference="";

    ImageView qrdialogue;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_address);
        setTitle("Send Payment");

        message = findViewById(R.id.sendpaymenttext);
        paymentdestinationTextView = findViewById(R.id.paymentaddressID);
        qrGenerator = findViewById(R.id.showQRPaymentTransaction);
        transactionidTextView = findViewById(R.id.transactionreferenceTextView);
        complete = findViewById(R.id.completeButtonSending);

        paymentdestination = getIntent().getStringExtra("payinaddress");
        sendingCurrency = getIntent().getStringExtra("sendingCurrency");
        recievingCurrency = getIntent().getStringExtra("recievingCurrency");
        sendingAmount = getIntent().getStringExtra("amountSending");
        recievingAmount = getIntent().getStringExtra("amountRecieving");
        transactionidReference = getIntent().getStringExtra("transactionReference");

        paymentdestinationTextView.setText(paymentdestination);

        message.setText("Send "+sendingAmount +" "+ sendingCurrency+ " to the address below to recieve "+recievingAmount+" "+recievingCurrency);

        transactionidTextView.setText(transactionidReference);

        paymentdestinationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getstring = paymentdestinationTextView.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("paymentdestination", getstring);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(SendingAddress.this, "Address Copied!", Toast.LENGTH_SHORT).show();
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                // Vibrate for 400 milliseconds
                v.vibrate(1000);
            }
        });


        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SendingAddress.this,ChangellyExchange.class));
                finish();
            }
        });

        qrGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paymentdestination.length() > 0) {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    qrgEncoder = new QRGEncoder(
                            paymentdestination, null,
                            QRGContents.Type.TEXT,
                            smallerDimension);
                    try {
                        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(SendingAddress.this);
                        View mview = getLayoutInflater().inflate(R.layout.dialoguepaymentwalletqr
                                , null);
                        alertDialog.setTitle("Scan QR CODE");
                        qrdialogue = mview.findViewById(R.id.qrcodeSendingAddress);
                        bitmap = qrgEncoder.encodeAsBitmap();
                        qrdialogue.setImageBitmap(bitmap);

                        alertDialog.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.setView(mview);
                        alertDialog.create();
                        alertDialog.show();
                    } catch (WriterException e) {
                        Log.v("QR CODE GENERATOR", e.toString());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Destination AddressFound", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


}
