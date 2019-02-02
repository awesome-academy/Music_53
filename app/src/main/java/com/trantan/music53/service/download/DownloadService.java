package com.trantan.music53.service.download;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import com.trantan.music53.R;
import com.trantan.music53.data.Track;

public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";
    public static final String EXTRA_BUNDLE = "EXTRA_BUNDLE";
    public static final String EXTRA_TRACK = "EXTRA_TRACK";
    private static final String BASE_FILE_PATH = "/SoundCloud/%s.mp3";
    private Handler mHandler;

    public DownloadService() {
        super(TAG);
        mHandler = new Handler();
    }

    public static Intent getIntent(Context context, Track track) {
        Intent intent = new Intent(context, DownloadService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DownloadService.EXTRA_TRACK, track);
        intent.putExtra(DownloadService.EXTRA_BUNDLE, bundle);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mHandler.post(new DisplayToast(this, getString(R.string.toast_downloading)));
        Bundle bundle = intent.getBundleExtra(EXTRA_BUNDLE);
        Track track = (Track) bundle.getSerializable(EXTRA_TRACK);
        String urlDownload = track.getStreamUrl();
        String path = String.format(BASE_FILE_PATH, track.getTitle().replaceAll("/", "_"));
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlDownload));
        request.setTitle(track.getTitle());
        request.setAllowedOverRoaming(true);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, path);
        downloadManager.enqueue(request);
    }

    public class DisplayToast implements Runnable {
        private final Context mContext;
        String mText;

        public DisplayToast(Context mContext, String text) {
            this.mContext = mContext;
            mText = text;
        }

        public void run() {
            Toast.makeText(mContext, mText, Toast.LENGTH_SHORT).show();
        }
    }
}
