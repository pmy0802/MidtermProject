package kr.ac.kopo.midtermproject.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity {
    @Id
    private String email;

    private String password;
    private String name;
    private boolean fromSocial;

    @ElementCollection
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();
    public void addMemberRole(MemberRole MemberRole){
        roleSet.add(MemberRole);
    }

}
