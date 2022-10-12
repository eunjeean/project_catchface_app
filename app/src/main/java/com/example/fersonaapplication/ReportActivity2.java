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

//        // RecyclerView
//        for(int i=0;i<4;i++){
//            addItem("img");
//        }

        adapter = new MonRAdapter(data);
        wantedListRv.setAdapter(adapter);
        wantedListRv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));

        // 안드로이드 6.0버전 이상인지 체크해서 퍼미션 체크
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

        // RecognizerIntent 생성
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName()); // 여분의 키
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR"); // 언어 설정

        // 범죄유형 체크
        WantedCheck();

        // 신고내용 작성
        if(reportConEt.getText().toString().length() < 5){
//            Toast.makeText(this, "신고내용을 작성해주세요😊", Toast.LENGTH_SHORT).show();
        }else if(180 < reportConEt.getText().toString().length()){
//            Toast.makeText(this, "신고내용이 초과하였습니다.😥", Toast.LENGTH_SHORT).show();
        }else{
            rd2.setChecked(true);
            rd2.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.pointOrange)));
            reportCont = reportConEt.getText().toString();
            Log.d("ReportActivity","신고내용 : "+reportCont);
        }

        // 사건발생일자
        ReportDate();

        // 사건발생시간
        ReportTime();


    }

//    public void addItem(String imgName){
//        MonFaceListVO item = new MonFaceListVO();
//
//        item.setMonList(imgName);
//
//        data.add(item);
//    }

    // 범죄유형 체크
    private void WantedCheck() {
        wantedSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("ReportActivity",""+wantedSpin.getItemAtPosition(position));
                if(!wantedSpin.getItemAtPosition(position).toString().equals("선택")){
                    rd1.setChecked(true);
                    rd1.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity2.this, R.color.pointOrange)));
                    reportWanted = (String) wantedSpin.getItemAtPosition(position);
                    Log.d("ReportActivity","범죄유형 선택 = "+reportWanted);
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

    // 사건발생일자
    private void ReportDate() {
        repDate.init(repDate.getYear(), repDate.getMonth(), repDate.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                // 값이 바뀔때마다 텍스트 뷰의 값을 바꿔준다
                dateTv.setVisibility(View.VISIBLE);
                rd3.setChecked(true);
                rd3.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity2.this, R.color.pointOrange)));
                dateTv.setText(String.format("%d/%d/%d",year,month+1,day));
            }
        });
    }

    // 사건발생시간
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
                Log.d("ReportAcitivity2","음성녹음");
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(this); // 새 SpeechRecognizer 를 만드는 팩토리 메서드
                mRecognizer.setRecognitionListener(listener); // 리스너 설정
                mRecognizer.startListening(intent); // 듣기 시작
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
                Log.d("ReportPage","위치찾기");
                new AlertDialog.Builder(this)
                        .setTitle("위치찾기")
                        .setMessage("지도API 보여줄 예정😁")
                        // positive : 긍정 , setNegativeButton : 부정, setNeutralButton : 긍정도 부정도 아닌
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                rd5.setChecked(true);
                                rd5.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity2.this, R.color.pointOrange)));
                            }
                        })
                        .setNeutralButton("닫기", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                            }
                        })
                        .show(); // 팝업창 보여줌
                break;
            case R.id.wantedviewBtn:
                Log.d("ReportPage","신고내용 공유동의");
                new AlertDialog.Builder(this)
                        .setTitle("신고내용동의서")
                        .setMessage("신고내용동의 내용 작성예정😁")
                        // positive : 긍정 , setNegativeButton : 부정, setNeutralButton : 긍정도 부정도 아닌
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                wantedCk.setChecked(true);
                            }
                        })
                        .setNeutralButton("닫기", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                wantedCk.setChecked(false);
                            }
                        })
                        .show(); // 팝업창 보여줌
                break;
            case R.id.infoViewBtn:
                Log.d("ReportPage","개인정보 수집동의");
                new AlertDialog.Builder(this)
                        .setTitle("개인정보 수집동의")
                        .setMessage("개인정보 수집동의 내용 작성예정😁")
                        // positive : 긍정 , setNegativeButton : 부정, setNeutralButton : 긍정도 부정도 아닌
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                infoCk.setChecked(true);
                            }
                        })
                        .setNeutralButton("닫기", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                infoCk.setChecked(false);
                            }
                        })
                        .show(); // 팝업창 보여줌
                break;
            case R.id.submitBtn:
                Log.d("ReportPage submitBtn",""+reportWanted+" , "+reportCont+" , "+dateTv.getText()+" , "+timeTv.getText());
                Toast.makeText(this, "제출😊", Toast.LENGTH_SHORT).show();
                break;

//                몽타주 이미지 클릭
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
            // 말하기 시작할 준비가되면 호출
            Toast.makeText(getApplicationContext(),"음성인식 시작",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {
            // 말하기 시작했을 때 호출
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // 입력받는 소리의 크기를 알려줌
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // 말을 시작하고 인식이 된 단어를 buffer에 담음
        }

        @Override
        public void onEndOfSpeech() {
            // 말하기를 중지하면 호출
        }

        @Override
        public void onError(int error) {
            // 네트워크 또는 인식 오류가 발생했을 때 호출
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER 가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }

            Toast.makeText(getApplicationContext(), "에러 발생 : " + message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // 인식 결과가 준비되면 호출
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줌
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for(int i = 0; i < matches.size() ; i++){
//                textView.setText(matches.get(i));
                monMakeEt.setText(matches.get(i));
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            // 부분 인식 결과를 사용할 수 있을 때 호출
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            // 향후 이벤트를 추가하기 위해 예약
        }
    };

    // 갤러리 사진 선택
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 ok를 선택했고 data에 뭔가가 들어있다면
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.d("ReportPage", "uri:" + String.valueOf(uri));
            try {
                //Uri파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                wantedImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(this, "로딩에 오류가 있습니다😥", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "취소 되었습니다.😣", Toast.LENGTH_SHORT).show();
        }
    }
}