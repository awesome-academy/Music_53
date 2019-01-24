package com.trantan.music53.ui.discover;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.trantan.music53.R;
import com.trantan.music53.data.Genre;
import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TrackRepository;
import com.trantan.music53.data.source.local.LocalDataSource;
import com.trantan.music53.data.source.remote.TracksRemoteDataSource;
import com.trantan.music53.service.music.PlayService;
import com.trantan.music53.ui.MainActivity;
import com.trantan.music53.ui.detail_genre.DetailGenreActivity;
import com.trantan.music53.ui.genres.GenresActivity;
import com.trantan.music53.ui.search.SearchActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment implements DiscoverContract.View,
        SuggestedAdapter.TrackClickListener, View.OnClickListener {
    private View mSearchView;
    private TextView mTextGenres;
    private TextView mTextSuggestedSong;
    private ImageView mImageAll;
    private ImageView mImageAllAudio;
    private ImageView mImageRock;
    private ImageView mImageClassic;
    private ImageView mImageCountry;
    private RecyclerView mRecyclerSuggestedSongs;
    private DiscoverContract.Presenter mPresenter;
    private ProgressBar mProgressBar;
    private ServiceConnection mConnection;
    private PlayService mPlayService;
    private List<Genre> mGenres;
    private MainActivity mMainActivity;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = PlayService.getIntent(getContext());
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unbindService(mConnection);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        initUi(view);
        initPresenter();
        initConnection();
        initClickListner();
        return view;
    }

    private void initConnection() {
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayService.MyBinder myBinder = (PlayService.MyBinder) service;
                mPlayService = myBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                getActivity().unbindService(mConnection);
            }
        };
    }

    private void initPresenter() {
        TrackRepository repository = TrackRepository.getInstance(TracksRemoteDataSource.getInstance(),
                LocalDataSource.getInstance(getContext()));
        mPresenter = new DiscoverPresenter(repository, this);
        mPresenter.loadSuggestedTrack();
        mPresenter.loadGenres();
    }

    private void initUi(View view) {
        mMainActivity = (MainActivity) getActivity();
        mSearchView = view.findViewById(R.id.layout_search);
        mTextGenres = view.findViewById(R.id.text_genres);
        mTextSuggestedSong = view.findViewById(R.id.text_suggested_song);
        mImageAll = view.findViewById(R.id.image_all);
        mImageAllAudio = view.findViewById(R.id.image_all_audio);
        mImageRock = view.findViewById(R.id.image_rock);
        mImageClassic = view.findViewById(R.id.image_classic);
        mImageCountry = view.findViewById(R.id.image_country);
        mProgressBar = view.findViewById(R.id.progress);
        mRecyclerSuggestedSongs = view.findViewById(R.id.recycle_suggested_song);
    }

    private void initClickListner() {
        mTextGenres.setOnClickListener(this);
        mImageAll.setOnClickListener(this);
        mImageAllAudio.setOnClickListener(this);
        mImageCountry.setOnClickListener(this);
        mImageClassic.setOnClickListener(this);
        mImageRock.setOnClickListener(this);
        mSearchView.setOnClickListener(this);
    }

    @Override
    public void showSuggestedTrack(List<Track> tracks) {
        mProgressBar.setVisibility(View.GONE);
        SuggestedAdapter adapter = new SuggestedAdapter(tracks, this);
        mRecyclerSuggestedSongs.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showSuggestedTrackFailure() {

    }

    @Override
    public void loadedGenres(List<Genre> genres) {
        mGenres = genres;
    }

    @Override
    public void onTrackClick(Track track) {
        mPlayService.changedTrack(track);
        mMainActivity.showMiniPlayFragment(track, true);
    }

    @Override
    public void onOptionClick(Track track) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.text_genres:
                intent = GenresActivity.getIntent(getContext());
                break;
            case R.id.image_all:
                intent = DetailGenreActivity.getDetailGenreIntent(getContext(),
                        getGenre(getString(R.string.title_all)));
                break;
            case R.id.image_all_audio:
                intent = DetailGenreActivity.getDetailGenreIntent(getContext(),
                        getGenre(getString(R.string.title_all_audio)));
                break;
            case R.id.image_classic:
                intent = DetailGenreActivity.getDetailGenreIntent(getContext(),
                        getGenre(getString(R.string.title_classic)));
                break;
            case R.id.image_country:
                intent = DetailGenreActivity.getDetailGenreIntent(getContext(),
                        getGenre(getString(R.string.title_country)));
                break;
            case R.id.image_rock:
                intent = DetailGenreActivity.getDetailGenreIntent(getContext(),
                        getGenre(getString(R.string.title_alternativerock)));
                break;
            case R.id.layout_search:
                intent = SearchActivity.getIntent(getContext());
                break;
            default:
        }
        startActivity(intent);
    }

    private Genre getGenre(String nameGenre) {
        for (int i = 0; i < mGenres.size(); i++) {
            if (mGenres.get(i).getName().equals(nameGenre)) return mGenres.get(i);
        }
        return null;
    }
}
