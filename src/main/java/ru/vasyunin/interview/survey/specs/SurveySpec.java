package ru.vasyunin.interview.survey.specs;

import org.springframework.data.jpa.domain.Specification;
import ru.vasyunin.interview.survey.entity.Survey;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.Date;

public class SurveySpec implements Specification<Survey> {
    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Survey> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        String operation = criteria.getOperation();
        Class<?> type = root.get(criteria.getKey()).getJavaType();

        if (criteria.getValue() == null) return null;

        if (criteria.getOperation().equalsIgnoreCase(">") && type == LocalDate.class) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.<LocalDate>get(criteria.getKey()), (LocalDate)criteria.getValue());
        } else if (criteria.getOperation().equalsIgnoreCase("<") && type == LocalDate.class) {
            return criteriaBuilder.lessThanOrEqualTo(
                    root.<LocalDate>get(criteria.getKey()), (LocalDate)criteria.getValue());
        } else if (operation.equalsIgnoreCase(":")){
            if (type == String.class){
                return criteriaBuilder.like(
                        root.get(criteria.getKey()),
                        "%" + criteria.getValue() + "%"
                );
            } else {
                return criteriaBuilder.equal(
                        root.get(criteria.getKey()),
                        criteria.getValue()
                );
            }
        }
        return null;
    }

    public SurveySpec(SearchCriteria criteria) {
        this.criteria = criteria;
    }
}

