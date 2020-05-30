package com.education.resources.service.resource;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.education.resources.bean.entity.BasicType;
import com.education.resources.bean.entity.Resource;
import com.education.resources.bean.entity.user.User;
import com.education.resources.bean.from.GetResourceFrom;
import com.education.resources.bean.from.PageForm;
import com.education.resources.bean.from.SearchForm;
import com.education.resources.bean.pojo.event.DingMessageEvent;
import com.education.resources.bean.pojo.export.ResourceImport;
import com.education.resources.datasource.mapper.ResourceMapper;
import com.education.resources.datasource.repository.BasicTypeRepository;
import com.education.resources.datasource.repository.UserRepository;
import com.education.resources.datasource.repository.resource.ResourceRepository;
import com.education.resources.framework.config.MediaConfig;
import com.education.resources.recommend.RecommendService;
import com.education.resources.recommend.RecommendSortService;
import com.education.resources.service.BaseService;
import com.education.resources.util.DateUtils;
import com.education.resources.util.MapBuilder;
import com.education.resources.util.StringUtil;
import com.education.resources.util.export.ExcelDataBuilder;
import com.education.resources.util.export.ExcelHeaderBuilder;
import com.education.resources.util.jpa.SpecificationUtil;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.spark_project.jetty.util.ArrayQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ResouceService extends BaseService {

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    RecommendService recommendService;

    @Autowired
    RecommendSortService recommendSortService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BasicTypeRepository basicTypeRepository;

    private MediaConfig mediaConfig;

    private ExecutorService executors;
    @Autowired
    public void setMediaConfig(MediaConfig mediaConfig) {
        this.mediaConfig = mediaConfig;
    }

    public Page<Resource> resourcesPage(Resource resource, PageForm pageForm) {
        PredicateBuilder<Resource> spec = SpecificationUtil.filter(resource);
        return resourceRepository.findAll(spec.build(), pageForm.pageRequest());
    }

    public Page<Resource> getResource(Resource resource, PageForm pageForm) {
        PredicateBuilder<Resource> spec = Specifications.<Resource>and().eq("presenceStatus", 1);
        spec.eq(resource.getBasicTypeId() != null, "basicTypeId", resource.getBasicTypeId());
        spec.eq("resourceStatus", Resource.ResourceStatus.PASS);
        return resourceRepository.findAll(spec.build(), pageForm.pageRequest());
    }

    public List<Resource> recommend() {
        List<Integer> resourceIdList = recommendService.recall(getCurrentUser().getId());
        List<Integer> LrResourceIdList = recommendSortService.sort(resourceIdList, getCurrentUser().getId(), "LR");
        List<Integer> GbdtresourceIdList = recommendSortService.sort(resourceIdList, getCurrentUser().getId(), "GBDT");
        List<Resource> LRModelList = LrResourceIdList.stream().map(id -> {
            Resource resource = get(id);
            resource.setOperator("LR");
            return resource;
        }).collect(Collectors.toList());
        List<Resource> GBDTModelList = GbdtresourceIdList.stream().map(id -> {
            Resource resource = get(id);
            resource.setOperator("GBDT");
            return resource;
        }).collect(Collectors.toList());

        //固定输出6个
        List<Resource> resultModelList = new ArrayList<>();
        Queue<Resource> LRQueue = new ArrayQueue<>();
        LRQueue.addAll(LRModelList);
        Queue<Resource> GBDTQueue = new ArrayQueue<>();
        GBDTQueue.addAll(GBDTModelList);
        for (int i = 0; i < 6; i++) {
            Resource addModel = null;
            if (i % 2 == 0) {
                //选取LR
                addModel = LRQueue.poll();
                boolean isRepeat = false;
                for (int j = 0; j < resultModelList.size(); j++) {
                    if (addModel.getId().equals(resultModelList.get(j).getId())) {
                        isRepeat = true;
                        break;
                    }
                }
                if (isRepeat) {
                    i--;
                    continue;
                }
            } else if (i % 2 == 1) {
                //选取GBDT
                addModel = GBDTQueue.poll();
                boolean isRepeat = false;
                for (int j = 0; j < resultModelList.size(); j++) {
                    if (addModel.getId().equals(resultModelList.get(j).getId())) {
                        isRepeat = true;
                        break;
                    }
                }
                if (isRepeat) {
                    i--;
                    continue;
                }
            }
            resultModelList.add(addModel);
        }

        return resultModelList;
    }


    public Resource get(Integer id) {
        Resource resource = resourceRepository.findItemById(id);
        if (resource == null) {
            return null;
        }
//        resource.setSellerModel(sellerService.get(shopModel.getSellerId()));
//        resource.setCategoryModel(categoryService.get(shopModel.getCategoryId()));
        return resource;
    }

    @Value("${elasticsearch.ip}")
    String ipAddress;

    public Map<String, Object> searchEs(SearchForm searchForm) throws IOException {
        String[] address = ipAddress.split(":");
        String ip = address[0];
        int port = Integer.valueOf(address[1]);
        HttpHost httpHost = new HttpHost(ip, port, "http");
        RestHighLevelClient highLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost[]{httpHost}));
        Map<String, Object> result = new HashMap<>();
        Request request = new Request("GET", "/resource/_search");
        //构建请求
        JSONObject jsonRequestObj = new JSONObject();
        //构建source部分
        jsonRequestObj.put("_source", "*");
        //构建query
        jsonRequestObj.put("query", new JSONObject());
        //构建function score
        jsonRequestObj.getJSONObject("query").put("function_score", new JSONObject());
        //构建function score内的query
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").put("query", new JSONObject());
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").put("bool", new JSONObject());
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").getJSONObject("bool").put("must", new JSONArray());
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").getJSONObject("bool")
                .getJSONArray("must").add(new JSONObject());
        //构建match query
        int queryIndex = 0;
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").getJSONObject("bool")
                .getJSONArray("must").getJSONObject(queryIndex).put("match", new JSONObject());
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").getJSONObject("bool")
                .getJSONArray("must").getJSONObject(queryIndex).getJSONObject("match").put("title", new JSONObject());
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").getJSONObject("bool")
                .getJSONArray("must").getJSONObject(queryIndex).getJSONObject("match").getJSONObject("title").put("query", searchForm.getKeyword());
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").getJSONObject("bool")
                .getJSONArray("must").getJSONObject(queryIndex).getJSONObject("match").getJSONObject("title").put("boost", 0.1);
        if (searchForm.getTag() != null) {
            queryIndex++;
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").getJSONObject("bool")
                    .getJSONArray("must").add(new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").getJSONObject("bool")
                    .getJSONArray("must").getJSONObject(queryIndex).put("term", new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").getJSONObject("bool")
                    .getJSONArray("must").getJSONObject(queryIndex).getJSONObject("term").put("tag", searchForm.getTag());
        }
        if (searchForm.getBasicTypeId() != null) {
            queryIndex++;
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").getJSONObject("bool")
                    .getJSONArray("must").add(new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").getJSONObject("bool")
                    .getJSONArray("must").getJSONObject(queryIndex).put("term", new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query").getJSONObject("bool")
                    .getJSONArray("must").getJSONObject(queryIndex).getJSONObject("term").put("basic_type_id", searchForm.getBasicTypeId());
        }
        //构建functions部分
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").put("functions", new JSONArray());
        int functionIndex = 0;
        if (searchForm.getOrderBy() == null) {
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions").add(new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions").getJSONObject(functionIndex).put("field_value_factor", new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions").getJSONObject(functionIndex).getJSONObject("field_value_factor")
                    .put("field", "score");
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").put("score_mode", "sum");
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").put("boost_mode", "replace");
        }

        //排序字段
        jsonRequestObj.put("sort", new JSONArray());
        jsonRequestObj.getJSONArray("sort").add(new JSONObject());
        jsonRequestObj.getJSONArray("sort").getJSONObject(0).put("_score", new JSONObject());
        if (searchForm.getOrderBy() == null) {
            jsonRequestObj.getJSONArray("sort").getJSONObject(0).getJSONObject("_score").put("order", "desc");
        } else {
            jsonRequestObj.getJSONArray("sort").getJSONObject(0).getJSONObject("_score").put("order", "asc");
        }

        //聚合字段
        jsonRequestObj.put("aggs", new JSONObject());
        jsonRequestObj.getJSONObject("aggs").put("group_by_tag", new JSONObject());
        jsonRequestObj.getJSONObject("aggs").getJSONObject("group_by_tag").put("terms", new JSONObject());
        jsonRequestObj.getJSONObject("aggs").getJSONObject("group_by_tag").getJSONObject("terms").put("field", "tag");
        String reqJson = jsonRequestObj.toJSONString();
        request.setJsonEntity(reqJson);
        Response response = highLevelClient.getLowLevelClient().performRequest(request);
        String responseStr = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject = JSONObject.parseObject(responseStr);
        JSONArray jsonArr = jsonObject.getJSONObject("hits").getJSONArray("hits");
        List<Resource> resourceList = new ArrayList<>();
        for(int i = 0; i < jsonArr.size(); i++){
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            Integer id = new Integer(jsonObj.get("_id").toString());
            Resource resource = get(id);
            resourceList.add(resource);
        }
        List<Map> tagsList = new ArrayList<>();
        JSONArray tagsJsonArray = jsonObject.getJSONObject("aggregations").getJSONObject("group_by_tag").getJSONArray("buckets");
        for (int i = 0; i < tagsJsonArray.size(); i++) {
            JSONObject jsonObj = tagsJsonArray.getJSONObject(i);
            Map<String, Object> tagMap = new HashMap<>();
            tagMap.put("tag", jsonObj.getString("key"));
            tagMap.put("num", jsonObj.getInteger("doc_count"));
            tagsList.add(tagMap);
        }
        List<BasicType> basicTypeList = basicTypeRepository.findAll();
        result.put("tag", tagsList);
        result.put("type", basicTypeList);
        result.put("resource", resourceList);
        return result;
    }


    public List<Resource> getBdResource(Integer status) {
        PredicateBuilder<Resource> spec = Specifications.<Resource>and().eq("presenceStatus", 1);
        if (status==null){
            return resourceMapper.newList();
        }
        if (status == 1) {
            return resourceMapper.lookList();
        }
        if (status == 2) {
            return resourceMapper.recommendList();
        }
        return resourceMapper.newList();
    }

    public Page<Resource> getMyResource(PageForm pageForm) {
        PredicateBuilder<Resource> spec = Specifications.<Resource>and().eq("presenceStatus", 1);
        spec.eq("userId", getCurrentUser().getId());
        return resourceRepository.findAll(spec.build(), pageForm.pageRequest());
    }

    public Resource getOne(Integer id) {
        Resource resource = resourceRepository.findItemById(id);
        resource.setClickNumber(resource.getClickNumber() + 1);
        return resourceRepository.save(resource);
    }

    public Resource resourceSave(Resource resource) {
        resource.setResourceStatus(Resource.ResourceStatus.PASS);
        resource.setManagerId(getCurrentManager().getId());
        return resourceRepository.save(resource);
    }

    public Resource resourceReview(Resource resource) {
        if (resource.getResourceStatus().equals(Resource.ResourceStatus.PASS)) {
            User user = userRepository.findItemById(resource.getUserId());
            user.setIntegral(user.getIntegral() + 5);
            userRepository.save(user);
        }
        return resourceRepository.save(resource);
    }

    public List<ResourceImport> importResource(MultipartFile multipartFile) throws IOException {

        List<ResourceImport> exceptionList = new ArrayList<>();

        EasyExcel.read(multipartFile.getInputStream(), ResourceImport.class, new AnalysisEventListener<ResourceImport>() {
            @Override
            public void invoke(ResourceImport data, AnalysisContext context) {
                try {
                    String title = data.getTitle().trim();
                    String content = data.getContent().trim();
                    String link = data.getLink().trim();
                    String tag = data.getTag1().trim() + "," + data.getTag2().trim() + "," + data.getTag3().trim() + "," + data.getTag4().trim();
                    Integer clickNumber = data.getClickNumber();
                    String type = data.getType().trim();

                    //保存资源
                    Resource resource = new Resource();
                    resource.setManagerId(getCurrentManager().getId());
                    List<BasicType> all = basicTypeRepository.findAll(Specifications.<BasicType>and().build());
                    for (BasicType item : all) {
                        if (item.getName().equals(type)) {
                            resource.setBasicTypeId(item.getId());
                        }
                    }

                    resource.setTitle(title);
                    resource.setContent(content);
                    resource.setTag(tag);
                    resource.setClickNumber(clickNumber);
                    resource.setLink("http://49.234.218.87:9000/education/avatar.jpg");
                    resource.setPresenceStatus(1);
                    resource.setResourceStatus(Resource.ResourceStatus.PASS);
                    resource.setStatus(true);
                    resource.setDownNumber(clickNumber);
                    resource.setScore((int) (Math.random() * 100 + 1));
                    exceptionList.add(data);
                    resourceRepository.save(resource);
                } catch (Exception e) {
                    data.setReason(e.getMessage());
                    exceptionList.add(data);
                }

            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet().doRead();
        return exceptionList;
    }

    public Map<String, String> exportResourceData(GetResourceFrom getResourceFrom){
        return  exportData(getResourceFrom.getIds(),new GetExportCallback() {
            @Override
            public Page<Resource> getData(int page) {
                PageForm pageForm = new PageForm(page,100);
                return getResources(getResourceFrom,pageForm);
            }
            @Override
            public List<List<String>> head() {
                return new ExcelHeaderBuilder().add("标题","链接","类型","标签","点击数","评分").build();
            }

            @Override
            public List<List<Object>> data(List<Resource> resources) {

                return resources.stream().map(it-> new ExcelDataBuilder()
                        .add(it.getTitle())
                        .add(it.getLink())
                        .add(it.getBasicTypeId())
                        .add(it.getTag())
                        .add(it.getClickNumber())
                        .add(it.getScore())
                        .build()).collect(Collectors.toList());
            }
        });
    }

    public String getLike(String value) {
        return "%" + value + "%";
    }

    private Page<Resource> getResources(GetResourceFrom getResourceFrom, PageForm pageForm) {
        PredicateBuilder<Resource> spec = SpecificationUtil.<Resource>filter(getResourceFrom, pageForm);
        if (!StringUtils.isEmpty(getResourceFrom.getKeyword())) {
            Set<Integer> setByResource = resourceRepository.getResourceByKeyWord(getLike(getResourceFrom.getKeyword()));
            if (setByResource.size() == 0) {
                return new PageImpl<>(new ArrayList<>());
            }
            spec.in("id", setByResource.toArray());
        }
        return resourceRepository.findAll(spec.build(), pageForm.pageRequest());
    }



    public Map<String, String> exportData(String ids, GetExportCallback callback) {
        String fileName = System.currentTimeMillis() + ".xlsx";
        String path = DateUtils.getDatePath();
        File pathFile = new File(mediaConfig.getExportPath() + path + File.separator);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        String downloadPath = File.separator + "export" + File.separator + path + File.separator + fileName;
        String savePath = pathFile.getAbsolutePath() + "/" + fileName;
        ExcelWriter excelWriter = EasyExcel.write(savePath).head(callback.head()).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        int page = 0;
        if (!StringUtils.isEmpty(ids)) {
            List<Resource> resources = resourceRepository.findAll(Specifications.<Resource>and().in("id", StringUtil.toIntArray(ids)).build());
            excelWriter.write(callback.data(resources), writeSheet);
            excelWriter.finish();
            return new MapBuilder<String, String>().put("url", downloadPath).build();
        } else {

            Page<Resource> pageData = callback.getData(page);
            excelWriter.write(callback.data(pageData.getContent()), writeSheet);
            if (pageData.getTotalElements() > 3) {
                executors.execute(new ExportTask( downloadPath, excelWriter, pageData, callback, writeSheet));
                return new MapBuilder<String, String>().put("msg", "已进入后台导出,稍后请在消息中心查看").build();
            } else {
                while (!pageData.isLast()) {
                    page += 1;
                    pageData = callback.getData(page);
                    excelWriter.write(callback.data(pageData.getContent()), writeSheet);
                }
                excelWriter.finish();
                return new MapBuilder<String, String>().put("url", downloadPath).build();
            }
        }

    }

    public interface GetExportCallback {
        Page<Resource> getData(int page);

        List<List<String>> head();

        List<List<Object>> data(List<Resource> tickets);
    }

    class ExportTask implements Runnable {
        private final String downloadPath;
        //        private final List<Integer> manageable;
//        private Integer employeeId;
        private ExcelWriter excelWriter;
        private Page<Resource> pageData;
        private GetExportCallback callback;
        private WriteSheet writeSheet;
        private int page = 0;

        ExportTask(String downloadPath, ExcelWriter excelWriter, Page<Resource> pageData, GetExportCallback callback, WriteSheet writeSheet) {
            this.excelWriter = excelWriter;
//            this.employeeId = employeeId;
            this.pageData = pageData;
            this.callback = callback;
            this.writeSheet = writeSheet;
            this.downloadPath = downloadPath;
        }

        @Override
        public void run() {
            while (!pageData.isLast()) {
                page += 1;
                pageData = callback.getData(page);
                excelWriter.write(callback.data(pageData.getContent()), writeSheet);
            }
            excelWriter.finish();
//            sendEvent(ResourceMessage.builder().employeeId(employeeId).type(UserMessage.Type.EXPORT).content("导出已完成,<a target=\"_blank\" href=\"" + downloadPath + "\">点击此处下载</a>").build());
        }
    }

        public Resource wxResourceAdd(Resource resource) {
//        resource.setPresenceStatus(1);
            resource.setUserId(getCurrentUser().getId());
            resource.setResourceStatus(Resource.ResourceStatus.UNPROCESSED);
            sendEvent(DingMessageEvent.builder().content("\n用户:" + getCurrentUser().getNickName() + "添加了资源:\n" + resource.getTitle() + "\n请管理员尽快处理").build());
            return resourceRepository.save(resource);
        }


        public void resourceDelete(String ids) {
            resourceRepository.softDelete(StringUtil.toIntArray(ids));
        }

        public void resourceDelete(Integer id) {
            Resource item = resourceRepository.findItemById(id);
            item.setPresenceStatus(0);
            resourceRepository.save(item);
        }

}
