package com.dheia.finalyear;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by Dheia on 26/02/2019.
 */
public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {
    private List<WalletCoin> items;
    private Activity activity;
    Context context;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    ImageView qrdialogue;


    public GridViewAdapter(Activity activity, List<WalletCoin> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_grid, viewGroup, false);
        context = viewGroup.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewAdapter.ViewHolder viewHolder, int position) {
//        viewHolder.imageView.setImageResource(items.get(position).getDrawableId());
        final GridViewAdapter.ViewHolder h = viewHolder;

        if(!(items.get(position).getQRUrl().equals(""))){
            Picasso.get().load(items.get(position).getQRUrl()).into(viewHolder.imageView);

        }
        viewHolder.textView.setText(items.get(position).getQRname());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GridViewAdapter.this.activity,PublicPrivateKeyActivity.class);
                intent.putExtra("walletCoinName",items.get(position).getQRname());
                intent.putExtra("walletCoinSymbol",items.get(position).getQRSymbol());
                intent.putExtra("walletCoinUrl",items.get(position).getQRUrl());
                intent.putExtra("walletCoinPublicKey",items.get(position).getPublicKeyy());
                context.startActivity(intent);

            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                String pubkey = items.get(position).getPublicKeyy();
                if (pubkey.length() > 0) {
                    WindowManager manager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    qrgEncoder = new QRGEncoder(
                            pubkey, null,
                            QRGContents.Type.TEXT,
                            smallerDimension);
                    try {
                        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(GridViewAdapter.this.activity);

                        View mview = GridViewAdapter.this.activity.getLayoutInflater().inflate(R.layout.dialoguepaymentwalletqr
                                , null);
                        alertDialog.setTitle("Your "+items.get(position).getQRname()+" Public Address");
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
                    Toast.makeText(context.getApplicationContext(), "No Public Address Found", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView)view.findViewById(R.id.wallettext);
            imageView = (ImageView) view.findViewById(R.id.walletimage);
        }
    }
}