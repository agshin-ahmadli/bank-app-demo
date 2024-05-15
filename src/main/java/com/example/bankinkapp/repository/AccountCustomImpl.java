package com.example.bankinkapp.repository;

import com.example.bankinkapp.models.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.math.BigDecimal;
import java.util.List;

public class AccountCustomImpl implements AccountCustomRepo{

    private final EntityManager entityManager;

    public AccountCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Account> findByAccountHolderNameAndBalance(String name, Integer balance) {

      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Account.class);
      Root<Account> accountRoot = criteriaQuery.from(Account.class);
      Predicate predicate = criteriaBuilder.like(accountRoot.get("accountHolderName"),"%" + name + "%");
      Predicate predicate1 = criteriaBuilder.equal(accountRoot.get("balance"),balance);

      Predicate orPredicate =criteriaBuilder.or(predicate,predicate1);

      criteriaQuery.where(orPredicate);

      TypedQuery<Account>typedQuery = entityManager.createQuery(criteriaQuery);
      return typedQuery.getResultList();
    }
}
