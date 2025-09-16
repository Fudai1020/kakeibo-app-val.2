package kakeibo_app_java.kakeibo_app_java.service;

import kakeibo_app_java.kakeibo_app_java.dto.ProfileDto;

public interface ShareService {
    public String generateShareCode(Long ownerId);
    public ProfileDto joinShared(Long partnerId,String code);
}
