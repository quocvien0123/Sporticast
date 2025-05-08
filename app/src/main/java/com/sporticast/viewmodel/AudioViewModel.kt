//package com.sporticast.viewmodel
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import com.sporticast.dto.request.BookRequest
//import com.sporticast.screens.data.api.RetrofitService
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class AudioBookViewModel : ViewModel() {
//
//    // Khởi tạo apiService từ RetrofitService
//    private val apiService = RetrofitService.adminManagerApi
//
//    fun addAudioBook(book: BookRequest) {
//        // Gọi API thêm sách từ apiService
//        apiService.addAudioBook(book).enqueue(object : Callback<List<BookRequest>> {
//            override fun onResponse(
//                call: Call<List<BookRequest>>,
//                response: Response<List<BookRequest>>
//            ) {
//                if (response.isSuccessful) {
//                    // Xử lý thành công
//                    Log.d("BOOK_ADD", "Thêm sách thành công: ${response.body()}")
//                } else {
//                    // Xử lý thất bại
//                    Log.e("BOOK_ADD", "Lỗi response: ${response.code()} - ${response.errorBody()?.string()}")
//                }
//            }
//
//            override fun onFailure(call: Call<List<BookRequest>>, t: Throwable) {
//                // Xử lý khi kết nối thất bại
//                Log.e("BOOK_ADD", "Lỗi kết nối: ${t.message}")
//            }
//        })
//    }
//}
