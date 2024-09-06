package com.stock.service.specification;


import com.stock.service.model.Order;
import com.stock.service.model.request.OrderQueryRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;


import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class OrderSpecification {

    public static Specification<Order> init(OrderQueryRequest orderQueryRequest) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            filterByCustomerUsername(orderQueryRequest, root, criteriaBuilder, predicates);
            filterByCustomerId(orderQueryRequest, root, criteriaBuilder, predicates);
            filterByAssetType(orderQueryRequest, root, criteriaBuilder, predicates);
            filterBySide(orderQueryRequest, root, criteriaBuilder, predicates);
            filterByCreateDate(orderQueryRequest, root, criteriaBuilder, predicates);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

    }

    private static void filterByCustomerUsername(OrderQueryRequest orderQueryRequest, Root<Order> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (orderQueryRequest.getUsername() != null) {
            predicates.add(criteriaBuilder.equal(root.get("customer").get("username"), orderQueryRequest.getUsername()));
        }
    }

    private static void filterByCustomerId(OrderQueryRequest orderQueryRequest, Root<Order> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (orderQueryRequest.getCustomerId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("customer").get("id"), orderQueryRequest.getCustomerId()));
        }
    }

    private static void filterByAssetType(OrderQueryRequest orderQueryRequest, Root<Order> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (orderQueryRequest.getAssetType() != null) {
            predicates.add(criteriaBuilder.equal(root.get("asset").get("assetType"), orderQueryRequest.getAssetType()));
        }
    }

    private static void filterBySide(OrderQueryRequest orderQueryRequest, Root<Order> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (orderQueryRequest.getSide() != null) {
            predicates.add(criteriaBuilder.equal(root.get("side"), orderQueryRequest.getSide()));
        }
    }

    private static void filterByCreateDate(OrderQueryRequest orderQueryRequest, Root<Order> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (orderQueryRequest.getCreatedDate() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), orderQueryRequest.getCreatedDate()));
        }
    }

}
