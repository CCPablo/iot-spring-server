package internal.repository;

import com.mongodb.client.result.UpdateResult;
import internal.model.node.Node;

import java.util.List;

public interface NodeRepository {

    List<Node> findAll();

    void deleteAll();

    void saveNode(Node node);

    Node findNodeById(Integer nodeId);

    UpdateResult updateNode(Node node);

    void deleteNodeById(Integer nodeId);
}
