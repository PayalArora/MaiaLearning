//package com.maialearning
//
//import androidx.paging.ExperimentalPagingApi
//import androidx.paging.PagingSource
//import androidx.paging.PagingSource.LoadResult.Page
//import androidx.paging.PagingState
//import com.google.gson.JsonObject
//import com.maialearning.model.UniversitiesSearchModel
//import com.maialearning.network.AllAPi
//import com.maialearning.network.UseCaseResult
//import com.maialearning.parser.SearchParser
//
//class SearchSource(private val apiService: AllAPi): PagingSource<Int, List<UniversitiesSearchModel>>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,List<UniversitiesSearchModel>> {
//
//        return try {
//            val currentLoadingPageKey = params.key ?: 1
//            val response = apiService.searchUniversties().await()
//
//            val parser: ArrayList<UniversitiesSearchModel>? = SearchParser(response).parseJson().university_list
//            val responseData = mutableListOf<UniversitiesSearchModel>()
//            val data = parser ?: emptyList()
//            responseData.addAll(data)
//
//            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
//
//            return LoadResult.Page(
//                data = responseData,
//                prevKey = prevKey,
//                nextKey = currentLoadingPageKey.plus(1) } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//
//    }
//
//    @OptIn(ExperimentalPagingApi::class)
//    override fun getRefreshKey(state: PagingState<Int, List<UniversitiesSearchModel>>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//
//        }
//    }
//}