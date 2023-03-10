package com.example.fersonaapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentReport#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentReport extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // RecyclerView
    ArrayList<MonFaceListVO> data = new ArrayList<>();
    private RecyclerView wantedListRv;
    private MonRAdapter adapter;


    ScrollView scrollView;
    LinearLayout mainLl, step1Ll, step2Ll, step3Ll, step4Ll, step5Ll;
    EditText monMakeEt, reportConEt;
    Button step1Btn, step2Btn, step3Btn, repAdrBtn, step4Btn, wantedviewBtn, infoViewBtn, step5Btn, submitBtn;
    ImageButton monMake1Btn, monMake2Btn, monMake3Btn, monMake4Btn, voiceBtn;
    ImageView wantedImg, monResultImg;
    RadioButton rd1, rd2, rd3, rd4, rd5, rd6, rd7;
    Spinner wantedSpin;
    TextView dateTv, timeTv, nameTv, phoneTv, wantedcontentTv;
    DatePicker repDate;
    TimePicker repTime;
    CheckBox wantedCk, infoCk;

    Intent intent;
    final int PERMISSION = 1;
    SpeechRecognizer mRecognizer;
    String reportCont = null;
    String reportWanted = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentReport newInstance(String param1, String param2) {
        FragmentReport fragment = new FragmentReport();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        scrollView = view.findViewById(R.id.scrollView);
        wantedListRv = view.findViewById(R.id.wantedListRv); // ????????? 4??? ?????????

        mainLl = view.findViewById(R.id.mainLl);
        step1Ll = view.findViewById(R.id.step1Ll);
        step2Ll = view.findViewById(R.id.step2Ll);
        step3Ll = view.findViewById(R.id.step3Ll);
        step4Ll = view.findViewById(R.id.step4Ll);
        step5Ll = view.findViewById(R.id.step5Ll);

        monMakeEt = view.findViewById(R.id.monMakeEt); // ????????? ?????? ?????? ??????
        reportConEt = view.findViewById(R.id.reportConEt); // ???????????? ??????

        step1Btn = view.findViewById(R.id.step1Btn);
        step2Btn = view.findViewById(R.id.step2Btn);
        step3Btn = view.findViewById(R.id.step3Btn);
        repAdrBtn = view.findViewById(R.id.repAdrBtn); // ???????????? ???????????? ??????
        step4Btn = view.findViewById(R.id.step4Btn);
        wantedviewBtn = view.findViewById(R.id.wantedviewBtn);
        infoViewBtn = view.findViewById(R.id.infoViewBtn);
        step5Btn = view.findViewById(R.id.step5Btn);
        submitBtn = view.findViewById(R.id.submitBtn);

        monMake1Btn = view.findViewById(R.id.monMake1Btn);
        monMake2Btn = view.findViewById(R.id.monMake2Btn);
        monMake3Btn = view.findViewById(R.id.monMake3Btn);
        monMake4Btn = view.findViewById(R.id.monMake4Btn);
        voiceBtn = view.findViewById(R.id.voiceBtn); // ???????????? ??????

        wantedImg = view.findViewById(R.id.wantedImg); // step3 > ????????? ?????????
        monResultImg = view.findViewById(R.id.monResultImg);

        rd1 = view.findViewById(R.id.rd1);
        rd2 = view.findViewById(R.id.rd2);
        rd3 = view.findViewById(R.id.rd3);
        rd4 = view.findViewById(R.id.rd4);
        rd5 = view.findViewById(R.id.rd5);
        rd6 = view.findViewById(R.id.rd6);
        rd7 = view.findViewById(R.id.rd7);

        wantedSpin = view.findViewById(R.id.wantedSpin); // ????????????

        dateTv = view.findViewById(R.id.dateTv);
        timeTv = view.findViewById(R.id.timeTv);
        nameTv = view.findViewById(R.id.nameTv);
        phoneTv = view.findViewById(R.id.phoneTv);
        wantedcontentTv = view.findViewById(R.id.wantedcontentTv);

        repDate = view.findViewById(R.id.repDate); // ??????????????????

        repTime = view.findViewById(R.id.repTime); // ??????????????????

        wantedCk = view.findViewById(R.id.wantedCk);
        infoCk = view.findViewById(R.id.infoCk);

        step1Btn.setOnClickListener(this);
        step2Btn.setOnClickListener(this);
        step3Btn.setOnClickListener(this);
        step4Btn.setOnClickListener(this);
        step5Btn.setOnClickListener(this);
        voiceBtn.setOnClickListener(this);
        repAdrBtn.setOnClickListener(this);
        wantedviewBtn.setOnClickListener(this);
        infoViewBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        // ????????? 4??? ????????? ?????????
        monMake1Btn.setOnClickListener(this);
        monMake2Btn.setOnClickListener(this);
        monMake3Btn.setOnClickListener(this);
        monMake4Btn.setOnClickListener(this);

        // RecyclerView
        for (int i = 0; i < 4; i++) {
            if(data != null){
                addItem("img");
            }else{
                Log.d("FragmentReport","item null");
            }
        }

        adapter = new MonRAdapter(data);
        wantedListRv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false));
        wantedListRv.setAdapter(adapter);

        // ??????????????? 6.0?????? ???????????? ???????????? ????????? ??????
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

        // RecognizerIntent ??????
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getActivity().getPackageName()); // ????????? ???
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR"); // ?????? ??????

        // ???????????? ??????
        WantedCheck();

        // ???????????? ??????
        reportConEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.d("FragmentReport","???????????? ????????? ????????? ??????");
                rd2.setChecked(true);
                rd2.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.pointOrange)));
            }
        });

        // ??????????????????
        ReportDate();

        // ??????????????????
        ReportTime();

        submitBtn.setVisibility(View.GONE);
        return view;
    }

    public void addItem(String imgName) {
        MonFaceListVO item = new MonFaceListVO();
        item.setMonList(imgName);
        if(item != null){
            data.add(item);
        }else{
            Log.d("FragmentReport","item null");
        }
    }

    // ???????????? ??????
    private void WantedCheck() {
        wantedSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("ReportActivity", "" + wantedSpin.getItemAtPosition(position));
                if (!wantedSpin.getItemAtPosition(position).toString().equals("??????")) {
                    rd1.setChecked(true);
                    rd1.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.pointOrange)));
                    reportWanted = (String) wantedSpin.getItemAtPosition(position);
                    Log.d("ReportActivity", "???????????? ?????? = " + reportWanted);
                } else {
                    rd1.setChecked(false);
                    rd1.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.btnGray)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // ??????????????????
    private void ReportDate() {
        repDate.init(repDate.getYear(), repDate.getMonth(), repDate.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                // ?????? ??????????????? ????????? ?????? ?????? ????????????
                dateTv.setVisibility(View.VISIBLE);
                rd3.setChecked(true);
                rd3.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.pointOrange)));
                dateTv.setText(String.format("%d/%d/%d", year, month + 1, day));
            }
        });
    }

    // ??????????????????
    private void ReportTime() {
        repTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int min) {
                timeTv.setVisibility(View.VISIBLE);
                rd4.setChecked(true);
                rd4.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.pointOrange)));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = repTime.getHour();
                    min = repTime.getMinute();
                } else {
                    hour = repTime.getCurrentHour();
                    min = repTime.getCurrentMinute();
                }

                if (hour >= 12) {
                    timeTv.setText(String.format("PM " + "%d : %d", hour, min));
                } else {
                    timeTv.setText(String.format("AM " + "%d : %d", hour, min));
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.voiceBtn:
                Log.d("ReportAcitivity2", "????????????");
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity().getApplicationContext()); // ??? SpeechRecognizer ??? ????????? ????????? ?????????
                mRecognizer.setRecognitionListener(listener); // ????????? ??????
                mRecognizer.startListening(intent); // ?????? ??????
                break;
            case R.id.step1Btn:
                Log.d("ReportAcitivity2", "step1");
                step2Ll.setVisibility(View.GONE);
                step3Ll.setVisibility(View.GONE);
                step4Ll.setVisibility(View.GONE);
                step5Ll.setVisibility(View.GONE);
                if (step1Ll.getVisibility() == View.VISIBLE) {
                    step2Ll.setVisibility(View.GONE);
                    step3Ll.setVisibility(View.GONE);
                    step4Ll.setVisibility(View.GONE);
                    step5Ll.setVisibility(View.GONE);
                } else {
                    step1Ll.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.step2Btn:
                Log.d("ReportAcitivity2", "step2");
                step1Ll.setVisibility(View.GONE);
                step3Ll.setVisibility(View.GONE);
                step4Ll.setVisibility(View.GONE);
                step5Ll.setVisibility(View.GONE);
                if (step2Ll.getVisibility() == View.VISIBLE) {
                    step1Ll.setVisibility(View.GONE);
                    step3Ll.setVisibility(View.GONE);
                    step4Ll.setVisibility(View.GONE);
                    step5Ll.setVisibility(View.GONE);
                } else {
                    step2Ll.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.step3Btn:
                Log.d("ReportAcitivity2", "step3");
                step1Ll.setVisibility(View.GONE);
                step2Ll.setVisibility(View.GONE);
                step4Ll.setVisibility(View.GONE);
                step5Ll.setVisibility(View.GONE);
                if (step3Ll.getVisibility() == View.VISIBLE) {
                    step1Ll.setVisibility(View.GONE);
                    step2Ll.setVisibility(View.GONE);
                    step4Ll.setVisibility(View.GONE);
                    step5Ll.setVisibility(View.GONE);
                } else {
                    step3Ll.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.step4Btn:
                Log.d("ReportAcitivity2", "step4");
                step1Ll.setVisibility(View.GONE);
                step2Ll.setVisibility(View.GONE);
                step3Ll.setVisibility(View.GONE);
                step5Ll.setVisibility(View.GONE);
                if (step4Ll.getVisibility() == View.VISIBLE) {
                    step1Ll.setVisibility(View.GONE);
                    step2Ll.setVisibility(View.GONE);
                    step3Ll.setVisibility(View.GONE);
                    step5Ll.setVisibility(View.GONE);
                } else {
                    step4Ll.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.step5Btn:
                Log.d("ReportAcitivity2", "step5");
                step1Ll.setVisibility(View.GONE);
                step2Ll.setVisibility(View.GONE);
                step3Ll.setVisibility(View.GONE);
                step4Ll.setVisibility(View.GONE);
                if (step5Ll.getVisibility() == View.VISIBLE) {
                    step1Ll.setVisibility(View.GONE);
                    step2Ll.setVisibility(View.GONE);
                    step3Ll.setVisibility(View.GONE);
                    step4Ll.setVisibility(View.GONE);
                } else {
                    step5Ll.setVisibility(View.VISIBLE);
                }

                // ????????????
                // nameTv, phoneTv
                // DB???????????? ????????? ?????? ????????????

                // ????????????
//                wantedcontentTv.getText().toString().equals(monMakeEt);
                wantedcontentTv.setText(monMakeEt.getText().toString());
                // ?????????
                // monMake1Btn monMake2Btn monMake3Btn monMake4Btn
                if(monMake1Btn.isSelected()==true){
                    // ????????? 1?????? ????????? ????????????
                }else if(monMake2Btn.isSelected()==true){
                    // ????????? 2?????? ????????? ????????????
                }else if(monMake3Btn.isSelected()==true){
                    // ????????? 3?????? ????????? ????????????
                }else if(monMake4Btn.isSelected()==true){
                    // ????????? 4?????? ????????? ????????????
                }
                break;
            case R.id.repAdrBtn:
                Log.d("ReportPage", "????????????");
                Bundle adrargs = new Bundle();
                PopupDialog adrPopup = new PopupDialog ("????????????",R.string.map);
                adrPopup.setArguments(adrargs);
                adrPopup.show(getActivity().getSupportFragmentManager(), "????????????");
                break;
            case R.id.wantedviewBtn:
                Log.d("ReportPage", "???????????? ????????????");
                Bundle shareargs = new Bundle();
                PopupDialog sharePopup = new PopupDialog ("???????????? ????????????",R.string.share);
                sharePopup.setArguments(shareargs);
                sharePopup.show(getActivity().getSupportFragmentManager(), "????????????????????????");
                break;
            case R.id.infoViewBtn:
                Log.d("ReportPage", "???????????? ????????????");
                Bundle infoargs = new Bundle();
                PopupDialog infoPopup = new PopupDialog ("???????????? ????????????",R.string.info);
                infoPopup.setArguments(infoargs);
                infoPopup.show(getActivity().getSupportFragmentManager(), "???????????? ????????????");
                // ????????? ok ?????? ???????????? ????????????
                // ???????????? if??? ?????? ???????????? ???
                if(infoCk.isChecked()==true&&wantedCk.isChecked()==true){
                    submitBtn.setVisibility(View.VISIBLE);
                }else{
                    submitBtn.setVisibility(View.GONE);
                }
                break;
            case R.id.submitBtn:
                reportCont = reportConEt.getText().toString();
                Log.d("ReportPage submitBtn", "" + reportWanted + " , " + reportCont + " , " + dateTv.getText() + " , " + timeTv.getText());
                    //????????????
                    if (reportConEt.getText().toString().length() < 5) {
                        rd2.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.red)));
                        Toast.makeText(getActivity().getApplicationContext(), "??????????????? ?????? ??????????????????????", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.d("ReportActivity", "???????????? : " + reportCont);
                    }

                Toast.makeText(getActivity().getApplicationContext(), "??????????", Toast.LENGTH_SHORT).show();
                break;

//                ????????? ????????? ??????
            case R.id.monMake1Btn:
                break;
            case R.id.monMake2Btn:
                break;
            case R.id.monMake3Btn:
                break;
            case R.id.monMake4Btn:
                break;

        }
    }

    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            // ????????? ????????? ??????????????? ??????
