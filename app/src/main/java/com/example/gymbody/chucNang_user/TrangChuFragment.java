package com.example.gymbody.chucNang_user;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymbody.R;
import com.example.gymbody.adapterVideo.AnhVideoAdapter;
import com.example.gymbody.dao.AnhVideoDAO;
import com.example.gymbody.model.AnhVideoModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class TrangChuFragment extends Fragment {
    private EditText edtTimKiem;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AnhVideoDAO anhVideoDAO;
    private AnhVideoAdapter adapterAnhVideo;
    private ArrayList<AnhVideoModel> arrayList = new ArrayList<>();

    public TrangChuFragment() {
        // Required empty public constructor
    }

    public static TrangChuFragment newInstance() {
        TrangChuFragment fragment = new TrangChuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapterAnhVideo.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        edtTimKiem = view.findViewById(R.id.edtTimKiem);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        anhVideoDAO = new AnhVideoDAO(getContext());

        // Lấy dữ liệu từ cơ sở dữ liệu
        arrayList = anhVideoDAO.getAll();
        if (arrayList == null || arrayList.isEmpty()) {
            Log.e("RecyclerView", "No data found in database");
        } else {
            Log.e("RecyclerView", "Data found in database");
        }

        // Hiển thị dữ liệu vào RecyclerView
        adapterAnhVideo = new AnhVideoAdapter(getContext(), arrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterAnhVideo);

        // Thong bao thay doi du lieu
        adapterAnhVideo.notifyDataSetChanged();

        edtTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Chuyển màn tìm kiếm", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), TimKiemActivity.class));
            }
        });
        swipeRefreshLayout.setOnRefreshListener( () -> {
            arrayList.clear();
            arrayList.addAll(anhVideoDAO.getAll());
            adapterAnhVideo.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        });
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        adapterAnhVideo.notifyDataSetChanged();
    }
}