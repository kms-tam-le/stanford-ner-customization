package com.kms.aplus.createTrainedModel.service.trainModel;

import org.springframework.web.multipart.MultipartFile;

public interface CreateTrainedModelService {
    void init();
    void uploadFile(MultipartFile file);
    void createTrainedModel();
}
