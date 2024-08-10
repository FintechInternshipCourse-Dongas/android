package com.example.seureureuk.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seureureuk.network.RetrofitInstance
import com.example.seureureuk.data.model.GroupInfoResponse
import com.example.seureureuk.data.model.GroupCreateRequest
import com.example.seureureuk.data.model.GroupCreateResponseData
import com.example.seureureuk.data.model.GroupEntranceRequest
import com.example.seureureuk.data.model.GroupEntranceResponseData
import com.example.seureureuk.data.model.GroupMemberResponseData
import com.example.seureureuk.data.model.GroupSettlementResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class GroupViewModel : ViewModel() {
    private val _groups = MutableLiveData<List<GroupInfoResponse>>()
    val groups: LiveData<List<GroupInfoResponse>> get() = _groups

    private val _groupMembers = MutableLiveData<GroupMemberResponseData>()
    val groupMembers: LiveData<GroupMemberResponseData> get() = _groupMembers

    private val _groupSettlements = MutableLiveData<GroupSettlementResponseData>()
    val groupSettlements: LiveData<GroupSettlementResponseData> get() = _groupSettlements

    private val _groupEntranceResponse = MutableLiveData<GroupEntranceResponseData>()
    val groupEntranceResponse: LiveData<GroupEntranceResponseData> get() = _groupEntranceResponse

    private val _createGroupResponse = MutableLiveData<GroupCreateResponseData>()
    val createGroupResponse: LiveData<GroupCreateResponseData> get() = _createGroupResponse

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

    fun fetchGroupMembers(groupId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getGroupMembers(groupId)

                if (response.isSuccessful) {
                    val fetchGroupMembersResponseData = response.body()
                    if (fetchGroupMembersResponseData != null) {
                        val fetchGroupMembersResponse = fetchGroupMembersResponseData.data
                        if (fetchGroupMembersResponse != null) {
                            _groupMembers.value = fetchGroupMembersResponseData
                            Log.d("GroupViewModel", "Members: $fetchGroupMembersResponse")
                        } else {
                            Log.e("GroupViewModel", "fetchGroupMembersResponse is null")
                            _error.value = "Error: fetchGroupMembersResponse is null"
                        }
                    } else {
                        Log.e("GroupViewModel", "fetchGroupMembersResponseData is null")
                        _error.value = "Error: fetchGroupMembersResponseData is null"
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("GroupViewModel", "fetchGroupMembers failed with status: ${response.code()}")
                    _error.value = "Failed to fetch group members: $errorBody"
                }

            } catch (e: Exception) {
                Log.e("GroupViewModel - fetchGroupMembers", "fetchGroupMembers Error: ${e.message}")
                _error.value = "fetchGroupMembers Error: ${e.message}"
            }
        }
    }

   fun fetchGroupSettlements(groupId: Int) {
       viewModelScope.launch {
           try {
               val response = RetrofitInstance.api.getGroupSettlements(groupId)

               if (response.isSuccessful) {
                   val fetchGroupSettlementsResponseData = response.body()
                   if (fetchGroupSettlementsResponseData != null) {
                       val fetchGroupSettlementsResponse = fetchGroupSettlementsResponseData.data
                       if (fetchGroupSettlementsResponse != null) {
                           _groupSettlements.value = fetchGroupSettlementsResponseData
                           Log.d("GroupViewModel", "Settlements: $fetchGroupSettlementsResponse")
                       } else {
                           Log.e("GroupViewModel", "fetchGroupSettlementsResponse is null")
                           _error.value = "Error: fetchGroupSettlementsResponse is null"
                       }
                   } else {
                       Log.e("GroupViewModel", "fetchGroupSettlementsResponseData is null")
                       _error.value = "Error: fetchGroupSettlementsResponseData is null"
                   }
               } else {
                   val errorBody = response.errorBody()?.string()
                   Log.e("GroupViewModel", "fetchGroupSettlements failed with status: ${response.code()}")
                   _error.value = "Failed to fetch settlements: $errorBody"
               }
           } catch (e: Exception) {
               Log.e("GroupViewModel- fetchGroupSettlements", "fetchGroupSettlements Error: ${e.message}")
               _error.value = "fetchGroupSettlements Error: ${e.message}"
           }
       }
   }


    fun createGroup(groupName: String) {
        viewModelScope.launch {
            try {
                val request = GroupCreateRequest(groupName)
                val response = RetrofitInstance.api.createGroup(request)

                if (response.isSuccessful) {
                    val groupCreateResponseData = response.body()
                    if (groupCreateResponseData != null) {
                        val groupCreateResponse = groupCreateResponseData.data
                        if (groupCreateResponse != null) {
                            _createGroupResponse.value = groupCreateResponseData
                            Log.d("GroupViewModel", "Created Group: ${groupCreateResponse.groupId}")
                        } else {
                            Log.e("GroupViewModel", "GroupCreateResponse is null")
                            _error.value = "Error: GroupCreateResponse is null"
                        }
                    } else {
                        Log.e("GroupViewModel", "GroupCreateResponseData is null")
                        _error.value = "Error: GroupCreateResponseData is null"
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("GroupViewModel", "createGroup failed with status: ${response.code()}")
                    _error.value = "Failed to create group: $errorBody"
                }
            } catch (e: Exception) {
                Log.e("GroupViewModel- createGroup", "createGroup Error: ${e.message}")
                _error.value = "createGroup Error: ${e.message}"
            }
        }
    }

    fun enterGroupWithInviteCode(invitationCode: String) {
        viewModelScope.launch {
            try {
                // GroupEntranceRequest 객체 생성
                val request = GroupEntranceRequest(invitationCode)

                // 네트워크 요청을 코루틴 내에서 수행
                val response = RetrofitInstance.api.enterGroupWithInviteCode(request)

                // 응답 처리
                if (response.isSuccessful) {
                    val groupEntranceResponseData = response.body()
                    if (groupEntranceResponseData != null) {
                        _groupEntranceResponse.value = groupEntranceResponseData
                        Log.d("GroupViewModel", "Entered Group: ${groupEntranceResponseData.data.groupId}")
                    } else {
                        Log.e("GroupViewModel", "GroupEntranceResponseData is null")
                        _error.value = "Error: GroupEntranceResponseData is null"
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("GroupViewModel", "enterGroupWithInviteCode failed with status: ${response.code()} and error: $errorBody")
                    _error.value = "Failed to enter group: $errorBody"
                }

            } catch (e: Exception) {
                Log.e("GroupViewModel", "Unexpected error: ${e.message}")
                _error.value = "Unexpected error: ${e.message}"
            }
        }
    }

}


