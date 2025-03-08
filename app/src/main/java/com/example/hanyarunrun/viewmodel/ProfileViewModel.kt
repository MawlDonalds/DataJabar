package com.example.hanyarunrun.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hanyarunrun.data.AppDatabase
import com.example.hanyarunrun.data.ProfileEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).profileDao()

    private val _profile = MutableStateFlow<ProfileEntity?>(null)
    val profile: StateFlow<ProfileEntity?> = _profile

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            dao.getProfile().collect { profileData ->
                _profile.value = profileData
            }
        }
    }

    fun updateProfile(name: String, studentId: String, email: String) {
        viewModelScope.launch {
            val currentProfile = _profile.value
            val updatedProfile = ProfileEntity(
                id = 1,
                name = name,
                studentId = studentId,
                email = email,
                image = currentProfile?.image // Jangan hapus gambar yang sudah ada
            )

            if (currentProfile == null) {
                dao.insert(updatedProfile)
            } else {
                dao.update(updatedProfile)
            }
        }
    }

    fun updateProfileImage(uri: Uri, context: Context) {
        viewModelScope.launch {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val byteArray = inputStream?.readBytes()
                inputStream?.close()

                if (byteArray != null) {
                    dao.updateProfileImage(byteArray)
                    val currentProfile = _profile.value
                    _profile.value = currentProfile?.copy(image = byteArray)
                }

                // Simpan gambar ke penyimpanan internal
                saveImageToInternalStorage(uri, context)

            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error updating profile image", e)
            }
        }
    }

    private fun saveImageToInternalStorage(uri: Uri, context: Context): File {
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val file = File(context.filesDir, "profile.jpg")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()
        return file
    }
}
