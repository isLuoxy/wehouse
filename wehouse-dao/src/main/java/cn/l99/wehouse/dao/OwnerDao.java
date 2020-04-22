package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.Owner;
import cn.l99.wehouse.pojo.Owner;
import org.springframework.stereotype.Repository;

/**
 * 房东持久层
 */
@Repository
public interface OwnerDao {

    boolean insertOwner(Owner Owner);

    Owner selectOwnerByOwnerName(String ownerName);

    Owner selectOwnerByOwnerPhone(String ownerPhone);

    Owner selectOwnerByOwnerId(String ownerId);

    boolean updateOwner(Owner owner);
}
