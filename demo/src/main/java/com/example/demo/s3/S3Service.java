package com.example.demo.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class S3Service {

//    @Value("cloud.aws.s3.bucket.url")
//    private String bucket_name = "capstons3service";
//
//    private final AmazonS3 amazonS3;
//
//    public List<String> getS3Keys() {
//        ListObjectsV2Result result = amazonS3.listObjectsV2(bucket_name);
////        System.out.println(result);
////        System.out.println(result.getClass());
//        List<S3ObjectSummary> objects = result.getObjectSummaries();
////        System.out.println(objects);
//        List<String> keys = new ArrayList<String>();
//        for (S3ObjectSummary os : objects) {
//            System.out.println("* " + os.getKey());
//            keys.add(os.getKey());
//        }
//        return keys;
//    }
//
//    public List<String> objectsURL(List<String> keys) {
//        List<String> urls = new ArrayList<String>();
//
//        for (String key : keys){
//            urls.add(String.valueOf(amazonS3.getUrl(bucket_name, key)));
//        }
//        urls.remove(0);
//        return urls;
//    }
}
