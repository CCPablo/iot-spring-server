package internal.repository.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
/*
@CompoundIndexes({
        @CompoundIndex(name = "email_age", def = "{'name' : 1, 'userId': 1}")
})
*/
@Document(collection = "users")
public class ApplicationUser {

    private String userId;

    private String name;

    private String password;

    private Long timestamp;

    @Override
    public String toString() {
        return "UserEntity{" +
                ", userName='" + name + '\'' +
                ", passWord='" + password + '\'' +
                '}';
    }
}
