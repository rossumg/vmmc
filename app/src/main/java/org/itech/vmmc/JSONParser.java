package org.itech.vmmc;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Objects;


public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }


    TextView content;
    EditText fname, email, login, pass, username, password;
    String Name, Email, Login, Pass, Username, Password;

    /** Called when the activity is first created. */
    public JSONObject example() {
        //Log.d(LOG, "example0");

 /*
           setContentView(R.layout.activity_http_post_example);

            content    =   (TextView)findViewById( R.id.content );
            fname      =   (EditText)findViewById(R.id.name);
            email      =   (EditText)findViewById(R.id.email);
            login      =    (EditText)findViewById(R.id.loginname);
            pass       =   (EditText)findViewById(R.id.password);


            Button saveme=(Button)findViewById(R.id.save);

            saveme.setOnClickListener(new Button.OnClickListener(){

                public void onClick(View v)
                {
                    try{

                        // CALL GetText method to make post method call
                        GetText();
                    }
                    catch(Exception ex)
                    {
                        content.setText(" url exeption! " );
                    }
                }
            });
            */
        try {
            GetText();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Create GetText Metod
    public  void  GetText()  throws  UnsupportedEncodingException
    {
        //Log.d(LOG, "GetText0");
        // Get user defined values
//            Name = fname.getText().toString();
//            Email   = email.getText().toString();
//            Login   = login.getText().toString();
//            Pass   = pass.getText().toString();
        Username = "tt";
        Password = "tt";

        //Log.d(LOG, "GetText1");

        // Create data variable for sent values to server

        String data = URLEncoder.encode("name", "UTF-8")
                + "=" + URLEncoder.encode(Name, "UTF-8");

        data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                + URLEncoder.encode(Email, "UTF-8");

        data += "&" + URLEncoder.encode("user", "UTF-8")
                + "=" + URLEncoder.encode(Login, "UTF-8");

        data += "&" + URLEncoder.encode("pass", "UTF-8")
                + "=" + URLEncoder.encode(Pass, "UTF-8");

        data += "&" + URLEncoder.encode("username", "UTF-8")
                + "=" + URLEncoder.encode(Username, "UTF-8");

        data += "&" + URLEncoder.encode("password", "UTF-8")
                + "=" + URLEncoder.encode(Password, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        //Log.d(LOG, "GetText2");

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("http://android.trainingdata.org/login.php");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Show response on activity
        //content.setText( text  );

    }

    // function get json from url
    // by making HTTP POST or GET mehtod
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String makeHttpRequest(URL url, String method, String data) {
        //Log.d(LOG, "makeHttpRequest0> ");
        String reply = "init";
        try {
            HttpURLConnection urlConnection = null;
            if (Objects.equals(method, "POST")) {

                try {

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setUseCaches(false);
                    urlConnection.setConnectTimeout(1000);
                    urlConnection.setReadTimeout(1000);
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.connect();

//                    String data = URLEncoder.encode("username", "UTF-8")
//                            + "=" + URLEncoder.encode("rossumg", "UTF-8");
//
//                    data += "&" + URLEncoder.encode("password", "UTF-8") + "="
//                            + URLEncoder.encode("sinnet", "UTF-8");
                    OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                    out.write(data);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (method == "GET") {
                // request method is GET
                //DefaultHttpClient httpClient = new DefaultHttpClient();
                //String paramString = URLEncodedUtils.format(params, "utf-8");
                // rewrite changed url, url += "?" + paramString;
                // rewrite changed url, HttpGet httpGet = new HttpGet(url);

//                HttpResponse httpResponse = httpClient.execute(httpGet);
//                HttpEntity httpEntity = httpResponse.getEntity();
//                is = httpEntity.getContent();
            }

            int HttpResult = urlConnection.getResponseCode();
            StringBuilder sb = new StringBuilder((100));
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                reply = sb.toString();
            } // if
        } //try
        catch (IOException e) {
            //Log.d(LOG, "makeHttpRequest2> ");
            e.printStackTrace();
        } catch (Exception e) {
            //Log.d(LOG, "makeHttpRequest3> ");
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        //Log.d(LOG, "makeHttpRequest4> ");
        return (reply);
    }
}