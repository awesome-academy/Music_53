package com.trantan.music53.ui.discover;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trantan.music53.R;
import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TrackRepository;
import com.trantan.music53.data.source.remote.TracksRemoteDataSource;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment implements DiscoverContract.View {
    private View mSearchView;
    private TextView mTextGenres;
    private TextView mTextSuggestedSong;
    private ImageView mImageAll;
    private ImageView mImageAllAudio;
    private ImageView mImageEdm;
    private ImageView mImageClassic;
    private ImageView mImageCountry;
    private RecyclerView mRecyclerSuggestedSongs;
    private DiscoverContract.Presenter mPresenter;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        initUi(view);
        initPresenter();
        return view;
    }

    private void initPresenter() {
        TrackRepository repository = TrackRepository.getInstance(TracksRemoteDataSource.getInstance());
        mPresenter = new DiscoverPresenter(repository, this);
        mPresenter.loadSuggestedTrack();
    }

    private void initUi(View view) {
        mSearchView = view.findViewById(R.id.layout_search);
        mTextGenres = view.findViewById(R.id.text_genres);
        mTextSuggestedSong = view.findViewById(R.id.text_suggested_song);
        mImageAll = view.findViewById(R.id.image_all);
        mImageAllAudio = view.findViewById(R.id.image_all_audio);
        mImageEdm = view.findViewById(R.id.image_edm);
        mImageClassic = view.findViewById(R.id.image_classic);
        mImageCountry = view.findViewById(R.id.image_country);
        mRecyclerSuggestedSongs = view.findViewById(R.id.recycle_suggested_song);
        mRecyclerSuggestedSongs.setAdapter(new SuggestedAdapter(null));
    }

    @Override
    public void showSuggestedTrack(List<Track> tracks) {
        SuggestedAdapter adapter = new SuggestedAdapter(tracks);
        mRecyclerSuggestedSongs.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showSuggestedTrackFailure() {

    }
}
