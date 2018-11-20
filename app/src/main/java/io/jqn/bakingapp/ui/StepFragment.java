package io.jqn.bakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.jqn.bakingapp.R;
import io.jqn.bakingapp.model.RetroRecipe;
import timber.log.Timber;

public class StepFragment extends Fragment {
    private RetroRecipe mRecipe;
    private String mShortDescription;
    private String mDescription;
    private String mMediaUrl;

    // Exoplayer
    private SimpleExoPlayer mPlayer;
    private long mPlaybackPosition = 0;
    private int mCurrentWindow = 0;
    private boolean mPlayWhenReady = true;

    private Uri mUri;

    @BindView(R.id.step_media)
    PlayerView mPlayerView;
    @BindView(R.id.step_description_title) TextView mShortDescriptionView;
    @BindView(R.id.step_description) TextView mTextView;

    // Mandatory empty constructor
    public StepFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShortDescription = getArguments().getString("SHORT_DESCRIPTION");
            mDescription = getArguments().getString("DESCRIPTION");
            mMediaUrl = getArguments().getString("VIDEO");
        }
    }

    // Inflate fragment layout file
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        // Inflate the Step fragment layout
        View rootView = inflater.inflate(R.layout.step_fragment, container, false);

        ButterKnife.bind(this, rootView);

        // Set the step content
        mShortDescriptionView.setText(mShortDescription);
        mTextView.setText(mDescription);
        Timber.v("Setting step fragment text");

        if (getActivity().getIntent().hasExtra("RECIPE_KEY")) {
            mRecipe = getActivity().getIntent().getExtras().getParcelable("RECIPE_KEY");
        }

        // Initialize exoplayer
        mPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());


        mPlayerView.setPlayer(mPlayer);
        mPlayer.setPlayWhenReady(mPlayWhenReady);
        mPlayer.seekTo(mCurrentWindow, mPlaybackPosition);

        mUri = Uri.parse(mMediaUrl);

        if (mUri != null) {
            MediaSource mediaSource = new ExtractorMediaSource.Factory(
                    new DefaultHttpDataSourceFactory("ExoPlayer"))
                    .createMediaSource(mUri);
            mPlayer.prepare(mediaSource, false, false);
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlaybackPosition = mPlayer.getCurrentPosition();
            mCurrentWindow = mPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }

}
