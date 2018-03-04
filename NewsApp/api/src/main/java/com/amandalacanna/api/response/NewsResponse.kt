package com.amandalacanna.api.response

import com.amandalacanna.data.Article
import java.io.Serializable

data class NewsResponse (val status: String, val totalResults: Int , val articles: List<Article>): Serializable