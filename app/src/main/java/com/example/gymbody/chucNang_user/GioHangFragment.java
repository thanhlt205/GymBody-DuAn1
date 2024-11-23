package com.example.gymbody.chucNang_user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
    private List<Product> originalProductList = new ArrayList<>(); // Danh sách gốc
    private List<Product> filteredProductList = new ArrayList<>(); // Danh sách hiển thị
    private EditText searchEditText;

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
        searchEditText = view.findViewById(R.id.searchEditText);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        productRecyclerView.setLayoutManager(gridLayoutManager);

        // Khởi tạo ProductDAO và lấy danh sách sản phẩm
        productDAO = new ProductDAO(getActivity());
        originalProductList = productDAO.getAllProducts();
        filteredProductList.addAll(originalProductList); // Sao chép toàn bộ danh sách ban đầu

        // Khởi tạo và thiết lập Adapter
        productAdapter = new ProductAdapter(filteredProductList, product -> {
            // Xử lý khi sản phẩm được click (có thể thêm logic tùy chỉnh ở đây)
        });
        productRecyclerView.setAdapter(productAdapter);

        // Lắng nghe thay đổi trên thanh tìm kiếm
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                // Gọi bộ lọc khi nội dung thay đổi
                getFilter().filter(editable.toString());
            }
        });

        return view;
    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                List<Product> tempFilteredList = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0) {
                    // Nếu nội dung tìm kiếm trống, hiển thị toàn bộ danh sách gốc
                    tempFilteredList.addAll(originalProductList);
                } else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();

                    // Lọc sản phẩm dựa trên tên
                    for (Product item : originalProductList) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            tempFilteredList.add(item);
                        }
                    }
                }

                filterResults.values = tempFilteredList;
                filterResults.count = tempFilteredList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                // Xóa danh sách hiển thị cũ và cập nhật lại
                filteredProductList.clear();
                if (filterResults.values != null) {
                    filteredProductList.addAll((List<Product>) filterResults.values);
                }
                productAdapter.notifyDataSetChanged();

                // Hiển thị thông báo nếu không có kết quả
                if (filteredProductList.isEmpty()) {
                    Toast.makeText(getContext(), "Không có kết quả tìm kiếm", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}
