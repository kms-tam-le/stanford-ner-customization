package com.kms.aplus.createTrainedModel.service.trainModel;

import com.kms.aplus.createTrainedModel.exception.StorageException;
import com.kms.aplus.createTrainedModel.properties.StorageProperties;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.sequences.SeqClassifierFlags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

@Service
public class CreateTrainedModelServiceImpl implements CreateTrainedModelService {

    private final Path rootLocation;

    @Autowired
    public CreateTrainedModelServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {

    }

    @Override
    public void uploadFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);

            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public void createTrainedModel() {
        String serializeFile = "upload-dir/ner-model.ser.gz";
        String prop = "src/main/resources/stanfordNER.prop";
        trainCrf(serializeFile, prop);
    }

    private void trainCrf(String serializeFile, String prop) {
        Properties props = edu.stanford.nlp.util.StringUtils.propFileToProperties(prop);
        props.setProperty("serializeTo", serializeFile);
        props.setProperty("trainFile", "upload-dir/train.txt");
        SeqClassifierFlags flags = new SeqClassifierFlags(props);
        CRFClassifier<CoreLabel> crf = new CRFClassifier<>(flags);
        crf.train();
        crf.serializeClassifier(serializeFile);
    }
}
