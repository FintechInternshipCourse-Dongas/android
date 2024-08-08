package com.example.seureureuk.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seureureuk.data.api.RetrofitClient
import com.example.seureureuk.data.model.GroupInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupViewModel : ViewModel() {
    private val _groups = MutableLiveData<List<GroupInfoResponse>>()
    val groups: LiveData<List<GroupInfoResponse>> get() = _groups

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchAllGroups() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.instance.getAllGroups().execute().body()
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
}
