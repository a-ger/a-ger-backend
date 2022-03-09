package com.ireland.ager.account.dto.response;

import com.ireland.ager.account.entity.Account;

import java.time.LocalDateTime;

/**
 * @Class : MyAccountResponse
 * @Description : 계정 도메인에 대한 Response DTO
 **/
public class MyAccountResponse {
    Long accountId;
    String accountEmail;
    String profileNickname;
    String userName;
    String profileImageUrl;
    String accessToken;
    Double avgStar;
    LocalDateTime createdAt;

    MyAccountResponse(Long accountId, String accountEmail, String profileNickname, String userName, String profileImageUrl, String accessToken, Double avgStar, LocalDateTime createdAt) {
        this.accountId = accountId;
        this.accountEmail = accountEmail;
        this.profileNickname = profileNickname;
        this.userName = userName;
        this.profileImageUrl = profileImageUrl;
        this.accessToken = accessToken;
        this.avgStar = avgStar;
        this.createdAt = createdAt;
    }

    /**
     * @Method : toAccountResponse
     * @Description : 계정 정보 데이터 응답 객체화
     * @Parameter : [account]
     * @Return : MyAccountResponse
     **/
    public static MyAccountResponse toAccountResponse(Account account) {
        return MyAccountResponse.builder()
                .accessToken(account.getAccessToken())
                .profileNickname(account.getProfileNickname())
                .userName(account.getUserName())
                .accountEmail(account.getAccountEmail())
                .profileImageUrl(account.getProfileImageUrl())
                .accountId(account.getAccountId())
                .avgStar(account.getAvgStar())
                .createdAt(account.getCreatedAt())
                .build();
    }

    public static MyAccountResponseBuilder builder() {
        return new MyAccountResponseBuilder();
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

    public String getUserName() {
        return this.userName;
    }

    public String getProfileImageUrl() {
        return this.profileImageUrl;
    }

    public String getAccessToken() {
        return this.accessToken;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAvgStar(Double avgStar) {
        this.avgStar = avgStar;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MyAccountResponse)) return false;
        final MyAccountResponse other = (MyAccountResponse) o;
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
        final Object this$userName = this.getUserName();
        final Object other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) return false;
        final Object this$profileImageUrl = this.getProfileImageUrl();
        final Object other$profileImageUrl = other.getProfileImageUrl();
        if (this$profileImageUrl == null ? other$profileImageUrl != null : !this$profileImageUrl.equals(other$profileImageUrl))
            return false;
        final Object this$accessToken = this.getAccessToken();
        final Object other$accessToken = other.getAccessToken();
        if (this$accessToken == null ? other$accessToken != null : !this$accessToken.equals(other$accessToken))
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
        return other instanceof MyAccountResponse;
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
        final Object $userName = this.getUserName();
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        final Object $profileImageUrl = this.getProfileImageUrl();
        result = result * PRIME + ($profileImageUrl == null ? 43 : $profileImageUrl.hashCode());
        final Object $accessToken = this.getAccessToken();
        result = result * PRIME + ($accessToken == null ? 43 : $accessToken.hashCode());
        final Object $avgStar = this.getAvgStar();
        result = result * PRIME + ($avgStar == null ? 43 : $avgStar.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "MyAccountResponse(accountId=" + this.getAccountId() + ", accountEmail=" + this.getAccountEmail() + ", profileNickname=" + this.getProfileNickname() + ", userName=" + this.getUserName() + ", profileImageUrl=" + this.getProfileImageUrl() + ", accessToken=" + this.getAccessToken() + ", avgStar=" + this.getAvgStar() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class MyAccountResponseBuilder {
        private Long accountId;
        private String accountEmail;
        private String profileNickname;
        private String userName;
        private String profileImageUrl;
        private String accessToken;
        private Double avgStar;
        private LocalDateTime createdAt;

        MyAccountResponseBuilder() {
        }

        public MyAccountResponseBuilder accountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public MyAccountResponseBuilder accountEmail(String accountEmail) {
            this.accountEmail = accountEmail;
            return this;
        }

        public MyAccountResponseBuilder profileNickname(String profileNickname) {
            this.profileNickname = profileNickname;
            return this;
        }

        public MyAccountResponseBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public MyAccountResponseBuilder profileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
            return this;
        }

        public MyAccountResponseBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public MyAccountResponseBuilder avgStar(Double avgStar) {
            this.avgStar = avgStar;
            return this;
        }

        public MyAccountResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public MyAccountResponse build() {
            return new MyAccountResponse(accountId, accountEmail, profileNickname, userName, profileImageUrl, accessToken, avgStar, createdAt);
        }

        public String toString() {
            return "MyAccountResponse.MyAccountResponseBuilder(accountId=" + this.accountId + ", accountEmail=" + this.accountEmail + ", profileNickname=" + this.profileNickname + ", userName=" + this.userName + ", profileImageUrl=" + this.profileImageUrl + ", accessToken=" + this.accessToken + ", avgStar=" + this.avgStar + ", createdAt=" + this.createdAt + ")";
        }
    }
}