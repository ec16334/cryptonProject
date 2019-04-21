package com.dheia.finalyear.util;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyJsonObjectRequest extends JsonObjectRequest {
    public MyJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public MyJsonObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<>();
        }
        Charset UTF8_CHARSET = Charset.forName("UTF-8");
        String s = new String(getBody(), UTF8_CHARSET);

        String s1 = null;
        try {
            s1 = new JSONObject(s).toString();
            s1=s1.replaceAll(":",": ").replaceAll(",",", ");
            System.out.println("\n\n>>>>>>>INDENTED S1\n"+s1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        String sign = Util.sign(Cred.API_SECRET, s);
        String sign = Util.sign(Cred.API_SECRET, s1).toLowerCase();


        System.out.println("\n\n"+s);
        System.out.println("\n\n>>>>>>S1 "+s1);
        System.out.println("\n\n>>>>>>SIGN "+sign);

//        sign = "e194c7c34ccdc0d7a6b65d4027ee4713826e36b0b1feb2aa727f847cc662a960a5428b9c7bbab33322069385e363937c863c0f8ff040abd8f35b20d8874f8861".toLowerCase();

        headers.put("Content-type", "application/json");
        headers.put("api-key", Cred.API_KEY);
        headers.put("sign", sign);
        return headers;
    }




}
