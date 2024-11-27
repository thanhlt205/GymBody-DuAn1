package com.example.gymbody.chucNang_user.donHang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gymbody.R;


public class DangXuLiFragment extends Fragment {

    private TextView productNameTextView, productPriceTextView, userNameTextView, userPhoneTextView, userAddressTextView, paymentMethodTextView;

    // Phương thức để tạo một Fragment với dữ liệu từ Activity
    public static DangXuLiFragment newInstance(Bundle orderInfo) {
        DangXuLiFragment fragment = new DangXuLiFragment();
        fragment.setArguments(orderInfo); // Lưu dữ liệu vào Fragment
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dang_xu_li, container, false);

        // Ánh xạ các TextView
        productNameTextView = rootView.findViewById(R.id.productNameTextView);
        productPriceTextView = rootView.findViewById(R.id.productPriceTextView);
        userNameTextView = rootView.findViewById(R.id.userNameTextView);
        userPhoneTextView = rootView.findViewById(R.id.userPhoneTextView);
        userAddressTextView = rootView.findViewById(R.id.userAddressTextView);
        paymentMethodTextView = rootView.findViewById(R.id.paymentMethodTextView);

        // Lấy thông tin từ Bundle
        Bundle args = getArguments();
        if (args != null) {
            productNameTextView.setText(args.getString("productName", "N/A"));
            productPriceTextView.setText(args.getString("productPrice", "N/A"));
            userNameTextView.setText(args.getString("userName", "N/A"));
            userPhoneTextView.setText(args.getString("userPhone", "N/A"));
            userAddressTextView.setText(args.getString("userAddress", "N/A"));
            paymentMethodTextView.setText(args.getString("paymentMethod", "N/A"));
        }

        return rootView;
    }
}

