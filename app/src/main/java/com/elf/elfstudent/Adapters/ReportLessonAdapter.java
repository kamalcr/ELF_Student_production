package com.elf.elfstudent.Adapters;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.animation.Animation;
import com.db.chart.animation.easing.BounceEase;
import com.db.chart.animation.easing.CubicEase;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.view.LineChartView;
import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.CustomUI.MyMarkerView;
import com.elf.elfstudent.CustomUI.UbuntuRegular;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.ScreenUtil;
import com.elf.elfstudent.model.Lesson;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
 *
 */
public class  ReportLessonAdapter  extends RecyclerView.Adapter<ReportLessonAdapter.LessonView>{


    private Context mContext;
    private List<Lesson> mList;
    LayoutInflater inflater = null;
    private LessonClickCallbacks mCallback;
    private int Animated_item_count = 0;
    int last_pos = -1;

    public ReportLessonAdapter(Context context,List<Lesson> mLessonList,LessonClickCallbacks mCallback) {
        this.mList  = mLessonList;
        this.mContext = context;
        this.mCallback = mCallback;
        Animated_item_count = mList.size();    }

    @Override
    public LessonView onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater== null){
            inflater = LayoutInflater.from(mContext);
        }
        return new LessonView(inflater.inflate(R.layout.report_lesson_item_row,parent,false),mCallback);
    }

    @Override
    public void onBindViewHolder(final LessonView holder, int position) {

//        Log.d(TAG, "onBindViewHolder: ");
        runEnterAnimations(holder,position);
        Log.d("Adapter", "onBindViewHolder: ");
//        setUpChart(holder.mChart,position);
        setUPWilliamChart(holder.mChart);
        holder.mLessonName.setText(mList.get(position).getmLessonName());


        ViewCompat.setTransitionName(holder.mLessonName,String.valueOf(position)+"_lesson");

        ViewCompat.setTransitionName(holder.itemView,String.valueOf(position)+"_item");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.ShowLessonReportFor(holder.getAdapterPosition(),holder);
            }
        });
    }

    private void setUPWilliamChart(final LineChartView mChart) {
        Log.d("Adapter", "setUPWilliamChart: started");
        final String[] mLabels = {"Test1", "Test2", "Test3", "Test4", "Test5"};

        final float[][] mValues = {{3.5f, 4.7f, 4.3f, 8f, 6.5f},
                {4.5f, 2.5f, 2.5f, 9f, 4.5f}};
         final Tooltip mTip;
        mTip = new Tooltip(mContext, R.layout.linechart_three_tooltip, R.id.value);


        Log.d("Adapter", "setUPWilliamChart: mto creation");
        mTip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        mTip.setDimensions((int) Tools.fromDpToPx(58), (int) Tools.fromDpToPx(25));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            mTip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

            mTip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

            mTip.setPivotX(Tools.fromDpToPx(65) / 2);
            mTip.setPivotY(Tools.fromDpToPx(25));
        }

        mChart.setTooltips(mTip);
        Log.d("Adapter", "setUPWilliamChart:setted tip ");

        // Data
        LineSet dataset = new LineSet(mLabels, mValues[0]);
        Log.d("Adapter", "setUPWilliamChart: dataset");
        dataset.setColor(Color.parseColor("#758cbb"))
                .setFill(ContextCompat.getColor(mContext,R.color.black_dribble))
                .setDotsColor(ContextCompat.getColor(mContext,R.color.colorAccent))
                .setThickness(4)
                .setDashed(new float[] {10f, 10f})
                .beginAt(0);
        mChart.addData(dataset);



        // Chart
        mChart.setBorderSpacing(Tools.fromDpToPx(15))
                .setAxisBorderValues(0, 20)
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setLabelsColor(ContextCompat.getColor(mContext,R.color.colorAccent))
                .setXAxis(false)
                .setYAxis(false);




    Animation anim = new Animation().setEasing(new BounceEase()).setEndAction(new Runnable() {
        @Override
        public void run() {
            mTip.prepare(mChart.getEntriesArea(0).get(4), mValues[0][4]);
            mChart.showTooltip(mTip, true);
        }


    });

    mChart.show(anim);

    }

    private void setUpChart(LineChart mChart, int position) {

        mChart.setDrawGridBackground(false);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);



        MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart


        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.removeAllLimitLines();
        xAxis.setAxisMaximum(5f);
        xAxis.disableAxisLineDashedLine();
        xAxis.disableGridDashedLine();
        xAxis.setAxisMinimum(0f);
        xAxis.setLabelCount(6,true);

        xAxis.setValueFormatter(new DefaultAxisValueFormatter(1));

//        xAxis.enableGridDashedLine(10f, 10f, 0f);
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
//        xAxis.addLimitLine(llXAxis); // add x-axis limit line





        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(ll1);
//        leftAxis.addLimitLine(ll2);
        leftAxis.disableAxisLineDashedLine();
        leftAxis.disableGridDashedLine();
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0);
        leftAxis.setLabelCount(3,true);
//        leftAxis.setYOffset(20f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
//        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        ArrayList<Entry> values = new ArrayList<Entry>();
        values.add(new Entry(0,0));
        //Till no fo tests
//        // TODO: 1/12/16 noof Tests
        values.add(new Entry(0,0));

        for (int i = 1; i < 6; i++) {

            float val = (float) (Math.random() * 100) +3;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "Progress");

            // set the line to be drawn like this "- - - - - -"
//            set1.enableDashedLine(10f, 5f, 0f);
//            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(ContextCompat.getColor(mContext,R.color.black_900));
            set1.setCircleColor(ContextCompat.getColor(mContext,R.color.colorAccent));
            set1.setLineWidth(3f);
            set1.disableDashedLine();

            set1.setCircleRadius(5f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);

//            set1.setFormLineWidth(1f);
//            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above

                set1.setFillColor(Color.WHITE);
            } else {
                set1.setFillColor(Color.WHITE);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mChart.setData(data);
            mChart.animateY(500);



        }
    }


    private void runEnterAnimations(LessonView holder, int position) {
        Log.d("Animation",""+position);

        if (position >= Animated_item_count){
            Log.d("Animation","postion one");
            return;
        }

        if (position>last_pos){

            Log.d("Animation","inside if");
            last_pos=position;
            holder.itemView.setTranslationY(ScreenUtil.getScreenHeight(mContext));
            holder.itemView.setScaleX(0.2f);

                    holder.itemView.animate().translationY(0)
                    .scaleX(1)

                    .setInterpolator(new DecelerateInterpolator(2f))
                    .setDuration(600)
                    .start();
        }
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class LessonView extends RecyclerView.ViewHolder{


       public  @BindView(R.id.report_frag_lesson_name)
       UbuntuRegular mLessonName;


       @BindView(R.id.chart1)
       LineChartView mChart;

        public LessonView(View itemView, final LessonClickCallbacks mCallback) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

   public interface LessonClickCallbacks{
        void ShowLessonReportFor(int position , LessonView itemView);

    }
}
