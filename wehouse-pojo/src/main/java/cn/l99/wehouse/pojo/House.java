package cn.l99.wehouse.pojo;

import cn.l99.wehouse.pojo.baseEnum.*;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * 房屋基础类
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "house", type = "house", shards = 1, replicas = 0)
public class House implements Serializable {

    private static final long serialVersionUID = 3890824795794279398L;

    @Id
    private String id;

    /**
     * text：存储数据时候，会自动分词，并生成索引
     * keyword：存储数据时候，不会分词建立索引
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Keyword)
    private RentalType rentalType;

    @Field(type = FieldType.Keyword)
    private Orientation orientation;

    @Field(type = FieldType.Double)
    private Double area;

    @Field(type = FieldType.Keyword)
    private String floor;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Date)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date checkInTime;

    @Field(type = FieldType.Keyword)
    private CommonType elevator;

    @Field(type = FieldType.Keyword)
    private String houseType;

    @Field(type = FieldType.Keyword)
    private String rentalRoom;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String provinceCnName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String cityCnName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String regionCnName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String streetCnName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String village;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String address;

    @Field(type = FieldType.Keyword)
    private HouseStatus houseStatus;

    @Field(type = FieldType.Integer)
    private Integer distanceToSubway;

    @Field(type = FieldType.Integer)
    private Integer ownerId;

    @Field(type = FieldType.Keyword)
    private String pictureUrl;

    // 该属性用于 es,不用于 mysql
    @Field(type = FieldType.Nested, includeInParent = true)
    private HouseExt houseExt;

    // 经度
    @Field(type = FieldType.Keyword)
    private String longitude;

    // 维度
    @Field(type = FieldType.Keyword)
    private String latitude;

    @Field(type = FieldType.Date)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
