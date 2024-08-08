// GroupViewModel.kt
package com.example.seureureuk.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seureureuk.network.RetrofitInstance
import com.example.seureureuk.data.model.GroupInfoResponse
import com.example.seureureuk.data.model.GroupSettlementResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupViewModel : ViewModel() {
    private val _groups = MutableLiveData<List<GroupInfoResponse>>()
    val groups: LiveData<List<GroupInfoResponse>> get() = _groups

    private val _groupSettlement = MutableLiveData<GroupSettlementResponse>()
    val groupSettlement: LiveData<GroupSettlementResponse> get() = _groupSettlement

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchAllGroups() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getAllGroups().execute().body()
                }
                if (response != null) {
                    _groups.value = response.data
                    Log.d("GroupViewModel", "Fetched groups: ${response.data}")
                } else {
                    Log.e("GroupViewModel", "No data received")
                }
            } catch (e: Exception) {
                _error.value = "Failed to fetch groups: ${e.message}"
                Log.e("GroupViewModel", "Error fetching groups", e)
            }
        }
    }

    fun setAllGroups(groups: List<GroupInfoResponse>) {
        _groups.value = groups
    }

    fun fetchGroupSettlement(groupId: Int) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getGroupSettlement(groupId).execute().body()
                }
                if (response != null) {
                    _groupSettlement.value = response.data
                    Log.d("GroupViewModel", "Fetched group settlement: ${response.data}")
                } else {
                    Log.e("GroupViewModel", "No data received")
                }
            } catch (e: Exception) {
                _error.value = "Failed to fetch group settlement: ${e.message}"
                Log.e("GroupViewModel", "Error fetching group settlement", e)
            }
        }
    }

    fun setGroupSettlement(groupSettlement: GroupSettlementResponse) {
        _groupSettlement.value = groupSettlement
    }

    fun fetchGroupSettlementSync(groupId: Int, callback: (GroupSettlementResponse?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getGroupSettlement(groupId).execute().body()
                }
                // response가 null이 아닌 경우 data를 콜백에 전달
                callback(response?.data)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }
}
