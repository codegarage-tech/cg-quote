package com.reversecoder.gcm.util;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONObject;

public class HttpRequestManager {
    public HttpRequestManager() {
    }

    public static HttpRequestManager.HttpResponse doGetRequest(String requestURL) {
        HttpRequestManager.HttpResponse response = null;
        HttpURLConnection httpConn = null;

        HttpRequestManager.HttpResponse var4;
        try {
            URL url = new URL(requestURL);
            httpConn = (HttpURLConnection)url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setReadTimeout(15000);
            httpConn.setConnectTimeout(15000);
            httpConn.setRequestProperty("Content-type", "application/json");
            httpConn.setRequestProperty("Accept", "application/json");
            httpConn.setDoInput(true);
            httpConn.setDoOutput(false);
            response = readStream(httpConn);
            var4 = response;
            return var4;
        } catch (Exception var8) {
            var4 = new HttpRequestManager.HttpResponse(var8);
        } finally {
            disconnectHttpURLConnection(httpConn);
        }

        return var4;
    }

    public static HttpRequestManager.HttpResponse doRestPostRequest(String URL, JSONObject param, ArrayList<HttpRequestManager.HttpParameter> header) {
        URL url = null;
        HttpURLConnection urlConn = null;
        HttpRequestManager.HttpResponse response = null;

        HttpRequestManager.HttpResponse var7;
        try {
            url = new URL(URL);
            urlConn = (HttpURLConnection)url.openConnection();
            urlConn.setReadTimeout(15000);
            urlConn.setConnectTimeout(15000);
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            setHeader(urlConn, header);
            urlConn.connect();
            if(writeStream(urlConn, param)) {
                response = readStream(urlConn);
                Log.d("Post response:", response.getResult().toString());
            }

            HttpRequestManager.HttpResponse var6 = response;
            return var6;
        } catch (Exception var11) {
            var7 = new HttpRequestManager.HttpResponse(var11);
        } finally {
            disconnectHttpURLConnection(urlConn);
        }

        return var7;
    }

    private static void setHeader(HttpURLConnection urlConnection, ArrayList<HttpRequestManager.HttpParameter> header) {
        if(urlConnection != null) {
            if(header != null && header.size() > 0) {
                for(int i = 0; i < header.size(); ++i) {
                    HttpRequestManager.HttpParameter mHeader = (HttpRequestManager.HttpParameter)header.get(i);
                    urlConnection.setRequestProperty(mHeader.getKey().toString(), mHeader.getValue().toString());
                }
            } else {
                urlConnection.setRequestProperty("Content-type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
            }
        }

    }

    public static boolean writeStream(HttpURLConnection httpURLConnection, JSONObject param) {
        if(httpURLConnection != null && param != null) {
            DataOutputStream printout = null;

            boolean var4;
            try {
                String stringParam = param.toString();
                Log.d("Parameter data:", stringParam);
                byte[] data = stringParam.getBytes("UTF-8");
                printout = new DataOutputStream(httpURLConnection.getOutputStream());
                printout.write(data);
                boolean var5 = true;
                return var5;
            } catch (Exception var15) {
                var4 = false;
            } finally {
                try {
                    if(printout != null) {
                        printout.flush();
                        printout.close();
                    }
                } catch (Exception var14) {
                    ;
                }

            }

            return var4;
        } else {
            return false;
        }
    }

    public static void disconnectHttpURLConnection(HttpURLConnection httpConn) {
        if(httpConn != null) {
            httpConn.disconnect();
        }

    }

    public static HttpRequestManager.HttpResponse readStream(HttpURLConnection httpURLConnection) {
        InputStream inputStream = null;
        String line = null;
        String result = null;
        BufferedReader reader = null;

        HttpRequestManager.HttpResponse var6;
        try {
            if(httpURLConnection == null || httpURLConnection.getResponseCode() != 200) {
                HttpRequestManager.HttpResponse var19 = new HttpRequestManager.HttpResponse(new Exception(httpURLConnection.getResponseMessage()));
                return var19;
            }

            inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();

            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            result = sb.toString();
            var6 = new HttpRequestManager.HttpResponse(result);
            return var6;
        } catch (Exception var17) {
            var6 = new HttpRequestManager.HttpResponse(var17);
        } finally {
            try {
                reader.close();
                inputStream.close();
            } catch (Exception var16) {
                ;
            }

        }

        return var6;
    }

    public static class HttpParameter<T> {
        private T mKey = null;
        private T mValue = null;
        private JSONObject mJSONParam = null;
        private ArrayList<HttpRequestManager.HttpParameter> mArryListParam = null;
        private ArrayList<HttpRequestManager.HttpParameter> mHeader = null;

        private HttpParameter(T key, T value) {
            this.mKey = key;
            this.mValue = value;
        }

        public static HttpRequestManager.HttpParameter getInstance() {
            return new HttpRequestManager.HttpParameter();
        }

        private HttpParameter() {
            this.mArryListParam = new ArrayList();
            this.mHeader = new ArrayList();
        }

        public T getKey() {
            return this.mKey;
        }

        public HttpRequestManager.HttpParameter setKey(T mKey) {
            this.mKey = mKey;
            return getInstance();
        }

        public T getValue() {
            return this.mValue;
        }

        public HttpRequestManager.HttpParameter setValue(T mValue) {
            this.mValue = mValue;
            return getInstance();
        }

        public HttpRequestManager.HttpParameter addJSONParam(T key, T value) {
            try {
                if(this.mJSONParam == null) {
                    this.mJSONParam = new JSONObject();
                }

                this.mJSONParam.put(key.toString(), value.toString());
                return this;
            } catch (Exception var4) {
                return null;
            }
        }

        public HttpRequestManager.HttpParameter addArrayListParam(T key, T value) {
            try {
                this.mArryListParam.add(new HttpRequestManager.HttpParameter(key, value));
                return this;
            } catch (Exception var4) {
                return null;
            }
        }

        public ArrayList<HttpRequestManager.HttpParameter> getArrayListParam() {
            return this.mArryListParam != null && this.mArryListParam.size() > 0?this.mArryListParam:null;
        }

        public JSONObject getJSONParam() {
            return this.mJSONParam != null?this.mJSONParam:null;
        }

        public HttpRequestManager.HttpParameter addHeader(T key, T value) {
            try {
                this.mHeader.add(new HttpRequestManager.HttpParameter(key, value));
                return this;
            } catch (Exception var4) {
                return null;
            }
        }

        public ArrayList<HttpRequestManager.HttpParameter> getHeader() {
            return this.mHeader != null && this.mArryListParam.size() > 0?this.mHeader:null;
        }
    }

    public static class HttpResponse<T> {
        private Long[] mData = null;
        private Exception mError = null;
        private T mValue = null;
        private boolean isSuccess = false;

        public HttpResponse(Long... data) {
            this.mData = data;
            this.isSuccess = true;
        }

        public HttpResponse(T value) {
            this.mValue = value;
            this.isSuccess = true;
        }

        public HttpResponse(Exception error) {
            this.mError = error;
            this.isSuccess = false;
        }

        public Exception getError() {
            return this.mError;
        }

        public T getResult() {
            return this.mValue;
        }

        public boolean isSuccess() {
            return this.isSuccess;
        }

        public Long[] getData() {
            return this.mData;
        }
    }
}
