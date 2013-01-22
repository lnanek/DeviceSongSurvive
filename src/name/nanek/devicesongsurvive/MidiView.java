package name.nanek.devicesongsurvive;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;

import com.automatous_monk.apps.MIDIFileGen;
import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOn;
import com.leff.midi.util.MidiEventListener;
import com.leff.midi.util.MidiProcessor;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MidiView extends View implements MidiEventListener {

	private static final String FILE_URI_PREFIX = "file://";

	private static final String TEMP_MIDI_FILE_PATH = "/test.midi";

	private static final int SONG_GENERATION_RULE_COUNT = 256;

	private static final int SONG_LENGTH = 100;
	
	private static final String INSTRUCTIONS = "Touch new circles to live!";

	private static final int NOTE_TOUCH_BONUS_LIFE_CIRCLE_RADIUS_PX = 50;

	private static final int LIFE_CIRCLE_STROKE_WIDTH_PX = 10;

	private static final int TOUCH_CIRCLE_RADIUS = 20;
	
	private static final int NOTE_CIRCLE_DISPLAY_TIME_MS = 2500;
	
	private static final int LIFE_CIRCLE_CHANGE_RATE_PIXELS_PER_S = 75;

	private static class Api11AdditiveXferSetter {
		public static void setAdditiveXfer(Paint paint) {
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
		}
	}

	private static class NoteCircle {
		private long mDurationMs;
		private long mRemainingMs;
		private long mNote;
		private boolean mTouched;
	}
	
	private final Paint mTextStroke = new Paint();
	
	private final Paint mTextFill = new Paint();

	private final Paint mPaint = new Paint();
	{
		mTextStroke.setColor(Color.BLACK);
		mTextStroke.setStyle(Style.STROKE);
		mTextStroke.setStrokeWidth(2);
		mTextStroke.setTextSize(40);
		mTextStroke.setTextAlign(Align.CENTER);
		mTextFill.setColor(Color.WHITE);
		mTextFill.setStyle(Style.FILL);
		mTextFill.setTextSize(40);
		mTextFill.setTextAlign(Align.CENTER);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			Api11AdditiveXferSetter.setAdditiveXfer(mPaint);
		}
		mPaint.setStrokeWidth(LIFE_CIRCLE_STROKE_WIDTH_PX);
	}
	
	private List<NoteCircle> mNoteCircles = Collections
			.synchronizedList(new LinkedList<NoteCircle>());

	private long mLastDrawTimestamp;

	private long mMaxOctaveIndexDrawn;

	private long mMinOctaveIndexDrawn;

	private long mMaxNoteIndexDrawn;

	private long mMinNoteIndexDrawn;

	private Float mLifeCircleRadius;

	private Float mLastX;

	private Float mLastY;
	
	private boolean mIsStarted;
	
	private boolean mIsOver;
	
	private boolean mFirstTouchOccurred;

	public MidiView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
	}

	public MidiView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public MidiView(final Context context) {
		super(context);
	}

	public void startGame(final Long aSeed) {
		final Random random = new Random();
		if ( null != aSeed ) {
			random.setSeed(aSeed);
		}
		
		final String tempMidiFilePath =
				getContext().getCacheDir() + TEMP_MIDI_FILE_PATH;

		// Remove any previous song and generate a new one.
		final File tempMidiFile = new File(tempMidiFilePath);
		tempMidiFile.delete();
		
		MIDIFileGen.writeScore(random.nextInt(SONG_GENERATION_RULE_COUNT), tempMidiFilePath,
				true, SONG_LENGTH, random);

		// Parse MIDI file to react to each event in song.
		MidiFile midi = null;
		try {
			midi = new MidiFile(new File(tempMidiFilePath));
		} catch (Exception e) {
			throw new RuntimeException("Error reading MIDI file.", e);
		}
		MidiProcessor processor = new MidiProcessor(midi);
		processor.registerListenerForAllEvents(this);

		// Play MIDI file.
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
        	FileInputStream fileInputStream = new FileInputStream(tempMidiFile);
        	mediaPlayer.setDataSource(fileInputStream.getFD());   
			mediaPlayer.prepare();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		processor.start();
        mediaPlayer.start();
	}
	
	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		mFirstTouchOccurred = true;
		
		if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
			mLastX = event.getX();
			mLastY = event.getY();
		}

		// Return not handled so that we don't get further events for each action, which we don't use.
		return false;
	}

	@Override
	public void onStart(final boolean fromBeginning) {
		mIsStarted = true;
	}

	@Override
	public void onEvent(final MidiEvent event, long ms) {
		if (event instanceof NoteOn) {

			final NoteOn note = (NoteOn) event;

			final NoteCircle circle = new NoteCircle();
			circle.mDurationMs = NOTE_CIRCLE_DISPLAY_TIME_MS;
			circle.mRemainingMs = NOTE_CIRCLE_DISPLAY_TIME_MS;
			circle.mNote = note.getNoteValue();
			mNoteCircles.add(circle);
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	@Override
	public void onStop(final boolean finished) {
		mIsOver = true;
	}

	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);

		if (0 == getWidth() || 0 == getHeight()) {
			return;
		}

		final float maxLifeCircleRadius = Math.min(getWidth() / 2, getHeight() / 2);
		if (null == mLifeCircleRadius) {
			mLifeCircleRadius = maxLifeCircleRadius;
		}
		
		canvas.drawColor(Color.BLACK);

		final long now = SystemClock.uptimeMillis();
		final long elapsed = now - mLastDrawTimestamp;
		if (0 == mLastDrawTimestamp) {
			mLastDrawTimestamp = now;
			return;
		}

		boolean lastTouchInCircle = false;

		synchronized (mNoteCircles) {
			final Iterator<NoteCircle> iter = mNoteCircles.listIterator();
			while (iter.hasNext()) {
				final NoteCircle circle = iter.next();

				final long octaveIndex = circle.mNote / 12;
				final long noteIndex = circle.mNote % 12;
				mMaxOctaveIndexDrawn = Math
						.max(mMaxOctaveIndexDrawn, octaveIndex);
				mMinOctaveIndexDrawn = Math
						.min(mMinOctaveIndexDrawn, octaveIndex);
				mMaxNoteIndexDrawn = Math.max(mMaxNoteIndexDrawn, noteIndex);
				mMinNoteIndexDrawn = Math.min(mMinNoteIndexDrawn, noteIndex);
				final long octaveIndexRange = mMaxOctaveIndexDrawn
						- mMinOctaveIndexDrawn;
				final long noteIndexRange = mMaxNoteIndexDrawn
						- mMinNoteIndexDrawn;
				final float percentDecayed = circle.mRemainingMs
						/ (float) circle.mDurationMs;

				final float widthPerNote = getWidth() / (noteIndexRange + 1);
				final float heightPerOctave = getHeight()
						/ (octaveIndexRange + 1);
				final float minRadiusToFit = Math.min(widthPerNote,
						heightPerOctave);
				final float noteCenterX = widthPerNote * noteIndex
						+ widthPerNote / 2;
				final float noteCenterY = heightPerOctave * octaveIndex
						+ heightPerOctave / 2;
				final float decayedRadius = minRadiusToFit * percentDecayed;
				
				if ( null != mLastX && null != mLastY && !circle.mTouched ) {
					final float xDistanceFromTouch = Math.abs(noteCenterX - mLastX);
					final float yDistanceFromTouch = Math.abs(noteCenterY - mLastY);
					final float distanceFromTouch = FloatMath.sqrt(xDistanceFromTouch * xDistanceFromTouch + yDistanceFromTouch * yDistanceFromTouch);
					if ( distanceFromTouch < decayedRadius ) {
						lastTouchInCircle = true;
						circle.mTouched = true;
						mLastX = null;
						mLastY = null;
						mLifeCircleRadius += NOTE_TOUCH_BONUS_LIFE_CIRCLE_RADIUS_PX;
					}
				}

				final int alpha = (int) (255 * percentDecayed);
				if ( !circle.mTouched ) {
					final int redPerNote = 255 - (int) (255 / (noteIndexRange + 1));
					final int greenPerOctave = 255 - (int) (255 / (octaveIndexRange + 1));
					final int bluePerNote = (int) (255 / (octaveIndexRange + 1));
					final int red = (int) (redPerNote * noteIndex);
					final int green = (int) (greenPerOctave * noteIndex);
					final int blue = (int) (bluePerNote * octaveIndex);
					mPaint.setColor(Color.argb(alpha, red, green, blue));
				} else {
					mPaint.setColor(Color.GREEN);
					mPaint.setAlpha(alpha);
				}
				mPaint.setStyle(Paint.Style.FILL);
				canvas.drawCircle(noteCenterX, noteCenterY, decayedRadius,
						mPaint);
				
				circle.mRemainingMs -= elapsed;
				if (circle.mRemainingMs <= 0) {
					iter.remove();
				}
			}
		}

		if ( mIsStarted && !mIsOver ) {
			mLifeCircleRadius -= LIFE_CIRCLE_CHANGE_RATE_PIXELS_PER_S * elapsed / 1000f;
		}
		mLifeCircleRadius = Math.min(mLifeCircleRadius, maxLifeCircleRadius);
		mLifeCircleRadius = Math.max(mLifeCircleRadius, 0);
		
		mPaint.setARGB(255, 255, 255, 255);
		mPaint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, mLifeCircleRadius, mPaint);
		
		if ( mIsOver ) {
			String message = mLifeCircleRadius > 0 ? "You win!" : "You're dead!";
			canvas.drawText(message,  getWidth() / 2, getHeight() / 2, mTextStroke);
			canvas.drawText(message,  getWidth() / 2, getHeight() / 2, mTextFill);			
		} else {
			if ( !mFirstTouchOccurred ) {
				canvas.drawText(INSTRUCTIONS,  getWidth() / 2, getHeight() / 2, mTextStroke);
				canvas.drawText(INSTRUCTIONS,  getWidth() / 2, getHeight() / 2, mTextFill);
			}			
		}
		
		if ( null != mLastX && null != mLastY ) {
			mPaint.setStyle(Paint.Style.FILL);
			mPaint.setColor(lastTouchInCircle ? Color.GREEN : Color.RED);
			mPaint.setAlpha(100);
			canvas.drawCircle(mLastX, mLastY, TOUCH_CIRCLE_RADIUS, mPaint);
		}

		ViewCompat.postInvalidateOnAnimation(this);
		mLastDrawTimestamp = now;

	}
	
	public boolean isOver() {
		return mIsOver;
	}

}
