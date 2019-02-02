package com.trantan.music53.ui.favorite;


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
public class FavoriteFragment extends Fragment implements FavoriteContract.View,
        FavoritesAdapter.TrackClickListener {
    private RecyclerView mRecyclerView;
    private FavoritesAdapter mAdapter;
    private FavoritePresenter mPresenter;
    private PlayService mService;
    private ServiceConnection mConnection;
    private List<Track> mTracks;

    public FavoriteFragment() {
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

        mPresenter = new FavoritePresenter(TrackRepository.getInstance(TracksRemoteDataSource.getInstance(),
                LocalDataSource.getInstance(getContext())), this);
        mPresenter.getFavorite();

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
    public void onStart() {
//        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void loadFavorites(List<Track> tracks) {
        mTracks = tracks;
        mAdapter = new FavoritesAdapter(tracks, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
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
    public void onRemoveFavorite(Track track) {
        mPresenter.removeFavorite(track);
    }

    @Override
    public void onAddFavorite(Track track) {
        mPresenter.addFavorite(track);
    }

}
