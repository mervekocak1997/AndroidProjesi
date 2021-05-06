package com.example.foodhuntermonster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameScreen extends AppCompatActivity {


    private ConstraintLayout cl;
    private TextView textViewSkor;
    private TextView textViewOyunaBasla;
    private  ImageView anakarakter;
    private ImageView banana;
    private  ImageView strawberry;
    private  ImageView fire;



    //Pozisyonlar
    private int anakarakterX;
    private int anakarakterY;
    private int bananaX;
    private int bananaY;
    private int strawberryX;
    private int strawberryY;
    private int fireX;
    private int fireY;

    //boyutlar
    private int ekranGenisligi;
    private int ekranYuksekligi;
    private int anakarakterGenisligi;
    private int anakarakterYuksekligi;

    //Hızlar
    private int anakarakterHiz;
    private int bananaHiz;
    private int fireHiz;
    private int strawberryHiz;

    //Kontroller
    private boolean dokunmaKontrol = false;
    private boolean baslangicKontrol = false;

    private int skor = 0 ;

    private Timer timer = new Timer();
    private Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        cl = findViewById(R.id.cl);
        textViewSkor = findViewById(R.id.textViewSkor);
        textViewOyunaBasla = findViewById(R.id.textViewOyunaBasla);
        banana = findViewById(R.id.banana);
        strawberry = findViewById(R.id.strawberry);
        fire = findViewById(R.id.fire);
        anakarakter = findViewById(R.id.anakarakter);


        //Cisimleri ekran dışına çıkarma
        fire.setX(-80);
        fire.setY(-80);
        banana.setY(-80);
        banana.setX(-80);
        strawberry.setX(-80);
        strawberry.setY(-80);

       cl.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               if (baslangicKontrol){
                   if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                       Log.e("MotionEvent", "Ekrana Dokunuldu");
                       dokunmaKontrol = true;
                   }
                   if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                       Log.e("MotionEvent", "Ekranı Bıraktı");
                       dokunmaKontrol = false;
                   }

               }else {
                   baslangicKontrol = true;

                   textViewOyunaBasla.setVisibility(View.INVISIBLE);


                   anakarakterX = (int) anakarakter.getX();
                   anakarakterY = (int) anakarakter.getY();

                   anakarakterGenisligi = anakarakter.getWidth();
                   anakarakterYuksekligi = anakarakter.getHeight();
                   ekranGenisligi   = cl.getWidth();
                   ekranYuksekligi = cl.getHeight();


                   timer.schedule(new TimerTask() {
                       @Override
                       public void run() {
                           handler.post(new Runnable() {
                               @Override
                               public void run() {
                                   anakarakterHareketEttirme();
                                   cisimleriHareketEttir();
                                   carpismaKontrol();

                               }

                           });

                       }
                   }, 0, 20);
               }



               return true;
           }
       });
    }

    public void anakarakterHareketEttirme(){

        anakarakterHiz = Math.round(ekranYuksekligi/60);//1280 / 60  = 20

        if(dokunmaKontrol){
            anakarakterY-=anakarakterHiz;
        }else{
            anakarakterY+=anakarakterHiz;
        }

        if(anakarakterY <= 0){
            anakarakterY = 0 ;
        }

        if(anakarakterY >= ekranYuksekligi - anakarakterYuksekligi){
            anakarakterY = ekranYuksekligi - anakarakterYuksekligi;
        }

        anakarakter.setY(anakarakterY);
    }


    public void cisimleriHareketEttir(){

        bananaHiz = Math.round(ekranGenisligi/60);//760 / 60  = 13
        fireHiz = Math.round(ekranGenisligi/60);//760 / 60  = 13
        strawberryHiz = Math.round(ekranGenisligi/30);//760 / 30  = 26

        fireX-=fireHiz;

        if (fireX < 0 ){
            fireX = ekranGenisligi + 20 ;
            fireY = (int) Math.floor(Math.random() * ekranYuksekligi);
        }

        fire.setX(fireX);
        fire.setY(fireY);


        bananaX-=bananaHiz;

        if (bananaX < 0 ){
            bananaX = ekranGenisligi + 20 ;
            bananaY = (int) Math.floor(Math.random() * ekranYuksekligi);
        }

        banana.setX(bananaX);
        banana.setY(bananaY);

        strawberryX-=strawberryHiz;

        if (strawberryX < 0 ){
            strawberryX = ekranGenisligi + 20 ;
            strawberryY = (int) Math.floor(Math.random() * ekranYuksekligi);
        }

        strawberry.setX(strawberryX);
        strawberry.setY(strawberryY);

    }

    public void carpismaKontrol(){

        int bananaMerkezX = bananaX + banana.getWidth()/2;
        int bananaMerkezY = bananaY + banana.getHeight()/2;

        if (0 <= bananaMerkezX && bananaMerkezX <= anakarakterGenisligi
                && anakarakterY <= bananaMerkezY && bananaMerkezY <= anakarakterY+anakarakterYuksekligi){

            skor+=20;
            bananaX = -10;

        }

        int strawberryMerkezX = strawberryX + strawberry.getWidth()/2;
        int strawberryMerkezY = strawberryY + strawberry.getHeight()/2;

        if (0 <= strawberryMerkezX && strawberryMerkezX <= anakarakterGenisligi
                && anakarakterY <= strawberryMerkezY && strawberryMerkezY <= anakarakterY+anakarakterYuksekligi){

            skor+=50;
            strawberryX = -10;

        }

        int fireMerkezX = fireX + fire.getWidth()/2;
        int fireMerkezY = fireY + fire.getHeight()/2;

        if (0 <= fireMerkezX && fireMerkezX <= anakarakterGenisligi
                && anakarakterY <= fireMerkezY && fireMerkezY <= anakarakterY+anakarakterYuksekligi){

            fireX = -10;

            //Timer durdur.
            timer.cancel();
            timer=null;

            Intent intent = new Intent(GameScreen.this,ResultsScreen.class);
            intent.putExtra("skor", skor);
            startActivity(intent);


        }

        textViewSkor.setText(String.valueOf(skor));

    }
}