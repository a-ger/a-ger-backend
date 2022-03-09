package com.ireland.ager.account.dto.response;

import com.ireland.ager.account.entity.Account;

/**
 * @Class : KakaoResponse
 * @Description : 카카오 도메인에 대한 Response DTO
 **/
public class KakaoResponse {

    private Integer id;
    private String connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;

    public KakaoResponse() {
    }

    public Integer getId() {
        return this.id;
    }

    public String getConnected_at() {
        return this.connected_at;
    }

    public Properties getProperties() {
        return this.properties;
    }

    public KakaoAccount getKakao_account() {
        return this.kakao_account;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setConnected_at(String connected_at) {
        this.connected_at = connected_at;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setKakao_account(KakaoAccount kakao_account) {
        this.kakao_account = kakao_account;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof KakaoResponse)) return false;
        final KakaoResponse other = (KakaoResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$connected_at = this.getConnected_at();
        final Object other$connected_at = other.getConnected_at();
        if (this$connected_at == null ? other$connected_at != null : !this$connected_at.equals(other$connected_at))
            return false;
        final Object this$properties = this.getProperties();
        final Object other$properties = other.getProperties();
        if (this$properties == null ? other$properties != null : !this$properties.equals(other$properties))
            return false;
        final Object this$kakao_account = this.getKakao_account();
        final Object other$kakao_account = other.getKakao_account();
        if (this$kakao_account == null ? other$kakao_account != null : !this$kakao_account.equals(other$kakao_account))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof KakaoResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $connected_at = this.getConnected_at();
        result = result * PRIME + ($connected_at == null ? 43 : $connected_at.hashCode());
        final Object $properties = this.getProperties();
        result = result * PRIME + ($properties == null ? 43 : $properties.hashCode());
        final Object $kakao_account = this.getKakao_account();
        result = result * PRIME + ($kakao_account == null ? 43 : $kakao_account.hashCode());
        return result;
    }

    public String toString() {
        return "KakaoResponse(id=" + this.getId() + ", connected_at=" + this.getConnected_at() + ", properties=" + this.getProperties() + ", kakao_account=" + this.getKakao_account() + ")";
    }

    public class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;

        public Properties() {
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getProfile_image() {
            return this.profile_image;
        }

        public String getThumbnail_image() {
            return this.thumbnail_image;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public void setThumbnail_image(String thumbnail_image) {
            this.thumbnail_image = thumbnail_image;
        }

        public boolean equals(final Object o) {
            if (o == this) return true;
            if (!(o instanceof Properties)) return false;
            final Properties other = (Properties) o;
            if (!other.canEqual((Object) this)) return false;
            final Object this$nickname = this.getNickname();
            final Object other$nickname = other.getNickname();
            if (this$nickname == null ? other$nickname != null : !this$nickname.equals(other$nickname)) return false;
            final Object this$profile_image = this.getProfile_image();
            final Object other$profile_image = other.getProfile_image();
            if (this$profile_image == null ? other$profile_image != null : !this$profile_image.equals(other$profile_image))
                return false;
            final Object this$thumbnail_image = this.getThumbnail_image();
            final Object other$thumbnail_image = other.getThumbnail_image();
            if (this$thumbnail_image == null ? other$thumbnail_image != null : !this$thumbnail_image.equals(other$thumbnail_image))
                return false;
            return true;
        }

        protected boolean canEqual(final Object other) {
            return other instanceof Properties;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $nickname = this.getNickname();
            result = result * PRIME + ($nickname == null ? 43 : $nickname.hashCode());
            final Object $profile_image = this.getProfile_image();
            result = result * PRIME + ($profile_image == null ? 43 : $profile_image.hashCode());
            final Object $thumbnail_image = this.getThumbnail_image();
            result = result * PRIME + ($thumbnail_image == null ? 43 : $thumbnail_image.hashCode());
            return result;
        }

        public String toString() {
            return "KakaoResponse.Properties(nickname=" + this.getNickname() + ", profile_image=" + this.getProfile_image() + ", thumbnail_image=" + this.getThumbnail_image() + ")";
        }
    }

    public class KakaoAccount {
        private Boolean profile_needs_agreement;
        private Profile profile;
        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;

        public KakaoAccount() {
        }

        public Boolean getProfile_needs_agreement() {
            return this.profile_needs_agreement;
        }

        public Profile getProfile() {
            return this.profile;
        }

        public Boolean getHas_email() {
            return this.has_email;
        }

        public Boolean getEmail_needs_agreement() {
            return this.email_needs_agreement;
        }

        public Boolean getIs_email_valid() {
            return this.is_email_valid;
        }

        public Boolean getIs_email_verified() {
            return this.is_email_verified;
        }

        public String getEmail() {
            return this.email;
        }

        public void setProfile_needs_agreement(Boolean profile_needs_agreement) {
            this.profile_needs_agreement = profile_needs_agreement;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        public void setHas_email(Boolean has_email) {
            this.has_email = has_email;
        }

        public void setEmail_needs_agreement(Boolean email_needs_agreement) {
            this.email_needs_agreement = email_needs_agreement;
        }

        public void setIs_email_valid(Boolean is_email_valid) {
            this.is_email_valid = is_email_valid;
        }

        public void setIs_email_verified(Boolean is_email_verified) {
            this.is_email_verified = is_email_verified;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean equals(final Object o) {
            if (o == this) return true;
            if (!(o instanceof KakaoAccount)) return false;
            final KakaoAccount other = (KakaoAccount) o;
            if (!other.canEqual((Object) this)) return false;
            final Object this$profile_needs_agreement = this.getProfile_needs_agreement();
            final Object other$profile_needs_agreement = other.getProfile_needs_agreement();
            if (this$profile_needs_agreement == null ? other$profile_needs_agreement != null : !this$profile_needs_agreement.equals(other$profile_needs_agreement))
                return false;
            final Object this$profile = this.getProfile();
            final Object other$profile = other.getProfile();
            if (this$profile == null ? other$profile != null : !this$profile.equals(other$profile)) return false;
            final Object this$has_email = this.getHas_email();
            final Object other$has_email = other.getHas_email();
            if (this$has_email == null ? other$has_email != null : !this$has_email.equals(other$has_email))
                return false;
            final Object this$email_needs_agreement = this.getEmail_needs_agreement();
            final Object other$email_needs_agreement = other.getEmail_needs_agreement();
            if (this$email_needs_agreement == null ? other$email_needs_agreement != null : !this$email_needs_agreement.equals(other$email_needs_agreement))
                return false;
            final Object this$is_email_valid = this.getIs_email_valid();
            final Object other$is_email_valid = other.getIs_email_valid();
            if (this$is_email_valid == null ? other$is_email_valid != null : !this$is_email_valid.equals(other$is_email_valid))
                return false;
            final Object this$is_email_verified = this.getIs_email_verified();
            final Object other$is_email_verified = other.getIs_email_verified();
            if (this$is_email_verified == null ? other$is_email_verified != null : !this$is_email_verified.equals(other$is_email_verified))
                return false;
            final Object this$email = this.getEmail();
            final Object other$email = other.getEmail();
            if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
            return true;
        }

        protected boolean canEqual(final Object other) {
            return other instanceof KakaoAccount;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $profile_needs_agreement = this.getProfile_needs_agreement();
            result = result * PRIME + ($profile_needs_agreement == null ? 43 : $profile_needs_agreement.hashCode());
            final Object $profile = this.getProfile();
            result = result * PRIME + ($profile == null ? 43 : $profile.hashCode());
            final Object $has_email = this.getHas_email();
            result = result * PRIME + ($has_email == null ? 43 : $has_email.hashCode());
            final Object $email_needs_agreement = this.getEmail_needs_agreement();
            result = result * PRIME + ($email_needs_agreement == null ? 43 : $email_needs_agreement.hashCode());
            final Object $is_email_valid = this.getIs_email_valid();
            result = result * PRIME + ($is_email_valid == null ? 43 : $is_email_valid.hashCode());
            final Object $is_email_verified = this.getIs_email_verified();
            result = result * PRIME + ($is_email_verified == null ? 43 : $is_email_verified.hashCode());
            final Object $email = this.getEmail();
            result = result * PRIME + ($email == null ? 43 : $email.hashCode());
            return result;
        }

        public String toString() {
            return "KakaoResponse.KakaoAccount(profile_needs_agreement=" + this.getProfile_needs_agreement() + ", profile=" + this.getProfile() + ", has_email=" + this.getHas_email() + ", email_needs_agreement=" + this.getEmail_needs_agreement() + ", is_email_valid=" + this.getIs_email_valid() + ", is_email_verified=" + this.getIs_email_verified() + ", email=" + this.getEmail() + ")";
        }

        public class Profile {
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;

            public Profile() {
            }

            public String getNickname() {
                return this.nickname;
            }

            public String getThumbnail_image_url() {
                return this.thumbnail_image_url;
            }

            public String getProfile_image_url() {
                return this.profile_image_url;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setThumbnail_image_url(String thumbnail_image_url) {
                this.thumbnail_image_url = thumbnail_image_url;
            }

            public void setProfile_image_url(String profile_image_url) {
                this.profile_image_url = profile_image_url;
            }

            public boolean equals(final Object o) {
                if (o == this) return true;
                if (!(o instanceof Profile)) return false;
                final Profile other = (Profile) o;
                if (!other.canEqual((Object) this)) return false;
                final Object this$nickname = this.getNickname();
                final Object other$nickname = other.getNickname();
                if (this$nickname == null ? other$nickname != null : !this$nickname.equals(other$nickname))
                    return false;
                final Object this$thumbnail_image_url = this.getThumbnail_image_url();
                final Object other$thumbnail_image_url = other.getThumbnail_image_url();
                if (this$thumbnail_image_url == null ? other$thumbnail_image_url != null : !this$thumbnail_image_url.equals(other$thumbnail_image_url))
                    return false;
                final Object this$profile_image_url = this.getProfile_image_url();
                final Object other$profile_image_url = other.getProfile_image_url();
                if (this$profile_image_url == null ? other$profile_image_url != null : !this$profile_image_url.equals(other$profile_image_url))
                    return false;
                return true;
            }

            protected boolean canEqual(final Object other) {
                return other instanceof Profile;
            }

            public int hashCode() {
                final int PRIME = 59;
                int result = 1;
                final Object $nickname = this.getNickname();
                result = result * PRIME + ($nickname == null ? 43 : $nickname.hashCode());
                final Object $thumbnail_image_url = this.getThumbnail_image_url();
                result = result * PRIME + ($thumbnail_image_url == null ? 43 : $thumbnail_image_url.hashCode());
                final Object $profile_image_url = this.getProfile_image_url();
                result = result * PRIME + ($profile_image_url == null ? 43 : $profile_image_url.hashCode());
                return result;
            }

            public String toString() {
                return "KakaoResponse.KakaoAccount.Profile(nickname=" + this.getNickname() + ", thumbnail_image_url=" + this.getThumbnail_image_url() + ", profile_image_url=" + this.getProfile_image_url() + ")";
            }
        }
    }

    /**
     * @Method : toAccount
     * @Description : 카카오 계정 정보 데이터 응답 객체화
     * @Parameter : [accessToken, refreshToken]
     * @Return : Account
     **/
    public Account toAccount(String accessToken) {
        Account account = new Account();
        if (this.kakao_account.email == null || this.kakao_account.email.equals(""))
            account.setAccountEmail(String.valueOf(this.id));
        else account.setAccountEmail(this.kakao_account.email);
        account.setProfileNickname(this.properties.nickname);
        account.setUserName(this.kakao_account.profile.nickname);
        account.setProfileImageUrl(this.kakao_account.profile.profile_image_url);
        account.setAccessToken(accessToken);
        return account;
    }
}