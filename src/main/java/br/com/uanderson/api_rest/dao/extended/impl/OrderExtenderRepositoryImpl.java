package br.com.uanderson.api_rest.dao.extended.impl;

import br.com.uanderson.api_rest.dao.extended.OrderExtenderRepository;
import br.com.uanderson.api_rest.model.dtos.OrderDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderExtenderRepositoryImpl implements OrderExtenderRepository {

    @PersistenceContext
    private final EntityManager entityManager;


    @Override
    @SuppressWarnings("unchecked")
    public List<OrderDTO> findByFilter(int filter, String orderNumber,
                                       LocalDateTime since, LocalDateTime until, String status,
                                       Long customerId, int page, int size) {
        String query = """ 
                SELECT 
                    order.id as id,
                    order.orderNumber as orderNumber,
                    order.orderDate as orderDate,
                    order.deliveryDate as deliveryDate,
                    order.status as status,
                    order.customer as customer                
                FROM 
                    Order order
                WHERE 
                    ((:filter = 1 and order.orderNumber = :orderNumber) OR 
                    (:filter = 2 and order.orderDate between :since and :until) OR 
                    (:filter = 3 and order.deliveryDate between :since and :until) OR 
                    (:filter = 4 and order.status = :status)) AND 
                    (order.customer.id = :customerId)
                """;

        TypedQuery<OrderDTO> typedQuery = entityManager.createQuery(query, OrderDTO.class)
                .unwrap(Query.class)
                .setTupleTransformer(OrderDTO::fromFields);

        typedQuery.setParameter("filter", filter);
        typedQuery.setParameter("orderNumber", orderNumber);
        typedQuery.setParameter("since", since);
        typedQuery.setParameter("until", until);
        typedQuery.setParameter("status", status);
        typedQuery.setParameter("customerId", customerId);

        typedQuery.setFirstResult(page * page);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int countFindByFilter(int filter, String orderNumber, LocalDateTime since, LocalDateTime until, String status, Long customerId) {
        String query = """ 
                SELECT 
                    count (order.id) as count             
                FROM 
                    Order order
                WHERE 
                    ((:filter = 1 and order.orderNumber = :orderNumber) OR 
                    (:filter = 2 and order.orderDate between :since and :until) OR 
                    (:filter = 3 and order.deliveryDate between :since and :until) OR 
                    (:filter = 4 and order.status = :status)) AND 
                    (order.customer.id = :customerId)
                """;

        TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class)
                .unwrap(Query.class);

        typedQuery.setParameter("filter", filter);
        typedQuery.setParameter("orderNumber", orderNumber);
        typedQuery.setParameter("since", since);
        typedQuery.setParameter("until", until);
        typedQuery.setParameter("status", status);
        typedQuery.setParameter("customerId", customerId);

        return typedQuery.getSingleResult().intValue();
    }


}//class
