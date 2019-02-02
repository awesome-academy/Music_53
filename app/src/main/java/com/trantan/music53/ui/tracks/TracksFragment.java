package com.trantan.music53.ui.tracks;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trantan.music53.R;
import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TrackRepository;
import com.trantan.music53.data.source.local.LocalDataSource;
import com.trantan.music53.data.source.remote.TracksRemoteDataSource;
import com.trantan.music53.service.music.PlayService;
import com.trantan.music53.ui.option.OptionFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TracksFragment extends Fragment implements TracksContract.View,
        TracksAdapter.TrackClickListener {
    private RecyclerView mRecyclerView;
    private TracksAdapter mAdapter;
    private TrackPresenter mPresenter;
    private PlayService mService;
    private ServiceConnection mConnection;
    private List<Track> mTracks;

    public TracksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        initUi(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        getActivity().unbindService(mConnection);
        super.onDestroyView();
    }

    private void initUi(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_tracks);

        mPresenter = new TrackPresenter(TrackRepository.getInstance(TracksRemoteDataSource.getInstance(),
                LocalDataSource.getInstance(getContext())), this);
        mPresenter.getTracks();

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayService.MyBinder myBinder = (PlayService.MyBinder) service;
                mService = myBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        getActivity().bindService(PlayService.getIntent(getContext()), mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onTrackClick(Track track) {
        mService.pauseTrack();
        mService.addTracks(mTracks);
        mService.unShuffledTracks();
        mService.changedTrack(track);
    }

    @Override
    public void onOptionClick(Track track) {
        OptionFragment optionFragment = OptionFragment.getIntance(track);
        optionFragment.show(getActivity().getSupportFragmentManager(), optionFragment.getTag());
    }

    @Override
    public void loadTracks(List<Track> tracks) {
        mTracks = tracks;
        mAdapter = new TracksAdapter(tracks, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
