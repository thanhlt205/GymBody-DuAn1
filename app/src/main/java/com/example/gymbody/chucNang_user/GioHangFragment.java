package com.example.gymbody.chucNang_user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymbody.R;
import com.example.gymbody.adapterUser.ProductAdapter;
import com.example.gymbody.dao.ProductDAO;
import com.example.gymbody.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class GioHangFragment extends Fragment {

    private RecyclerView productRecyclerView;
    private ProductDAO productDAO;
    private ProductAdapter productAdapter;
    private List<Product> originalProductList = new ArrayList<>(); // Danh sách gốc
    private List<Product> filteredProductList = new ArrayList<>(); // Danh sách hiển thị
    private EditText searchEditText;
    private AddProductDialog addProductDialog; // Để lưu trạng thái của Dialog
    private FirebaseAuth mAuth;

    // ActivityResultLauncher để xử lý phản hồi khi chọn ảnh
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    public GioHangFragment() {
        // Required empty public constructor
    }

    public static GioHangFragment newInstance() {
        return new GioHangFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        // Đăng ký ActivityResultLauncher để nhận kết quả khi chọn ảnh
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            Uri selectedImageUri = result.getData().getData();
                            // Xử lý URI của ảnh đã chọn (chuyển URI này vào dialog để hiển thị ảnh)
                            addProductDialog.setSelectedImageUri(selectedImageUri);
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        productAdapter = new ProductAdapter(filteredProductList, new ProductAdapter.OnProductActionListener() {
            @Override
            public void onDelete(Product product) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn chắc chắn muốn xóa chứ?");
                builder.setPositiveButton("Có", (dialog, which) -> {

                    // Xóa sản phẩm khỏi cơ sở dữ liệu
                    productDAO.deleteProduct(product);

                    // Xóa sản phẩm khỏi danh sách hiển thị
                    originalProductList.remove(product);
                    filteredProductList.remove(product);

                    // Cập nhật RecyclerView
                    productAdapter.notifyDataSetChanged();

                    Toast.makeText(getContext(), "Sản phẩm đã được xóa", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
                builder.show();
            }

            @Override
            public void onClick(Product product) {
                // Xử lý sự kiện click (ví dụ: xem chi tiết sản phẩm)
                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                intent.putExtra("product", product);

                // Truyền thông tin sản phẩm qua Intent
                intent.putExtra("name", product.getName());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("image", product.getImage());

                // Bắt đầu Activity
                startActivity(intent);
            }
        });
        productRecyclerView.setAdapter(productAdapter);

        // Lắng nghe thay đổi trên thanh tìm kiếm
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Gọi bộ lọc khi nội dung thay đổi
                getFilter().filter(editable.toString());
            }
        });

        // Xử lý nút thêm sản phẩm
        FloatingActionButton addProductButton = view.findViewById(R.id.addProductButton);

        String email = mAuth.getCurrentUser().getEmail().trim();
        if (email.equals("admin@gmail.com")) {
            addProductButton.setVisibility(View.VISIBLE);

            addProductButton.setOnClickListener(v -> {
//            // Khởi tạo AddProductDialog và truyền vào ActivityResultLauncher
                addProductDialog = new AddProductDialog(getContext(), newProduct -> {
                    // Xử lý khi sản phẩm được thêm
                    productDAO.addProduct(newProduct);
                    originalProductList.add(newProduct);
                    filteredProductList.add(newProduct);
                    productAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Sản phẩm đã được thêm thành công", Toast.LENGTH_SHORT).show();
                });

                // Truyền ActivityResultLauncher vào Dialog để xử lý chọn ảnh
                addProductDialog.setImagePickerLauncher(imagePickerLauncher);
                addProductDialog.show();
            });
        } else {
            addProductButton.setVisibility(View.GONE);
        }
        return view;
    }

    // Bộ lọc tìm kiếm sản phẩm
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
                // Cập nhật danh sách hiển thị
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
