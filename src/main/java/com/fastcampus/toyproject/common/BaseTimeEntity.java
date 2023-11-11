package com.fastcampus.toyproject.common;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Embeddable
public class BaseTimeEntity {

    @CreatedDate
    @Comment("생성일시")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Comment("수정일시")
    private LocalDateTime updatedAt;

    @Column(insertable = false)
    @Comment("삭제일시")
    private LocalDateTime deletedAt;

    public void delete(LocalDateTime currentTime) {
        if (deletedAt == null) {
            deletedAt = currentTime;
        }
    }


}
