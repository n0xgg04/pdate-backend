package com.noxinfinity.pdating.Applications.User;

import com.noxinfinity.pdating.graphql.types.CloudinaryUploadResponse;
import com.noxinfinity.pdating.graphql.types.UpdateUserInfo;
import com.noxinfinity.pdating.graphql.types.UserInfoSuccessResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IUserApp {
    UserInfoSuccessResponse getUserInfoById(String id);
    UserInfoSuccessResponse updateUserInfoById(String id, UpdateUserInfo body) throws Exception;
    CloudinaryUploadResponse uploadAvatar(String id, MultipartFile file) throws Exception;
}
