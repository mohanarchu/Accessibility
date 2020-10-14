package com.example.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MyAccessibilityService extends AccessibilityService {
    private final AccessibilityServiceInfo info = new AccessibilityServiceInfo();

    private static final String TAG = "MyAccessibilityService";
    private static final String TAGEVENTS = "TAGEVENTS";
    private String currntApplicationPackage = "";

    private WindowPositionController windowController;
    private WindowManager windowManager;
    private boolean showWindow = false;

    MyAccessibilityService myAccessibilityService;
    AccessibilityService.GestureResultCallback gestureDescription  = new AccessibilityService.GestureResultCallback() {
        @Override
        public void onCompleted(GestureDescription gestureDescription) {
            super.onCompleted(gestureDescription);
            Log.d(TAG, "gesture completed");
        }
        @Override
        public void onCancelled(GestureDescription gestureDescription) {
            super.onCancelled(gestureDescription);
            Log.d(TAG, "gesture cancelled");
        }
    };

    @Override
    public void onAccessibilityEvent(final AccessibilityEvent accessibilityEvent) {
        Log.d(TAG, "onAccessibilityEvent");
        myAccessibilityService = this;
        final String sourcePackageName = (String) accessibilityEvent.getPackageName();
//        currntApplicationPackage = sourcePackageName;
//        Log.d(TAG, "sourcePackageName:" + sourcePackageName);
//        Log.d(TAG, "parcelable:" + accessibilityEvent.getText().toString());
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        AccessibilityNodeInfo source = accessibilityEvent.getSource();
    /*    if (source == null) {
            Log.d("onAccessibilityEvent", "source was null for: " + source);
        } else {
            Log.d("test", "clickPerform:  "+ source.getText());
            source.refresh();
            for (int i = 0; i < source.getChildCount(); i++) {
                AccessibilityNodeInfo childNode = source.getChild(i);
//                Log.e("test", "clickPerform:  "+ childNode.getText());
            }
        }*/


        if (sourcePackageName.equals("com.amazon.rabbit")) {
           /* WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
            params.gravity = Gravity.BOTTOM | Gravity.CENTER;*/
            final AccessibilityNodeInfo node = accessibilityEvent.getSource();
            if (node != null)
            for (int i = 0; i < node.getChildCount(); i++) {
                AccessibilityNodeInfo childNode = source.getChild(i);
               if(childNode.getClassName().equals(FrameLayout.class.getName())) {
                    for (int j = 0; j < childNode.getChildCount(); j++) {
                        AccessibilityNodeInfo subChild = childNode.getChild(j);
                        if (subChild.getClassName().equals(FrameLayout.class.getName())) {
                            for (int j1 = 0; j1 < subChild.getChildCount(); j1++) {
                                AccessibilityNodeInfo subChild1 = subChild.getChild(j1);
                              for (int j2 = 0; j2 < subChild1.getChildCount(); j2++) {
                                   AccessibilityNodeInfo subChild2 = subChild1.getChild(j2);
                                  for (int j3 = 0; j3 < subChild2.getChildCount(); j3++) {
                                      AccessibilityNodeInfo subChild3 = subChild2.getChild(j3);
                                      if (subChild3.getClassName().equals("android.support.v7.widget.RecyclerView")) {
                                      for (int j4 = 0; j4 < subChild3.getChildCount(); j4++) {
                                              AccessibilityNodeInfo subChild4 = subChild3.getChild(j4);
                                          if (subChild4.getClassName().equals(LinearLayout.class.getName())) {
                                          for (int j5 = 0; j5 < subChild4.getChildCount(); j5++) {
                                                  AccessibilityNodeInfo subChild5 = subChild4.getChild(j5);
                                              if (subChild5.getClassName().equals(RelativeLayout.class.getName())) {
                                                  for (int j6 = 0; j6 < subChild5.getChildCount(); j6++) {
                                                      AccessibilityNodeInfo subChild6 = subChild5.getChild(j6);
                                                      if(subChild6.getClassName().equals(TextView.class.getName())) {
                                                          subChild4.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                                      }
                                                  }
                                                }
                                              }
                                          }
                                          if (subChild4.getClassName().equals(FrameLayout.class.getName())) {
                                              for (int j5 = 0; j5 < subChild4.getChildCount(); j5++) {
                                                  AccessibilityNodeInfo subChild5 = subChild4.getChild(j5);
                                                  if (subChild5.getClassName().equals(FrameLayout.class.getName())) {
                                                      for (int j6 = 0; j6 < subChild5.getChildCount(); j6++) {
                                                          AccessibilityNodeInfo subChild6 = subChild5.getChild(j6);

                                                      }
                                                  }

                                              }

                                          }

//                                              Log.i("test", "clickPerform:  "+subChild4.getChildCount()+"  "+ subChild4);
                                          }

                                      }
//                                      Log.i("test", "clickPerform:  "+subChild3.getChildCount()+"  "+ subChild3.getClassName());
                                  }
                              }


//                                    Log.i("test", "clickPerform:  "+ subChild2);

                            }
                        }
                    }

                }

            }
            Log.i("TAG","The values"+node);

//            myAccessibilityService .dispatchGesture(createClick(params.x,params.y), gestureDescription, null);
            final Handler handler = new Handler();
             Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if(node != null) {

                        if (node.getText() == null)
                            return;
                        if (node.getText().toString().toLowerCase().equals("refresh")) {
                            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                    handler.postDelayed(this, 10000); // reschedule the handler
                }
            };
           handler.postDelayed(runnable, 20000); // 10 mins int.
        }

        /*  if (accessibilityEvent.getEventType() == AccessibilityEvent.CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION) {
            Log.d(TAGEVENTS, "CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Log.d(TAGEVENTS, "TYPE_WINDOW_STATE_CHANGED");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.CONTENT_CHANGE_TYPE_SUBTREE) {
            Log.d(TAGEVENTS, "CONTENT_CHANGE_TYPE_SUBTREE");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.CONTENT_CHANGE_TYPE_TEXT) {
            Log.d(TAGEVENTS, "CONTENT_CHANGE_TYPE_TEXT");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.INVALID_POSITION) {
            Log.d(TAGEVENTS, "INVALID_POSITION");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.CONTENT_CHANGE_TYPE_UNDEFINED) {
            Log.d(TAGEVENTS, "CONTENT_CHANGE_TYPE_UNDEFINED");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_ANNOUNCEMENT) {
            Log.d(TAGEVENTS, "TYPE_ANNOUNCEMENT");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT) {
            Log.d(TAGEVENTS, "TYPE_ASSIST_READING_CONTEXT");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_GESTURE_DETECTION_END) {
            Log.d(TAGEVENTS, "TYPE_GESTURE_DETECTION_END");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            Log.d(TAGEVENTS, "TYPE_VIEW_CLICKED");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START) {
            Log.d(TAGEVENTS, "TYPE_TOUCH_EXPLORATION_GESTURE_START");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_GESTURE_DETECTION_START) {
            Log.d(TAGEVENTS, "TYPE_GESTURE_DETECTION_START");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED) {
            Log.d(TAGEVENTS, "TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
            Log.d(TAGEVENTS, "TYPE_VIEW_ACCESSIBILITY_FOCUSED");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOWS_CHANGED) {
            Log.d(TAGEVENTS, "TYPE_WINDOWS_CHANGED");
        }*/

        if (accessibilityEvent.getPackageName() == null || !(accessibilityEvent.getPackageName().equals("com.bsb.hike") || !(accessibilityEvent.getPackageName().equals("com.whatsapp") || accessibilityEvent.getPackageName().equals("com.facebook.orca") || accessibilityEvent.getPackageName().equals("com.twitter.android") || accessibilityEvent.getPackageName().equals("com.facebook.katana") || accessibilityEvent.getPackageName().equals("com.facebook.lite"))))
            showWindow = false;

        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            Log.d(TAGEVENTS, "TYPE_VIEW_TEXT_CHANGED");
            if (windowController == null)
                windowController = new WindowPositionController(windowManager, getApplicationContext());
            showWindow = true;
            windowController.notifyDatasetChanged(accessibilityEvent.getText().toString(), currntApplicationPackage);
        } else if(accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            Log.d(TAGEVENTS, "TYPE_WINDOW_STATE_CHANGED:"+accessibilityEvent.getContentDescription());

            if (accessibilityEvent.getPackageName().equals("com.whatsapp") && (accessibilityEvent.getContentDescription() == null || !accessibilityEvent.getContentDescription().equals("Type a message")))
                showWindow = false;
            if (accessibilityEvent.getPackageName().equals("com.facebook.katana") && (accessibilityEvent.getText().toString().equals("[What's on your mind?]") || accessibilityEvent.getText().toString().equals("[Search]")))
                showWindow = false;
            if (accessibilityEvent.getPackageName().equals("com.twitter.android") && (accessibilityEvent.getText().toString().equals("[What\u2019s happening?]") || accessibilityEvent.getText().toString().equals("[Search Twitter]")))
                showWindow = false;
            if (accessibilityEvent.getContentDescription()!=null && (accessibilityEvent.getContentDescription().toString().equals("Textbox in chat thread")))
                showWindow = true;

            //remove window when keyboard closed or user moved from chatting to other things
            if (windowController != null && !showWindow)
                windowController.onDestroy();
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onServiceConnected() {
        // Set the type of events that this service wants to listen to.
        //Others won't be passed to this service.
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.flags =  AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED |
                AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        info.notificationTimeout = 100;
        this.setServiceInfo(info);
    }

    /**
     * Check if Accessibility Service is enabled.
     *
     * @param mContext
     * @return <code>true</code> if Accessibility Service is ON, otherwise <code>false</code>
     */
    public static boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        //your package /   accesibility service path/class
        final String service = "com.accessibilityexample/com.accessibilityexample.Service.MyAccessibilityService";

        boolean accessibilityFound = false;
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILIY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();

                    Log.v(TAG, "-------------- > accessabilityService :: " + accessabilityService);
                    if (accessabilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILIY IS DISABLED***");
        }

        return accessibilityFound;
    }
    private static GestureDescription createClick(final float x, final float y) {
        final GestureDescription.Builder clickBuilder = new GestureDescription.Builder();
        // for a single tap a duration of 1 ms is enough
        Path clickPath = new Path();
        clickPath.moveTo(x, y);
        GestureDescription.StrokeDescription clickStroke =
                new GestureDescription.StrokeDescription(clickPath, 0, 1);
        clickBuilder.addStroke(clickStroke);
        return clickBuilder.build();
    }

}