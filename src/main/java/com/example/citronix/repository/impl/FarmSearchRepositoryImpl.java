package com.example.citronix.repository.impl;

import com.example.citronix.model.Farm;
import com.example.citronix.repository.FarmSearchRepository;
import com.example.citronix.services.dto.SearchDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Repository
public class FarmSearchRepositoryImpl implements FarmSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SearchDTO> findByCriteria(SearchDTO searchDTO) {
        CriteriaBuilder cb  = entityManager.getCriteriaBuilder();
        CriteriaQuery<SearchDTO> query = cb.createQuery(SearchDTO.class);
        Root<Farm> farmRoot = query.from(Farm.class);


        query.select(cb.construct(SearchDTO.class,
            farmRoot.get("name"),
            farmRoot.get("location"),
            farmRoot.get("creationDate")
        ));

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(searchDTO.getName())) {
            predicates.add(cb.equal(farmRoot.get("name"), searchDTO.getName()));
        }
        if (StringUtils.hasText(searchDTO.getLocation())) {
            predicates.add(cb.like(cb.lower(farmRoot.get("location")),
                "%" + searchDTO.getLocation().toLowerCase() + "%"));
        }
        if (searchDTO.getCreationDate() != null) {
            predicates.add(cb.equal(farmRoot.get("creationDate"), searchDTO.getCreationDate()));
        }
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        return entityManager.createQuery(query).getResultList();
    }
}
