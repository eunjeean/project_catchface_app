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
        wantedListRv = view.findViewById(R.id.wantedListRv); // 수배자 4명 리스트

        mainLl = view.findViewById(R.id.mainLl);
        step1Ll = view.findViewById(R.id.step1Ll);
        step2Ll = view.findViewById(R.id.step2Ll);
        step3Ll = view.findViewById(R.id.step3Ll);
        step4Ll = view.findViewById(R.id.step4Ll);
        step5Ll = view.findViewById(R.id.step5Ll);

        monMakeEt = view.findViewById(R.id.monMakeEt); // 목격한 인물 내용 작성
        reportConEt = view.findViewById(R.id.reportConEt); // 신고내용 작성

        step1Btn = view.findViewById(R.id.step1Btn);
        step2Btn = view.findViewById(R.id.step2Btn);
        step3Btn = view.findViewById(R.id.step3Btn);
        repAdrBtn = view.findViewById(R.id.repAdrBtn); // 신고발생 위치찾기 버튼
        step4Btn = view.findViewById(R.id.step4Btn);
        wantedviewBtn = view.findViewById(R.id.wantedviewBtn);
        infoViewBtn = view.findViewById(R.id.infoViewBtn);
        step5Btn = view.findViewById(R.id.step5Btn);
        submitBtn = view.findViewById(R.id.submitBtn);

        monMake1Btn = view.findViewById(R.id.monMake1Btn);
        monMake2Btn = view.findViewById(R.id.monMake2Btn);
        monMake3Btn = view.findViewById(R.id.monMake3Btn);
        monMake4Btn = view.findViewById(R.id.monMake4Btn);
        voiceBtn = view.findViewById(R.id.voiceBtn); // 음성녹음 버튼

        wantedImg = view.findViewById(R.id.wantedImg); // step3 > 몽타주 이미지
        monResultImg = view.findViewById(R.id.monResultImg);

        rd1 = view.findViewById(R.id.rd1);
        rd2 = view.findViewById(R.id.rd2);
        rd3 = view.findViewById(R.id.rd3);
        rd4 = view.findViewById(R.id.rd4);
        rd5 = view.findViewById(R.id.rd5);
        rd6 = view.findViewById(R.id.rd6);
        rd7 = view.findViewById(R.id.rd7);

        wantedSpin = view.findViewById(R.id.wantedSpin); // 범죄유형

        dateTv = view.findViewById(R.id.dateTv);
        timeTv = view.findViewById(R.id.timeTv);
        nameTv = view.findViewById(R.id.nameTv);
        phoneTv = view.findViewById(R.id.phoneTv);
        wantedcontentTv = view.findViewById(R.id.wantedcontentTv);

        repDate = view.findViewById(R.id.repDate); // 사건발생일자

        repTime = view.findViewById(R.id.repTime); // 사건발생시간

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

        // 몽타주 4개 이미지 리스트
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

        // 안드로이드 6.0버전 이상인지 체크해서 퍼미션 체크
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

        // RecognizerIntent 생성
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getActivity().getPackageName()); // 여분의 키
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR"); // 언어 설정

        // 범죄유형 체크
        WantedCheck();

        // 신고내용 작성
        reportConEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.d("FragmentReport","신고내용 포커스 이벤트 발생");
                rd2.setChecked(true);
                rd2.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.pointOrange)));
            }
        });

        // 사건발생일자
        ReportDate();

        // 사건발생시간
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

    // 범죄유형 체크
    private void WantedCheck() {
        wantedSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("ReportActivity", "" + wantedSpin.getItemAtPosition(position));
                if (!wantedSpin.getItemAtPosition(position).toString().equals("선택")) {
                    rd1.setChecked(true);
                    rd1.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.pointOrange)));
                    reportWanted = (String) wantedSpin.getItemAtPosition(position);
                    Log.d("ReportActivity", "범죄유형 선택 = " + reportWanted);
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

    // 사건발생일자
    private void ReportDate() {
        repDate.init(repDate.getYear(), repDate.getMonth(), repDate.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                // 값이 바뀔때마다 텍스트 뷰의 값을 바꿔준다
                dateTv.setVisibility(View.VISIBLE);
                rd3.setChecked(true);
                rd3.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.pointOrange)));
                dateTv.setText(String.format("%d/%d/%d", year, month + 1, day));
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
                Log.d("ReportAcitivity2", "음성녹음");
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity().getApplicationContext()); // 새 SpeechRecognizer 를 만드는 팩토리 메서드
                mRecognizer.setRecognitionListener(listener); // 리스너 설정
                mRecognizer.startListening(intent); // 듣기 시작
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

                // 인적사항
                // nameTv, phoneTv
                // DB연결되면 사용자 정보 불러오기

                // 목격내용
