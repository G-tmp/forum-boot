package com.gtmp.mapper;

import com.gtmp.POJO.Like;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {

    Integer insertOrUpdateLike(Like like);


}
