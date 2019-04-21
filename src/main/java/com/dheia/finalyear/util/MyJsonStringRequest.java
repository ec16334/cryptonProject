package com.dheia.finalyear.util;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyJsonStringRequest extends StringRequest {

    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private JSONObject jsonObject;

    public MyJsonStringRequest(int method, String url, JSONObject jsonRequest, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.jsonObject = jsonRequest;
    }

    public MyJsonStringRequest(String url, JSONObject jsonRequest, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        this.jsonObject = jsonRequest;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return jsonObject.toString().replaceAll(":", ": ").replaceAll(",", ", ").getBytes();
    }

    ;

    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<>();
        }

        String s = new String(getBody(), UTF8_CHARSET);
        String sign = Util.sign(Cred.API_SECRET, s).toLowerCase();

        headers.put("Content-type", "application/json");
        headers.put("api-key", Cred.API_KEY);
        headers.put("sign", sign);
        return headers;
    }


}
