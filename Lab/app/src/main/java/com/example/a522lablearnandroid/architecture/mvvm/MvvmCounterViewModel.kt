package com.example.a522lablearnandroid.architecture.mvvm

import androidx.lifecycle.ViewModel
import com.example.a522lablearnandroid.utils.FirestoreHelper
import com.example.a522lablearnandroid.utils.GuestMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MvvmCounterViewModel : ViewModel() {

    private val model = MvvmCounterModel()

    // StateFlow for View to Observe
    private val _count = MutableStateFlow(model.getCount()) // 11
    val count: StateFlow<Int> = _count.asStateFlow()

    fun onIncrementClicked() {
        model.incrementCounter()
        _count.value = model.getCount()
    }

    fun onDecrementClicked() {
        model.DecrementCounter()
        _count.value = model.getCount()
    }

    private val _messages = MutableStateFlow<List<GuestMessage>>(emptyList())
    val messages = _messages.asStateFlow()

    init {
        // ทันทีที่เปิดหน้านี้ ให้เริ่ม "ฟัง" ข้อมูลจาก Firebase
        FirestoreHelper.listenToMessages { updatedList ->
            _messages.value = updatedList
        }
    }

    fun sendMessage(name: String, text: String) {
        if (name.isNotBlank() && text.isNotBlank()) {
            FirestoreHelper.addMessage(name, text) {
                // ทำอะไรต่อหลังส่งเสร็จไหม? (เช่น เคลียร์ช่องพิมพ์)
            }
        }
    }

    fun deleteMessage(id: String) {
        FirestoreHelper.deleteMessage(id)
    }
}

