package com.ireland.ager.account.dto.response;

import com.ireland.ager.account.entity.Account;

import java.time.LocalDateTime;

/**
 * @Class : OtherAccountResponse
 * @Description : 계정 도메인에 대한 Response DTO
 **/
public class OtherAccountResponse {
    Long accountId;
    String accountEmail;
    String profileNickname;
    String profileImageUrl;
    Double avgStar;
    LocalDateTime createdAt;

    OtherAccountResponse(Long accountId, String accountEmail, String profileNickname, String profileImageUrl, Double avgStar, LocalDateTime createdAt) {
        this.accountId = accountId;
        this.accountEmail = accountEmail;
        this.profileNickname = profileNickname;
        this.profileImageUrl = profileImageUrl;
        this.avgStar = avgStar;
        this.createdAt = createdAt;
    }

    /**
     * @Method : toOtherAccountResponse
     * @Description : 계정 정보 데이터 응답 객체화
     * @Parameter : [account]
     * @Return : OtherAccountResponse
     **/
    public static OtherAccountResponse toOtherAccountResponse(Account account) {
        return OtherAccountResponse.builder()
                .profileNickname(account.getProfileNickname())
                .accountEmail(account.getAccountEmail())
                .profileImageUrl(account.getProfileImageUrl())
                .accountId(account.getAccountId())
                .createdAt(account.getCreatedAt())
                .avgStar(account.getAvgStar())
                .build();
    }

    public static OtherAccountResponseBuilder builder() {
        return new OtherAccountResponseBuilder();
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public String getAccountEmail() {
        return this.accountEmail;
    }

    public String getProfileNickname() {
        return this.profileNickname;
    }

    public String getProfileImageUrl() {
        return this.profileImageUrl;
    }

    public Double getAvgStar() {
        return this.avgStar;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public void setProfileNickname(String profileNickname) {
        this.profileNickname = profileNickname;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setAvgStar(Double avgStar) {
        this.avgStar = avgStar;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OtherAccountResponse)) return false;
        final OtherAccountResponse other = (OtherAccountResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$accountId = this.getAccountId();
        final Object other$accountId = other.getAccountId();
        if (this$accountId == null ? other$accountId != null : !this$accountId.equals(other$accountId)) return false;
        final Object this$accountEmail = this.getAccountEmail();
        final Object other$accountEmail = other.getAccountEmail();
        if (this$accountEmail == null ? other$accountEmail != null : !this$accountEmail.equals(other$accountEmail))
            return false;
        final Object this$profileNickname = this.getProfileNickname();
        final Object other$profileNickname = other.getProfileNickname();
        if (this$profileNickname == null ? other$profileNickname != null : !this$profileNickname.equals(other$profileNickname))
            return false;
        final Object this$profileImageUrl = this.getProfileImageUrl();
        final Object other$profileImageUrl = other.getProfileImageUrl();
        if (this$profileImageUrl == null ? other$profileImageUrl != null : !this$profileImageUrl.equals(other$profileImageUrl))
            return false;
        final Object this$avgStar = this.getAvgStar();
        final Object other$avgStar = other.getAvgStar();
        if (this$avgStar == null ? other$avgStar != null : !this$avgStar.equals(other$avgStar)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OtherAccountResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $accountId = this.getAccountId();
        result = result * PRIME + ($accountId == null ? 43 : $accountId.hashCode());
        final Object $accountEmail = this.getAccountEmail();
        result = result * PRIME + ($accountEmail == null ? 43 : $accountEmail.hashCode());
        final Object $profileNickname = this.getProfileNickname();
        result = result * PRIME + ($profileNickname == null ? 43 : $profileNickname.hashCode());
        final Object $profileImageUrl = this.getProfileImageUrl();
        result = result * PRIME + ($profileImageUrl == null ? 43 : $profileImageUrl.hashCode());
        final Object $avgStar = this.getAvgStar();
        result = result * PRIME + ($avgStar == null ? 43 : $avgStar.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "OtherAccountResponse(accountId=" + this.getAccountId() + ", accountEmail=" + this.getAccountEmail() + ", profileNickname=" + this.getProfileNickname() + ", profileImageUrl=" + this.getProfileImageUrl() + ", avgStar=" + this.getAvgStar() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class OtherAccountResponseBuilder {
        private Long accountId;
        private String accountEmail;
        private String profileNickname;
        private String profileImageUrl;
        private Double avgStar;
        private LocalDateTime createdAt;

        OtherAccountResponseBuilder() {
        }

        public OtherAccountResponseBuilder accountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public OtherAccountResponseBuilder accountEmail(String accountEmail) {
            this.accountEmail = accountEmail;
            return this;
        }

        public OtherAccountResponseBuilder profileNickname(String profileNickname) {
            this.profileNickname = profileNickname;
            return this;
        }

        public OtherAccountResponseBuilder profileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
            return this;
        }

        public OtherAccountResponseBuilder avgStar(Double avgStar) {
            this.avgStar = avgStar;
            return this;
        }

        public OtherAccountResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OtherAccountResponse build() {
            return new OtherAccountResponse(accountId, accountEmail, profileNickname, profileImageUrl, avgStar, createdAt);
        }

        public String toString() {
            return "OtherAccountResponse.OtherAccountResponseBuilder(accountId=" + this.accountId + ", accountEmail=" + this.accountEmail + ", profileNickname=" + this.profileNickname + ", profileImageUrl=" + this.profileImageUrl + ", avgStar=" + this.avgStar + ", createdAt=" + this.createdAt + ")";
        }
    }
}
