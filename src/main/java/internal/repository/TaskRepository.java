package internal.repository;

import internal.model.node.Node;
import internal.repository.model.Task;

import java.util.List;

public interface TaskRepository {

    List<Task> findAll();

    void deleteAll();

    void saveNode(Node node);

    Node findNodeById(Integer nodeId);

    long updateNode(Node node);

    void deleteNodeById(Integer nodeId);
}
