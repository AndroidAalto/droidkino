package org.androidaalto.droidkino.imdb;

import org.androidaalto.droidkino.beans.ImdbInfo;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ImdbApiClient {


    private static final int CONNECTION_TIMEOUT = 30 * 1000; // ms
    
    private static String BASE_URL = "http://www.imdbapi.com/";
    private static DefaultHttpClient httpClient;
    
    public ImdbApiClient() {
        ensureHttpClient();
    }
    
    public ImdbInfo fetchInfo(String title, String year) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("t", URLEncoder.encode(title)));
        params.add(new BasicNameValuePair("y", year));
        
        HttpResponse response = executeGetRequest(BASE_URL, params);
        String responseString = null;
        
        if (ok(response)) {
          InputStream is = null;
          is = getResponseStream(response);
          responseString = convertStreamToString(is);
        }
        
        JSONObject json = null;
        ImdbInfo imbdInfo = new ImdbInfo();
        try {
            json = new JSONObject(responseString);
            if (json.getString("Response").equalsIgnoreCase("True")) {
                imbdInfo.setRating(new Float(json.getString("Rating")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return imbdInfo;
    }


    protected HttpResponse executeGetRequest(String url, ArrayList<NameValuePair> params) {
      StringBuilder uriBuilder = new StringBuilder(url);

      for (NameValuePair param : params) {
        uriBuilder.append(params.indexOf(param) == 0 ? "?" : "&");
        uriBuilder.append(param.getName() + "=" + param.getValue());
      }
      HttpGet request = new HttpGet(uriBuilder.toString());
      return execute(request);
    }

    protected HttpResponse execute(HttpRequestBase request) {
      try {
        HttpResponse response = httpClient.execute(request);
        return response;
      } catch (final IOException e) {
        return null;
      }
    }

    protected boolean ok(HttpResponse response) {
      return response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }

    protected InputStream getResponseStream(HttpResponse response) {
      try {
        return response.getEntity().getContent();
      } catch (IOException e) {
        return null;
      }
    }
    

    private static String convertStreamToString(InputStream is) {

      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();

      String line = null;
      try {
        while ((line = reader.readLine()) != null) {
          sb.append(line + "\n");
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return sb.toString();
    }
    

    protected static void ensureHttpClient() {
      if (httpClient == null) {
        httpClient = new DefaultHttpClient();
        final HttpParams params = httpClient.getParams();

        HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, CONNECTION_TIMEOUT);
        ConnManagerParams.setTimeout(params, CONNECTION_TIMEOUT);
      }
    }
}
