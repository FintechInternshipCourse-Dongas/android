package com.example.seureureuk.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seureureuk.data.model.GroupCreateRequest
import com.example.seureureuk.data.model.GroupCreateResponseData
import com.example.seureureuk.data.model.SettlementAddRequest
import com.example.seureureuk.data.model.SettlementAddResponseData
import com.example.seureureuk.network.RetrofitInstance
import kotlinx.coroutines.launch

class SettlementViewModel: ViewModel() {
    private val _addSettlementResponse = MutableLiveData<SettlementAddResponseData>()
    val addSettlementResponse: LiveData<SettlementAddResponseData> get() = _addSettlementResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    fun addSettlement(settlementAddRequest: SettlementAddRequest) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.addSettlement(settlementAddRequest)

                if (response.isSuccessful) {
                    val addSettlementResponseData = response.body()
                    if (addSettlementResponseData != null) {
                        val addSettlementResponse = addSettlementResponseData.data
                        if (addSettlementResponse != null) {
                            _addSettlementResponse.value = addSettlementResponseData
                            Log.d("GroupViewModel", "Added Settlement: ${addSettlementResponse.settlementId}")
                        } else {
                            Log.e("GroupViewModel", "addSettlementResponse is null")
                            _error.value = "Error: addSettlementResponse is null"
                        }
                    } else {
                        Log.e("GroupViewModel", "addSettlementResponseData is null")
                        _error.value = "Error: addSettlementResponseData is null"
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("GroupViewModel", "addSettlement failed with status: ${response.code()}")
                    _error.value = "Failed to add settlement: $errorBody"
                }
            } catch (e: Exception) {
                Log.e("GroupViewModel- addSettlement", "addSettlement Error: ${e.message}")
                _error.value = "addSettlement Error: ${e.message}"
            }
        }
    }

}