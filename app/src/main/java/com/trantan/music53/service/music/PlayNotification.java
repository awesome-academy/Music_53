package com.trantan.music53.service.music;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.NotificationTarget;
import com.trantan.music53.R;
import com.trantan.music53.data.Track;
import com.trantan.music53.ui.mainplay.MainPlayActivity;
import com.trantan.music53.utils.GlideApp;

public class PlayNotification {
    private static final int REQUEST_CODE = 0;
    private static final String NAME_CHANNEL = "com.trantan.music53.NAME_CHANNEL";
    private static final String ID_CHANNEL = "com.trantan.music53.ID_CHANNEL";
    private static final int INT_NINE = 9;
    private static RemoteViews sBigRemoteViews;
    private static RemoteViews sRemoteViews;
    private static NotificationCompat.Builder sBuilder;
    private static NotificationManager sManager;
    public static final int NOTIFICATION_ID = 247;

    public static Notification setUpNotification(Context context, Track track) {
        createNotificationChannel(context);
        initBigRemoteViews(context, track);
        initRemoteViews(context, track);

        Intent intent = new Intent(context, MainPlayActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(intent);

        PendingIntent pendingIntent = stackBuilder
                .getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);
        sBuilder = new NotificationCompat.Builder(context, ID_CHANNEL);
        sBuilder.setSmallIcon(R.drawable.ic_music_24dp)
                .setContentText(context.getString(R.string.app_name))
                .setCustomContentView(sRemoteViews)
                .setCustomBigContentView(sBigRemoteViews)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW);
        sManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = sBuilder.build();

        loadImageArtworkForNormalView(context, track, notification);
        loadImageArtworkForBigView(context, track, notification);

        setPreviousClick(context);
        setPlayClick(context);
        setNextClick(context);
        setCloseClick(context);

        return notification;
    }

    private static void setPreviousClick(Context context) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction(PlayService.ACTION_PREVIOUS);
        PendingIntent pendingIntent
                = PendingIntent.getService(context, REQUEST_CODE, intent, 0);
        sBigRemoteViews.setOnClickPendingIntent(R.id.image_previous, pendingIntent);
    }

    private static void setPlayClick(Context context) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction(PlayService.ACTION_PLAY_PAUSE);
        PendingIntent pendingIntent
                = PendingIntent.getService(context, REQUEST_CODE, intent, 0);
        sBigRemoteViews.setOnClickPendingIntent(R.id.image_play, pendingIntent);
        sRemoteViews.setOnClickPendingIntent(R.id.image_play, pendingIntent);
    }

    private static void setNextClick(Context context) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction(PlayService.ACTION_NEXT);
        PendingIntent pendingIntent
                = PendingIntent.getService(context, REQUEST_CODE, intent, 0);
        sBigRemoteViews.setOnClickPendingIntent(R.id.image_next, pendingIntent);
    }

    private static void setCloseClick(Context context) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction(PlayService.ACTION_CLOSE);
        PendingIntent pendingIntent
                = PendingIntent.getService(context, REQUEST_CODE, intent, 0);
        sBigRemoteViews.setOnClickPendingIntent(R.id.image_close, pendingIntent);
    }

    private static void loadImageArtworkForNormalView(Context context, Track track, Notification notification) {
        NotificationTarget target = new NotificationTarget(context,
                R.id.image_artwork,
                sRemoteViews,
                notification,
                NOTIFICATION_ID);
        GlideApp.with(context)
                .asBitmap()
                .transform(new RoundedCorners(INT_NINE))
                .load(track.getArtworkUrl())
                .into(target);
    }

    private static void loadImageArtworkForBigView(Context context, Track track, Notification notification) {
        NotificationTarget target = new NotificationTarget(context,
                R.id.image_artwork,
                sBigRemoteViews,
                notification,
                NOTIFICATION_ID);
        GlideApp.with(context)
                .asBitmap()
                .transform(new RoundedCorners(INT_NINE))
                .load(track.getArtworkUrl())
                .into(target);
    }

    private static void initRemoteViews(Context context, Track track) {
        sRemoteViews = new RemoteViews(context.getPackageName(),
                R.layout.layout_notification);
        sRemoteViews.setTextViewText(R.id.text_name, track.getTitle());
        sRemoteViews.setTextViewText(R.id.text_artist, track.getArtist());
    }

    private static void initBigRemoteViews(Context context, Track track) {
        sBigRemoteViews = new RemoteViews(context.getPackageName(),
                R.layout.layout_large_notification);
        sBigRemoteViews.setTextViewText(R.id.text_name, track.getTitle());
        sBigRemoteViews.setTextViewText(R.id.text_artist, track.getArtist());
    }

    public static void upDateImagePlay(boolean isPlaying) {
        if (sManager == null) return;
        if (isPlaying) {
            sRemoteViews.setImageViewResource(R.id.image_play, R.drawable.ic_pause);
            sBigRemoteViews.setImageViewResource(R.id.image_play, R.drawable.ic_pause);
        } else {
            sRemoteViews.setImageViewResource(R.id.image_play, R.drawable.ic_play);
            sBigRemoteViews.setImageViewResource(R.id.image_play, R.drawable.ic_play);
        }
        sManager.notify(NOTIFICATION_ID, sBuilder.build());

    }

    private static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(ID_CHANNEL, NAME_CHANNEL, importance);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
