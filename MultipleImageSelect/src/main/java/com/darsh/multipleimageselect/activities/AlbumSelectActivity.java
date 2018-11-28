package com.darsh.multipleimageselect.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.darsh.multipleimageselect.R;
import com.darsh.multipleimageselect.adapters.CustomAlbumSelectAdapter;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Album;
import com.darsh.multipleimageselect.utils.FileUtils;
import com.darsh.multipleimageselect.utils.PermissionsManager_;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Darshan on 4/14/2015.
 * <p>
 * 描述：选择相册目录
 */
public class AlbumSelectActivity extends HelperActivity {


    private static final String TAG = "AlbumSelectActivity";

    private ArrayList<Album> albums;

    private TextView errorDisplay;

    private ProgressBar progressBar;
    private GridView gridView;
    private CustomAlbumSelectAdapter adapter;

    private ActionBar actionBar;

    private ContentObserver observer;
    private Handler handler;
    private Thread thread;
    /**
     * 当当前应用调用系统相机时有一定的概率会把当前的Activity回收，所以当我们拍完照再退回到当前界面时就
     * 回出现avatarimg为空的现象，所以我们在在被系统意外回收时保存当前的avatarimg对象，通过onSavaInstanceState()
     * 这个方法，然后在onCreate拿出来，而这个就是保在cameraimg的key
     */
    public static final String ACCIDENT_KILL_AVATARIMG = "cameraimg";

    /**
     * 请求码，请求摄像头返回的
     */
    public static final int REQUEST_CODE_CAMERA = 3;
    protected File cameraImg;
    //需要申请的权限
    private final String[] permissions = new String[]{Manifest.permission.CAMERA};

    private final String[] projection = new String[]{
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA};
    private PermissionsManager_ mPermissionsManager;

