package com.example.fersonaapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.view.View;
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

import java.io.IOException;
import java.util.ArrayList;

public class ReportActivity2 extends AppCompatActivity implements View.OnClickListener {

    // RecyclerView
    ArrayList<MonFaceListVO> data;
    private RecyclerView wantedListRv;
    private MonRAdapter adapter;

    ScrollView scrollView;
    LinearLayout mainLl,step1Ll,step2Ll,step3Ll,step4Ll,step5Ll;
    EditText monMakeEt, reportConEt;
    Button step1Btn, step2Btn, step3Btn, repAdrBtn, step4Btn, wantedviewBtn, infoViewBtn, step5Btn, submitBtn;
    ImageButton monMake1Btn,monMake2Btn,monMake3Btn,monMake4Btn,voiceBtn;
    ImageView wantedImg,monResultImg;
    RadioButton rd1, rd2, rd3, rd4, rd5, rd6;
    Spinner wantedSpin;
    TextView dateTv, timeTv, nameTv, phoneTv;
    DatePicker repDate;
    TimePicker repTime;
    CheckBox wantedCk, infoCk;

    Intent intent;
    final int PERMISSION = 1;
    SpeechRecognizer mRecognizer;
    String reportCont = null;
    String reportWanted = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report2);

        data = new ArrayList<>();
        wantedListRv = findViewById(R.id.wantedListRv);

        scrollView = findViewById(R.id.scrollView);

        mainLl = findViewById(R.id.mainLl);
        step1Ll = findViewById(R.id.step1Ll);
        step2Ll = findViewById(R.id.step2Ll);
        step3Ll = findViewById(R.id.step3Ll);
        step4Ll = findViewById(R.id.step4Ll);
        step5Ll = findViewById(R.id.step5Ll);

        monMakeEt = findViewById(R.id.monMakeEt);
        reportConEt = findViewById(R.id.reportConEt);

        step1Btn = findViewById(R.id.step1Btn);
        step2Btn = findViewById(R.id.step2Btn);
        step3Btn = findViewById(R.id.step3Btn);
        repAdrBtn = findViewById(R.id.repAdrBtn);
        step4Btn = findViewById(R.id.step4Btn);
        wantedviewBtn = findViewById(R.id.wantedviewBtn);
        infoViewBtn = findViewById(R.id.infoViewBtn);
        step5Btn = findViewById(R.id.step5Btn);
        submitBtn = findViewById(R.id.submitBtn);
        voiceBtn = findViewById(R.id.voiceBtn);

        monMake1Btn = findViewById(R.id.monMake1Btn);
        monMake2Btn = findViewById(R.id.monMake2Btn);
        monMake3Btn = findViewById(R.id.monMake3Btn);
        monMake4Btn = findViewById(R.id.monMake4Btn);

        wantedImg = findViewById(R.id.wantedImg);
        monResultImg = findViewById(R.id.monResultImg);

        wantedListRv = findViewById(R.id.wantedListRv);

        rd1 = findViewById(R.id.rd1);
        rd2 = findViewById(R.id.rd2);
        rd3 = findViewById(R.id.rd3);
        rd4 = findViewById(R.id.rd4);
        rd5 = findViewById(R.id.rd5);
        rd6 = findViewById(R.id.rd6);

        wantedSpin = findViewById(R.id.wantedSpin);

        dateTv = findViewById(R.id.dateTv);
        timeTv = findViewById(R.id.timeTv);
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);

        repDate = findViewById(R.id.repDate);

        repTime = findViewById(R.id.repTime);

        wantedCk = findViewById(R.id.wantedCk);
        infoCk = findViewById(R.id.infoCk);

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

        monMake1Btn.setOnClickListener(this);
        monMake2Btn.setOnClickListener(this);
        monMake3Btn.setOnClickListener(this);
        monMake4Btn.setOnClickListener(this);

        // RecyclerView
        for(int i=0;i<4;i++){
            addItem("img");
        }

        adapter = new MonRAdapter(data);
        wantedListRv.setAdapter(adapter);
        wantedListRv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));

        // ??????????????? 6.0?????? ???????????? ???????????? ????????? ??????
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

        // RecognizerIntent ??????
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName()); // ????????? ???
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR"); // ?????? ??????

        // ???????????? ??????
        WantedCheck();

        // ???????????? ??????
        if(reportConEt.getText().toString().length() < 5){
//            Toast.makeText(this, "??????????????? ??????????????????????", Toast.LENGTH_SHORT).show();
        }else if(180 < reportConEt.getText().toString().length()){
//            Toast.makeText(this, "??????????????? ?????????????????????.????", Toast.LENGTH_SHORT).show();
        }else{
            rd2.setChecked(true);
            rd2.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.pointOrange)));
            reportCont = reportConEt.getText().toString();
            Log.d("ReportActivity","???????????? : "+reportCont);
        }

        // ??????????????????
        ReportDate();

        // ??????????????????
        ReportTime();


    }

    public void addItem(String imgName){
        MonFaceListVO item = new MonFaceListVO();

        item.setMonList(imgName);

        data.add(item);
    }

    // ???????????? ??????
    private void WantedCheck() {
        wantedSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("ReportActivity",""+wantedSpin.getItemAtPosition(position));
                if(!wantedSpin.getItemAtPosition(position).toString().equals("??????")){
                    rd1.setChecked(true);
                    rd1.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity2.this, R.color.pointOrange)));
                    reportWanted = (String) wantedSpin.getItemAtPosition(position);
                    Log.d("ReportActivity","???????????? ?????? = "+reportWanted);
                }else{
                    rd1.setChecked(false);
                    rd1.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity2.this, R.color.btnGray)));
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
                rd3.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity2.this, R.color.pointOrange)));
                dateTv.setText(String.format("%d/%d/%d",year,month+1,day));
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
                rd4.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity2.this, R.color.pointOrange)));

                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    hour = repTime.getHour();
                    min = repTime.getMinute();
                }else{
                    hour = repTime.getCurrentHour();
                    min = repTime.getCurrentMinute();
                }

                if(hour>=12){
                    timeTv.setText(String.format("PM "+"%d : %d",hour,min));
                }else{
                    timeTv.setText(String.format("AM "+"%d : %d",hour,min));
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.voiceBtn:
                Log.d("ReportAcitivity2","????????????");
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(this); // ??? SpeechRecognizer ??? ????????? ????????? ?????????
                mRecognizer.setRecognitionListener(listener); // ????????? ??????
                mRecognizer.startListening(intent); // ?????? ??????
                break;
            case R.id.step1Btn:
                Log.d("ReportAcitivity2","step1");
                step2Ll.setVisibility(View.GONE);
                step3Ll.setVisibility(View.GONE);
                step4Ll.setVisibility(View.GONE);
                step5Ll.setVisibility(View.GONE);
                if(step1Ll.getVisibility()==View.VISIBLE){
                    step2Ll.setVisibility(View.GONE);
                    step3Ll.setVisibility(View.GONE);
                    step4Ll.setVisibility(View.GONE);
                    step5Ll.setVisibility(View.GONE);
                }else{
                    step1Ll.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.step2Btn:
                Log.d("ReportAcitivity2","step2");
                step1Ll.setVisibility(View.GONE);
                step3Ll.setVisibility(View.GONE);
                step4Ll.setVisibility(View.GONE);
                step5Ll.setVisibility(View.GONE);
                if(step2Ll.getVisibility()==View.VISIBLE){
                    step1Ll.setVisibility(View.GONE);
                    step3Ll.setVisibility(View.GONE);
                    step4Ll.setVisibility(View.GONE);
                    step5Ll.setVisibility(View.GONE);
                }else{
                    step2Ll.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.step3Btn:
                Log.d("ReportAcitivity2","step3");
                step1Ll.setVisibility(View.GONE);
                step2Ll.setVisibility(View.GONE);
                step4Ll.setVisibility(View.GONE);
                step5Ll.setVisibility(View.GONE);
                if(step3Ll.getVisibility()==View.VISIBLE){
                    step1Ll.setVisibility(View.GONE);
                    step2Ll.setVisibility(View.GONE);
                    step4Ll.setVisibility(View.GONE);
                    step5Ll.setVisibility(View.GONE);
                }else{
                    step3Ll.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.step4Btn:
                Log.d("ReportAcitivity2","step4");
                step1Ll.setVisibility(View.GONE);
                step2Ll.setVisibility(View.GONE);
                step3Ll.setVisibility(View.GONE);
                step5Ll.setVisibility(View.GONE);
                if(step4Ll.getVisibility()==View.VISIBLE){
                    step1Ll.setVisibility(View.GONE);
                    step2Ll.setVisibility(View.GONE);
                    step3Ll.setVisibility(View.GONE);
                    step5Ll.setVisibility(View.GONE);
                }else{
                    step4Ll.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.step5Btn:
                Log.d("ReportAcitivity2","step5");
                step1Ll.setVisibility(View.GONE);
                step2Ll.setVisibility(View.GONE);
                step3Ll.setVisibility(View.GONE);
                step4Ll.setVisibility(View.GONE);
                if(step5Ll.getVisibility()==View.VISIBLE){
                    step1Ll.setVisibility(View.GONE);
                    step2Ll.setVisibility(View.GONE);
                    step3Ll.setVisibility(View.GONE);
                    step4Ll.setVisibility(View.GONE);
                }else{
                    step5Ll.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.repAdrBtn:
                Log.d("ReportPage","????????????");
                new AlertDialog.Builder(this)
                        .setTitle("????????????")
                        .setMessage("??????API ????????? ??????????")
                        // positive : ?????? , setNegativeButton : ??????, setNeutralButton : ????????? ????????? ??????
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                rd5.setChecked(true);
                                rd5.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity2.this, R.color.pointOrange)));
                            }
                        })
                        .setNeutralButton("??????", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                            }
                        })
                        .show(); // ????????? ?????????
                break;
            case R.id.wantedviewBtn:
                Log.d("ReportPage","???????????? ????????????");
                new AlertDialog.Builder(this)
                        .setTitle("?????????????????????")
                        .setMessage("?????????????????? ?????? ????????????????")
                        // positive : ?????? , setNegativeButton : ??????, setNeutralButton : ????????? ????????? ??????
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                wantedCk.setChecked(true);
                            }
                        })
                        .setNeutralButton("??????", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                wantedCk.setChecked(false);
                            }
                        })
                        .show(); // ????????? ?????????
                break;
            case R.id.infoViewBtn:
                Log.d("ReportPage","???????????? ????????????");
                new AlertDialog.Builder(this)
                        .setTitle("???????????? ????????????")
                        .setMessage("???????????? ???????????? ?????? ????????????????")
                        // positive : ?????? , setNegativeButton : ??????, setNeutralButton : ????????? ????????? ??????
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                infoCk.setChecked(true);
                            }
                        })
                        .setNeutralButton("??????", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                infoCk.setChecked(false);
                            }
                        })
                        .show(); // ????????? ?????????
                break;
            case R.id.submitBtn:
                Log.d("ReportPage submitBtn",""+reportWanted+" , "+reportCont+" , "+dateTv.getText()+" , "+timeTv.getText());
                Toast.makeText(this, "??????????", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(),"???????????? ??????",Toast.LENGTH_SHORT).show();
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

            Toast.makeText(getApplicationContext(), "?????? ?????? : " + message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // ?????? ????????? ???????????? ??????
            // ?????? ?????? ArrayList??? ????????? ?????? textView??? ????????? ?????????
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for(int i = 0; i < matches.size() ; i++){
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //request????????? 0?????? ok??? ???????????? data??? ????????? ???????????????
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.d("ReportPage", "uri:" + String.valueOf(uri));
            try {
                //Uri????????? Bitmap?????? ???????????? ImageView??? ?????? ?????????.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                wantedImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(this, "????????? ????????? ????????????????", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "?????? ???????????????.????", Toast.LENGTH_SHORT).show();
        }
    }
}