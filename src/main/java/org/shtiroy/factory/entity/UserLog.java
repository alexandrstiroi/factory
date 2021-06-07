package org.shtiroy.factory.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(schema = "working_data", name = "t_user_log")
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "log_date")
    private Timestamp logDate;

    @Column(name = "operation_log")
    private String operationLog;

    @Column(name = "params_log")
    private String paramsLog;

    public UserLog() {
    }

    public UserLog(Long id, User userId, Timestamp logDate, String operationLog, String paramsLog) {
        this.id = id;
        this.userId = userId;
        this.logDate = logDate;
        this.operationLog = operationLog;
        this.paramsLog = paramsLog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Timestamp getLogDate() {
        return logDate;
    }

    public void setLogDate(Timestamp logDate) {
        this.logDate = logDate;
    }

    public String getOperationLog() {
        return operationLog;
    }

    public void setOperationLog(String operationLog) {
        this.operationLog = operationLog;
    }

    public String getParamsLog() {
        return paramsLog;
    }

    public void setParamsLog(String paramsLog) {
        this.paramsLog = paramsLog;
    }

    @Override
    public String toString() {
        return "user = " + userId.toString() + " | "  + operationLog;
    }
}
