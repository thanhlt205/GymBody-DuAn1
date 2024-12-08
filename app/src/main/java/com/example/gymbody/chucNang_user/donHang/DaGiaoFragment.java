package com.example.gymbody.chucNang_user.donHang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymbody.R;
import com.example.gymbody.adapterAdmin.StatusProductAdapter;
import com.example.gymbody.dao.StatusProductDAO;
import com.example.gymbody.model.StatusProduct;

import java.util.List;

public class DaGiaoFragment extends Fragment {
    private RecyclerView rcvStatusProduct;
    private StatusProductDAO statusProductDAO;
    List<StatusProduct> lstStatusProduct;
    private StatusProductAdapter statusProductAdapter;
    public DaGiaoFragment() {
        // Required empty public constructor
    }
    public static DaGiaoFragment newInstance() {
        DaGiaoFragment fragment = new DaGiaoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_da_giao, container, false);
        rcvStatusProduct = rootView.findViewById(R.id.rcvStatusProduct);

        statusProductDAO = new StatusProductDAO(requireContext());
        lstStatusProduct = statusProductDAO.getByStatus("Đã giao");
        statusProductAdapter = new StatusProductAdapter(requireContext(), lstStatusProduct);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 1);
        rcvStatusProduct.setLayoutManager(layoutManager);
        rcvStatusProduct.setAdapter(statusProductAdapter);
        statusProductAdapter.notifyDataSetChanged();
        return rootView;
    }
}