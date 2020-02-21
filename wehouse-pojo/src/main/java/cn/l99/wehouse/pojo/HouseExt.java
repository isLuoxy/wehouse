package cn.l99.wehouse.pojo;

import cn.l99.wehouse.pojo.baseEnum.CommonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * 房屋信息扩展表
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "house",type = "house_ext",shards = 1, replicas = 0)
public class HouseExt implements Serializable {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String houseId;

    @Field(type = FieldType.Keyword)
    private CommonType television;

    @Field(type = FieldType.Keyword)
    private CommonType fridge;

    @Field(type = FieldType.Keyword)
    private CommonType washingMachine;

    @Field(type = FieldType.Keyword)
    private CommonType airConditioning;

    @Field(type = FieldType.Keyword)
    private CommonType heater;

    @Field(type = FieldType.Keyword)
    private CommonType bed;

    @Field(type = FieldType.Keyword)
    private CommonType broadband;

    @Field(type = FieldType.Keyword)
    private CommonType wardrobe;

    @Field(type = FieldType.Keyword)
    private String subwayLineId;

    @Field(type = FieldType.Keyword)
    private String subwayStationId;

    @Field(type = FieldType.Keyword)
    private String description;


}
