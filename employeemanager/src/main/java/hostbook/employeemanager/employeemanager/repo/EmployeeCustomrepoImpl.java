package hostbook.employeemanager.employeemanager.repo;

import hostbook.employeemanager.employeemanager.entity.Employee;
import hostbook.employeemanager.employeemanager.entity.PaginationCriteria;
import hostbook.employeemanager.employeemanager.exception.EmpException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import java.util.List;

@Transactional
@Repository
public class EmployeeCustomrepoImpl implements EmployeeCustomRepo{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Employee findEmployeeByEmpcode(String empc,Integer id) {
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery cq=cb.createQuery(Employee.class);

        Root<Employee> employee=cq.from(Employee.class);


        Predicate employeeCodePredicate=cb.equal(employee.get("empcode"),empc);

        Predicate employeeIdPredicate=cb.notEqual(employee.get("id"),id);
        Predicate predicate = cb.and(employeeCodePredicate,employeeIdPredicate);
    cq.where(predicate);
        List<Employee> l=entityManager.createQuery(cq).getResultList();
        if(l.size()>0){
            return l.get(0);
        }

        return null ;
    }

    //pagination
    public List<Employee> findAll(PaginationCriteria paginationCriteria) throws EmpException{
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee>root = criteriaQuery.from(Employee.class);

        if(paginationCriteria.getSearchItem()!=null&&!paginationCriteria.getSearchItem().isEmpty()) {

            Predicate predicate=criteriaBuilder.like(root.get("name"),paginationCriteria.getSearchItem());

            criteriaQuery.where(predicate);

        }

        // Condition for sorting.
        Order order;
        if (paginationCriteria.getSortField() != null && !paginationCriteria.getSortField().isEmpty()) {
            if (paginationCriteria.getSortType().equals("desc") ) {
                order = criteriaBuilder.desc(root.get(paginationCriteria.getSortField()));
            } else {
                order = criteriaBuilder.asc(root.get(paginationCriteria.getSortField()));
            }
        } else {
            order = criteriaBuilder.desc(root.get("empcode"));
        }
        criteriaQuery.orderBy(order);




        List<Employee> result =
                entityManager
                        .createQuery(criteriaQuery)
                        .setMaxResults(paginationCriteria.getLimit())
                        .setFirstResult((paginationCriteria.getPage()-1)* paginationCriteria.getLimit())
                        .getResultList();

        return result;
    }

}



