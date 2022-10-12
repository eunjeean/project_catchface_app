package com.example.fersonaapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    RequestQueue requestQueue;
    ScrollView scrollView;
    FragmentMypage fragmentMypage;
    LinearLayout mainLl, step1Ll, step2Ll, step3Ll, step4Ll, step5Ll;
    EditText monMakeEt, reportConEt, repAdrET;
    Button step1Btn, step2Btn, step3Btn, step4Btn, wantedviewBtn, infoViewBtn, step5Btn, submitBtn;
    ImageButton voiceBtn;
    ImageView wantedImg, monResultImg, userImg, monMake1Img, monMake2Img, monMake3Img, monMake4Img;
    RadioButton rd1, rd2, rd3, rd4, rd5, rd6, rd7, rd8, rd9;
    Spinner wantedSpin;
    TextView dateTv, timeTv, nameTv, phoneTv, wantedcontentTv, reportGetTv, reportGetAdrTv;
    DatePicker repDate;
    TimePicker repTime;
    CheckBox wantedCk, infoCk;

    Intent intent;
    final int PERMISSION = 1;
    SpeechRecognizer mRecognizer;
    String reportCont = null;
    String reportWanted = null;
    public static String id, pw, name, date, city, dong, phone, rep_cate, rep_con, rep_date1, rep_date,rep_time1,rep_time, mem_id, rep_adr;
    public static String shared = "fersona";
    public static String mon_id = "00";
    public static String rep_pro = "접수대기";
    public static String want_id = "want1";
    StringRequest request;

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

        // viewFindViewById 너무너무 길어서 메소드 만들어버려썽용! 맨 밑에 있습니당!ㅎㅎ
        viewFindViewById(view);

        userImg.setOnClickListener(this);
        step1Btn.setOnClickListener(this);
        step2Btn.setOnClickListener(this);
        step3Btn.setOnClickListener(this);
        step4Btn.setOnClickListener(this);
        step5Btn.setOnClickListener(this);
        voiceBtn.setOnClickListener(this);
        wantedviewBtn.setOnClickListener(this);
        infoViewBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        // LoginActivity에서 로그인 정보 불러오기
        loginContent();

        // 몽타주 4개 이미지 리스트
        monMake1Img.setOnClickListener(this);
        monMake2Img.setOnClickListener(this);
        monMake3Img.setOnClickListener(this);
        monMake4Img.setOnClickListener(this);


        // RecyclerView
        for (int i = 0; i < 4; i++) {
            if (data != null) {
                addItem("img");
            } else {
                Log.d("FragmentReport", "item null");
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
                Log.d("FragmentReport", "신고내용 포커스 이벤트 발생");
                rd2.setChecked(true);
                rd2.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.pointOrange)));
            }
        });

        ReportDate(); // 사건발생일자
        ReportTime(); // 사건발생시간
        // 신고발생위치 작성
        repAdrET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                rd5.setChecked(true);
                rd5.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.pointOrange)));
            }
        });

        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        submitBtn.setVisibility(View.GONE);

        return view;
    }

    // LoginActivity에서 로그인 정보 불러오기
    private void loginContent() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(shared, Context.MODE_PRIVATE);
        id = sharedPreferences.getString("id", "");
        pw = sharedPreferences.getString("pw", "");
        name = sharedPreferences.getString("name", "");
        date = sharedPreferences.getString("date", "");
        city = sharedPreferences.getString("city", "");
        dong = sharedPreferences.getString("dong", "");
        phone = sharedPreferences.getString("phone", "");
        nameTv.setText(name);
        phoneTv.setText(phone);
        mem_id = id;
    }


    public void addItem(String imgName) {
        MonFaceListVO item = new MonFaceListVO();
        item.setMonList(imgName);
        if (item != null) {
            data.add(item);
        } else {
            Log.d("FragmentReport", "item null");
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
                    rep_cate = reportWanted;
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
                rep_date1 = String.format("%d/%d/%d", year, month + 1, day);
                dateTv.setText(rep_date1);

                String year1 = Integer.toString(year);
                String month1 = Integer.toString(month+1); // 핸드폰 연결해서 확인해봐야 함!
                if(month1.length()==1){ month1 = "0"+month1; }
                String day1 = Integer.toString(day);
                if(day1.length()==1){ day1 = "0"+day1; }
                rep_date = year1 + month1 + day1;


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
                    rep_time1 = String.format("PM " + "%d : %d", hour, min);
                    rep_time = String.format("%d:%d", hour, min);
                } else {
                    rep_time1 =String.format("AM " + "%d : %d", hour, min);
                    rep_time = String.format("%d:%d", hour, min);
                }
                timeTv.setText(rep_time1);


            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userImg:
                Log.d("FragmentReport", "userImg");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl, new FragmentMypage()).commit();
                break;
            case R.id.voiceBtn:
                Log.d("FragmentReport", "음성녹음");
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity().getApplicationContext()); // 새 SpeechRecognizer 를 만드는 팩토리 메서드
                mRecognizer.setRecognitionListener(listener); // 리스너 설정
                mRecognizer.startListening(intent); // 듣기 시작
                break;
            case R.id.step1Btn:
                Log.d("FragmentReport", "step1");
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
                    step5Btn.setVisibility(view.VISIBLE);
                }
                break;
            case R.id.step2Btn:
                Log.d("FragmentReport", "step2");
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
                    step5Btn.setVisibility(view.VISIBLE);
                }

                break;
            case R.id.step3Btn:
                Log.d("FragmentReport", "step3");
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
                    step5Btn.setVisibility(view.VISIBLE);
                }
                break;
            case R.id.step4Btn:
                Log.d("FragmentReport", "step4");
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
                    step5Btn.setVisibility(view.VISIBLE);
                }
                break;
            case R.id.step5Btn:
                Log.d("FragmentReport", "step5");
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
                    submitBtn.setVisibility(view.VISIBLE);
                    step5Btn.setVisibility(view.GONE);
                }

                // 신고내용
                rep_con = reportConEt.getText().toString();
                reportGetTv.setText(rep_con);
                // 목격내용 <- 몽타주에 대한 설명, 몽타주 상세 내용
                wantedcontentTv.setText(monMakeEt.getText().toString());
                // 신고발생 위치
                rep_adr = repAdrET.getText().toString();
                reportGetAdrTv.setText(rep_adr);
                // 몽타주
                // monMake1Btn monMake2Btn monMake3Btn monMake4Btn

                if (monMake1Img.isSelected() == true) {
                    // 몽타주 1번째 이미지 보여주기
//                    mon_id = monMake1Img.toString();
                } else if (monMake2Img.isSelected() == true) {
                    // 몽타주 2번째 이미지 보여주기
//                    mon_id = monMake2Img.toString();
                } else if (monMake3Img.isSelected() == true) {
                    // 몽타주 3번째 이미지 보여주기
//                    mon_id = monMake3Img.toString();
                } else if (monMake4Img.isSelected() == true) {
                    // 몽타주 4번째 이미지 보여주기
//                    mon_id = monMake4Img.toString();
                }
                break;
            case R.id.wantedviewBtn:
                Log.d("FragmentReport", "신고내용 공유동의");
                Bundle shareargs = new Bundle();
                PopupDialog sharePopup = new PopupDialog("신고내용 공유동의", R.string.share);
                sharePopup.setArguments(shareargs);
                sharePopup.show(getActivity().getSupportFragmentManager(), "신고내용공유동의");
                break;
            case R.id.infoViewBtn:
                Log.d("FragmentReport", "개인정보 수집동의");
                Bundle infoargs = new Bundle();
                PopupDialog infoPopup = new PopupDialog("개인정보 수집동의", R.string.info);
                infoPopup.setArguments(infoargs);
                infoPopup.show(getActivity().getSupportFragmentManager(), "개인정보 수집동의");
                break;
            case R.id.submitBtn:
                reportCont = reportConEt.getText().toString();
                Log.d("ReportPage submitBtn", "" + reportWanted + " , " + reportCont + " , " + dateTv.getText() + " , " + timeTv.getText());
