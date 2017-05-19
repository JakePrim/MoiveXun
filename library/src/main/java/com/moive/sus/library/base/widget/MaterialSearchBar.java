package com.moive.sus.library.base.widget;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moive.sus.library.R;
import com.moive.sus.library.base.adapter.DefaultSuggestionsAdapter;
import com.moive.sus.library.base.adapter.SuggestionsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linksus on 5/18 0018.
 * Material 设计风格的搜索
 */

public class MaterialSearchBar extends RelativeLayout implements View.OnClickListener,
        Animation.AnimationListener, SuggestionsAdapter.OnItemViewClickListener,
        View.OnFocusChangeListener, TextView.OnEditorActionListener {
    public static final int BUTTON_SPEECH = 1;
    public static final int BUTTON_NAVIGATION = 2;

    private LinearLayout inputContainer;
    private ImageView searchIcon;
    private ImageView arrowIcon;
    private EditText searchEdit;
    private ImageView navIcon;
    private TextView placeHolder;
    private OnSearchActionListener onSearchActionListener;
    private boolean searchEnabled;
    private boolean suggestionsVisible;
    public static final int VIEW_VISIBLE = 1;
    public static final int VIEW_INVISIBLE = 0;
    private SuggestionsAdapter adapter;
    private float destiny;

    private int searchIconRes;
    private int navIconResId;
    private CharSequence hint;
    private CharSequence placeholderText;
    private int maxSuggestionCount;
    private boolean speechMode;

    private int menuResource;
    private PopupMenu popupMenu;

    private int textColor;
    private int hintColor;

    private boolean navButtonEnabled;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MaterialSearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MaterialSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialSearchBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.searchbar, this);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MaterialSearchBar);
        searchIconRes = array.getResourceId(R.styleable.MaterialSearchBar_mt_searchIconDrawable, -1);
        navIconResId = array.getResourceId(R.styleable.MaterialSearchBar_mt_navIconDrawable, -1);
        hint = array.getString(R.styleable.MaterialSearchBar_mt_hint);
        placeholderText = array.getString(R.styleable.MaterialSearchBar_mt_placeholder);
        maxSuggestionCount = array.getInt(R.styleable.MaterialSearchBar_mt_maxSuggestionsCount, 3);
        speechMode = array.getBoolean(R.styleable.MaterialSearchBar_mt_speechMode, false);

        hintColor = array.getColor(R.styleable.MaterialSearchBar_mt_hintColor, -1);
        textColor = array.getColor(R.styleable.MaterialSearchBar_mt_textColor, -1);
        navButtonEnabled = array.getBoolean(R.styleable.MaterialSearchBar_mt_navIconEnabled, false);

        destiny = getResources().getDisplayMetrics().density;
        if (adapter == null) {
            adapter = new DefaultSuggestionsAdapter(LayoutInflater.from(getContext()));
        }
        if (adapter instanceof DefaultSuggestionsAdapter)
            ((DefaultSuggestionsAdapter) adapter).setListener(this);
        adapter.setMaxSuggestionsCount(maxSuggestionCount);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mt_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        array.recycle();

        searchIcon = (ImageView) findViewById(R.id.mt_search);
        arrowIcon = (ImageView) findViewById(R.id.mt_arrow);
        searchEdit = (EditText) findViewById(R.id.mt_editText);
        placeHolder = (TextView) findViewById(R.id.mt_placeholder);
        inputContainer = (LinearLayout) findViewById(R.id.inputContainer);
        navIcon = (ImageView) findViewById(R.id.mt_nav);
        findViewById(R.id.mt_clear).setOnClickListener(this);

        setOnClickListener(this);
        arrowIcon.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
        searchEdit.setOnFocusChangeListener(this);
        searchEdit.setOnEditorActionListener(this);
        navIcon.setOnClickListener(this);
        postSetup();
    }

    /**
     * Inflate menu for searchBar
     *
     * @param menuResource - menu resource
     */
    public void inflateMenu(int menuResource) {
        this.menuResource = menuResource;
        if (menuResource > 0) {
            ImageView menuIcon = (ImageView) findViewById(R.id.mt_menu);
            RelativeLayout.LayoutParams params = (LayoutParams) searchIcon.getLayoutParams();
            params.rightMargin = (int) (36 * destiny);
            searchIcon.setLayoutParams(params);
            menuIcon.setVisibility(VISIBLE);
            menuIcon.setOnClickListener(this);
            popupMenu = new PopupMenu(getContext(), menuIcon);
            popupMenu.inflate(menuResource);
            popupMenu.setGravity(Gravity.RIGHT);
        }
    }

    /**
     * Get popup menu
     *
     * @return PopupMenu
     */
    public PopupMenu getMenu() {
        return this.popupMenu;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void postSetup() {
        if (searchIconRes < 0)
            searchIconRes = R.mipmap.ic_magnify_black_48dp;
        setSpeechMode(speechMode);
        if (navIconResId < 0)
            navIconResId = R.mipmap.ic_menu_black_24dp;
        setNavigationIcon(navIconResId);
        if (hint != null)
            searchEdit.setHint(hint);
        if (placeholderText != null) {
            arrowIcon.setBackground(null);
            placeHolder.setText(placeholderText);
        }
        setupTextColors();
        setNavButtonEnabled(navButtonEnabled);
        if (popupMenu == null)
            findViewById(R.id.mt_menu).setVisibility(GONE);
    }

    private void setupTextColors() {
        if (hintColor != -1)
            searchEdit.setHintTextColor(ContextCompat.getColor(getContext(), hintColor));
        if (textColor != -1)
            searchEdit.setTextColor(ContextCompat.getColor(getContext(), textColor));
    }

    /**
     * Register listener for search bar callbacks.
     *
     * @param onSearchActionListener the callback listener
     */
    public void setOnSearchActionListener(OnSearchActionListener onSearchActionListener) {
        this.onSearchActionListener = onSearchActionListener;
    }

    /**
     * Hides search input and close arrow
     */
    public void disableSearch() {
        searchEnabled = false;
        Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_right);
        out.setAnimationListener(this);
        searchIcon.setVisibility(VISIBLE);
        inputContainer.startAnimation(out);
        searchIcon.startAnimation(in);

        if (placeholderText != null) {
            placeHolder.setVisibility(VISIBLE);
            placeHolder.startAnimation(in);
        }
        if (listenerExists())
            onSearchActionListener.onSearchStateChanged(false);
        if (suggestionsVisible) animateSuggestions(getListHeight(false), 0);
    }

    /**
     * Shows search input and close arrow
     */
    public void enableSearch() {
        adapter.notifyDataSetChanged();
        searchEnabled = true;
        Animation left_in = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_left);
        Animation left_out = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_left);
        left_in.setAnimationListener(this);
        placeHolder.setVisibility(GONE);
        inputContainer.setVisibility(VISIBLE);
        inputContainer.startAnimation(left_in);
        if (listenerExists()) {
            onSearchActionListener.onSearchStateChanged(true);
        }
        searchIcon.startAnimation(left_out);
    }

    private void animateSuggestions(int from, int to) {
        suggestionsVisible = to > 0;
        final RelativeLayout last = (RelativeLayout) findViewById(R.id.last);
        final ViewGroup.LayoutParams lp = last.getLayoutParams();
        if (to == 0 && lp.height == 0)
            return;
        ValueAnimator animator = ValueAnimator.ofInt(from, to);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.height = (int) animation.getAnimatedValue();
                last.setLayoutParams(lp);
            }
        });
        if (adapter.getItemCount() > 0)
            animator.start();
    }

    public void showSuggestionsList() {
        animateSuggestions(0, getListHeight(false));
    }

    public void hideSuggestionsList() {
        animateSuggestions(getListHeight(false), 0);
    }

    public void clearSuggestions() {
        if (suggestionsVisible)
            animateSuggestions(getListHeight(false), 0);
        adapter.clearSuggestions();
    }

    /**
     * Set search icon drawable resource
     *
     * @param searchIconResId icon resource id
     */
    public void setSearchIcon(int searchIconResId) {
        this.searchIconRes = searchIconResId;
        this.searchIcon.setImageResource(searchIconResId);
    }

    /**
     * Set navigation icon drawable resource
     *
     * @param navigationIconResId icon resource id
     */
    public void setNavigationIcon(int navigationIconResId) {
        this.navIconResId = navigationIconResId;
        this.navIcon.setImageResource(navigationIconResId);
    }

    /**
     * Sets search bar hint
     *
     * @param hint
     */
    public void setHint(CharSequence hint) {
        this.hint = hint;
        searchEdit.setHint(hint);
    }

    /**
     * Set the place holder text
     *
     * @param placeholder
     */
    public void setPlaceHolder(CharSequence placeholder) {
        this.placeholderText = placeholder;
        placeHolder.setText(placeholder);
    }

    /**
     * sets the speechMode for the search bar.
     * If set to true, microphone icon will display instead of the search icon.
     * Also clicking on this icon will trigger the callback method onButtonClicked()
     *
     * @param speechMode
     * @see #BUTTON_SPEECH
     * @see OnSearchActionListener#onButtonClicked(int)
     */
    public void setSpeechMode(boolean speechMode) {
        this.speechMode = speechMode;
        if (speechMode) {
            searchIcon.setImageResource(R.mipmap.ic_microphone_black_48dp);
            searchIcon.setClickable(true);
        } else {
            searchIcon.setImageResource(searchIconRes);
            searchIcon.setClickable(false);
        }
    }

    /**
     * True if MaterialSearchBar is in speech mode
     *
     * @return speech mode
     */
    public boolean isSpeechModeEnabled() {
        return speechMode;
    }

    /**
     * Check if search bar is in edit mode
     *
     * @return true if search bar is in edit mode
     */
    public boolean isSearchEnabled() {
        return searchEnabled;
    }

    /**
     * Specifies the maximum number of search queries stored until the activity is destroyed
     *
     * @param maxSuggestionsCount maximum queries
     */
    public void setMaxSuggestionCount(int maxSuggestionsCount) {
        this.maxSuggestionCount = maxSuggestionsCount;
        adapter.setMaxSuggestionsCount(maxSuggestionsCount);
    }

    /**
     * Sets a custom adapter for suggestions list view.
     *
     * @param suggestionAdapter customized adapter
     */
    public void setCustomSuggestionAdapter(SuggestionsAdapter suggestionAdapter) {
        this.adapter = suggestionAdapter;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mt_recycler);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Returns the last search queries.
     * The queries are stored only for the duration of one activity session.
     * When the activity is destroyed, the queries will be deleted.
     * To save queries, use the method getLastSuggestions().
     * To recover the queries use the method setLastSuggestions().
     * <p><b color="red">List< String >  will be returned if You don't use custom adapter.</b></p>
     *
     * @return array with the latest search queries
     * @see #setLastSuggestions(List)
     * @see #setMaxSuggestionCount(int)
     */
    public List getLastSuggestions() {
        return adapter.getSuggestions();
    }

    /**
     * Changes the array of recent search queries with animation.
     * <p><b color="red">Pass a List< String >  if You don't use custom adapter.</b></p>
     *
     * @param suggestions an array of queries
     */
    public void updateLastSuggestions(List suggestions) {
        int startHeight = getListHeight(false);
        if (suggestions.size() > 0) {
            List newSuggestions = new ArrayList<>(suggestions);
            adapter.setSuggestions(newSuggestions);
            animateSuggestions(startHeight, getListHeight(false));
        } else {
            animateSuggestions(startHeight, 0);
        }
    }

    /**
     * Sets the array of recent search queries.
     * It is advisable to save the queries when the activity is destroyed
     * and call this method when creating the activity.
     * <p><b color="red">Pass a List< String > if You don't use custom adapter.</b></p>
     *
     * @param suggestions an array of queries
     * @see #getLastSuggestions()
     * @see #setMaxSuggestionCount(int)
     */
    public void setLastSuggestions(List suggestions) {
        adapter.setSuggestions(suggestions);
    }

    /**
     * Allows you to intercept the suggestions click event
     * <p><b color="red">This method will not work with custom Suggestion Adapter</b></p>
     *
     * @param listener
     */
    public void setSuggstionsClickListener(SuggestionsAdapter.OnItemViewClickListener listener) {
        if (adapter instanceof DefaultSuggestionsAdapter)
            ((DefaultSuggestionsAdapter) adapter).setListener(listener);
    }

    /**
     * Set search input text color
     *
     * @param textColor text color
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        setupTextColors();
    }

    /**
     * Set text input hint color
     *
     * @param hintColor text hint color
     */
    public void setTextHintColor(int hintColor) {
        this.hintColor = hintColor;
        setupTextColors();
    }

    /**
     * Set navigation drawer menu icon enabled
     *
     * @param navButtonEnabled icon enabled
     */
    public void setNavButtonEnabled(boolean navButtonEnabled) {
        this.navButtonEnabled = navButtonEnabled;
        if (navButtonEnabled) {
            navIcon.setVisibility(VISIBLE);
            navIcon.setClickable(true);
//            LayoutParams lp = (LayoutParams) inputContainer.getLayoutParams();
//            lp.leftMargin = (int) (50 * destiny);
//            inputContainer.setLayoutParams(lp);
//            arrowIcon.setVisibility(GONE);
        } else {
            navIcon.setVisibility(GONE);
            navIcon.setClickable(false);
        }
    }

    /**
     * Set search text
     *
     * @param text text
     */
    public void setText(String text) {
        searchEdit.setText(text);
    }

    /**
     * Get search text
     *
     * @return text
     */
    public String getText() {
        return searchEdit.getText().toString();
    }

    /**
     * Set CardView elevation
     *
     * @param elevation
     */
    public void setCardViewElevation(int elevation) {
        CardView cardView = (CardView) findViewById(R.id.mt_container);
        cardView.setCardElevation(elevation);
    }

    /**
     * Add text watcher to searchbar's EditText
     *
     * @param textWatcher
     */
    public void addTextChangeListener(TextWatcher textWatcher) {
        searchEdit.addTextChangedListener(textWatcher);
    }

    private boolean listenerExists() {
        return onSearchActionListener != null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == getId()) {
            if (!searchEnabled) {
                enableSearch();
            }
        } else if (id == R.id.mt_arrow) {
            disableSearch();
        } else if (id == R.id.mt_search) {
            if (listenerExists())
                onSearchActionListener.onButtonClicked(BUTTON_SPEECH);
        } else if (id == R.id.mt_clear) {
            searchEdit.setText("");
        } else if (id == R.id.mt_menu) {
            popupMenu.show();
        } else if (id == R.id.mt_nav)
            if (listenerExists())
                onSearchActionListener.onButtonClicked(BUTTON_NAVIGATION);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (!searchEnabled) {
            inputContainer.setVisibility(GONE);
            navIcon.setVisibility(VISIBLE);
            searchEdit.setText("");
        } else {
            inputContainer.setVisibility(VISIBLE);
            arrowIcon.setVisibility(VISIBLE);
            navIcon.setVisibility(GONE);
            searchIcon.setVisibility(GONE);
            searchEdit.requestFocus();
            if (!suggestionsVisible)
                showSuggestionsList();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (hasFocus) {
            imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
        } else {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (listenerExists())
            onSearchActionListener.onSearchConfirmed(searchEdit.getText());
        if (suggestionsVisible)
            hideSuggestionsList();
        if (adapter instanceof DefaultSuggestionsAdapter)
            adapter.addSuggestion(searchEdit.getText().toString());
        return true;
    }

    /**
     * For calculate the height change when item delete or add animation
     * false is retrurn the full height of item,
     * true is return the height of postion subtraction one
     *
     * @param isSubtraction
     */
    private int getListHeight(boolean isSubtraction) {
        if (!isSubtraction)
            return (int) (adapter.getListHeight() * destiny);
        return (int) (((adapter.getItemCount() - 1) * adapter.getSingleViewHeight()) * destiny);
    }

    @Override
    public void OnItemClickListener(int position, View v) {
        if (v.getTag() instanceof String) {
            searchEdit.setText((String) v.getTag());
        }
    }

    @Override
    public void OnItemDeleteListener(int position, View v) {
        if (v.getTag() instanceof String) {
            /*Order of two line should't be change,
            because sholud calculate the height of item first*/
            animateSuggestions(getListHeight(false), getListHeight(true));
            adapter.deleteSuggestion(position, (String) v.getTag());
        }
    }

    /**
     * Interface definition for MaterialSearchBar callbacks.
     */
    public interface OnSearchActionListener {
        /**
         * Invoked when SearchBar opened or closed
         *
         * @param enabled
         */
        void onSearchStateChanged(boolean enabled);

        /**
         * Invoked when search confirmed and "search" button is clicked on the soft keyboard
         *
         * @param text search input
         */
        void onSearchConfirmed(CharSequence text);

        /**
         * Invoked when "speech" or "navigation" buttons clicked.
         *
         * @param buttonCode {@link #BUTTON_NAVIGATION} or {@link #BUTTON_SPEECH} will be passed
         */
        void onButtonClicked(int buttonCode);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.isSearchBarVisible = searchEnabled ? VIEW_VISIBLE : VIEW_INVISIBLE;
        savedState.suggestionsVisible = suggestionsVisible ? VIEW_VISIBLE : VIEW_INVISIBLE;
        savedState.speechMode = speechMode ? VIEW_VISIBLE : VIEW_INVISIBLE;
        savedState.navIconResId = navIconResId;
        savedState.searchIconRes = searchIconRes;
        savedState.suggestions = getLastSuggestions();
        savedState.maxSuggestions = maxSuggestionCount;
        if (hint != null) savedState.hint = hint.toString();
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        searchEnabled = savedState.isSearchBarVisible == VIEW_VISIBLE;
        suggestionsVisible = savedState.suggestionsVisible == VIEW_VISIBLE;
        setLastSuggestions(savedState.suggestions);
        if (suggestionsVisible)
            animateSuggestions(0, getListHeight(false));
        if (searchEnabled) {
            inputContainer.setVisibility(VISIBLE);
            placeHolder.setVisibility(GONE);
            searchIcon.setVisibility(GONE);
        }
    }

    private static class SavedState extends BaseSavedState {
        private int isSearchBarVisible;
        private int suggestionsVisible;
        private int speechMode;
        private int searchIconRes;
        private int navIconResId;
        private String hint;
        private List suggestions;
        private int maxSuggestions;

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(isSearchBarVisible);
            out.writeInt(suggestionsVisible);
            out.writeInt(speechMode);

            out.writeInt(searchIconRes);
            out.writeInt(navIconResId);
            out.writeString(hint);
            out.writeList(suggestions);
            out.writeInt(maxSuggestions);
        }

        public SavedState(Parcel source) {
            super(source);
            isSearchBarVisible = source.readInt();
            suggestionsVisible = source.readInt();
            speechMode = source.readInt();

            navIconResId = source.readInt();
            searchIconRes = source.readInt();
            hint = source.readString();
            suggestions = source.readArrayList(null);
            maxSuggestions = source.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && searchEnabled) {
            animateSuggestions(getListHeight(false), 0);
            disableSearch();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
