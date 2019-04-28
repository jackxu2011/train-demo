package me.xuling.train.mybatis;

import com.github.pagehelper.PageInfo;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import me.xuling.train.mybatis.mapper.CoffeeCustomMapper;
import me.xuling.train.mybatis.mapper.CoffeeMapper;
import me.xuling.train.mybatis.model.Coffee;
import me.xuling.train.mybatis.model.CoffeeExample;
import org.apache.ibatis.session.RowBounds;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @since 2019-04-26
 */
@SpringBootApplication
@RestController
@MapperScan("me.xuling.train.mybatis.mapper")
public class MybatisApplication {

    @Resource
    CoffeeCustomMapper coffeeCustomMapper;

    @Resource
    CoffeeMapper coffeeMapper;

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }

    @RequestMapping("coffee/page/1")
    public List<Coffee> getCoffees() {
        return coffeeCustomMapper.findAllWithParam(1,2);
    }

    @RequestMapping("coffee/page/2")
    public List<Coffee> getCoffees2() {
        return coffeeCustomMapper.findAllWithRowBounds(new RowBounds(2,2));
    }

    @RequestMapping("coffee/page/info")
    public PageInfo getCoffee3() {
        List<Coffee> list = coffeeCustomMapper.findAllWithParam(2, 3);
        PageInfo page = new PageInfo(list);
        return page;
    }

    @RequestMapping("coffee/generate")
    public Coffee playGenerateInsertSelect() {
        Coffee espresso = new Coffee()
            .withName("espresso")
            .withPrice(Money.of(CurrencyUnit.of("CNY"), 20.0))
            .withCreateTime(new Date())
            .withUpdateTime(new Date());
        coffeeMapper.insert(espresso);

        // Money.of(CurrencyUnit.of("CNY"), 20.0)
        Coffee latte = new Coffee()
            .withName("latte")
            .withPrice(Money.of(CurrencyUnit.of("CNY"), 30.0))
            .withCreateTime(new Date())
            .withUpdateTime(new Date());
        coffeeMapper.insert(latte);

        return coffeeMapper.selectByPrimaryKey(latte.getId());
    }

    @RequestMapping("coffee/example")
    public List<Coffee> playExample() {
        CoffeeExample example = new CoffeeExample();
        example.createCriteria().andNameEqualTo("latte");

        return coffeeMapper.selectByExample(example);
    }


}
