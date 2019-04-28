package me.xuling.train.mybatis.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;

/**
 * @since 2019-04-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoffeeC {
    private Long id;
    private String name;
    private Money price;
    private Date createTime;
    private Date updateTime;
}