//            Toast.makeText(getApplicationContext(), "???????????? ??????", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity().getApplicationContext(), "???????????? ??????", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {
            // ????????? ???????????? ??? ??????
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // ???????????? ????????? ????????? ?????????
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // ?????? ???????????? ????????? ??? ????????? buffer??? ??????
        }

        @Override
        public void onEndOfSpeech() {
            // ???????????? ???????????? ??????
        }

        @Override
        public void onError(int error) {
            // ???????????? ?????? ?????? ????????? ???????????? ??? ??????
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "??????????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "???????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "????????? ????????????";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "?????? ??? ??????";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER ??? ??????";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "????????? ?????????";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "????????? ????????????";
                    break;
                default:
                    message = "??? ??? ?????? ?????????";
                    break;
            }

            Toast.makeText(getActivity().getApplicationContext(), "?????? ?????? : " + message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // ?????? ????????? ???????????? ??????
            // ?????? ?????? ArrayList??? ????????? ?????? textView??? ????????? ?????????
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for (int i = 0; i < matches.size(); i++) {
//                textView.setText(matches.get(i));
                monMakeEt.setText(matches.get(i));
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            // ?????? ?????? ????????? ????????? ??? ?????? ??? ??????
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            // ?????? ???????????? ???????????? ?????? ??????
        }
    };

    // ????????? ?????? ??????
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request????????? 0?????? ok??? ???????????? data??? ????????? ???????????????
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Log.d("ReportPage", "uri:" + String.valueOf(uri));
            try {
                //Uri????????? Bitmap?????? ???????????? ImageView??? ?????? ?????????.
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                wantedImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(getActivity().getApplicationContext(), "????????? ????????? ????????????????", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "?????? ???????????????.????", Toast.LENGTH_SHORT).show();
        }
    }
}