package com.example.wenhaibo.vister.match;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GestureDetectorCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.recorder.api.LiveSession;
import com.baidu.recorder.api.LiveSessionHW;
import com.baidu.recorder.api.LiveSessionSW;
import com.baidu.recorder.api.SessionStateListener;
import com.example.wenhaibo.olympic.R;

public class MatchActivity extends Activity
        implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private static final String TAG = "MatchActivity";
    private LiveSession mLiveSession = null;
    private Button mRecorderButton = null;
    private ProgressBar mLoadingAnimation = null;
    private View mFocusIcon = null;

    private boolean isSessionReady = false;
    private boolean isSessionStarted = false;
    private boolean isConnecting = false;
    private boolean needRestartAfterStopped = false;

    private static final int UI_EVENT_RECORDER_CONNECTING = 0;
    private static final int UI_EVENT_RECORDER_STARTED = 1;
    private static final int UI_EVENT_RECORDER_STOPPED = 2;
    private static final int UI_EVENT_SESSION_PREPARED = 3;
    private static final int UI_EVENT_HIDE_FOCUS_ICON = 4;
    private static final int UI_EVENT_RESTART_STREAMING = 5;
    private static final int UI_EVENT_RECONNECT_SERVER = 6;
    private static final int UI_EVENT_STOP_STREAMING = 7;
    private static final int UI_EVENT_SHOW_TOAST_MESSAGE = 8;
    private static final int UI_EVENT_RESIZE_CAMERA_PREVIEW = 9;
    private static final int TEST_EVENT_SHOW_UPLOAD_BANDWIDTH = 10;

    private Handler mUIEventHandler = null;
    private SurfaceView mCameraView = null;
    private Button mFlashStateButton = null;
    private Button mCameraStateButton = null;
    private SessionStateListener mStateListener = null;
    private GestureDetectorCompat mDetector = null;
    private SurfaceHolder mHolder = null;
    private int mCurrentCamera = -1;
    private boolean isFlashOn = false;

    private int mVideoWidth = 1280;
    private int mVideoHeight = 720;
    private int mFrameRate = 15;
    private int mBitrate = 2048000;
    private String mStreamingUrl = "rtmp://push.bcelive.com/live/XXXXXXXXXXXXX"; // TODO:: Replace it with your streaming url.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        win.requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_match);

        TextView tv=(TextView)findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MatchActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });

        initUIElements();

        mCurrentCamera = Camera.CameraInfo.CAMERA_FACING_BACK;
        isFlashOn = false;
        initUIEventHandler();
        initStateListener();
        initRTMPSession(mCameraView.getHolder());

        mDetector = new GestureDetectorCompat(this, this);
        mDetector.setOnDoubleTapListener(this);
    }

    private void initUIElements() {
        mLoadingAnimation  = (ProgressBar) findViewById(R.id.progressBar);
        mRecorderButton = (Button) findViewById(R.id.connect);
        mRecorderButton.setEnabled(false);
        mFocusIcon = (ImageView) findViewById(R.id.iv_focus);
        mCameraView = (SurfaceView) findViewById(R.id.camera);
        mFlashStateButton = (Button) findViewById(R.id.flash_ctl);
        mCameraStateButton = (Button) findViewById(R.id.camera_ctl);

    }

    private void initUIEventHandler() {
        mUIEventHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UI_EVENT_RECORDER_CONNECTING:
                        isConnecting = true;
                        mRecorderButton.setBackgroundResource(R.drawable.block);
                        mRecorderButton.setEnabled(false);
                        break;
                    case UI_EVENT_RECORDER_STARTED:
                        Log.i(TAG, "Starting Streaming succeeded!");
                        isSessionStarted = true;
                        needRestartAfterStopped = false;
                        isConnecting = false;
                        mRecorderButton.setBackgroundResource(R.drawable.to_stop);
                        mRecorderButton.setEnabled(true);
                        break;
                    case UI_EVENT_RECORDER_STOPPED:
                        Log.i(TAG, "Stopping Streaming succeeded!");
                        isSessionStarted = false;
                        needRestartAfterStopped = false;
                        isConnecting = false;
                        mRecorderButton.setBackgroundResource(R.drawable.to_start);
                        mRecorderButton.setEnabled(true);
                        break;
                    case UI_EVENT_SESSION_PREPARED:
                        isSessionReady = true;
                        mLoadingAnimation.setVisibility(View.GONE);
                        mRecorderButton.setVisibility(View.VISIBLE);
                        mRecorderButton.setEnabled(true);
                        break;
                    case UI_EVENT_HIDE_FOCUS_ICON:
                        mFocusIcon.setVisibility(View.GONE);
                        break;
                    case UI_EVENT_RECONNECT_SERVER:
                        Log.i(TAG, "Reconnecting to server...");
                        if (isSessionReady) {
                            mLiveSession.startRtmpSession(mStreamingUrl);
                        }
                        mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_CONNECTING);
                        break;
                    case UI_EVENT_STOP_STREAMING:
                        if (!isConnecting) {
                            Log.i(TAG, "Stopping current session...");
                            if (isSessionReady) {
                                mLiveSession.stopRtmpSession();
                            }
                            mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_STOPPED);
                        }
                        break;
                    case UI_EVENT_RESTART_STREAMING:
                        if (!isConnecting) {
                            Log.i(TAG, "Restarting session...");
                            isConnecting = true;
                            needRestartAfterStopped = true;
                            if (isSessionReady) {
                                mLiveSession.stopRtmpSession();
                            }
                            mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_CONNECTING);
                        }
                        break;
                    case UI_EVENT_SHOW_TOAST_MESSAGE:
                        String text = (String) msg.obj;
                        Toast.makeText(MatchActivity.this, text, Toast.LENGTH_LONG).show();
                        break;
                    case UI_EVENT_RESIZE_CAMERA_PREVIEW:
                        String hint = String.format("注意：当前摄像头不支持您所选择的分辨率\\n实际分辨率为%dx%d",
                                mVideoWidth, mVideoHeight);
                        Toast.makeText(MatchActivity.this, hint, Toast.LENGTH_LONG).show();
                        fitPreviewToParentByResolution(mCameraView.getHolder(), mVideoWidth, mVideoHeight);
                        break;
                    case TEST_EVENT_SHOW_UPLOAD_BANDWIDTH:
                        Log.d(TAG, "Current upload bandwidth is "
                                + mLiveSession.getCurrentUploadBandwidthKbps() + " KBps.");
                        mUIEventHandler.sendEmptyMessageDelayed(TEST_EVENT_SHOW_UPLOAD_BANDWIDTH, 2000);
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };
//        mUIEventHandler.sendEmptyMessageDelayed(TEST_EVENT_SHOW_UPLOAD_BANDWIDTH, 2000);
    }

    private void initStateListener() {
        mStateListener = new SessionStateListener() {
            @Override
            public void onSessionPrepared(int code) {
                if (code == SessionStateListener.RESULT_CODE_OF_OPERATION_SUCCEEDED) {
                    if (mUIEventHandler != null) {
                        mUIEventHandler.sendEmptyMessage(UI_EVENT_SESSION_PREPARED);
                    }
                    int realWidth = mLiveSession.getAdaptedVideoWidth();
                    int realHeight = mLiveSession.getAdaptedVideoHeight();
                    if (realHeight != mVideoHeight || realWidth != mVideoWidth) {
                        mVideoHeight = realHeight;
                        mVideoWidth = realWidth;
                        mUIEventHandler.sendEmptyMessage(UI_EVENT_RESIZE_CAMERA_PREVIEW);
                    }
                }
            }

            @Override
            public void onSessionStarted(int code) {
                if (code == SessionStateListener.RESULT_CODE_OF_OPERATION_SUCCEEDED) {
                    if (mUIEventHandler != null) {
                        mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_STARTED);
                    }
                    mLiveSession.enableDefaultBeautyEffect(true);
                } else {
                    Log.e(TAG, "Starting Streaming failed!");
                }
            }

            @Override
            public void onSessionStopped(int code) {
                if (code == SessionStateListener.RESULT_CODE_OF_OPERATION_SUCCEEDED) {
                    if (mUIEventHandler != null) {
                        if (needRestartAfterStopped && isSessionReady) {
                            mLiveSession.startRtmpSession(mStreamingUrl);
                        } else {
                            mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_STOPPED);
                        }
                    }
                    mLiveSession.enableDefaultBeautyEffect(false);
                } else {
                    Log.e(TAG, "Stopping Streaming failed!");
                }
            }

            @Override
            public void onSessionError(int code) {
                switch (code) {
                    case SessionStateListener.ERROR_CODE_OF_OPEN_MIC_FAILED:
                        Log.e(TAG, "Error occurred while opening MIC!");
                        onOpenDeviceFailed();
                        break;
                    case SessionStateListener.ERROR_CODE_OF_OPEN_CAMERA_FAILED:
                        Log.e(TAG, "Error occurred while opening Camera!");
                        onOpenDeviceFailed();
                        break;
                    case SessionStateListener.ERROR_CODE_OF_PREPARE_SESSION_FAILED:
                        Log.e(TAG, "Error occurred while preparing recorder!");
                        onPrepareFailed();
                        break;
                    case SessionStateListener.ERROR_CODE_OF_CONNECT_TO_SERVER_FAILED:
                        Log.e(TAG, "Error occurred while connecting to server!");
                        if (mUIEventHandler != null) {
                            Message msg = mUIEventHandler.obtainMessage(UI_EVENT_SHOW_TOAST_MESSAGE);
                            msg.obj = "连接推流服务器失败，正在重试！";
                            mUIEventHandler.sendMessage(msg);
//                            mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_STOPPED);
                            mUIEventHandler.sendEmptyMessageDelayed(UI_EVENT_RECONNECT_SERVER, 2000);
                        }
                        break;
                    case SessionStateListener.ERROR_CODE_OF_DISCONNECT_FROM_SERVER_FAILED:
                        Log.e(TAG, "Error occurred while disconnecting from server!");
                        isConnecting = false;
                        // Although we can not stop session successfully, we still need to take it as stopped
                        if (mUIEventHandler != null) {
                            mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_STOPPED);
                        }
                        break;
                    default:
                        onStreamingError(code);
                        break;
                }
            }
        };
    }

    private void onOpenDeviceFailed() {
        if (mUIEventHandler != null) {
            Message msg = mUIEventHandler.obtainMessage(UI_EVENT_SHOW_TOAST_MESSAGE);
            msg.obj = "摄像头或MIC打开失败！请确认您已开启相关硬件使用权限！";
            mUIEventHandler.sendMessage(msg);
        }
//        StreamingActivity.this.finish();
    }

    private void onPrepareFailed() {
        isSessionReady = false;
    }

    int mWeakConnectionHintCount = 0;

    private void onStreamingError(int errno) {
        Message msg = mUIEventHandler.obtainMessage(UI_EVENT_SHOW_TOAST_MESSAGE);
        switch (errno) {
            case SessionStateListener.ERROR_CODE_OF_PACKET_REFUSED_BY_SERVER:
            case SessionStateListener.ERROR_CODE_OF_SERVER_INTERNAL_ERROR:
                msg.obj = "因服务器异常，当前直播已经中断！正在尝试重新推流...";
                if (mUIEventHandler != null) {
                    mUIEventHandler.sendMessage(msg);
                    mUIEventHandler.sendEmptyMessage(UI_EVENT_RESTART_STREAMING);
                }
                break;
            case SessionStateListener.ERROR_CODE_OF_WEAK_CONNECTION:
                Log.i(TAG, "Weak connection...");
                msg.obj = "当前网络不稳定，请检查网络信号！";
                mWeakConnectionHintCount++;
                if (mUIEventHandler != null) {
                    mUIEventHandler.sendMessage(msg);
                    if (mWeakConnectionHintCount >= 5) {
                        mWeakConnectionHintCount = 0;
                        mUIEventHandler.sendEmptyMessage(UI_EVENT_RESTART_STREAMING);
                    }
                }
                break;
            case SessionStateListener.ERROR_CODE_OF_CONNECTION_TIMEOUT:
                Log.i(TAG, "Timeout when streaming...");
                msg.obj = "连接超时，请检查当前网络是否畅通！我们正在努力重连...";
                if (mUIEventHandler != null) {
                    mUIEventHandler.sendMessage(msg);
                    mUIEventHandler.sendEmptyMessage(UI_EVENT_RESTART_STREAMING);
                }
                break;
            default:
                Log.i(TAG, "Unknown error when streaming...");
                msg.obj = "未知错误，当前直播已经中断！正在重试！";
                mUIEventHandler.sendMessage(msg);
                if (mUIEventHandler != null) {
                    mUIEventHandler.sendMessage(msg);
                    mUIEventHandler.sendEmptyMessageDelayed(UI_EVENT_RESTART_STREAMING, 1000);
                }
                break;
        }
    }

    private void initRTMPSession(SurfaceHolder sh) {
        Log.d(TAG, "Calling initRTMPSession...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
            mLiveSession = new LiveSessionHW(this, mVideoWidth, mVideoHeight, mFrameRate, mBitrate, mCurrentCamera);
        else
            mLiveSession = new LiveSessionSW(this, mVideoWidth, mVideoHeight, mFrameRate, mBitrate, mCurrentCamera);
        mLiveSession.setStateListener(mStateListener);
        mLiveSession.bindPreviewDisplay(sh);
        mLiveSession.prepareSessionAsync();
    }

    public void onClickQuit(View v) {
        if (isSessionStarted) {
            Toast.makeText(this, "直播过程中不能返回，请先停止直播！", Toast.LENGTH_SHORT).show();
        } else {
            this.finish();
        }
    }

    public void onClickSwitchFlash(View v) {
        mCameraStateButton.setEnabled(false);
        if (mCurrentCamera == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mLiveSession.toggleFlash(!isFlashOn);
            isFlashOn = !isFlashOn;
            if (isFlashOn) {
                mFlashStateButton.setBackgroundResource(R.drawable.flash_on);
            } else {
                mFlashStateButton.setBackgroundResource(R.drawable.flash_off);
            }
        } else {
            Toast.makeText(this, "抱歉！前置摄像头不支持切换闪光灯！", Toast.LENGTH_SHORT).show();
        }
        mCameraStateButton.setEnabled(true);
    }

    public void onClickSwitchCamera(View v) {
        mCameraStateButton.setEnabled(false);
        if (mLiveSession.canSwitchCamera()) {
            if (mCurrentCamera == Camera.CameraInfo.CAMERA_FACING_BACK) {
                mCurrentCamera = Camera.CameraInfo.CAMERA_FACING_FRONT;
                mLiveSession.switchCamera(mCurrentCamera);
            } else {
                mCurrentCamera = Camera.CameraInfo.CAMERA_FACING_BACK;
                mLiveSession.switchCamera(mCurrentCamera);
            }
            isFlashOn = false;
            mFlashStateButton.setBackgroundResource(R.drawable.flash_off);
        } else {
            Toast.makeText(this, "抱歉！该分辨率下不支持切换摄像头！", Toast.LENGTH_SHORT).show();
        }
        mCameraStateButton.setEnabled(true);
    }

    public void onClickStreamingButton(View v) {
        if (!isSessionReady) {
            return;
        }
        if (!isSessionStarted && !TextUtils.isEmpty(mStreamingUrl)) {
            if (mLiveSession.startRtmpSession(mStreamingUrl)) {
                mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_CONNECTING);
            }
        } else {
            if (mLiveSession.stopRtmpSession()) {
                mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_CONNECTING);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isSessionStarted) {
            Toast.makeText(this, "直播过程中不能返回，请先停止直播！", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

    @Override
    public void onStart() {
        Log.i(TAG, "===========> onStop()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "===========> onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "===========> onDestroy()");
        mUIEventHandler.removeCallbacksAndMessages(null);
        if (isSessionStarted) {
            mLiveSession.stopRtmpSession();
            isSessionStarted = false;
        }
        if (isSessionReady) {
            mLiveSession.destroyRtmpSession();
            mLiveSession = null;
            mStateListener = null;
            mUIEventHandler = null;
            isSessionReady = false;
        }
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent arg0) {
    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent arg0) {
        if (mLiveSession != null && !mLiveSession.zoomInCamera()) {
            Log.e(TAG, "Zooming camera failed!");
            mLiveSession.cancelZoomCamera();
        }
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent arg0) {
        if (mLiveSession != null) {
            mLiveSession.focusToPosition((int) arg0.getX(), (int) arg0.getY());
            mFocusIcon.setX(arg0.getX() - mFocusIcon.getWidth() / 2);
            mFocusIcon.setY(arg0.getY() - mFocusIcon.getHeight() / 2);
            mFocusIcon.setVisibility(View.VISIBLE);
            mUIEventHandler.sendEmptyMessageDelayed(UI_EVENT_HIDE_FOCUS_ICON, 1000);
        }
        return true;
    }

    private void fitPreviewToParentByResolution(SurfaceHolder holder, int width, int height) {
        // Adjust the size of SurfaceView dynamically
        int screenHeight = getWindow().getDecorView().getRootView().getHeight();
        int screenWidth = getWindow().getDecorView().getRootView().getWidth();

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT) { // If portrait, we should swap width and height
            width = width ^ height;
            height = width ^ height;
            width = width ^ height;
        }

        int adjustedVideoHeight = screenHeight;
        int adjustedVideoWidth = screenWidth;
        if (width * screenHeight > height * screenWidth) { // means width/height > screenWidth/screenHeight
            // Fit width
            adjustedVideoHeight = height * screenWidth / width;
            adjustedVideoWidth = screenWidth;
        } else {
            // Fit height
            adjustedVideoHeight = screenHeight;
            adjustedVideoWidth = width * screenHeight / height;
        }
        holder.setFixedSize(adjustedVideoWidth, adjustedVideoHeight);
    }

}