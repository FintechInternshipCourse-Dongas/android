package com.example.seureureuk.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seureureuk.data.model.SettlementAddRequest
import com.example.seureureuk.data.model.SettlementAddResponseData
import com.example.seureureuk.data.model.SettlementParticipantResponseData
import com.example.seureureuk.network.RetrofitInstance
import kotlinx.coroutines.launch

class SettlementViewModel: ViewModel() {
    private val _getSettlementParticipantsResponse =
        MutableLiveData<SettlementParticipantResponseData>()
    val getSettlementParticipantsResponse: LiveData<SettlementParticipantResponseData> get() = _getSettlementParticipantsResponse

    private val _addSettlementResponse = MutableLiveData<SettlementAddResponseData>()
    val addSettlementResponse: LiveData<SettlementAddResponseData> get() = _addSettlementResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getSettlementParticipants(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getSettlementParticipants(id)

                if (response.isSuccessful) {
                    val getSettlementParticipantsResponseData = response.body()
                    if (getSettlementParticipantsResponseData != null) {
                        val getSettlementParticipantsResponse =
                            getSettlementParticipantsResponseData.data
                        if (getSettlementParticipantsResponse != null) {
                            _getSettlementParticipantsResponse.value =
                                getSettlementParticipantsResponseData
                            Log.d(
                                "GroupViewModel",
                                "Settlement Participants: ${getSettlementParticipantsResponse}"
                            )
                        } else {
                            Log.e("GroupViewModel", "getSettlementParticipantsResponse is null")
                            _error.value = "Error: getSettlementParticipantsResponse is null"
                        }
                    } else {
                        Log.e("GroupViewModel", "getSettlementParticipantsResponse is null")
                        _error.value = "Error: getSettlementParticipantsResponse is null"
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(
                        "GroupViewModel",
                        "getSettlementParticipantsResponse failed with status: ${response.code()}"
                    )
                    _error.value = "Failed to get settlement participants: $errorBody"
                }
            } catch (e: Exception) {
                Log.e(
                    "GroupViewModel- getSettlementParticipantsResponse",
                    "getSettlementParticipantsResponse Error: ${e.message}"
                )
                _error.value = "getSettlementParticipantsResponse Error: ${e.message}"
            }
        }
    }

    fun addSettlement(groupId: Int, settlementAddRequest: SettlementAddRequest) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.addSettlement(groupId, settlementAddRequest)

                if (response.isSuccessful) {
                    val addSettlementResponseData = response.body()
                    if (addSettlementResponseData != null) {
                        val addSettlementResponse = addSettlementResponseData.data
                        if (addSettlementResponse != null) {
                            _addSettlementResponse.value = addSettlementResponseData
                            Log.d(
                                "GroupViewModel",
                                "Added Settlement: ${addSettlementResponse.id}"
                            )
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
                    Log.e(
                        "GroupViewModel",
                        "addSettlement failed with status: ${response.code()}"
                    )
                    _error.value = "Failed to add settlement: $errorBody"
                }
            } catch (e: Exception) {
                Log.e("GroupViewModel- addSettlement", "addSettlement Error: ${e.message}")
                _error.value = "addSettlement Error: ${e.message}"
            }
        }
    }
}