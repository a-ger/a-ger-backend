package com.ireland.ager.main.repository

import com.ireland.ager.main.entity.Search
import org.springframework.data.jpa.repository.JpaRepository

interface SearchRepository : JpaRepository<Search?, Long?>, SearchRepositoryCustom