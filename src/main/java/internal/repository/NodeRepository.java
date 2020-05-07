package internal.repository;

import internal.model.node.Node;

import java.util.List;

public interface NodeRepository {

    List<Node> findAll();

    void deleteAll();

    void saveNode(Node node);

    Node findNodeById(Integer nodeId);

    long updateNode(Node node);

    void deleteNodeById(Integer nodeId);
}
