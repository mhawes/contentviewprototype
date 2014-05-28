package com.mystack.contentviewprototype;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity{

	private GestureDetectorCompat gestureDetector;
	private ContentModel contentModel;

	private boolean firstTime = true;
	
	ImageSwitcher imageSwitch;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		// gesture detection
		gestureDetector = new GestureDetectorCompat(this, new SwipeGestureDetector());
		
		//model
		contentModel = ContentModel.getInstance();
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		
		// when we first start up initialise the content
		if(firstTime)
		{
			// get the first piece of content and setup
			setContentPaneToNext();
			setDebugText();
			
			firstTime = false;
		}
	}

	@Override 
    public boolean onTouchEvent(MotionEvent event){ 
        this.gestureDetector.onTouchEvent(event);
        
        setDebugText();
        
        return super.onTouchEvent(event);
    }

	private void setDebugText() {
		// TODO prototype code to show debug info	
 		TextView text = (TextView) findViewById(R.id.debug);
 		text.setText(contentModel.toString());
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void onLeftSwipe() {
		setContentPaneToNext();
		
		TextView text = (TextView) findViewById(R.id.textView1);
		text.setText("NEXT");
	}

	private void onRightSwipe() {
		setContentPaneToPrev();
		
		TextView text = (TextView) findViewById(R.id.textView1);
		text.setText("PREV");
	}

	public void onUpSwipe() {
		contentModel.likeCurrentContent();
		
		TextView text = (TextView) findViewById(R.id.textView1);
		text.setText("LIKE");
	}

	public void onDownSwipe() {
		contentModel.dislikeCurrentContent();
		
		TextView text = (TextView) findViewById(R.id.textView1);
		text.setText("DISLIKE");
	}
	
	private void setContentPaneToPrev() {
		ImageView imageView = (ImageView) findViewById(R.id.contentPane);
		imageView.setImageResource(contentModel.getPreviousContent());
	}
	
	private void setContentPaneToNext() {
		ImageView imageView = (ImageView) findViewById(R.id.contentPane);
		
		try {
			imageView.setImageResource(contentModel.getNextContent());
		} catch (NoModifierException e) {
			// TODO Show message saying no content modifiers not set
		}
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	// Private class for gestures
	  private class SwipeGestureDetector
	          extends SimpleOnGestureListener {
	    // Swipe properties, you can change it to make the swipe
	    // longer or shorter and speed
	    private static final int SWIPE_MIN_DISTANCE = 150;
	    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

	    @Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			TextView text = (TextView) findViewById(R.id.textView1);
			try {
				float diffX = e1.getX() - e2.getX();
				float diffY = e1.getY() - e2.getY();

				// Left swipe
				if (diffX > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					MainActivity.this.onLeftSwipe();

				// Right swipe
				} else if (-diffX > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					MainActivity.this.onRightSwipe();
				}
				// Down swipe
				else if (diffY > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
					MainActivity.this.onUpSwipe();
				}
				// Up swipe
				else if (-diffY > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
					MainActivity.this.onDownSwipe();
				}
			} catch (Exception e) {
				text.setText("exception");
			}
			return false;
		}
		
	    @Override
        public boolean onDown(MotionEvent e) {
	    	// dummy stub to allow flings to be detected correctly
	    	return true;
        }
	}
}
