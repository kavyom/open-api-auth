package com.test.api.mapper;

import com.test.api.model.OpenApiConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by pzh on 2022/9/7.
 */
@Mapper
@Repository
public interface OpenApiConfigMapper {

    OpenApiConfig getByAppId(String appId);
}
