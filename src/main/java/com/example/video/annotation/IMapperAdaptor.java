package com.example.video.annotation;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface IMapperAdaptor<T> extends Mapper<T>, MySqlMapper<T> {
}
