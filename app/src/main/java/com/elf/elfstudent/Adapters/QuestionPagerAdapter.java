package com.elf.elfstudent.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.elf.elfstudent.CustomUI.HelviticaLight;
import com.elf.elfstudent.R;
import com.elf.elfstudent.model.Answers;
import com.elf.elfstudent.model.Question;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 18/10/16.
 */
public class QuestionPagerAdapter extends PagerAdapter {


    private static final String TAG = "Question Adapter";
    private Context mContext;
    List<Question> mList;


    String optionSelected;




    private static int currentPosition = 0;

    @BindView(R.id.test_question_text)
    HelviticaLight mQuestionText;

    @BindView(R.id.test_radio_group)
    RadioGroup mGroup;

    @BindView(R.id.test_radio_opta)
    RadioButton optionA;

    @BindView(R.id.test_radio_optb) RadioButton optionB;

    @BindView(R.id.test_radio_optc) RadioButton optionC;

    @BindView(R.id.test_radio_optd) RadioButton optionD;




    private int count = 0;
    public QuestionPagerAdapter(Context context , List<Question> mQuestionList) {


        this.mContext = context;

        this.mList = mQuestionList;
        count = mQuestionList.size();





    }


    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
        Log.d(TAG, "restoreState: ");
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }



    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        Log.d(TAG, "instantiateItem: "+position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.questionview,container, false);
        ButterKnife.bind(this,layout);
        Log.d(TAG, "Question ID "+mList.get(position).getmQuestionId());

        mQuestionText.setText(mList.get(position).getmQuestion());
        optionA.setText(String.format("A.    %s", mList.get(position).getmOpt_a()));
        optionB.setText(String.format("B.    %s", mList.get(position).getmOpt_b()));
        optionC.setText(String.format("C.    %s", mList.get(position).getmOpt_c()));
        optionD.setText(String.format("D.    %s", mList.get(position).getmOpt_d()));

        isAlreadyChecked(mList.get(position),position);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButtonClicked(checkedId,position);

            }
        });


//        checkAlreadySelected(position);
        /*    if (mAnswersList.get(position){

                try {

                    if (mList.get(position).getmQuestionId() == mAnswersList.get(position).getQuestionId()){
                        //same Question Id
                        //check whether answer Exists or not
                        if (mAnswersList.get(position).isOptionSelected()){
                            //option have been selected previosuly
                            Log.d(TAG, "Previously selected option "+mAnswersList.get(position).getOptionSelected());
                        }

                    }
                }
                catch (Exception e){
                    Log.d(TAG, "instantiateItem: ");
                }
                */
        container.addView(layout);
        return layout;
    }

    private void isAlreadyChecked(Question question, int position) {

        if (question.isSelected()){
            Log.d(TAG, "Already Selected option Exists for "+position +" and the option is "+ question.getSelectedOption());
            String optionselected =  question.getSelectedOption();

            if (optionselected.equals("A")){

                optionA.setChecked(true);



            }
            if (optionselected.equals("B")){

                optionB.setChecked(true);


            }
            if (optionselected.equals("C")){

                optionC.setChecked(true);

            }
            if (optionselected.equals("D")){
                optionD.setChecked(true);


            }
        }
        else{
            //No question previously Selected
            Log.d(TAG, "No Question Previously selected for "+position);
        }

    }

    /*private void checkAlreadySelected(int position) {
        Question q = mList.get(position);
        if (mAnswersList.size()>0){

            if (a == null){
                Log.d(TAG, "a is null");
            }
        else{
                if (a.equals(q)){
                    Log.d(TAG, "checkAlreadyExists ");
                }else{
                    Log.d(TAG, "No it doesnnt exists");
                }
            }
        }
        */




      /*  if (mAnswersList.get(position)!=null) {
            if (mAnswersList.get(position).getQuestionId().equals(mList.get(position).getmQuestionId())) {
                //same QuestionId
                Log.d(TAG, "same question ID");
            }
        }
        */



    /*  if (mList.get(position).isSelected()){
            String optionSelected = mList.get(position).getSelectedOption();
            switch (optionSelected){
                case "A":
                    optionA.setChecked(true);
                     break;
                case "B":
                    optionB.setChecked(true);
                    break;
                case "C":
                    optionC.setChecked(true);
                    break;
                case "D":
                    optionD.setChecked(true);
                    break;
            }
        }
        */



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {


        (container).removeView((View) object);



    }



    private void radioButtonClicked(int checkedId, int position) {



        String option = null;


        switch (checkedId){
            case R.id.test_radio_opta:
                option ="A";

                break;
            case R.id.test_radio_optb:

                option ="B";
                break;
            case R.id.test_radio_optc:

                option ="C";
                break;
            case R.id.test_radio_optd:
                option="D";
                break;
            default:
                option = "Noting";
                break;
        }
//      mAnswersList.add(new Answers(mList.get(position).getmQuestionId(),option,true));
        mList.get(position).setSelected(true);
        mList.get(position).setSelectedOption(option);
        notifyDataSetChanged();
        Log.d(TAG, "Radio Button Selected Notiy Data set Changed Called ");
    }

    public void setOptionselected(int pageNumber, String option) {

        Log.d(TAG, "setting Option Selected");
        mList.get(pageNumber).setSelected(true);
        mList.get(pageNumber).setSelectedOption(option);
        notifyDataSetChanged();
    }

    public List<Question> getQuestionList() {
        return this.mList;
    }


}