    public static final String KEY_GO_TO_CAMERA_ACTION = "camera";
    private boolean mActionCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            cameraImg = (File) savedInstanceState.getSerializable(ACCIDENT_KILL_AVATARIMG);
        }
        setContentView(R.layout.activity_album_select);
        setView(findViewById(R.id.layout_album_select));

        initParams();

        initView();

        initData();


    }


    /**
     * 初始化参数
     */
    private void initParams() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        Constants.limit = intent.getIntExtra(Constants.INTENT_EXTRA_LIMIT, Constants.DEFAULT_LIMIT);
    }

    /**
     * 初始化view
     */
    private void initView() {
        errorDisplay = (TextView) findViewById(R.id.text_view_error);
        errorDisplay.setVisibility(View.INVISIBLE);

        //进度view
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_album_select);

        //网络view
        gridView = (GridView) findViewById(R.id.grid_view_album_select);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //如果点击的是第一个item跳到拍照
                    goToCamera();
                } else {
                    Intent intent = new Intent(AlbumSelectActivity.this, ImageSelectActivity.class);
                    intent.putExtra(Constants.INTENT_EXTRA_ALBUM, albums.get(position).name);
                    startActivityForResult(intent, Constants.REQUEST_CODE);
                }

            }


        });

        //返回按钮
        findViewById(R.id.iv_back).setOnClickListener(new MyOnClickListener());
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //权限申请
        mPermissionsManager = new PermissionsManager_(this);
        mPermissionsManager.setPermissions(permissions);
        mPermissionsManager.setPermissionCallback(new PermissionsManager_.OnPermissionsCallback() {
            @Override
            public void hasPermissions() {

                //跳到拍照页面
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraImg = new File(FileUtils.createDirToSDCard(Constants.IMG_DIR), Constants.getTempImgName());
                Uri fileUri = null;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    fileUri = Uri.fromFile(cameraImg);
                } else {
                    fileUri = FileProvider.getUriForFile(AlbumSelectActivity.this, "com.micropole.usdmall.fileprovider", cameraImg);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                }
                Log.d(TAG, "fileUri: " + fileUri.toString());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);

            }

            //
            @Override
            public void noPermissions() {
                mPermissionsManager.showOpenSettingDialog(AlbumSelectActivity.this);
            }
        });

        //是否为打开摄像头
        mActionCamera = getIntent().getBooleanExtra(KEY_GO_TO_CAMERA_ACTION, false);
        if (mActionCamera) {
            goToCamera();
        }
    }

    /**
     * 打开摄像头
     */
    private void goToCamera() {
        mPermissionsManager.requestPermission();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionsManager.resultPermissionsProcess(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("handler:" + handler);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constants.PERMISSION_GRANTED: {
                        loadAlbums();
                        break;
                    }

                    case Constants.FETCH_STARTED: {
                        progressBar.setVisibility(View.VISIBLE);
                        gridView.setVisibility(View.INVISIBLE);
                        break;
                    }

                    case Constants.FETCH_COMPLETED: {
                        if (adapter == null) {
                            adapter = new CustomAlbumSelectAdapter(getApplicationContext(), albums);
                            gridView.setAdapter(adapter);

                            progressBar.setVisibility(View.INVISIBLE);
                            gridView.setVisibility(View.VISIBLE);
                            orientationBasedUI(getResources().getConfiguration().orientation);

                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    }

                    case Constants.ERROR: {
                        progressBar.setVisibility(View.INVISIBLE);
                        errorDisplay.setVisibility(View.VISIBLE);
                        break;
                    }

                    default: {
                        super.handleMessage(msg);
                    }
                }
            }
        };
        observer = new ContentObserver(handler) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                loadAlbums();
            }
        };
        getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, observer);

        checkPermission();
    }

    @Override
    protected void onStop() {
        super.onStop();

        stopThread();

        getContentResolver().unregisterContentObserver(observer);
        observer = null;

        if (handler != null) {
            Log.d(TAG, "handler remove");
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(null);
        }
        albums = null;
        if (adapter != null) {
            adapter.releaseResources();
        }
        stopThread();
        thread = null;
        gridView.setOnItemClickListener(null);
        gridView = null;

        if (handler != null) {
            Log.d(TAG, "handler remove");
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientationBasedUI(newConfig.orientation);
    }

    private void orientationBasedUI(int orientation) {
        final WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        if (adapter != null) {
            int size = orientation == Configuration.ORIENTATION_PORTRAIT ? metrics.widthPixels / 2 : metrics.widthPixels / 4;
            adapter.setLayoutParams(size);
        }
        gridView.setNumColumns(orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 4);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != mPermissionsManager) {
            mPermissionsManager.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == Constants.REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            setResult(RESULT_OK, data);
            finish();
        } else if (requestCode == REQUEST_CODE_CAMERA) {
            Log.d(TAG, "拍照返回的图片大小：" + cameraImg.length());

            setIntent(cameraImg);
        }
    }

    private void setIntent(File cameraImg) {

        if (null == cameraImg || cameraImg.length() == 0) {
            if (mActionCamera) {
                finish();
            }
            return;
        }

        Intent intent = new Intent();
//        intent.putParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES, getSelected());
        ArrayList<String> strings = new ArrayList<>();
        strings.add(cameraImg.getAbsolutePath());
        intent.putStringArrayListExtra(Constants.INTENT_EXTRA_IMAGES, strings);
        intent.putExtra("FILE",cameraImg.getAbsolutePath());
        setResult(RESULT_OK, intent);
        finish();
    }
    public  Uri FileToUri(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            fileUri = Uri.fromFile(file);
        } else {
            fileUri = FileProvider.getUriForFile(context, "com.antiphon.lifetree.fileprovider", file);
        }
        return fileUri;
    }

    public  Uri FileToUri(Context context, String file) {
        return FileToUri(context, new File(file));
    }

    private void loadAlbums() {
        startThread(new AlbumLoaderRunnable());
    }

    private class AlbumLoaderRunnable implements Runnable {
        @Override
        public void run() {
            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            if (adapter == null) {
                sendMessage(Constants.FETCH_STARTED);
            }

            Cursor cursor = getApplicationContext().getContentResolver()
                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                            null, null, MediaStore.Images.Media.DATE_ADDED);
            if (cursor == null) {
                sendMessage(Constants.ERROR);
                return;
            }

            ArrayList<Album> temp = new ArrayList<>(cursor.getCount());
            HashSet<Long> albumSet = new HashSet<>();
            File file;
            if (cursor.moveToLast()) {
                do {
                    if (Thread.interrupted()) {
                        return;
                    }

                    long albumId = cursor.getLong(cursor.getColumnIndex(projection[0]));
                    String album = cursor.getString(cursor.getColumnIndex(projection[1]));
                    String image = cursor.getString(cursor.getColumnIndex(projection[2]));

                    if (!albumSet.contains(albumId)) {
                        /*
                        It may happen that some image file paths are still present in cache,
                        though image file does not exist. These last as long as media
                        scanner is not run again. To avoid get such image file paths, check
                        if image file exists.
                         */
                        file = new File(image);
                        if (file.exists() && !image.endsWith(".gif")) {
                            temp.add(new Album(album, image));
                            albumSet.add(albumId);
                        }
                    }

                } while (cursor.moveToPrevious());
            }
            cursor.close();

            if (albums == null) {
                albums = new ArrayList<>();
            }
            albums.clear();
            albums.add(new Album(getString(R.string.text_open_camera_), ""));
            albums.addAll(temp);

            sendMessage(Constants.FETCH_COMPLETED);
        }
    }

    private void startThread(Runnable runnable) {
        stopThread();
        thread = new Thread(runnable);
        thread.start();
    }

    private void stopThread() {
        if (thread == null || !thread.isAlive()) {
            return;
        }

        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(int what) {
        if (handler == null) {
            return;
        }

        Message message = handler.obtainMessage();
        message.what = what;
        message.sendToTarget();
    }

    @Override
    protected void permissionGranted() {
        Message message = handler.obtainMessage();
        message.what = Constants.PERMISSION_GRANTED;
        message.sendToTarget();
    }

    @Override
    protected void hideViews() {
        progressBar.setVisibility(View.INVISIBLE);
        gridView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ACCIDENT_KILL_AVATARIMG, cameraImg);
    }

    /**
     * 返回键点击事件
     */
    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }
}
