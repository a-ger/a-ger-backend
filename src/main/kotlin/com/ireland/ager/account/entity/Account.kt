package com.ireland.ager.account.entity

import com.ireland.ager.board.entity.Board
import com.ireland.ager.board.entity.Comment
import com.ireland.ager.config.BaseEntity
import com.ireland.ager.product.entity.Product
import com.ireland.ager.review.entity.Review
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Formula
import javax.persistence.*
import kotlinx.serialization.*;

/**
 * @Class : Account
 * @Description : 계정 도메인에 대한 엔티티
 */
@Entity
@DynamicUpdate
@Serializable
class Account : BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var accountId: Long? = null
    var accountEmail: String? = null
    var profileNickname: String? = null
    var userName: String? = null
    var profileImageUrl: String? = null
    var accessToken: String? = null

    @Formula("(select avg(r.stars) from review r where r.seller_id=account_id)")
    var avgStar: Double? = null

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "account")
    var products: Set<Product> = HashSet()

    @OneToMany(mappedBy = "sellerId", cascade = [CascadeType.ALL])
    var reviews: Set<Review> = LinkedHashSet()

    @OneToMany(mappedBy = "accountId", cascade = [CascadeType.ALL])
    var boards: List<Board> = ArrayList()

    @OneToMany(mappedBy = "accountId", cascade = [CascadeType.ALL])
    var comments: List<Comment> = ArrayList()

    constructor(
        accountId: Long?,
        accountEmail: String?,
        profileNickname: String?,
        userName: String?,
        profileImageUrl: String?,
        accessToken: String?,
        avgStar: Double?,
        products: Set<Product>,
        reviews: Set<Review>,
        boards: List<Board>,
        comments: List<Comment>
    ) {
        this.accountId = accountId
        this.accountEmail = accountEmail
        this.profileNickname = profileNickname
        this.userName = userName
        this.profileImageUrl = profileImageUrl
        this.accessToken = accessToken
        this.avgStar = avgStar
        this.products = products
        this.reviews = reviews
        this.boards = boards
        this.comments = comments
    }

    constructor() {}

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is Account) return false
        val other = o
        if (!other.canEqual(this as Any)) return false
        val `this$accountId`: Any? = accountId
        val `other$accountId`: Any? = other.accountId
        return if (if (`this$accountId` == null) `other$accountId` != null else `this$accountId` != `other$accountId`) false else true
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is Account
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = 1
        val `$accountId`: Any? = accountId
        result = result * PRIME + (`$accountId`?.hashCode() ?: 43)
        return result
    }
}