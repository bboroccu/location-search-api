package com.kakaobank.location.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter
@EqualsAndHashCode
@ToString(exclude = {"passwd"})
@DynamicUpdate
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "user_id")
    @NotNull
    private String userId;

    @Column(name = "passwd")
    @NotNull
    private String passwd;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    @NotNull
    protected UserRole userRole;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;
}
