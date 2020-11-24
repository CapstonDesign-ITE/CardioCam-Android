//package org.gradproj.CardioCheckIn.activity
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.KeyEvent
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class SearchAddressActivity : AppCompatActivity() {
//    var mAdaper: AdapterKeywordSearchList = AdapterKeywordSearchList()
//    private var addressStr: String? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_address)
//        setCustomAppBar()
//        binding.editSearchAddress.setOnKeyListener({ view, keyCode, keyEvent ->
//            if (keyEvent.getAction() === KeyEvent.ACTION_DOWN && keyCode === KeyEvent.KEYCODE_ENTER) {
//                address
//                return@setOnKeyListener true
//            }
//            false
//        })
//        binding.recyclerAddress.setLayoutManager(
//            LinearLayoutManager(
//                this,
//                LinearLayoutManager.VERTICAL,
//                false
//            )
//        )
//        address
//        setFinishAction()
//        val itemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
//        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_vertical_1dp))
//        binding.recyclerAddress.addItemDecoration(itemDecoration)
//        binding.recyclerAddress.setAdapter(mAdaper)
//    }
//
//    private fun setCustomAppBar() {
//        val customAppBar = CustomAppBar(this, supportActionBar)
//        customAppBar.setCustomAppBar("주소 검색")
//        customAppBar.setBackClickListener({ v -> finish() })
//    }
//
//    fun setFinishAction() {
//        mAdaper.setOnItemClickListener({ v, address, placeName ->
//            val intentAddress = Intent()
//            intentAddress.putExtra("select Data", address + placeName)
//            setResult(RESULT_OK, intentAddress)
//            finish()
//        })
//    }
//
//    private val address: Unit
//        private get() {
//            addressStr = binding.editSearchAddress.getText().toString()
//            Singleton.KakaoMaoApi.categoryList(1, addressStr)
//                .enqueue(object : Callback<KaKaoMapSearchModel?>() {
//                    fun onResponse(
//                        call: Call<KaKaoMapSearchModel?>?,
//                        response: Response<KaKaoMapSearchModel?>
//                    ) {
//                        if (response.code() === 200) {
//                            if (response.body() != null) {
//                                setResultView(true)
//                                mAdaper.setSearchList(response.body())
//                            }
//                        } else if (response.code() === 400) setResultView(false)
//                        Log.d(TAG, "response success" + response.code())
//                    }
//
//                    fun onFailure(call: Call<KaKaoMapSearchModel?>?, t: Throwable) {
//                        Log.d(TAG, "OnFailure$t")
//                    }
//                })
//        }
//
//    private fun setResultView(hasData: Boolean) {
//        if (hasData) {
//            binding.editSearchAddress.setBackground(resources.getDrawable(R.drawable.rounded_rectangle_outline_red))
//            binding.tvSearchTip.setVisibility(View.INVISIBLE)
//            binding.tvSearchTips.setVisibility(View.INVISIBLE)
//            binding.recyclerAddress.setVisibility(View.VISIBLE)
//        } else {
//            binding.editSearchAddress.setBackground(resources.getDrawable(R.drawable.rounded_rectangle_outline_8dp))
//            binding.tvSearchTip.setVisibility(View.VISIBLE)
//            binding.tvSearchTips.setVisibility(View.VISIBLE)
//            binding.recyclerAddress.setVisibility(View.INVISIBLE)
//        }
//    }
//
//    companion object {
//        private var binding: ActivitySearchAddressBinding? = null
//        private const val TAG = "SEARCH_ADDRESS"
//    }
//}