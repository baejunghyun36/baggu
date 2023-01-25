package com.project.baggu.repository;

import com.project.baggu.domain.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

  @Query(value = "select i from Item i where i.dong = :dong and i.isValid = true order by i.createdAt desc")
  List<Item> itemListOrderByNeighbor(@Param(value = "dong") String dong);

  @Query(value = "select i from Item i where i.user.userIdx = :userIdx order by i.createdAt desc")
  List<Item> userItemList(@Param(value = "userIdx") Long userIdx);

  @Modifying
  @Query("update Item i set i.isValid = false where i.itemIdx = :itemIdx")
  void deleteItem(@Param("itemIdx") Long itemIdx);

  @Modifying
  @Query("update Item i set i.title = :title, i.category = :category, i.content = :content where i.itemIdx = :itemIdx")
  void updateItem(
      @Param("itemIdx")Long itemIdx,
      @Param("title") String title,
      @Param("category") int category,
      @Param("content") String content);

  @Query("select i from Item i where i.title like %:itemName%")
  List<Item> itemListByItemName(@Param("itemName") String itemName);

  @Query("select i from Item i, ItemKeep ik where ik.user.userIdx = :userIdx and ik.item = i")
  List<Item> userKeepItemList(@Param("userIdx") Long userIdx);

}