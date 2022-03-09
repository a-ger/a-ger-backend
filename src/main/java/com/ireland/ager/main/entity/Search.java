package com.ireland.ager.main.entity;

import com.ireland.ager.config.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Class : Product
 * @Description : 상품도메인에 대한 엔티티
 **/
@Entity
public class Search extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchId;

    private String keyword;

    public Search(String keyword) {
        this.keyword=keyword;
    }

    public Search(Long searchId, String keyword) {
        this.searchId = searchId;
        this.keyword = keyword;
    }

    public Search() {
    }

    public static SearchBuilder builder() {
        return new SearchBuilder();
    }

    public Long getSearchId() {
        return this.searchId;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setSearchId(Long searchId) {
        this.searchId = searchId;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Search)) return false;
        final Search other = (Search) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$searchId = this.getSearchId();
        final Object other$searchId = other.getSearchId();
        if (this$searchId == null ? other$searchId != null : !this$searchId.equals(other$searchId)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Search;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $searchId = this.getSearchId();
        result = result * PRIME + ($searchId == null ? 43 : $searchId.hashCode());
        return result;
    }

    public static class SearchBuilder {
        private Long searchId;
        private String keyword;

        SearchBuilder() {
        }

        public SearchBuilder searchId(Long searchId) {
            this.searchId = searchId;
            return this;
        }

        public SearchBuilder keyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public Search build() {
            return new Search(searchId, keyword);
        }

        public String toString() {
            return "Search.SearchBuilder(searchId=" + this.searchId + ", keyword=" + this.keyword + ")";
        }
    }
}