//                wantedcontentTv.getText().toString().equals(monMakeEt);
                wantedcontentTv.setText(monMakeEt.getText().toString());
                // 몽타주
                // monMake1Btn monMake2Btn monMake3Btn monMake4Btn
                if(monMake1Btn.isSelected()==true){
                    // 몽타주 1번째 이미지 보여주기
                }else if(monMake2Btn.isSelected()==true){
                    // 몽타주 2번째 이미지 보여주기
                }else if(monMake3Btn.isSelected()==true){
                    // 몽타주 3번째 이미지 보여주기
                }else if(monMake4Btn.isSelected()==true){
                    // 몽타주 4번째 이미지 보여주기
                }
                break;
            case R.id.repAdrBtn:
                Log.d("ReportPage", "위치찾기");
                Bundle adrargs = new Bundle();
                PopupDialog adrPopup = new PopupDialog ("위치정보",R.string.map);
                adrPopup.setArguments(adrargs);
                adrPopup.show(getActivity().getSupportFragmentManager(), "위치정보");
                break;
            case R.id.wantedviewBtn:
                Log.d("ReportPage", "신고내용 공유동의");
                Bundle shareargs = new Bundle();
                PopupDialog sharePopup = new PopupDialog ("신고내용 공유동의",R.string.share);
                sharePopup.setArguments(shareargs);
                sharePopup.show(getActivity().getSupportFragmentManager(), "신고내용공유동의");
                break;
            case R.id.infoViewBtn:
                Log.d("ReportPage", "개인정보 수집동의");
                Bundle infoargs = new Bundle();
                PopupDialog infoPopup = new PopupDialog ("개인정보 수집동의",R.string.info);
                infoPopup.setArguments(infoargs);
                infoPopup.show(getActivity().getSupportFragmentManager(), "개인정보 수집동의");
                // 팝업창 ok 되면 제출버튼 나오도록
                // 아래코드 if문 안에 작성하면 됨
                if(infoCk.isChecked()==true&&wantedCk.isChecked()==true){
                    submitBtn.setVisibility(View.VISIBLE);
                }else{
                    submitBtn.setVisibility(View.GONE);
                }
                break;
            case R.id.submitBtn:
                reportCont = reportConEt.getText().toString();
                Log.d("ReportPage submitBtn", "" + reportWanted + " , " + reportCont + " , " + dateTv.getText() + " , " + timeTv.getText());
                    //신고내용
                    if (reportConEt.getText().toString().length() < 5) {
                        rd2.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.red)));
                        Toast.makeText(getActivity().getApplicationContext(), "신고내용을 다시 작성해주세요😊", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.d("ReportActivity", "신고내용 : " + reportCont);
                    }

                Toast.makeText(getActivity().getApplicationContext(), "제출😊", Toast.LENGTH_SHORT).show();
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
//            Toast.makeText(getApplicationContext(), "음성인식 시작", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity().getApplicationContext(), "음성인식 시작", Toast.LENGTH_SHORT).show();
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

            Toast.makeText(getActivity().getApplicationContext(), "에러 발생 : " + message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // 인식 결과가 준비되면 호출
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줌
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for (int i = 0; i < matches.size(); i++) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 ok를 선택했고 data에 뭔가가 들어있다면
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Log.d("ReportPage", "uri:" + String.valueOf(uri));
            try {
                //Uri파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                wantedImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(getActivity().getApplicationContext(), "로딩에 오류가 있습니다😥", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "취소 되었습니다.😣", Toast.LENGTH_SHORT).show();
        }
    }
}