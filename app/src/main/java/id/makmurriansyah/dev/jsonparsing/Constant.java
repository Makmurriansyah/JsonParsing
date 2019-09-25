package id.makmurriansyah.dev.jsonparsing;

import android.content.*;
import com.android.volley.*;
import com.android.volley.toolbox.*;

public class Constant
{
    private static Constant mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private Constant(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized Constant getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Constant(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}