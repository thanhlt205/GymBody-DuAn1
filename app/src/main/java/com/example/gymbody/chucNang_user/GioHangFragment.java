package com.example.gymbody.chucNang_user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymbody.R;
import com.example.gymbody.adapterUser.ProductAdapter;
import com.example.gymbody.dao.ProductDAO;
import com.example.gymbody.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GioHangFragment extends Fragment {

    private RecyclerView productRecyclerView;
    private ProductDAO productDAO;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();

    public GioHangFragment() {
        // Required empty public constructor
    }

    public static GioHangFragment newInstance() {
        return new GioHangFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);

        // Khởi tạo RecyclerView
        productRecyclerView = view.findViewById(R.id.productRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        productRecyclerView.setLayoutManager(gridLayoutManager);

        // Khởi tạo ProductDAO và lấy danh sách sản phẩm
        productDAO = new ProductDAO(getActivity());
        productList = productDAO.getAllProducts();

        // Khởi tạo và thiết lập Adapter
        productAdapter = new ProductAdapter(productList, product -> {
            // Xử lý khi sản phẩm được click
        });
        productRecyclerView.setAdapter(productAdapter);

        // Trả về view
        return view;
    }
}
