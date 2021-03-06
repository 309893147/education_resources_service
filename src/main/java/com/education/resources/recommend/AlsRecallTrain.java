package com.education.resources.recommend;

import lombok.Data;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.io.Serializable;

//als 召回算法的训练
public class AlsRecallTrain implements Serializable {
    public static void main(String[] args) {
        //初始化spark 运行环境
        SparkSession spark = SparkSession.builder().master("local").appName("education").getOrCreate();
        JavaRDD<String> csvFile = spark.read().textFile("D:\\spark\\data\\userScoreBehavior.csv").toJavaRDD();
        JavaRDD<Rating> ratingJavaRDD = csvFile.map(new Function<String, Rating>() {
            @Override
            public Rating call(String s) throws Exception {

                return Rating.parseRating(s);
            }
        });
        Dataset<Row> rating = spark.createDataFrame(ratingJavaRDD, Rating.class);
        //将所有的rating 数据 分成 28 份
        Dataset<Row>[] splits = rating.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> trainingData = splits[0];
        Dataset<Row> testingData = splits[1];
        //过拟合 : 增大数据规模 ,减少 Rank ,增大正则化系数
        //欠拟合 : 增加rank,减少正则化系数
        ALS als = new ALS().setMaxIter(10).setRank(5).setRegParam(0.01)
                .setUserCol("userId").setItemCol("resourceId").setRatingCol("rating");
        //模型训练
        ALSModel alsModel = als.fit(trainingData);
        //模型评测
        Dataset<Row> predictions = alsModel.transform(testingData);
        //rmse 均方根误测,预测值与真实值得偏差的平方除以观测次数
        RegressionEvaluator evaluator = new RegressionEvaluator().setMetricName("rmse")
                .setLabelCol("rating").setPredictionCol("prediction");
        double rmse = evaluator.evaluate(predictions);
        System.out.println("rmse=" + rmse);
        try {
            alsModel.save("D:\\spark\\recommend\\alsModel");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Data
    public static class Rating implements Serializable {
        private int userId;
        private int resourceId;
        private int rating;

        public static Rating parseRating(String str) {
            str = str.replace("\"", "");
            String[] strArr = str.split(",");
            int userId = Integer.parseInt(strArr[0]);
            int resourceId = Integer.parseInt(strArr[1]);
            int rating = Integer.parseInt(strArr[2]);

            return new Rating(userId, resourceId, rating);
        }

        public Rating(int userId, int resourceId, int rating) {
            this.userId = userId;
            this.resourceId = resourceId;
            this.rating = rating;
        }
    }
}
