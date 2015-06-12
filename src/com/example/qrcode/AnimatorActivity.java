package com.example.qrcode;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class AnimatorActivity extends Activity implements OnScrollChangedListener{

	LinearLayout bottom;
	Button hide;
	float currentY;
	float translateY;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animator);
		hide = (Button) findViewById(R.id.hide_bottom);
		bottom = (LinearLayout) findViewById(R.id.bottom_layout);
		currentY = bottom.getTranslationY();
		float y = bottom.getHeight();
		translateY = currentY + 500;
		Log.e("values[]", currentY + "/" + y + "/" + translateY);
		hide.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				float bottomY = bottom.getTranslationY();
				if (bottomY == currentY) {
					ObjectAnimator animator = ObjectAnimator.ofFloat(bottom, "translationY", currentY,translateY);
					animator.setDuration(500);
					animator.start();
				}else {
					ObjectAnimator animator = ObjectAnimator.ofFloat(bottom, "translationY", translateY,currentY);
					animator.setDuration(500);
					animator.start();
				}
				
				
			}
		});
		
		
	}
	public void getViewHeight(String method){
		float y = bottom.getHeight();
		Log.e("viewHeight", y + "/" + method);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getViewHeight("onStart");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getViewHeight("onResume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		getViewHeight("onPause");
	}
	@Override
	protected void onStop() {
		super.onStop();
		getViewHeight("onStop");
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		getViewHeight("onDestroy");
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		getViewHeight("onRestart");
	}

	@Override
	public void onScrollChanged() {
		
	}
}
