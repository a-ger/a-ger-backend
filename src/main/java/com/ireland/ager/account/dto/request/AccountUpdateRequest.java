package com.ireland.ager.account.dto.request;

import com.ireland.ager.account.entity.Account;

/**
 * @Class : AccountUpdateRequest
 * @Description : 계정 도메인에 대한 Request DTO
 **/
public class AccountUpdateRequest {
    private String profileNickname;

    public AccountUpdateRequest() {
    }

    /**
     * @Method : toAccount
     * @Description : 계정 정보 수정 데이터 객체화
     * @Parameter : [updateAccount]
     * @Return : Account
     **/
    public Account toAccount(Account updateAccount) {
        updateAccount.setProfileNickname(this.profileNickname);
        return updateAccount;
    }

    public String getProfileNickname() {
        return this.profileNickname;
    }

    public void setProfileNickname(String profileNickname) {
        this.profileNickname = profileNickname;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AccountUpdateRequest)) return false;
        final AccountUpdateRequest other = (AccountUpdateRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$profileNickname = this.getProfileNickname();
        final Object other$profileNickname = other.getProfileNickname();
        if (this$profileNickname == null ? other$profileNickname != null : !this$profileNickname.equals(other$profileNickname))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AccountUpdateRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $profileNickname = this.getProfileNickname();
        result = result * PRIME + ($profileNickname == null ? 43 : $profileNickname.hashCode());
        return result;
    }

    public String toString() {
        return "AccountUpdateRequest(profileNickname=" + this.getProfileNickname() + ")";
    }
}