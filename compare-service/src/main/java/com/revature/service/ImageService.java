package com.revature.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.revature.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ImageService {
    String bucket = "elasticbeanstalk-us-east-2-950115502660";


   AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

    public String compareSameFaces(String imgA, String imgB) {
        if(imgA.isEmpty() || imgB.isEmpty()){
            throw new ResourceNotFoundException();
        }
        String result = "";

        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(new Image().withS3Object(new S3Object()
                        .withName(imgA).withBucket(bucket))).withTargetImage(new Image()
                        .withS3Object(new S3Object().withName(imgB).withBucket(bucket)))
                .withSimilarityThreshold(70F);

        try {
            CompareFacesResult compareFacesResult = rekognitionClient.compareFaces(request);
            List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();

            if(!faceDetails.isEmpty()) {
                for (CompareFacesMatch match : faceDetails) {
                    result = match.getSimilarity().toString();
                    if(Float.parseFloat(result) >= 70F){
                         result = "Image " + imgA
                                + " and Image " + imgB
                                + " matches with " + match.getSimilarity().toString()
                                + "% confidence.\n\nThe faces are the same";
                        }
                    }
                } else {
                result = "Image " + imgA
                        + " and Image " + imgB
                        + " The faces are NOT the same";
            }
        } catch (Exception e){
            System.out.println("An error has occurred");
        }

        return result;
    }
}
