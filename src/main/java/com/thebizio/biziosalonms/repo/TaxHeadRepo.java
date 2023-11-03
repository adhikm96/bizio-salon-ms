package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.TaxHead;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.projection.tax_head.TaxHeadDetailPrj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.nio.file.OpenOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaxHeadRepo extends JpaRepository<TaxHead, UUID> {

    boolean existsByCode(String code);

    @Query("SELECT th.id as id, th.code as code, th.name as name, th.chargeOn as chargeOn, th.status as status " +
            "FROM TaxHead th WHERE th.code = :code ORDER BY th.modified DESC")
    List<TaxHeadDetailPrj> findAllByCode(String code);

    @Query("SELECT th.id as id, th.code as code, th.name as name, th.chargeOn as chargeOn, th.status as status " +
            "FROM TaxHead th WHERE th.name = :name AND th.status = :status ORDER BY th.modified DESC")
    List<TaxHeadDetailPrj> findAllByNameAndStatus(String name, StatusEnum status);

    @Query("SELECT th.id as id, th.code as code, th.name as name, th.chargeOn as chargeOn, th.status as status " +
            "FROM TaxHead th WHERE th.name = :name ORDER BY th.modified DESC")
    List<TaxHeadDetailPrj> findAllByName(String name);

    @Query("SELECT th.id as id, th.code as code, th.name as name, th.chargeOn as chargeOn, th.status as status " +
            "FROM TaxHead th WHERE th.status = :status ORDER BY th.modified DESC")
    List<TaxHeadDetailPrj> findAllByStatus(StatusEnum status);

    @Query("SELECT th.id as id, th.code as code, th.name as name, th.chargeOn as chargeOn, th.status as status " +
            "FROM TaxHead th ORDER BY th.modified DESC")
    List<TaxHeadDetailPrj> findAllTaxHead();

    @Query("SELECT th.id as id, th.code as code, th.name as name, th.chargeOn as chargeOn, th.status as status " +
            "FROM TaxHead th WHERE th.id = :taxId")
    Optional<TaxHeadDetailPrj> findTaxHeadById(UUID taxId);
}
