package com.example.endo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.db.models.DictionaryCategoryModel
import com.example.db.models.WordsModel
import com.example.db.repositories.DictionaryCategoryRepositories
import com.example.db.repositories.WordsRepositories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryCategoryViewModel @Inject constructor(
    private val repositories: DictionaryCategoryRepositories
) : ViewModel() {

    private val _dictionaryCategoryModel: MutableStateFlow<List<DictionaryCategoryModel>?> = MutableStateFlow(null)
    val dictionaryCategoryModel = _dictionaryCategoryModel.asStateFlow()

    fun insertData(model: DictionaryCategoryModel) =
        viewModelScope.launch(Dispatchers.IO) {
            repositories.insert(model)
        }



    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            repositories.getData().collect {
                it?.let {
                    _dictionaryCategoryModel.emit(it)
                }
            }
        }
    }
}