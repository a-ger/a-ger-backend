package com.ireland.ager.main.entity

import com.ireland.ager.config.BaseEntity
import javax.persistence.Entity
import kotlinx.serialization.*
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * @Class : Product
 * @Description : 상품도메인에 대한 엔티티
 */
@Entity
@Serializable
class Search : BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var searchId: Long? = null
    var keyword: String? = null

    constructor(keyword: String?) {
        this.keyword = keyword
    }

    constructor(searchId: Long?, keyword: String?) {
        this.searchId = searchId
        this.keyword = keyword
    }

    class SearchBuilder internal constructor() {
        private var searchId: Long? = null
        private var keyword: String? = null
        fun searchId(searchId: Long?): SearchBuilder {
            this.searchId = searchId
            return this
        }

        fun keyword(keyword: String?): SearchBuilder {
            this.keyword = keyword
            return this
        }

        fun build(): Search {
            return Search(searchId, keyword)
        }
    }

    companion object {
        fun builder(): SearchBuilder {
            return SearchBuilder()
        }
    }
}