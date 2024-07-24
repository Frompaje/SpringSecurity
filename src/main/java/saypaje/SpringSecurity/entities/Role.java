package saypaje.SpringSecurity.entities;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    @Column(name="roleId")
    private UUID roleId;

    private String name;

    public enum Value{
        ADMIN(),
        BASIC();

        long roleId;

        void Values(long roleId){
            this.roleId = roleId;
        }

        public long getRoleId(){
            return roleId;
        }

    }
}
