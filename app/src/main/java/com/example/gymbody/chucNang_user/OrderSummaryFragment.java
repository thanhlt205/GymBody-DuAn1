package com.example.gymbody.chucNang_user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gymbody.R;

public class OrderSummaryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_summary, container, false);

        // Ánh xạ các TextView
        TextView productNameTextView = view.findViewById(R.id.productNameTextView);
        TextView productPriceTextView = view.findViewById(R.id.productPriceTextView);
        TextView userNameTextView = view.findViewById(R.id.userNameTextView);
        TextView userPhoneTextView = view.findViewById(R.id.userPhoneTextView);
        TextView userAddressTextView = view.findViewById(R.id.userAddressTextView);
        TextView paymentMethodTextView = view.findViewById(R.id.paymentMethodTextView);

        // Nhận dữ liệu từ arguments
        Bundle args = getArguments();
        if (args != null) {
            productNameTextView.setText(args.getString("productName", "N/A"));
            productPriceTextView.setText(args.getString("productPrice", "N/A"));
            userNameTextView.setText(args.getString("userName", "N/A"));
            userPhoneTextView.setText(args.getString("userPhone", "N/A"));
            userAddressTextView.setText(args.getString("userAddress", "N/A"));
            paymentMethodTextView.setText(args.getString("paymentMethod", "N/A"));
        }

        return view;
    }
}
