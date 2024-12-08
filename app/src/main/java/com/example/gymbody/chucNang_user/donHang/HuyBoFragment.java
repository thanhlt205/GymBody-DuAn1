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

public class HuyBoFragment extends Fragment {
    private RecyclerView rcvStatusProduct;
    private StatusProductDAO statusProductDAO;
    List<StatusProduct> lstStatusProduct;
    private StatusProductAdapter statusProductAdapter;
    public HuyBoFragment() {
        // Required empty public constructor
    }
    public static HuyBoFragment newInstance() {
        HuyBoFragment fragment = new HuyBoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_huy_bo, container, false);
        rcvStatusProduct = rootView.findViewById(R.id.rcvStatusProduct);

        statusProductDAO = new StatusProductDAO(requireContext());
        lstStatusProduct = statusProductDAO.getByStatus("Đã hủy");
        statusProductAdapter = new StatusProductAdapter(requireContext(), lstStatusProduct);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 1);
        rcvStatusProduct.setLayoutManager(layoutManager);
        rcvStatusProduct.setAdapter(statusProductAdapter);
        statusProductAdapter.notifyDataSetChanged();
        return rootView;
    }
}