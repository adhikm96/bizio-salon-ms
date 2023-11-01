package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Item;
import com.thebizio.biziosalonms.enums.ItemType;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.projection.item.ItemDetailPrj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepo extends JpaRepository<Item, UUID> {
    boolean existsByCode(String code);

    @Query("SELECT i.id as id, i.name as name, i.code as code, i.itemType as itemType," +
            "i.price as price, i.cost as cost, i.description as description, i.brand as brand," +
            "i.status as status FROM Item i WHERE i.code = :code ORDER BY i.modified DESC")
    List<ItemDetailPrj> findByCode(String code);

    @Query("SELECT i.id as id, i.name as name, i.code as code, i.itemType as itemType," +
            "i.price as price, i.cost as cost, i.description as description, i.brand as brand," +
            "i.status as status FROM Item i WHERE i.name = :name ORDER BY i.modified DESC")
    List<ItemDetailPrj> findByName(String name);

    @Query("SELECT i.id as id, i.name as name, i.code as code, i.itemType as itemType," +
            "i.price as price, i.cost as cost, i.description as description, i.brand as brand," +
            "i.status as status FROM Item i WHERE i.itemType = :itemType AND i.status = :status ORDER BY i.modified DESC")
    List<ItemDetailPrj> findAllByItemTypeAndStatus(ItemType itemType, StatusEnum status);

    @Query("SELECT i.id as id, i.name as name, i.code as code, i.itemType as itemType," +
            "i.price as price, i.cost as cost, i.description as description, i.brand as brand," +
            "i.status as status FROM Item i WHERE i.itemType = :itemType ORDER BY i.modified DESC")
    List<ItemDetailPrj> findByItemType(ItemType itemType);


    @Query("SELECT i.id as id, i.name as name, i.code as code, i.itemType as itemType," +
            "i.price as price, i.cost as cost, i.description as description, i.brand as brand," +
            "i.status as status FROM Item i WHERE i.status = :status ORDER BY i.modified DESC")
    List<ItemDetailPrj> findByStatus(StatusEnum status);

    @Query("SELECT i.id as id, i.name as name, i.code as code, i.itemType as itemType," +
            "i.price as price, i.cost as cost, i.description as description, i.brand as brand," +
            "i.status as status FROM Item i ORDER BY i.modified DESC")
    List<ItemDetailPrj> findAllItem();

    @Query("SELECT i.id as id, i.name as name, i.code as code, i.itemType as itemType," +
            "i.price as price, i.cost as cost, i.description as description, i.brand as brand," +
            "i.status as status FROM Item i WHERE i.id = :itemId")
    Optional<ItemDetailPrj> findItemById(UUID itemId);
}
