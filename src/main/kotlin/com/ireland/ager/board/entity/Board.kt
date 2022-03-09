package com.ireland.ager.board.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.ireland.ager.account.entity.Account
import com.ireland.ager.config.BaseEntity
import org.hibernate.annotations.Formula
import javax.persistence.*

/**
 * @Class : Board
 * @Description : 게시판 도메인에 대한 엔티티
 */
@Entity
class Board : BaseEntity, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var boardId: Long? = null

    @set:JsonIgnore
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "account_id")
    var accountId: Account? = null

    @Column(length = 100, nullable = false)
    var title: String? = null

    @Column(columnDefinition = "TEXT", nullable = false)
    var content: String? = null
    var boardViewCnt: Long? = null

    @Formula("(select count(1) from comment c where c.board_id=board_id)")
    var totalCommentCount: Long? = null

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "board", orphanRemoval = true)
    var urlList: MutableList<BoardUrl>? = ArrayList()

    @set:JsonIgnore
    @JsonIgnore
    @OneToMany(mappedBy = "boardId", fetch = FetchType.LAZY)
    var comments: List<Comment>? = ArrayList()

    constructor(
        boardId: Long?,
        accountId: Account?,
        title: String?,
        content: String?,
        boardViewCnt: Long?,
        totalCommentCount: Long?,
        urlList: MutableList<BoardUrl>?,
        comments: List<Comment>?
    ) {
        this.boardId = boardId
        this.accountId = accountId
        this.title = title
        this.content = content
        this.boardViewCnt = boardViewCnt
        this.totalCommentCount = totalCommentCount
        this.urlList = urlList
        this.comments = comments
    }

    constructor() {}

    /**
     * @Method : addAccount
     * @Description : 계정 정보 추가
     * @Parameter : [account]
     * @Return : null
     */
    fun addAccount(account: Account?) {
        accountId = account
    }

    /**
     * @Method : addUrl
     * @Description : 이미지 Url 정보 추가
     * @Parameter : url
     * @Return : null
     */
    fun addUrl(url: BoardUrl) {
        urlList!!.add(url)
        url.board = this
    }

    /**
     * @Method : deleteUrl
     * @Description : 이미지 Url 삭제
     * @Parameter : []
     * @Return : null
     */
    fun deleteUrl() {
        val it = urlList!!.iterator()
        while (it.hasNext()) {
            val url = it.next()
            url.board = null
            it.remove()
        }
    }

    class BoardBuilder internal constructor() {
        private var boardId: Long? = null
        private var accountId: Account? = null
        private var title: String? = null
        private var content: String? = null
        private var boardViewCnt: Long? = null
        private var totalCommentCount: Long? = null
        private var urlList: MutableList<BoardUrl>? = null
        private var comments: List<Comment>? = null
        fun boardId(boardId: Long?): BoardBuilder {
            this.boardId = boardId
            return this
        }

        fun accountId(accountId: Account?): BoardBuilder {
            this.accountId = accountId
            return this
        }

        fun title(title: String?): BoardBuilder {
            this.title = title
            return this
        }

        fun content(content: String?): BoardBuilder {
            this.content = content
            return this
        }

        fun boardViewCnt(boardViewCnt: Long?): BoardBuilder {
            this.boardViewCnt = boardViewCnt
            return this
        }

        fun totalCommentCount(totalCommentCount: Long?): BoardBuilder {
            this.totalCommentCount = totalCommentCount
            return this
        }

        fun urlList(urlList: MutableList<BoardUrl>?): BoardBuilder {
            this.urlList = urlList
            return this
        }

        fun comments(comments: List<Comment>?): BoardBuilder {
            this.comments = comments
            return this
        }

        fun build(): Board {
            return Board(boardId, accountId, title, content, boardViewCnt, totalCommentCount, urlList, comments)
        }

        override fun toString(): String {
            return "Board.BoardBuilder(boardId=" + boardId + ", accountId=" + accountId + ", title=" + title + ", content=" + content + ", boardViewCnt=" + boardViewCnt + ", totalCommentCount=" + totalCommentCount + ", urlList=" + urlList + ", comments=" + comments + ")"
        }
    }

    companion object {
        fun builder(): BoardBuilder {
            return BoardBuilder()
        }
    }
}