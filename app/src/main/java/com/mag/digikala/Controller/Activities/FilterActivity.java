package com.mag.digikala.Controller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mag.digikala.View.Fragment.CommonToolbarFragment;
import com.mag.digikala.Controller.Fragments.FilterFragment;
import com.mag.digikala.View.Fragment.FilterSelectionFragment;
import com.mag.digikala.View.FilterToolbarFragment;
import com.mag.digikala.R;
import com.mag.digikala.Util.UiUtil;

public class FilterActivity extends AppCompatActivity implements FilterFragment.FilterSelectionCallBack {

    public static final String EXTRA_SEARCH_STRING = "extra_search_string";
    public static final String EXTRA_CATEGORY_ID = "extra_category_id";
    private FilterToolbarFragment filterToolbarFragment;
    private FilterFragment filterFragment;
    private CommonToolbarFragment filterSelectionFragmentCommonToolbar;
    private FilterSelectionFragment filterSelectionFragment;

    public static Intent newIntent(Context context, String searchString, String categoryId) {
        Intent intent = new Intent(context, FilterActivity.class);
        intent.putExtra(EXTRA_SEARCH_STRING, searchString);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        showFilterPage();

    }

    public void showFilterPage() {

        if (filterToolbarFragment == null)
            filterToolbarFragment = FilterToolbarFragment.newInstance(getIntent().getExtras().getString(EXTRA_SEARCH_STRING));
        UiUtil.changeFragment(getSupportFragmentManager(), filterToolbarFragment, R.id.filter_activity__toolbar_frame, true, EXTRA_SEARCH_STRING);


        if (filterFragment == null)
            filterFragment = FilterFragment.newInstance(getIntent().getExtras().getString(EXTRA_SEARCH_STRING), getIntent().getExtras().getString(EXTRA_CATEGORY_ID));
        UiUtil.changeFragment(getSupportFragmentManager(), filterFragment, R.id.filter_activity__main_frame, true, EXTRA_SEARCH_STRING);

    }

    @Override
    public void showFitlerSelectionPage() {

        if (filterSelectionFragmentCommonToolbar == null)
            filterSelectionFragmentCommonToolbar = CommonToolbarFragment.newInstance(getResources().getString(R.string.fiter_product), this::showFilterPage);
        UiUtil.changeFragment(getSupportFragmentManager(), filterSelectionFragmentCommonToolbar, R.id.filter_activity__toolbar_frame, true, EXTRA_SEARCH_STRING);


        if (filterSelectionFragment == null)
            filterSelectionFragment = FilterSelectionFragment.newInstance(() -> {
                showFilterPage();
//                filterFragment.filter();
            });
        UiUtil.changeFragment(getSupportFragmentManager(), filterSelectionFragment, R.id.filter_activity__main_frame, true, EXTRA_SEARCH_STRING);


    }

}
