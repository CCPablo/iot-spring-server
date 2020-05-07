package internal.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "users")
public class ApplicationUser {

    private String userId;

    private String name;

    private String password;

    private Long timeStamp;

    @Override
    public String toString() {
        return "UserEntity{" +
                ", userName='" + name + '\'' +
                ", passWord='" + password + '\'' +
                '}';
    }
}
