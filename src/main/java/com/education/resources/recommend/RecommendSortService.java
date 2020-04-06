package com.education.resources.recommend;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.ml.classification.GBTClassificationModel;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendSortService {


    private SparkSession spark ;

    private LogisticRegressionModel lrModel ;

    private GBTClassificationModel gbtClassificationModel;

    @PostConstruct
    public void init(){
        //加载LR模型
        spark = SparkSession.builder().master("local").appName("DianpingApp").getOrCreate();

        lrModel = LogisticRegressionModel.load("D:\\spark\\recommend\\lrmode");
        gbtClassificationModel = GBTClassificationModel.load("D:\\spark\\recommend\\gbdtmode");
    }

    public List<Integer> sort(List<Integer> shopIdList, Integer userId, String operator){
        //需要根据lrmode所需要11唯的x，生成特征，然后调用其预测方法
        List<ResourceSortModel> list = new ArrayList<>();
        for(Integer shopId : shopIdList){
            //造的假数据，可以从数据库或缓存中拿到对应的性别，年龄，评分，价格等做特征转化生成feture向量
            Vector v = Vectors.dense(1,0,0,0,0,1,0.6,0,0,1,0);
            Vector result = null;
            if(StringUtils.equals(operator,"LR")){
                result = lrModel.predictProbability(v);
            }else{
                result = gbtClassificationModel.predictProbability(v);
            }

            double[] arr = result.toArray();
            double score = arr[1];
            ResourceSortModel shopSortModel = new ResourceSortModel();
            shopSortModel.setResourceId(shopId);
            shopSortModel.setScore(score);
            list.add(shopSortModel);
        }
        list.sort(new Comparator<ResourceSortModel>() {
            @Override
            public int compare(ResourceSortModel o1, ResourceSortModel o2) {
                if(o1.getScore() < o2.getScore()){
                    return 1;
                }else if(o1.getScore() > o2.getScore()){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
        return list.stream().map(shopSortModel -> shopSortModel.getResourceId()).collect(Collectors.toList());


    }

}
