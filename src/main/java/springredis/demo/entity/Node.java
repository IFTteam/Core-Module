package springredis.demo.entity;

import lombok.Data;
import springredis.demo.entity.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
public class Node extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long frontEndId;
    private String name;
    private String type;

    private Integer headOrTail;
    private String status;

    private List<Long> nexts;
    private List<Long> lasts;




    public Node() {
        super();
    }

    public Node(String name, String type, String status, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(createdAt, createdBy,updatedAt,updatedBy);
        this.name = name;
        this.type = type;
        this.status = status;
    }
}
