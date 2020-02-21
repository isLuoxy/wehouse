package cn.l99.wehouse.elasticsearch;

import cn.l99.wehouse.pojo.House;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ESHouseRepository extends ElasticsearchRepository<House, String> {

    List<House> findByNameLike(String houseName);
}
