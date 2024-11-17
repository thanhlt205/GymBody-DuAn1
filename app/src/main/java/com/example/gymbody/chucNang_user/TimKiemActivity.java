package com.example.gymbody.chucNang_user;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymbody.R;
import com.example.gymbody.adapterVideo.AnhVideoAdapter;
import com.example.gymbody.dao.AnhVideoDAO;
import com.example.gymbody.model.anhVideoModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class TimKiemActivity extends AppCompatActivity implements Filterable {

    private RecyclerView recyclerView;
    private AnhVideoDAO anhVideoDAO;
    ArrayList<anhVideoModel> arrayList = new ArrayList<>();
    ArrayList<anhVideoModel> filteredList ;
    TextInputEditText edtSearch;
    TextInputLayout tipTimKIem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tim_kiem);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtSearch = findViewById(R.id.edtSearch);
        tipTimKIem = findViewById(R.id.tipTimKIem);
        edtSearch.requestFocus();
        recyclerView = findViewById(R.id.rcvListTimKiem);
        anhVideoDAO = new AnhVideoDAO(this);

        arrayList = anhVideoDAO.getAll();
        Log.e("DEBUG", "Size of arrayList: " + arrayList.size());
        filteredList = new ArrayList<>(arrayList);
        // Lấy dữ liệu từ cơ sở dữ liệu
        // Hiển thị dữ liệu vào RecyclerView
        AnhVideoAdapter adapterAnhVideo = new AnhVideoAdapter(this, filteredList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterAnhVideo);

        String search = edtSearch.getText().toString().trim();
        Log.e("TAG", "search: "+ search );


        tipTimKIem.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getFilter().filter(editable.toString());
            }
        });

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                List<anhVideoModel> filteredList = new ArrayList<>();
                if (charSequence == null || charSequence.length() == 0) {
                    filteredList.addAll(arrayList);
                } else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    Log.e("filterPattern", "filterPattern: "+filterPattern );
                    for (anhVideoModel item : arrayList) {
                        Log.e("item.getTen()", "item.getTen(): "+item.getTen() );
                        Log.e("item", "item: "+item );
                        if (item.getTen().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                filterResults.values = filteredList;
                filterResults.count = filteredList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults.values != null) {
                    filteredList.clear();
                    filteredList.addAll((List<anhVideoModel>) filterResults.values);
                    if (recyclerView.getAdapter() != null) {
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(TimKiemActivity.this, "Không có kết quả tìm kiếm", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        if (fragmentManager.getBackStackEntryCount() > 0) {
//            // Nếu có fragment trong Back Stack, quay lại fragment trước đó
//            fragmentManager.popBackStack();
//            Toast.makeText(this, "Quay lại", Toast.LENGTH_SHORT).show();
//        } else {
//            // Nếu không có fragment trong Back Stack, thực hiện hành động mặc định của nút back
//            super.onBackPressed();
//            Toast.makeText(this, "K ôs có gì", Toast.LENGTH_SHORT).show();
//        }
//    }
}