package com.ireland.ager.main.repository

interface SearchRepositoryCustom {
    fun findFirst5SearchesOrderByPopularDesc(): List<String>
}