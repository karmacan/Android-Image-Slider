package edu.example.ca_image_slider;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PagerBuilder extends PagerAdapter {

  private static void logd(String msg) { Log.d("\t\t>>", "\t" + msg); }

  private static final int WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;
  private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

  /********************************************************************************/
  /* CONSTRUCTOR FOR PASSED SUBSTITUTED CONTENT
  /********************************************************************************/

  private Context mContext;
  private ArrayList<Integer> mImageIds = new ArrayList<>();
  private ArrayList<String> mTitles = new ArrayList<>();

  public PagerBuilder(Context mContext, ArrayList<Integer> mImageIds, ArrayList<String> mTitles) {
    this.mContext = mContext;
    this.mImageIds = mImageIds;
    this.mTitles = mTitles;
  }

  ////////////////////////////////////////////////////////////////////////////////
  // CREATE IMAGE AND TEXT LAYOUTS
  ////////////////////////////////////////////////////////////////////////////////

  private static final String GRADIENT_COLOR_START = "#FF263238"; // #FFXXXXXX - fully opaque
  private static final String GRADIENT_COLOR_FINISH = "#00CFD8DC"; // #00XXXXXX - fully transparent

  private FrameLayout createImageViewForBackground(int i) {

    FrameLayout frameLayout = new FrameLayout(mContext);

    ImageView imageViewPhoto = new ImageView(mContext); // obj
    imageViewPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
    imageViewPhoto.setImageResource(mImageIds.get(i));

    GradientDrawable gd = new GradientDrawable();
    gd.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
    int startColor = Color.parseColor(GRADIENT_COLOR_START);
    int endColor = Color.parseColor(GRADIENT_COLOR_FINISH);
    gd.setColors(new int[]{startColor, endColor});

    ImageView imageViewGradient = new ImageView(mContext);
    imageViewGradient.setImageDrawable(gd);

    frameLayout.addView(imageViewPhoto);
    frameLayout.addView(imageViewGradient);

    return frameLayout;
  }

  private static final int TV_TEXT_SIZE = 30;
  private static final String TV_TEXT_COLOR = "#ffffff";
  private static final String LT_COLOR = "#1DE9B6"; // top label color
  private static final String LB_COLOR = "#E0E0E0"; // bottom label color

  private LinearLayout createStackedLayoutForTexts(int i) {
    LinearLayout linearLayout = new LinearLayout(mContext);
    linearLayout.setOrientation(LinearLayout.VERTICAL);

    TextView tl = new TextView(mContext); // label top
    tl.setText("СПОРТ");
    tl.setTextColor(Color.parseColor(LT_COLOR));
    tl.setTextSize(22);
    tl.setTypeface(null, Typeface.BOLD);

    TextView textView = new TextView(mContext);
    textView.setText(mTitles.get(i));
    textView.setTextSize(TV_TEXT_SIZE);
    textView.setTextColor(Color.parseColor(TV_TEXT_COLOR));

    TextView bl = new TextView(mContext);
    bl.setText("15 минут назад");
    bl.setTextColor(Color.parseColor(LB_COLOR));
    bl.setTextSize(18);

    linearLayout.addView(tl);
    linearLayout.addView(textView);
    linearLayout.addView(bl);

    return linearLayout;
  }

  private static final int RL_MARGIN_LEFT = 45;
  private static final int RL_MARGIN_RIGHT = 265;
  private static final int RL_MARGIN_BOTTOM = 50;

  private RelativeLayout createBottomedLayoutForTexts(int i) {

    RelativeLayout relativeLayout = new RelativeLayout(mContext);

    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
    lp.setMargins(RL_MARGIN_LEFT, 0, RL_MARGIN_RIGHT, RL_MARGIN_BOTTOM);
    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    relativeLayout.setLayoutParams(lp);

    LinearLayout linearLayout = createStackedLayoutForTexts(i);
    relativeLayout.addView(linearLayout);

    return relativeLayout;
  }

  /********************************************************************************/
  /* IMPLEMENT SUPER CLASS
  /********************************************************************************/
  @Override
  public int getCount() {
    return mImageIds.size();
  }

  /*!!!*/
  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup pageGroup, int i) {

    RelativeLayout pageKeeper = new RelativeLayout(mContext);

    FrameLayout frameLayout = createImageViewForBackground(i);
    RelativeLayout linearLayout = createBottomedLayoutForTexts(i);

    pageKeeper.addView(frameLayout);
    pageKeeper.addView(linearLayout);

    pageGroup.addView(pageKeeper, i);
    return pageKeeper;
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object pageKeeper) {
    return view == pageKeeper;
  }

  @Override
  public void destroyItem(@NonNull ViewGroup pageGroup, int i, @NonNull Object pageKeeper) {
    pageGroup.removeView((RelativeLayout) pageKeeper);
  }

}