//                submitBtn.setBackgroundResource(R.color.subGray);

                // 신고내용, 목격한 상세내용, 신고발생 위치, 몽타주 이미지, 신고내용 공유동의, 개인정보 수집동의
                if(reportGetTv.length()<5 || monMakeEt.length()<5 || repAdrET.length()<5 || infoCk.isChecked() == false || wantedCk.isChecked() == false){
                    //신고내용
                    if (reportGetTv.length() < 5) {
                        rd2.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.red)));
                        Toast.makeText(getActivity().getApplicationContext(), "신고내용을 작성해주세요😊", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("FragmentReport", "신고내용 : " + reportCont);
                    }

                    // 목격한 상세내용
                    if(monMakeEt.length()<5){
                        Toast.makeText(getActivity().getApplicationContext(), "Step1의 진술내용을 작성해 주세요", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.d("FragmentReport", "목격한 상세내용 : " + monMakeEt);
                    }

                    // 신고발생 위치
                    if(repAdrET.length()<5 ){
                        Toast.makeText(getActivity().getApplicationContext(), "Step4의 신고발생위치를 작성해주세요", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.d("FragmentReport", "목격한 상세내용 : " + monMakeEt);
                    }

                    //몽타주 이미지

                    // 신고내용 및 개인정보 수집 동의 내용
                    if (infoCk.isChecked() == true && wantedCk.isChecked() == true) {
                        Log.d("FragmentReport", "check success");
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "모든항목을 입력 및 선택해주세요", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    submitBtn.setBackgroundResource(R.color.pointOrange);

                    String url = "http://121.147.52.96:5000/report";

                    // 요청 만들기
                    request = new StringRequest(
                            Request.Method.POST,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getActivity().getApplicationContext(), "신고하기 연결 성공😊", Toast.LENGTH_SHORT).show();
                                    Log.d("신고", response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity().getApplicationContext(), "신고하기 연결 실패😣", Toast.LENGTH_SHORT).show();
                                    Log.d("신고", "실패");
                                }
                            }
                    ){ // getParams 라는 메서드 오버라이딩 : alt + insert
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            Log.d("신고", rep_cate);
                            params.put("rep_cate", rep_cate);
                            params.put("rep_con", rep_con);
                            params.put("rep_date", rep_date);
                            params.put("rep_time", rep_time);
                            params.put("mem_id", mem_id);
                            params.put("rep_adr", rep_adr);
                            params.put("mon_id", mon_id);
                            params.put("want_id", want_id);
                            params.put("rep_pro", rep_pro);
                            return params;
                        }
                    };
                    Toast.makeText(getActivity().getApplicationContext(), "제출😊", Toast.LENGTH_SHORT).show();

                    request.setShouldCache(false);
                    requestQueue.add(request);
