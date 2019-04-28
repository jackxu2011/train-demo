package me.xuling.train.mybatis.mapper;

import java.util.List;
import me.xuling.train.mybatis.model.Coffee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

/**
 * @since 2019-04-26
 */
@Mapper
public interface CoffeeCustomMapper {
    @Insert("insert into t_coffee (name, price, create_time, update_time)"
        + "values (#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true)
    int save(Coffee coffee);

    @Select("select * from t_coffee where id = #{id}")
    @Results({
        @Result(id = true, column = "id", property = "id"),
        @Result(column = "create_time", property = "createTime"),
        // map-underscore-to-camel-case = true 可以实现一样的效果
        // @Result(column = "update_time", property = "updateTime"),
    })
    Coffee findById(@Param("id") Long id);

    @Select("select * from t_coffee order by id")
    List<Coffee> findAllWithRowBounds(RowBounds rowBounds);

    @Select("select * from t_coffee order by id")
    List<Coffee> findAllWithParam(@Param("pageNum") int pageNum,
        @Param("pageSize") int pageSize);
}
