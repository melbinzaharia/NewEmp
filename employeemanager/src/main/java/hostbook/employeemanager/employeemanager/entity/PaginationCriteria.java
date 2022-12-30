package hostbook.employeemanager.employeemanager.entity;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class PaginationCriteria implements Serializable {

    private int page;

    private int limit;



    private String sortType;

    private String sortField;

    private String searchItem;



}