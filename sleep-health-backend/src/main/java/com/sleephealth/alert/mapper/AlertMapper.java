package com.sleephealth.alert.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleephealth.alert.entity.Alert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlertMapper extends BaseMapper<Alert> {
}
