package com.example.uas2baymax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView mImgView;
    Bitmap mBitmap;
    Canvas mCanvas;
    private int mColorBackground;
    Paint mCirclePaint = new Paint();
    Paint mHeadPaint = new Paint();
    int width, height;
    float centerX, centerY, radiusX, radiusY;
    ObjectAnimator mAnimatorFadeIn1, mAnimatorFadeOut;
    private AnimatorSet animatorSet = new AnimatorSet();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgView = findViewById(R.id.my_img_view);
        width = mImgView.getWidth();
        height= mImgView.getHeight();


        mCirclePaint.setColor(getResources().getColor(R.color.black));
        mHeadPaint.setColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int vWidth = mImgView.getWidth();
        int vHeight = mImgView.getHeight();

        mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
        mImgView.setImageBitmap(mBitmap);
        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.yellow, null);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(mColorBackground);


        drawHead(vWidth,vHeight);
        drawRightEye(vWidth,vHeight);
        drawLeftEye(vWidth,vHeight);
        drawEyeConnector(vWidth,vHeight);
        AnimatorSet flipAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip_animation);
        flipAnimator.setTarget(mImgView);

        Animation fadeAnimation = new AlphaAnimation(1.0f, 0.0f);
        fadeAnimation.setDuration(3000);
        mAnimatorFadeIn1 = ObjectAnimator.ofFloat(mImgView,"alpha",0,1);
        mAnimatorFadeIn1.setDuration(2000);
        mAnimatorFadeOut = ObjectAnimator.ofFloat(mImgView,"alpha",1,0);
        mAnimatorFadeOut.setDuration(2000);
        mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                animatorSet.playSequentially(mAnimatorFadeIn1,flipAnimator,mAnimatorFadeOut);
                animatorSet.start();


            }
        });






    }
    public void drawHead(int width, int height){
        radiusX = width/2 - 200;
        radiusY = height/2 - 800;
        RectF head = new RectF(width/2 - radiusX, height/2 - radiusY,width / 2 + radiusX , height / 2 + radiusY);

        mCanvas.drawOval(head,mHeadPaint);
    }

    public void drawRightEye(int width, int height){
        mCanvas.drawCircle(width/2-170, height / 2, 60, mCirclePaint);
    }

    public void drawLeftEye(int width, int height){
        mCanvas.drawCircle(width/2 + 170, height / 2, 60, mCirclePaint);

    }

    public void drawEyeConnector(int width, int height){
        mCanvas.drawRect(width / 2 - 120, height / 2 + 12, width / 2 + 120, height / 2 -12, mCirclePaint);

    }


}