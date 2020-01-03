package com.mag.digikala.View;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.mag.digikala.Controller.Activities.CardActivity;
import com.mag.digikala.R;
import com.mag.digikala.Var.Constants;
import com.mag.digikala.viewmodel.toolbar.ProductDetailToolbarViewModel;


public class ProductDetailToolbarFragment extends Fragment {

    private ProductDetailToolbarViewModel viewModel;

    private TextView cardNumber;
    private MaterialButton cardBtn, backBtn;

    public static ProductDetailToolbarFragment newInstance() {

        Bundle args = new Bundle();

        ProductDetailToolbarFragment fragment = new ProductDetailToolbarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductDetailToolbarFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ProductDetailToolbarViewModel.class);
        viewModel.loadData();
        viewModel.getNumberOfCardProducts().observe(this, numberOfCardProducts -> {
            if (numberOfCardProducts == 0) {
                cardNumber.setBackgroundColor(getResources().getColor(R.color.nothing));
                cardNumber.setText(Constants.EMPTY_CHAR);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    cardNumber.setBackground(getResources().getDrawable(R.drawable.cart_counter));
                }
                cardNumber.setText(String.valueOf(numberOfCardProducts));
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail_toolbar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findComponents(view);

        setEvents();

    }

    private void setEvents() {
        cardBtn.setOnClickListener(cardNumberView -> getActivity().startActivity(CardActivity.newIntent(getContext())));
        backBtn.setOnClickListener(backBtnView -> getActivity().finish());
    }

    private void findComponents(@NonNull View view) {
        cardNumber = view.findViewById(R.id.product_detail_toolbar_fragment__card_number);
        cardBtn = view.findViewById(R.id.product_detail_toolbar_fragment__cart_btn);
        backBtn = view.findViewById(R.id.toolbar_fragment__back_btn);
    }

}
