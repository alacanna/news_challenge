package com.amandalacanna.api.response

import com.amandalacanna.data.Article

data class NewsResponse (val status: String, val totalResults: Int , val articles: List<Article>)