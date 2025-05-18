//package com.sporticast.viewmodel
//
//            import androidx.lifecycle.ViewModel
//            import androidx.lifecycle.viewModelScope
//            import com.sporticast.dto.request.AddAudiobookRequest
//            import com.sporticast.model.AudiobookDTO
//            import com.sporticast.model.Book
//            import com.sporticast.model.PlaylistDTO
//            import com.sporticast.screens.data.api.PlaylistApi
//            import com.sporticast.screens.data.api.RetrofitService
//            import kotlinx.coroutines.flow.MutableStateFlow
//            import kotlinx.coroutines.flow.StateFlow
//            import kotlinx.coroutines.launch
//
//            class PlaylistViewModel() : ViewModel() {
//
//                private val _audiobooks = MutableStateFlow<List<Book>>(emptyList())
//                val audiobooks: StateFlow<List<Book>> = _audiobooks
//
//                private val _playlists = MutableStateFlow<List<PlaylistDTO>>(emptyList())
//                val playlists: StateFlow<List<PlaylistDTO>> = _playlists
//
//                private val _errorMessage = MutableStateFlow<String?>(null)
//                val errorMessage: StateFlow<String?> = _errorMessage
//
//                fun getAudiobooksInPlaylist(playlistId: Int) {
//                    viewModelScope.launch {
//                        try {
//                            val response = RetrofitService.playlistApi.getAudiobooksInPlaylist(playlistId)
//                            if (response.isSuccessful) {
//                                _audiobooks.value = response.body() ?: emptyList()
//                            } else {
//                                _errorMessage.value = "Failed to load audiobooks: ${response.message()}"
//                            }
//                        } catch (e: Exception) {
//                            _errorMessage.value = "Error: ${e.message}"
//                        }
//                    }
//                }
//                fun addAudiobookToPlaylist(playlistId: Int, audiobookId: Int) {
//                    viewModelScope.launch {
//                        try {
//                           val request = AddAudiobookRequest(
//                                playlistId = playlistId,
//                                audiobookId = audiobookId,
//                                playlistName = "Default Playlist Name"
//                            )
//                            val response = RetrofitService.playlistApi.createOrAdd(request)
//                            if (response.isSuccessful) {
//                                _errorMessage.value = "Audiobook added successfully!"
//                            } else {
//                                _errorMessage.value = "Failed to add audiobook: ${response.message()}"
//                            }
//                        } catch (e: Exception) {
//                            _errorMessage.value = "Error: ${e.message}"
//                        }
//                    }
//                }
//                fun getAllPlaylists() {
//                    viewModelScope.launch {
//                        try {
//                            val response = RetrofitService.playlistApi.getAllPlaylists()
//                            if (response.isSuccessful) {
//                                _playlists.value = response.body() ?: emptyList()
//                            } else {
//                                _errorMessage.value = "Failed to load playlists: ${response.message()}"
//                            }
//                        } catch (e: Exception) {
//                            _errorMessage.value = "Error: ${e.message}"
//                        }
//                    }
//                }
//            }