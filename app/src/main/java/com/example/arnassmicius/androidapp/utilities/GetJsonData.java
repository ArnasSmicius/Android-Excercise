package com.example.arnassmicius.androidapp.utilities;

import android.os.AsyncTask;
import android.util.Log;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Request;

import org.json.JSONObject;

/**
 * Created by arnas on 18.2.10.
 */

/**
 * This AsyncTask class is for downloading Json object from the internet.
 * This class works on a separate Thread, so you have to implement OnJsonObjectDownloadComplete interface to get
 * Json object when download is finished.
 */
public class GetJsonData extends AsyncTask<String, Void, JSONObject> {
    private static final String TAG = "GetJsonData";
    private OnJsonObjectDownloadComplete onDownloadCompleteCallback;

    public interface OnJsonObjectDownloadComplete {
        void onJsonObjectDownloadCompleted(JSONObject jsonObject);
    }

    public GetJsonData(OnJsonObjectDownloadComplete callback) {
        onDownloadCompleteCallback = callback;
    }

    @Override
    protected JSONObject doInBackground(String... url) {
        Log.d(TAG, "doInBackground: Started download JSON");
        Request request;
        try {
            request = Bridge.get(url[0]).request();
            return request.response().asJsonObject();
        } catch (BridgeException e) {
            Log.e(TAG, "doInBackground: got an error while downloading a request");
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        onDownloadCompleteCallback.onJsonObjectDownloadCompleted(jsonObject);
    }
}
