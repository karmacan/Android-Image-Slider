package edu.example.ca_image_slider;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private static void logd(String msg) { Log.d("\t\t>>", "\t" + msg); }
  private static void logd(int msg) { Log.d("\t\t>>", "\t" + Integer.toString(msg)); }

  private static final int WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;

  ////////////////////////////////////////////////////////////////////////////////
  // CREATE DOTS PANEL
  ////////////////////////////////////////////////////////////////////////////////

  private static final int DOTS_NUMBER = 3;
  private ArrayList<ImageView> mDotsImageViews; // for direct manipulations on dots

  private void createDotsPanel() {

    mDotsImageViews = new ArrayList<>();

    ImageView imageView;
    LinearLayout.LayoutParams lp;

    for (int i=0; i<DOTS_NUMBER; i++) {
      imageView = new ImageView(this);
      imageView.setImageResource(R.drawable.dot_nonactive);

      lp = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);

      lp.setMargins(5, 5, 5, 5);
      lp.height = 20;
      lp.width = 20;
      imageView.setLayoutParams(lp);

      mDotsImageViews.add(imageView);
    }

    LinearLayout dotsPanel = findViewById(R.id.dotsPanel);
    /*???*///dotsPanel.setVerticalGravity(Gravity.BOTTOM); // solved by android:layout_alignParentBottom="true"
    dotsPanel.setHorizontalGravity(Gravity.CENTER);
    /*TMP*///dotsPanel.setBackgroundColor(Color.parseColor("#ff0000"));

    for (ImageView iv : mDotsImageViews) {
      dotsPanel.addView(iv);
    }

    mDotsImageViews.get(0).setImageResource(R.drawable.dot_active);
  }

  // SET ACTIVE DOT METHOD
  private void setActiveDotImage(int i) {
    for (ImageView iv : mDotsImageViews) {
      iv.setImageResource(R.drawable.dot_nonactive);
    }
    mDotsImageViews.get(i).setImageResource(R.drawable.dot_active);
  }

  ////////////////////////////////////////////////////////////////////////////////
  // FILL MEMBERS WITH DATA
  ////////////////////////////////////////////////////////////////////////////////

  private ArrayList<Integer> mImageIds = new ArrayList<>();
  private ArrayList<String> mTitles = new ArrayList<>();

  private void fillSubstitutedContent() {
    mImageIds.add(R.drawable.vpi1);
    mImageIds.add(R.drawable.vpi2);
    mImageIds.add(R.drawable.vpi3);

    mTitles.add("Хоккей. Кубок Первого канала");
    mTitles.add("Футбол. Итоги чемпионата мира");
    mTitles.add("Теннис. Игры основного раунда");
  }

  ////////////////////////////////////////////////////////////////////////////////
  // CREATE PAGER
  ////////////////////////////////////////////////////////////////////////////////

  ViewPager mViewPager;

  private void buildPagerView() {
    mViewPager = findViewById(R.id.viewPager);
    /*!!!*/PagerBuilder pagerBuilder = new PagerBuilder(this, mImageIds, mTitles);
    mViewPager.setAdapter(pagerBuilder);
  }

  // SET LISTENER ON PAGER
  private void listenPageChange() {
    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override public void onPageSelected(int i) {
        setActiveDotImage(i);
      }

      @Override public void onPageScrolled(int i0, float v, int i1) { }
      @Override public void onPageScrollStateChanged(int i) { }
    });
  }

  /********************************************************************************/
  /*
  /********************************************************************************/

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    /*!!!*/
    createDotsPanel();
    fillSubstitutedContent();
    buildPagerView();
    listenPageChange();
  }

}