//                    finish();


                }

                break;

//                몽타주 이미지 클릭
            case R.id.monMake1Img:
                Log.d("몽타주 이미지 클릭", "monMake1Img");
                if (monMake1Img.isSelected() == false) {
                    monMake1Img.setBackgroundResource(R.color.pointOrange);
                    monMake2Img.setColorFilter(Color.parseColor("#55293241"));
                    monMake3Img.setColorFilter(Color.parseColor("#55293241"));
                    monMake4Img.setColorFilter(Color.parseColor("#55293241"));
                } else {
                    monMake1Img.setBackgroundResource(R.color.white);
                    monMake2Img.setBackgroundResource(R.color.white);
                    monMake3Img.setBackgroundResource(R.color.white);
                    monMake4Img.setBackgroundResource(R.color.white);
                }
                break;
            case R.id.monMake2Img:
                Log.d("몽타주 이미지 클릭", "monMake2Img");
                break;
            case R.id.monMake3Img:
                Log.d("몽타주 이미지 클릭", "monMake3Img");
                break;
            case R.id.monMake4Img:
                Log.d("몽타주 이미지 클릭", "monMake4Img");
                break;

        }
    }

    // 음성 인식
    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            // 말하기 시작할 준비가되면 호출
//            Toast.makeText(getApplicationContext(), "음성인식 시작", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity().getApplicationContext(), "음성인식 시작", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() { // 말하기 시작했을 때 호출
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

    // viewFindViewById 너무너무 길어서 메소드 만들어버려썽용!
    private void viewFindViewById(View view) {
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
        repAdrET = view.findViewById(R.id.repAdrET); // 신고발생위치 작성

        step1Btn = view.findViewById(R.id.step1Btn);
        step2Btn = view.findViewById(R.id.step2Btn);
        step3Btn = view.findViewById(R.id.step3Btn);
        step4Btn = view.findViewById(R.id.step4Btn);
        wantedviewBtn = view.findViewById(R.id.wantedviewBtn);
        infoViewBtn = view.findViewById(R.id.infoViewBtn);
        step5Btn = view.findViewById(R.id.step5Btn);
        submitBtn = view.findViewById(R.id.submitBtn);

        voiceBtn = view.findViewById(R.id.voiceBtn); // 음성녹음 버튼

        // 몽타주 4개 이미지
        monMake1Img = view.findViewById(R.id.monMake1Img);
        monMake2Img = view.findViewById(R.id.monMake2Img);
        monMake3Img = view.findViewById(R.id.monMake3Img);
        monMake4Img = view.findViewById(R.id.monMake4Img);

        wantedImg = view.findViewById(R.id.wantedImg); // step3 > 몽타주 이미지
        monResultImg = view.findViewById(R.id.monResultImg);
        userImg = view.findViewById(R.id.userImg);

        rd1 = view.findViewById(R.id.rd1);
        rd2 = view.findViewById(R.id.rd2);
        rd3 = view.findViewById(R.id.rd3);
        rd4 = view.findViewById(R.id.rd4);
        rd5 = view.findViewById(R.id.rd5);
        rd6 = view.findViewById(R.id.rd6);
        rd7 = view.findViewById(R.id.rd7);
        rd8 = view.findViewById(R.id.rd8);
        rd9 = view.findViewById(R.id.rd9);

        wantedSpin = view.findViewById(R.id.wantedSpin); // 범죄유형

        dateTv = view.findViewById(R.id.dateTv);
        timeTv = view.findViewById(R.id.timeTv);
        nameTv = view.findViewById(R.id.nameTv);
        phoneTv = view.findViewById(R.id.phoneTv);
        wantedcontentTv = view.findViewById(R.id.wantedcontentTv);
        reportGetTv = view.findViewById(R.id.reportGetTv);
        reportGetAdrTv = view.findViewById(R.id.reportGetAdrTv);

        repDate = view.findViewById(R.id.repDate); // 사건발생일자

        repTime = view.findViewById(R.id.repTime); // 사건발생시간

        wantedCk = view.findViewById(R.id.wantedCk);
        infoCk = view.findViewById(R.id.infoCk);
    }

}