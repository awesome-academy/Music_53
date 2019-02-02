package com.trantan.music53.ui.playing_list;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trantan.music53.R;
import com.trantan.music53.data.Track;
import com.trantan.music53.service.music.PlayService;
import com.trantan.music53.service.music.PlayServiceListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayingListFragment extends BottomSheetDialogFragment
        implements PlayServiceListener, PlayingListAdapter.TrackClickListener {
    private static final String NUMBER_OF_TRACKS = "Playing list (%d)";
    private static final int OFFSET = 0;
    private TextView mTextPlayingList;
    private RecyclerView mRecyclerTracks;
    private PlayService mService;
    private ServiceConnection mConnection;
    private PlayingListAdapter mListAdapter;
    private List<Track> mTracks;
    private LinearLayoutManager mLayoutManager;
    private ItemTouchHelper mItemTouchHelper;

    public PlayingListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playing_list, container, false);
        initUi(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        getActivity().unbindService(mConnection);
        super.onDestroyView();
    }

    private void bindData(List<Track> tracks) {
        mTracks = tracks;
        mTextPlayingList.setText(String.format(NUMBER_OF_TRACKS, tracks.size()));
        mListAdapter = new PlayingListAdapter(mTracks, this);
        mListAdapter.setTrackPlaying(mService.getCurrentTrack());
        mLayoutManager.scrollToPosition(mService.getTracks()
                .indexOf(mService.getCurrentTrack()));
        mRecyclerTracks.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();

        ItemTouchHelper.Callback callback = new MyItemTouchCallack(mListAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerTracks);
    }

    private void initUi(View view) {
        mTextPlayingList = view.findViewById(R.id.text_number_of_tracks);
        mRecyclerTracks = view.findViewById(R.id.recycle_playing_list);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerTracks.setLayoutManager(mLayoutManager);

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayService.MyBinder myBinder = (PlayService.MyBinder) service;
                mService = myBinder.getService();
                mService.addPlayServiceListener(PlayingListFragment.this);
                bindData(mService.getTracks());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        Intent intent = PlayService.getIntent(getContext());
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void listenCurrentTime(int currentTime) {

    }

    @Override
    public void listenChangeSong(Track track) {
        mLayoutManager.scrollToPositionWithOffset(mService.getTracks()
                .indexOf(mService.getCurrentTrack()), OFFSET);
        mListAdapter.setTrackPlaying(mService.getCurrentTrack());
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void listenPlayingState(boolean isPlaying) {

    }

    @Override
    public void listenPrepared() {

    }

    @Override
    public void onTrackClick(Track track) {
        mService.changedTrack(track);
    }

    @Override
    public void onRemoveTrackClick(Track track) {
        int position = mTracks.indexOf(track);
        mTracks.remove(track);
        mListAdapter.notifyItemRemoved(position);
        mListAdapter.notifyItemRangeChanged(position, mTracks.size());
        mService.removeTrack(track);
    }
}
