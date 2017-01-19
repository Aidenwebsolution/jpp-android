package com.jaipurpinkpanthers.android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.WebActivity;
import com.jaipurpinkpanthers.android.util.CustomFonts;
import com.jaipurpinkpanthers.android.util.FormValidation;
import com.jaipurpinkpanthers.android.util.InternetOperations;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class FanFragment extends Fragment {
    private View view;
    private TextView tvSign, tvFan1, tvFan2, tvSubmit, tvchoosepanther;
    private EditText etFirst, etLast, etEmail, etMob;
    private ImageView ivFb, ivTw, ivInsta;
    private Activity activity;
    private AutoCompleteTextView actvcity;
    private CheckBox cbplayer1, cbplayer2, cbplayer3, cbplayer4, cbplayer5, cbplayer6, cbplayer7, cbplayer8, cbplayer9, cbplayer10, cbplayer11, cbplayer12, cbplayer13, cbplayer14, cbplayer15, cbplayer16, cbplayer17;
    public ArrayList<String> selections = new ArrayList<String>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fan, container, false);
        initilizeViews();
        setListeners();

        return view;

    }

    private void initilizeViews() {

        activity = getActivity();
        tvSign = (TextView) view.findViewById(R.id.tvSign);
        tvFan1 = (TextView) view.findViewById(R.id.tvFan1);
        tvFan2 = (TextView) view.findViewById(R.id.tvFan2);
        etFirst = (EditText) view.findViewById(R.id.etFirst);
        etLast = (EditText) view.findViewById(R.id.etLast);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etMob = (EditText) view.findViewById(R.id.etMob);
        tvSubmit = (TextView) view.findViewById(R.id.tvSubmit);
        tvchoosepanther = (TextView) view.findViewById(R.id.tvChoosePanthers);
        actvcity = (AutoCompleteTextView) view.findViewById(R.id.actvCity);

        cbplayer1 = (CheckBox) view.findViewById(R.id.cbplayer1);
        cbplayer2 = (CheckBox) view.findViewById(R.id.cbplayer2);
//        cbplayer3 = (CheckBox) view.findViewById(R.id.cbplayer3);
        cbplayer4 = (CheckBox) view.findViewById(R.id.cbplayer4);
        cbplayer5 = (CheckBox) view.findViewById(R.id.cbplayer5);
        cbplayer6 = (CheckBox) view.findViewById(R.id.cbplayer6);
//        cbplayer7 = (CheckBox) view.findViewById(R.id.cbplayer7);
        cbplayer8 = (CheckBox) view.findViewById(R.id.cbplayer8);
        cbplayer9 = (CheckBox) view.findViewById(R.id.cbplayer9);
        cbplayer10 = (CheckBox) view.findViewById(R.id.cbplayer10);
        cbplayer11 = (CheckBox) view.findViewById(R.id.cbplayer11);
        cbplayer12 = (CheckBox) view.findViewById(R.id.cbplayer12);
        cbplayer13 = (CheckBox) view.findViewById(R.id.cbplayer13);
        cbplayer14 = (CheckBox) view.findViewById(R.id.cbplayer14);
        cbplayer15 = (CheckBox) view.findViewById(R.id.cbplayer15);
        cbplayer16 = (CheckBox) view.findViewById(R.id.cbplayer16);
        cbplayer17 = (CheckBox) view.findViewById(R.id.cbplayer17);


        tvSign.setTypeface(CustomFonts.getRegularFont(activity));
        tvSubmit.setTypeface(CustomFonts.getRegularFont(activity));
        tvFan2.setTypeface(CustomFonts.getRegularFont(activity));

        tvFan1.setTypeface(CustomFonts.getLightFont(activity));
        etFirst.setTypeface(CustomFonts.getLightFont(activity));
        etLast.setTypeface(CustomFonts.getLightFont(activity));
        etMob.setTypeface(CustomFonts.getLightFont(activity));
        etEmail.setTypeface(CustomFonts.getLightFont(activity));
        tvchoosepanther.setTypeface(CustomFonts.getLightFont(activity));
        actvcity.setTypeface(CustomFonts.getLightFont(activity));


        cbplayer1.setTypeface(CustomFonts.getLightFont(activity));
        cbplayer2.setTypeface(CustomFonts.getLightFont(activity));
//        cbplayer3.setTypeface(CustomFonts.getLightFont(activity));
        cbplayer4.setTypeface(CustomFonts.getLightFont(activity));
        cbplayer5.setTypeface(CustomFonts.getLightFont(activity));
        cbplayer6.setTypeface(CustomFonts.getLightFont(activity));
//        cbplayer7.setTypeface(CustomFonts.getLightFont(activity));
        cbplayer8.setTypeface(CustomFonts.getLightFont(activity));
        cbplayer9.setTypeface(CustomFonts.getLightFont(activity));
        cbplayer10.setTypeface(CustomFonts.getLightFont(activity));
        cbplayer11.setTypeface(CustomFonts.getLightFont(activity));
        cbplayer12.setTypeface(CustomFonts.getLightFont(activity));
        cbplayer13.setTypeface(CustomFonts.getLightFont(activity));
        cbplayer14.setTypeface(CustomFonts.getLightFont(activity));
        cbplayer15.setTypeface(CustomFonts.getLightFont(activity));
        cbplayer16.setTypeface(CustomFonts.getLightFont(activity));
        cbplayer17.setTypeface(CustomFonts.getLightFont(activity));

        ivFb = (ImageView) view.findViewById(R.id.ivFb);
        ivTw = (ImageView) view.findViewById(R.id.ivTw);
        ivInsta = (ImageView) view.findViewById(R.id.ivInsta);
    }



    private void setListeners() {

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fName = etFirst.getText().toString().trim();
                String lName = etLast.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etMob.getText().toString().trim();
                String city = actvcity.getText().toString().trim();


                if (cbplayer1.isChecked())
                    selections.add("Amit Hooda");
                else
                    selections.remove("Amit Hooda");

                if (cbplayer2.isChecked())
                    selections.add("Amit Nagar");
                else
                    selections.remove("Amit Nagar");

           /*     if (cbplayer3.isChecked())
                    selections.add("David Tsai");
                else
                    selections.remove("David Tsai");*/

                if (cbplayer4.isChecked())
                    selections.add("Jasvir Singh");
                else
                    selections.remove("Jasvir Singh");

                if (cbplayer5.isChecked())
                    selections.add("Lo Chia Wei");
                else
                    selections.remove("Lo Chia Wei");

                if (cbplayer6.isChecked())
                    selections.add("Mahipal Narwal");
                else
                    selections.remove("Mahipal Narwal");
/*
                if (cbplayer7.isChecked())
                    selections.add("Masayuki Shimokawa");
                else
                    selections.remove("Masayuki Shimokawa");*/

                if (cbplayer8.isChecked())
                    selections.add("Parvesh Malik");
                else
                    selections.remove("Parvesh Malik");

                if (cbplayer9.isChecked())
                    selections.add("Praveen Narwal");
                else
                    selections.remove("Praveen Narwal");

                if (cbplayer10.isChecked())
                    selections.add("Rajesh Narwal");
                else
                    selections.remove("Rajesh Narwal");

                if (cbplayer11.isChecked())
                    selections.add("Ran Singh");
                else
                    selections.remove("Ran Singh");

                if (cbplayer12.isChecked())
                    selections.add("Rohit Rana");
                else
                    selections.remove("Rohit Rana");

                if (cbplayer13.isChecked())
                    selections.add("Shabeer Bapu Sharfudheen");
                else
                    selections.remove("Shabeer Bapu Sharfudheen");

                if (cbplayer14.isChecked())
                    selections.add("Shrikant Tewthia");
                else
                    selections.remove("Shrikant Tewthia");

                if (cbplayer15.isChecked())
                    selections.add("Tushar Patil");
                else
                    selections.remove("Tushar Patil");


                if (cbplayer16.isChecked())
                    selections.add("Ajay Kumar");
                else
                    selections.remove("Ajay Kumar");


                if (cbplayer17.isChecked())
                    selections.add("Jawahar");
                else
                    selections.remove("Jawahar");

                String str = "";
                //Log.d("hi", str);
                for (String Selection : selections) {
                    //Log.d("hi", str);
                    str = str + " " + Selection + ",";
                }
                char ch[] = str.toCharArray();
                String cbplayer = "";
                for (int i = 0; i < str.length() - 1; i++)
                    cbplayer = cbplayer + ch[i];
                cbplayer = cbplayer.trim();
                //Log.i("hi", cbplayer);

                boolean valid = true;

                if (fName.isEmpty()) {
                    etFirst.setError("Enter First Name");
                    valid = false;
                } else {
                    etFirst.setError(null);
                }

                if (lName.isEmpty()) {
                    etLast.setError("Enter Last Name");
                    valid = false;
                } else {
                    etLast.setError(null);
                }

                if (phone.isEmpty()) {
                    etMob.setError("Enter Mobile Number");
                    valid = false;
                } else if (!FormValidation.isValidMobile(phone)) {
                    etMob.setError("Enter Valid Mobile Number");
                    valid = false;
                } else {
                    etMob.setError(null);
                }

                if (email.isEmpty()) {
                    etEmail.setError("Enter Email");
                    valid = false;
                } else if (!FormValidation.isValidEmail(email)) {
                    etEmail.setError("Enter Valid Email");
                    valid = false;
                } else {
                    etEmail.setError(null);
                }


                if (city.isEmpty()) {
                    actvcity.setError("Enter City");
                    valid = false;
                } else if (!FormValidation.isValidEmail(email)) {
                    actvcity.setError("Enter Valid City");
                    valid = false;
                } else {
                    actvcity.setError(null);
                }


                if (valid) {
                    submit(fName, lName, phone, email, city, cbplayer);
                    //Toast.makeText(activity, "Thank you!", Toast.LENGTH_SHORT).show();
                }
            }
        });
       /* //city auto complete text view action
        actvcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city[] = {"Mumbai", "Pune", "Thane", "Nashik", "Nagpur",
                        "Lonavala", "dhule", "Navi Mumbai"};
                String city1[] = getResources().getStringArray(R.array.city);

                AutoCompleteTextView t1 = (AutoCompleteTextView)
                        view.findViewById(R.id.actvCity);

                ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_dropdown_item_1line, city1);

                t1.setThreshold(1);
                t1.setAdapter(adp);
            }
        });
*/


        ivFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra("webLink", "https://www.facebook.com/JaipurPinkPanthers/");
                startActivity(intent);
            }
        });

        ivTw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra("webLink", "https://twitter.com/JaipurPanthers");
                startActivity(intent);
            }
        });

        ivInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra("webLink", "https://www.instagram.com/jaipur_pinkpanthers/");
                startActivity(intent);
            }
        });

    }



    public void submit(final String fName, final String lName, final String mobile, final String email,final String city,final String cbplayer){

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        //progressDialog.show();


        new AsyncTask<Void, Void, String>() {
            boolean submit = false;

            @Override
            protected String doInBackground(Void... params) {

               try {
                    String submitJson = getSubmitJson(fName, lName, mobile, email,city,cbplayer).toString();
                    Log.v("submitjson",submitJson);
                    String response = InternetOperations.post(InternetOperations.SERVER_URL+ "contactus", submitJson);
                    JSONObject jsonObject = new JSONObject(response);

                    String value = jsonObject.optString("value");
                    if (value.equals("true")) {
                        progressDialog.dismiss();
                        submit = true;
                    } else {
                        progressDialog.dismiss();
                        submit = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch(SocketTimeoutException sc){
                    sc.printStackTrace();
                    Log.d("socket","hi");

                }catch (IOException io) {
                    io.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (submit) {
                    Toast.makeText(activity, "Thank you!!!", Toast.LENGTH_LONG).show();
                    etFirst.setText("");
                    etLast.setText("");
                    etMob.setText("");
                    etEmail.setText("");
                    actvcity.setText("");

                    if (cbplayer1.isChecked()) {
                        cbplayer1.setChecked(false);
                        selections.remove("Amit Nagar");
                    }
                /*    if (cbplayer2.isChecked()) {
                        cbplayer2.setChecked(false);
                        selections.remove("David Tsai");
                    }*/
                    if (cbplayer3.isChecked()) {
                        cbplayer3.setChecked(false);
                        selections.remove("Amit Hooda");
                    }
                    if (cbplayer4.isChecked()) {
                        cbplayer4.setChecked(false);
                        selections.remove("Jasvir Singh");
                    }
                    if (cbplayer5.isChecked()) {
                        cbplayer5.setChecked(false);
                        selections.remove("Lo Chia Wei");
                    }
                    if (cbplayer6.isChecked()) {
                        cbplayer6.setChecked(false);
                        selections.remove("Mahipal Narwal");
                    }
                /*    if (cbplayer7.isChecked()) {
                        cbplayer7.setChecked(false);
                        selections.remove("Masayuki Shimokawa");
                    }*/
                    if (cbplayer8.isChecked()) {
                        cbplayer8.setChecked(false);
                        selections.remove("Parvesh Malik");
                    }
                    if (cbplayer9.isChecked()) {
                        cbplayer9.setChecked(false);
                        selections.remove("Praveen Narwal");
                    }
                    if (cbplayer10.isChecked()) {
                        cbplayer10.setChecked(false);
                        selections.remove("Rajesh Narwal");
                    }
                    if (cbplayer11.isChecked()) {
                        cbplayer11.setChecked(false);
                        selections.remove("Ran Singh");
                    }
                    if (cbplayer12.isChecked()) {
                        cbplayer12.setChecked(false);
                        selections.remove("Rohit Rana");
                    }
                    if (cbplayer13.isChecked()) {
                        cbplayer13.setChecked(false);
                        selections.remove("Shabeer Bapu Sharfudheen");
                    }
                    if (cbplayer14.isChecked()) {
                        cbplayer14.setChecked(false);
                        selections.remove("Shrikant Tewthia");
                    }
                    if (cbplayer15.isChecked()) {
                        cbplayer15.setChecked(false);
                        selections.remove("Tushar Patil");
                    }

                    if (cbplayer16.isChecked()) {
                        cbplayer16.setChecked(false);
                        selections.remove("Ajay Kumar");
                    }
                    if (cbplayer17.isChecked()) {
                        cbplayer17.setChecked(false);
                        selections.remove("Jawahar");
                    }

                }else {
                    Toast.makeText(activity, "Oops, Something went wrong!", Toast.LENGTH_LONG).show();
                }
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }.execute(null, null, null);
    }

    public JSONObject getSubmitJson(String fName, String lName, String mobile, String email,String city,String cbplayer) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstname", fName);
            jsonObject.put("lastname", lName);
            jsonObject.put("mobile", mobile);
            jsonObject.put("email", email);
            jsonObject.put("city",city);
            jsonObject.put("favouriteplayer",cbplayer);
        } catch (JSONException je) {
        }
        return jsonObject;
    }





}
