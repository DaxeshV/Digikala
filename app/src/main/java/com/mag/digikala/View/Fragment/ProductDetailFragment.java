package com.mag.digikala.View.Fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.mag.digikala.Controller.Activities.CardActivity;
import com.mag.digikala.Model.Adapter.SliderViewPagerAdapter;
import com.mag.digikala.Model.Adapter.SpinnerAdapter;
import com.mag.digikala.Model.ProductAttributesRepository;
import com.mag.digikala.R;
import com.mag.digikala.Repository.CardRepository;
import com.mag.digikala.databinding.FragmentProductDetailBinding;
import com.mag.digikala.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailFragment extends Fragment {

    public static final String ARG_MECHANDICE = "arg_mechandice";

    private FragmentProductDetailBinding binding;
    private ProductViewModel viewModel;

    private SliderViewPagerAdapter sliderAdapter;


    public static ProductDetailFragment newInstance(String merchandiceId) {

        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MECHANDICE, merchandiceId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ProductViewModel(getArguments().getString(ARG_MECHANDICE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_product_detail, container, false);
        binding.setProductViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter();

        setSpinners();

        viewModel.getImagesSrc().observe(this, strings -> sliderAdapter.setImagesFragment(strings));

        binding.productDetailFragmentProductRegularPrice.setPaintFlags(binding.productDetailFragmentProductSalePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        binding.productDetailFragmentCardBtn.setOnClickListener(view1 -> {

            CardRepository.getInstance().addToCard(viewModel.getProduct());
            getActivity().startActivity(CardActivity.newIntent(getContext()));

        });

    }

    private void setAdapter() {
        sliderAdapter = new SliderViewPagerAdapter(getFragmentManager());
        binding.productDetailActivityViewPager.setAdapter(sliderAdapter);
    }


    // Should be changed
    private void setSpinners() {
        List<String> spinnerColorArray = new ArrayList<>();
        for (ProductAttributesRepository.Term term : ProductAttributesRepository.getInstance().getAttributeById("3").getTerms())
            spinnerColorArray.add(term.getName());
        SpinnerAdapter colorAdapter = new SpinnerAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                spinnerColorArray
        );
        binding.productDetailFragmentColorSpinner.setAdapter(colorAdapter);


        List<String> spinnerSizeArray = new ArrayList<>();
        for (ProductAttributesRepository.Term term : ProductAttributesRepository.getInstance().getAttributeById("4").getTerms())
            spinnerSizeArray.add(term.getName());
        SpinnerAdapter sizeAdapter = new SpinnerAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                spinnerSizeArray
        );
        binding.productDetailFragmentSizeSpinner.setAdapter(sizeAdapter);

    }

}