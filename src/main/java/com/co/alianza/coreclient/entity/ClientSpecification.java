package com.co.alianza.coreclient.entity;

import com.co.alianza.coreclient.util.DateUtil;
import com.co.alianza.coreclient.util.Util;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientSpecification implements Specification<Client> {

    private final Map<String, String> params;

    public ClientSpecification(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        params.forEach((key, value) -> {
            if (!Util.isNullOrEmptyObject(value)) {
                if (key.equals("creationDateFrom")) {
                    LocalDate date = DateUtil.formatStringToLocaleDate(value);
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), date));
                } else if (key.equals("creationDateTo")) {
                    LocalDate date = DateUtil.formatStringToLocaleDate(value);
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"), date));
                } else {
                    predicates.add(criteriaBuilder.like(root.get(key), "%" + value + "%"));
                }
            }
        });

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
